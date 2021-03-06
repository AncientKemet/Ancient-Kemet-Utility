package aku.IO.AdvancedByteOperations;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;

/**
 * @author Robert Kollar
 */
public class PNGDecoder extends Object {

    /**
     * black and white image mode.
     */
    public static final byte BW_MODE = 0;
    /**
     * grey scale image mode.
     */
    public static final byte GREYSCALE_MODE = 1;
    /**
     * full color image mode.
     */
    public static final byte COLOR_MODE = 2;

    InputStream in;

    /**
     * Constructs a PNGDecoder object.
     *
     * @param in input stream to read PNG image from.
     */
    public PNGDecoder(InputStream in) {
        this.in = in;
    }

    byte read() throws IOException {
        byte b = (byte) in.read();
        return (b);
    }

    int readInt() throws IOException {
        byte b[] = read(4);
        return (((b[0] & 0xff) << 24)
                + ((b[1] & 0xff) << 16)
                + ((b[2] & 0xff) << 8)
                + ((b[3] & 0xff)));
    }

    byte[] read(int count) throws IOException {
        byte[] result = new byte[count];
        for (int i = 0; i < count; i++) {
            result[i] = read();
        }
        return (result);
    }

    boolean compare(byte[] b1, byte[] b2) {
        if (b1.length != b2.length) {
            return (false);
        }
        for (int i = 0; i < b1.length; i++) {
            if (b1[i] != b2[i]) {
                return (false);
            }
        }
        return (true);
    }

    void checkEquality(byte[] b1, byte[] b2) {
        if (!compare(b1, b2)) {
            throw (new RuntimeException("Format error"));
        }
    }

    /**
     * Decodes image from an input stream passed into constructor.
     *
     * @return a BufferedImage object
     * @throws IOException
     */
    public BufferedImage decode() throws IOException {

        byte[] id = read(12);
        checkEquality(id, new byte[]{-119, 80, 78, 71, 13, 10, 26, 10, 0, 0, 0, 13});

        byte[] ihdr = read(4);
        checkEquality(ihdr, "IHDR".getBytes());

        int width = readInt();
        int height = readInt();

        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        byte[] head = read(5);
        int mode;
        if (compare(head, new byte[]{1, 0, 0, 0, 0})) {
            mode = BW_MODE;
        } else if (compare(head, new byte[]{8, 0, 0, 0, 0})) {
            mode = GREYSCALE_MODE;
        } else if (compare(head, new byte[]{8, 2, 0, 0, 0})) {
            mode = COLOR_MODE;
        } else {
            throw (new RuntimeException("Format error"));
        }

        readInt();//!!crc

        int size = readInt();

        byte[] idat = read(4);
        checkEquality(idat, "IDAT".getBytes());

        byte[] data = read(size);

        Inflater inflater = new Inflater();
        inflater.setInput(data, 0, size);

        int color;

        try {
            switch (mode) {
                case BW_MODE: {
                    int bytes = (int) (width / 8);
                    if ((width % 8) != 0) {
                        bytes++;
                    }
                    byte colorset;
                    byte[] row = new byte[bytes];
                    for (int y = 0; y < height; y++) {
                        inflater.inflate(new byte[1]);
                        inflater.inflate(row);
                        for (int x = 0; x < bytes; x++) {
                            colorset = row[x];
                            for (int sh = 0; sh < 8; sh++) {
                                if (x * 8 + sh >= width) {
                                    break;
                                }
                                if ((colorset & 0x80) == 0x80) {
                                    result.setRGB(x * 8 + sh, y, Color.white.getRGB());
                                } else {
                                    result.setRGB(x * 8 + sh, y, Color.black.getRGB());
                                }
                                colorset <<= 1;
                            }
                        }
                    }
                }
                break;
                case GREYSCALE_MODE: {
                    byte[] row = new byte[width];
                    for (int y = 0; y < height; y++) {
                        inflater.inflate(new byte[1]);
                        inflater.inflate(row);
                        for (int x = 0; x < width; x++) {
                            color = row[x];
                            result.setRGB(x, y, (color << 16) + (color << 8) + color);
                        }
                    }
                }
                break;
                case COLOR_MODE: {
                    byte[] row = new byte[width * 3];
                    for (int y = 0; y < height; y++) {
                        inflater.inflate(new byte[1]);
                        inflater.inflate(row);
                        for (int x = 0; x < width; x++) {
                            result.setRGB(x, y,
                                    ((row[x * 3 + 0] & 0xff) << 16)
                                    + ((row[x * 3 + 1] & 0xff) << 8)
                                    + ((row[x * 3 + 2] & 0xff)));
                        }
                    }
                }
            }
        } catch (DataFormatException e) {
            throw (new RuntimeException("ZIP error" + e));
        }

        readInt();//!!crc
        readInt();//0

        byte[] iend = read(4);
        checkEquality(iend, "IEND".getBytes());

        readInt();//!!crc
        in.close();

        return (result);
    }

    /**
     * Decodes image from file.
     *
     * @param fileName a file to read image from
     * @return a BufferedImage instance.
     */
    public static BufferedImage decode(InputStream in) {
        try {
            return (new PNGDecoder(in)).decode();
        } catch (IOException e) {
            throw (new RuntimeException("IOException during image reading" + e));
        }
    }

}
