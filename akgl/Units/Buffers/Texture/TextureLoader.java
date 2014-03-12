package akgl.Units.Buffers.Texture;

import aku.IO.AdvancedFileOperations.Files.AKFile;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.PixelGrabber;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;

public class TextureLoader {

    /**
     * The table of textures that have been loaded in this loader
     */
    private static HashMap<String, Texture> table = new HashMap<String, Texture>();
    /**
     * The color model including alpha for the GL image
     */
    private static ColorModel glAlphaColorModel;
    /**
     * The color model for the GL image
     */
    private static ColorModel glColorModel;
    /**
     * Scratch buffer for texture ID's
     */
    private static IntBuffer textureIDBuffer = BufferUtils.createIntBuffer(1);
    private static boolean useAlpha = false;
    private static boolean useMipMap = true;

    /**
     * Create a new texture loader based on the game panel
     */
    static {
        glAlphaColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB),
                new int[]{8, 8, 8, 8},
                true,
                false,
                ComponentColorModel.TRANSLUCENT,
                DataBuffer.TYPE_BYTE);

        glColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB),
                new int[]{8, 8, 8, 0},
                false,
                false,
                ComponentColorModel.OPAQUE,
                DataBuffer.TYPE_BYTE);
    }

    public static void deleteAllTextures() {
        for (int i = 0; i < 1000; i++) {
            glDeleteTextures(i);
        }
        table = new HashMap<String, Texture>();
    }

    /**
     * Create a new texture ID
     *
     * @return A new texture ID
     */
    public static int createTextureID() {
        glGenTextures(textureIDBuffer);
        return textureIDBuffer.get(0);
    }

    /**
     * Load a texture
     *
     * @param resourceName The location of the resource to load
     * @return The loaded texture
     * @throws IOException Indicates a failure to access the resource
     */
    public static Texture getTexture(String resourceName) {
        Texture tex = table.get(resourceName);

        if (tex != null) {
            return tex;
        }

        if (useMipMap) {
            try {
                tex = getTexture(resourceName,
                        GL_TEXTURE_2D, // target
                        GL_RGBA, // dst pixel format
                        GL_LINEAR, // min filter (unused)
                        GL_LINEAR);
            } catch (IOException ex) {
                Logger.getLogger(TextureLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                tex = getTexture(resourceName,
                        GL_TEXTURE_2D, // target
                        GL_RGBA, // dst pixel format
                        GL_LINEAR, // min filter (unused)
                        GL_LINEAR);
            } catch (IOException ex) {
                Logger.getLogger(TextureLoader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Error er) {
                Logger.getLogger(TextureLoader.class.getName()).log(Level.SEVERE, null, er);
            }
        }

        table.put(resourceName, tex);

        return tex;
    }

    public static ByteBuffer GetByteBufferFromBufferedImage(BufferedImage bufferedImage, int texWidth, int texHeight) {
        ByteBuffer imageBuffer;
        WritableRaster raster;
        BufferedImage texImage;
        // create a raster that can be used by OpenGL as a source
        // for a texture
        if (bufferedImage.getColorModel().hasAlpha()) {
            raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, texWidth, texHeight, 4, null);
            texImage = new BufferedImage(glAlphaColorModel, raster, false, new Hashtable());
        } else {
            raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, texWidth, texHeight, 3, null);
            texImage = new BufferedImage(glColorModel, raster, false, new Hashtable());
        }

        // copy the source image into the produced image
        Graphics g = texImage.getGraphics();
        g.setColor(new Color(0f, 0f, 0f, 0f));
        g.fillRect(0, 0, texWidth, texHeight);
        g.drawImage(bufferedImage, 0, 0, null);

        // build a byte buffer from the temporary image
        // that be used by OpenGL to produce a texture.
        byte[] data = ((DataBufferByte) texImage.getRaster().getDataBuffer()).getData();

        imageBuffer = BufferUtils.createByteBuffer(data.length);
        //imageBuffer.order(ByteOrder.nativeOrder());
        imageBuffer.put(data, 0, data.length);
        imageBuffer.flip();

        return imageBuffer;
    }

    public Texture getTexture(String resourceName, ByteBuffer b) throws IOException {
        Texture tex = table.get(resourceName);

        if (tex != null) {
            return tex;
        }

        if (!useMipMap) {
            tex = getTexture(resourceName,
                    GL_TEXTURE_2D, // target
                    GL_RGBA, // dst pixel format
                    GL_LINEAR, // min filter (unused)
                    GL_LINEAR);
        } else {
            tex = getTexture(resourceName,
                    GL_TEXTURE_2D, // target
                    GL_RGBA, // dst pixel format
                    GL_LINEAR, // min filter (unused)
                    GL_LINEAR);
        }

        table.put(resourceName, tex);

        return tex;
    }

    /**
     * Load a texture into OpenGL from a image reference on disk.
     *
     * @param resourceName   The location of the resource to load
     * @param target         The GL target to load the texture against
     * @param dstPixelFormat The pixel format of the screen
     * @param minFilter      The minimising filter
     * @param magFilter      The magnification filter
     * @return The loaded texture
     * @throws IOException Indicates a failure to access the resource
     */
    public static Texture getTexture(String resourceName,
            int target,
            int dstPixelFormat,
            int minFilter,
            int magFilter) throws IOException {
        int srcPixelFormat;

        // create the texture ID for this texture
        int textureID = createTextureID();
        Texture texture = new Texture(target, textureID, resourceName);

        // bind this texture
        glBindTexture(target, textureID);

        BufferedImage bufferedImage = loadImage(resourceName);
        texture.setWidth(bufferedImage.getWidth());
        texture.setHeight(bufferedImage.getHeight());

        if (bufferedImage.getColorModel().hasAlpha()) {
            srcPixelFormat = GL_RGBA;
        } else {
            srcPixelFormat = GL_RGB;
        }

        // convert that image into a byte buffer of texture data
        ByteBuffer textureBuffer = convertImageData(bufferedImage, texture);

        if (target == GL_TEXTURE_2D) {
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, minFilter);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, magFilter);
        }
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, get2Fold(bufferedImage.getWidth()),
                get2Fold(bufferedImage.getHeight()), 0, GL_RGBA, GL_UNSIGNED_BYTE, textureBuffer);

        return texture;
    }

    /**
     * Get the closest greater power of 2 to the fold number
     *
     * @param fold The target number
     * @return The power of 2
     */
    private static int get2Fold(int fold) {
        int ret = 2;
        while (ret < fold) {
            ret *= 2;
        }
        return ret;
    }

    /**
     * Convert the buffered image to a texture
     *
     * @param bufferedImage The image to convert to a texture
     * @param texture       The texture to store the data into
     * @return A buffer containing the data
     */
    public static ByteBuffer convertImageData(BufferedImage bufferedImage, Texture texture) {

        int texWidth = 2;
        int texHeight = 2;

        // find the closest power of 2 for the width and height
        // of the produced texture
        while (texWidth < bufferedImage.getWidth()) {
            texWidth *= 2;
        }
        while (texHeight < bufferedImage.getHeight()) {
            texHeight *= 2;
        }

        texture.setTextureHeight(texHeight);
        texture.setTextureWidth(texWidth);

        return GetByteBufferFromBufferedImage(bufferedImage, texWidth, texHeight);
    }

    /**
     * Load a given resource as a buffered image
     *
     * @param ref The location of the resource to load
     * @return The loaded buffered image
     * @throws IOException Indicates a failure to find a resource
     */
    public static BufferedImage loadImage(String ref) throws IOException {
        URL url = new URL("file:" + new AKFile(ref).getPath());

        if (url == null) {
            throw new Error(new IOException("Cannot find: " + ref));
        }
        if (!new AKFile(ref).exists() || !new AKFile(ref).isFile()) {
            throw new Error(new Error("Non existing image:[ " + ref + " ]."));
        }
        // due to an issue with ImageIO and mixed signed code
        // we are now using good oldfashioned ImageIcon to load
        // images and the paint it on top of a new BufferedImage
        Image img = new ImageIcon(url).getImage();
        PixelGrabber pg = new PixelGrabber(img, 0, 0, 1, 1, false);
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            useAlpha = pg.getColorModel().hasAlpha();
        } catch (NullPointerException e) {
            throw new Error(new Error("Null pointer image:[ " + ref + " ]."));
        }
        BufferedImage bufferedImage = null;
        if (useAlpha) {
            bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        } else {
            bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        }
        Graphics g = bufferedImage.getGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();

        return bufferedImage;
    }

    public static ByteBuffer createBuffer(String ref) {
        Image img = null;
        try {
            img = loadImage(ref);
        } catch (IOException ex) {
            throw new Error(ex);
        }
        int len = img.getHeight(null) * img.getWidth(null);
        ByteBuffer temp = ByteBuffer.allocateDirect(len << 2);;
        temp.order(ByteOrder.LITTLE_ENDIAN);

        int[] pixels = new int[len];

        PixelGrabber pg = new PixelGrabber(img, 0, 0, img.getWidth(null), img.getHeight(null), pixels, 0, img.getWidth(null));

        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
            throw new Error("Error Loading: " + img.getSource());
        }

        for (int i = 0; i < len; i++) {
            int pos = i << 2;
            int texel = pixels[i];
            if (texel != 0) {
                texel |= 0xff000000;
            }
            temp.putInt(pos, texel);
        }

        return temp.asReadOnlyBuffer();
    }

    public void setUseAlpha(boolean useAlpha) {
        this.useAlpha = useAlpha;
    }

    public void useMipMap(boolean b) {
        this.useMipMap = b;
    }
}
