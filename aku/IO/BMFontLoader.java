package aku.IO;

import akgl.Units.Buffers.Mesh;
import akgl.Units.Buffers.Texture.*;
import akgl.Units.Geometry.HardCodedGeometry.Quad2DGenerator;
import akgl.Units.Geometry.Vectors.*;
import aku.*;
import aku.IO.TextureLoader;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.logging.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * @author Robert Kollar
 */
public class BMFontLoader {

    /**
     * The line cache size, this is how many lines we can render before starting to
     * regenerate lists
     */
    private static final int DISPLAY_LIST_CACHE_SIZE = 200;

    /**
     * The highest character that BMFontLoader will support.
     */
    private static final int MAX_CHAR = 255;

    /**
     * True if this font should use display list caching
     */
    private boolean displayListCaching = false;

    /**
     * The image containing the bitmap font
     */
    private Texture fontTexture;
    /**
     * The characters building up the font
     */
    private CharDef[] chars;
    /**
     * The height of a line
     */
    private int lineHeight;
    /**
     * The first display list ID
     */
    private int baseDisplayListID = -1;
    /**
     * The eldest display list ID
     */
    private int eldestDisplayListID;
    /**
     * The eldest display list
     */
    private DisplayList eldestDisplayList;

    /**
     * The display list cache for rendered lines
     */
    private final LinkedHashMap displayLists = new LinkedHashMap(DISPLAY_LIST_CACHE_SIZE, 1, true) {
        protected boolean removeEldestEntry(Entry eldest) {
            eldestDisplayList = (DisplayList) eldest.getValue();
            eldestDisplayListID = eldestDisplayList.id;

            return false;
        }
    };

    private static HashMap<String, BMFontLoader> fonts = new HashMap<String, BMFontLoader>();

    public static BMFontLoader getBMFont(String fontName) {
        if (!fonts.containsKey(fontName)) {
            fonts.put(fontName, new BMFontLoader(fontName));
        }
        return fonts.get(fontName);
    }

    /**
     * Create a new font based on a font definition from AngelCode's tool and the font
     * image generated from the tool.
     *
     * @param fntFile The location of the font defnition file=
     */
    private BMFontLoader(String fntFile) {
        URL url = null;
        InputStream in = null;
        try {
            url = new URL(AncientKemetRegistry.getDataHost() + "fonts/" + fntFile + ".fnt");
            in = url.openStream();
        } catch (MalformedURLException ex) {
            Logger.getLogger(BMFontLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BMFontLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        fontTexture = TextureLoader.getTexture("fonts/" + fntFile + "_0.png");

        parseFnt(in);
    }

    /**
     * Parse the font definition file
     *
     * @param fntFile The stream from which the font file can be read
     */
    private void parseFnt(InputStream fntFile) {
        if (displayListCaching) {
            baseDisplayListID = glGenLists(DISPLAY_LIST_CACHE_SIZE);
            if (baseDisplayListID == 0) {
                displayListCaching = false;
            }
        }

        try {
            // now parse the font file
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    fntFile));
            String info = in.readLine();
            String common = in.readLine();
            String page = in.readLine();

            Map kerning = new HashMap(64);
            List charDefs = new ArrayList(MAX_CHAR);
            int maxChar = 0;
            boolean done = false;
            while (!done) {
                String line = in.readLine();
                if (line == null) {
                    done = true;
                } else {
                    if (line.startsWith("chars c")) {
                        // ignore
                    } else if (line.startsWith("char")) {
                        CharDef def = parseChar(line);
                        if (def != null) {
                            maxChar = Math.max(maxChar, def.id);
                            charDefs.add(def);
                        }
                    }
                    if (line.startsWith("kernings c")) {
                        // ignore
                    } else if (line.startsWith("kerning")) {
                        StringTokenizer tokens = new StringTokenizer(line, " =");
                        tokens.nextToken(); // kerning
                        tokens.nextToken(); // first
                        short first = Short.parseShort(tokens.nextToken()); // first value
                        tokens.nextToken(); // second
                        int second = Integer.parseInt(tokens.nextToken()); // second value
                        tokens.nextToken(); // offset
                        int offset = Integer.parseInt(tokens.nextToken()); // offset value
                        List values = (List) kerning.get(new Short(first));
                        if (values == null) {
                            values = new ArrayList();
                            kerning.put(new Short(first), values);
                        }
                        // Pack the character and kerning offset into a short.
                        values.add(new Short((short) ((offset << 8) | second)));
                    }
                }
            }

            chars = new CharDef[maxChar + 1];
            for (Iterator iter = charDefs.iterator(); iter.hasNext();) {
                CharDef def = (CharDef) iter.next();
                chars[def.id] = def;
            }

            // Turn each list of kerning values into a short[] and set on the chardef.
            for (Iterator iter = kerning.entrySet().iterator(); iter.hasNext();) {
                Entry entry = (Entry) iter.next();
                short first = ((Short) entry.getKey()).shortValue();
                List valueList = (List) entry.getValue();
                short[] valueArray = new short[valueList.size()];
                int i = 0;
                for (Iterator valueIter = valueList.iterator(); valueIter.hasNext(); i++) {
                    valueArray[i] = ((Short) valueIter.next()).shortValue();
                }
                chars[first].kerning = valueArray;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parse a single character line from the definition
     *
     * @param line The line to be parsed
     * @return The character definition from the line
     */
    private CharDef parseChar(String line) {
        CharDef def = new CharDef();
        StringTokenizer tokens = new StringTokenizer(line, " =");

        tokens.nextToken(); // char
        tokens.nextToken(); // id
        def.id = Short.parseShort(tokens.nextToken()); // id value
        if (def.id < 0) {
            return null;
        }
        if (def.id > MAX_CHAR) {
            throw new Error("Invalid character '" + def.id
                    + "': BMFont does not support characters above " + MAX_CHAR);
        }

        tokens.nextToken(); // x
        def.x = Short.parseShort(tokens.nextToken()); // x value
        tokens.nextToken(); // y
        def.y = Short.parseShort(tokens.nextToken()); // y value
        tokens.nextToken(); // width
        def.width = Short.parseShort(tokens.nextToken()); // width value
        tokens.nextToken(); // height
        def.height = Short.parseShort(tokens.nextToken()); // height value
        tokens.nextToken(); // x offset
        def.xoffset = Short.parseShort(tokens.nextToken()); // xoffset value
        tokens.nextToken(); // y offset
        def.yoffset = Short.parseShort(tokens.nextToken()); // yoffset value
        tokens.nextToken(); // xadvance
        def.xadvance = Short.parseShort(tokens.nextToken()); // xadvance

        def.init();

        if (def.id != ' ') {
            lineHeight = Math.max(def.height + def.yoffset, lineHeight);
        }

        return def;
    }

    public void drawString(float x, float y, String text) {
        if (text != null) {
            drawString(x, y, text, 0, text.length() - 1);
        }
    }

    public void drawString(float x, float y, String text,
            int startIndex, int endIndex) {
        fontTexture.bind();
        glPushMatrix();
        if (displayListCaching && startIndex == 0 && endIndex == text.length() - 1) {
            DisplayList displayList = (DisplayList) displayLists.get(text);
            if (displayList != null) {
                glCallList(displayList.id);
            } else {
                // Compile a new display list.
                displayList = new DisplayList();
                displayList.text = text;
                int displayListCount = displayLists.size();
                if (displayListCount < DISPLAY_LIST_CACHE_SIZE) {
                    displayList.id = baseDisplayListID + displayListCount;
                } else {
                    displayList.id = eldestDisplayListID;
                    displayLists.remove(eldestDisplayList.text);
                }

                displayLists.put(text, displayList);

                glNewList(displayList.id, GL_COMPILE_AND_EXECUTE);
                render(text, startIndex, endIndex);
                glEndList();
            }
        } else {
            render(text, startIndex, endIndex);
        }
        glPopMatrix();
    }

    /**
     * Render based on immediate rendering
     *
     * @param text  The text to be rendered
     * @param start The index of the first character in the string to render
     * @param end   The index of the last character in the string to render
     */
    private void render(String text, int start, int end) {

        int x = 0, y = 0;
        CharDef lastCharDef = null;
        char[] data = text.toCharArray();
        for (int i = 0; i < data.length; i++) {
            int id = data[i];
            if (id == '\n') {
                x = 0;
                y += getLineHeight();
                continue;
            }
            if (id >= chars.length) {
                continue;
            }
            CharDef charDef = chars[id];
            if (charDef == null) {
                continue;
            }

            if (lastCharDef != null) {
                x += lastCharDef.getKerning(id);
            }
            lastCharDef = charDef;

            if ((i >= start) && (i <= end)) {
                charDef.draw(x, -y);
            }

            x += charDef.xadvance;
        }
    }

    /**
     * Returns the distance from the y drawing location to the top most pixel of the
     * specified text.
     *
     * @param text The text that is to be tested
     * @return The yoffset from the y draw location at which text will start
     */
    public int getYOffset(String text) {
        DisplayList displayList = null;
        if (displayListCaching) {
            displayList = (DisplayList) displayLists.get(text);
            if (displayList != null && displayList.yOffset != null) {
                return displayList.yOffset.intValue();
            }
        }

        int stopIndex = text.indexOf('\n');
        if (stopIndex == -1) {
            stopIndex = text.length();
        }

        int minYOffset = 10000;
        for (int i = 0; i < stopIndex; i++) {
            int id = text.charAt(i);
            CharDef charDef = chars[id];
            if (charDef == null) {
                continue;
            }
            minYOffset = Math.min(charDef.yoffset, minYOffset);
        }

        if (displayList != null) {
            displayList.yOffset = new Short((short) minYOffset);
        }

        return minYOffset;
    }

    public int getHeight(String text) {
        DisplayList displayList = null;
        if (displayListCaching) {
            displayList = (DisplayList) displayLists.get(text);
            if (displayList != null && displayList.height != null) {
                return displayList.height.intValue();
            }
        }

        int lines = 0;
        int maxHeight = 0;
        for (int i = 0; i < text.length(); i++) {
            int id = text.charAt(i);
            if (id == '\n') {
                lines++;
                maxHeight = 0;
                continue;
            }
            // ignore space, it doesn't contribute to height
            if (id == ' ') {
                continue;
            }
            CharDef charDef = chars[id];
            if (charDef == null) {
                continue;
            }

            maxHeight = Math.max(charDef.height + charDef.yoffset,
                    maxHeight);
        }

        maxHeight += lines * getLineHeight();

        if (displayList != null) {
            displayList.height = new Short((short) maxHeight);
        }

        return maxHeight;
    }

    /**
     * @see org.newdawn.slick.Font#getWidth(java.lang.String)
     */
    public int getWidth(String text) {
        DisplayList displayList = null;
        if (displayListCaching) {
            displayList = (DisplayList) displayLists.get(text);
            if (displayList != null && displayList.width != null) {
                return displayList.width.intValue();
            }
        }

        int maxWidth = 0;
        int width = 0;
        CharDef lastCharDef = null;
        for (int i = 0, n = text.length(); i < n; i++) {
            int id = text.charAt(i);
            if (id == '\n') {
                width = 0;
                continue;
            }
            if (id >= chars.length) {
                continue;
            }
            CharDef charDef = chars[id];
            if (charDef == null) {
                continue;
            }

            if (lastCharDef != null) {
                width += lastCharDef.getKerning(id);
            }
            lastCharDef = charDef;

            if (i < n - 1) {
                width += charDef.xadvance;
            } else {
                width += charDef.width;
            }
            maxWidth = Math.max(maxWidth, width);
        }

        if (displayList != null) {
            displayList.width = new Short((short) maxWidth);
        }

        return maxWidth;
    }

    /**
     * The definition of a single character as defined in the AngelCode file format
     *
     * @author kevin
     */
    private class CharDef {

        /**
         * The id of the character
         */
        public short id;
        /**
         * The x location on the sprite sheet
         */
        public short x;
        /**
         * The y location on the sprite sheet
         */
        public short y;
        /**
         * The width of the character image
         */
        public short width;
        /**
         * The height of the character image
         */
        public short height;
        /**
         * The amount the x position should be offset when drawing the image
         */
        public short xoffset;
        /**
         * The amount the y position should be offset when drawing the image
         */
        public short yoffset;

        /**
         * The amount to move the current position after drawing the character
         */
        public short xadvance;
        /**
         * The image containing the character
         */
        public Mesh mesh;
        /**
         * The display list index for this character
         */
        public short dlIndex;
        /**
         * The kerning info for this character
         */
        public short[] kerning;

        /**
         * @see java.lang.Object#toString()
         */
        public String toString() {
            return "[CharDef id=" + id + " x=" + x + " y=" + y + " yoff=" + yoffset + "]";
        }

        /**
         * Draw this character embedded in a image draw
         *
         * @param x The x position at which to draw the text
         * @param y The y position at which to draw the text
         */
        public void draw(float x, float y) {
            glPushMatrix();
            glTranslatef(x + xoffset, y + yoffset, 0);
            mesh.render();
            glPopMatrix();
        }

        /**
         * Get the kerning offset between this character and the specified character.
         *
         * @param otherCodePoint The other code point
         * @return the kerning offset
         */
        public int getKerning(int otherCodePoint) {
            if (kerning == null) {
                return 0;
            }
            int low = 0;
            int high = kerning.length - 1;
            while (low <= high) {
                int midIndex = (low + high) >>> 1;
                int value = kerning[midIndex];
                int foundCodePoint = value & 0xff;
                if (foundCodePoint < otherCodePoint) {
                    low = midIndex + 1;
                } else if (foundCodePoint > otherCodePoint) {
                    high = midIndex - 1;
                } else {
                    return value >> 8;
                }
            }
            return 0;
        }

        private void init() {
            mesh = Quad2DGenerator.generateQuadForSubTexture(fontTexture, x, y, width, height);
            mesh.uploadDataToGPU();
        }
    }

    public int getLineHeight() {
        return lineHeight;
    }

    /**
     * A descriptor for a single display list<br>
     */
    static private class DisplayList {

        /**
         * The if of the distance list
         */
        int id;
        /**
         * The offset of the line rendered
         */
        Short yOffset;
        /**
         * The width of the line rendered
         */
        Short width;
        /**
         * The height of the line rendered
         */
        Short height;
        /**
         * The text that the display list holds
         */
        String text;
    }
}
