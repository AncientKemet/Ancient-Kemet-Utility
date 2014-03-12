package aku;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Data extends ByteStream {

    public Data(int random) {
    }

    public Data() {
    }

    public void addShort(int i) {
        /*  41 */ addByte(i >> 8);
        /*  42 */ addByte(i);
    }

    public void addInt(int i) {
        /*  46 */ addByte(i >> 24);
        /*  47 */ addByte(i >> 16);
        /*  48 */ addByte(i >> 8);
        /*  49 */ addByte(i);
    }

    public void addLong(long l) {
        /*  53 */ addByte((int) (l >> 56));
        /*  54 */ addByte((int) (l >> 48));
        /*  55 */ addByte((int) (l >> 40));
        /*  56 */ addByte((int) (l >> 32));
        /*  57 */ addByte((int) (l >> 24));
        /*  58 */ addByte((int) (l >> 16));
        /*  59 */ addByte((int) (l >> 8));
        /*  60 */ addByte((int) l);
    }

    public void addString(String s) {
        /*  64 */ for (byte b : s.getBytes()) {
            /*  65 */ addByte(b);
        }
        /*  67 */ addByte(0);
    }

    public void addSmart(int i) {
        /*  71 */ i &= 65535;
        /*  72 */ if ((i >= 128) && (i <= 32768)) /*  73 */ {
            addShort(i + 32768);
        } else /*  75 */ {
            addByte(i);
        }
    }

    public void addOpcode(int id) {
        /*  80 */ addByte(id);
    }

    public void addOpcodeVarByte(int id) {
        /*  84 */ addOpcode(id);
        /*  85 */ addByte(0);
        /*  86 */ this.opcodeStart = (this.offset - 1);
    }

    public void addOpcodeVarShort(int id) {
        /*  90 */ addOpcode(id);
        /*  91 */ addShort(0);
        /*  92 */ this.opcodeStart = (this.offset - 2);
    }

    public void endOpcodeVarByte() {
        /*  96 */ addByte(this.offset - (this.opcodeStart + 1), this.opcodeStart);
    }

    public void endOpcodeVarShort() {
        /* 100 */ int size = this.offset - (this.opcodeStart + 2);
        /* 101 */ addByte(size >> 8, this.opcodeStart++);
        /* 102 */ addByte(size, this.opcodeStart);
    }

    public void addFloat(float f) {
        /* 116 */ addInt(Float.floatToIntBits(f));
    }

    public int getUnsignedByte() {
        /* 124 */ return getByte() & 0xFF;
    }

    public int getShort() {
        /* 128 */ int i = (getUnsignedByte() << 8) + getUnsignedByte();
        /* 129 */ if (i > 32767) {
            /* 130 */ i -= 65536;
        }
        /* 132 */ return i;
    }

    public int getShortBE() {
        /* 128 */ int i = getUnsignedShortBE();
        /* 129 */ if (i > 32767) {
            /* 130 */ i -= 65536;
        }
        /* 132 */ return i;
    }

    public int getUnsignedShort() {
        /* 136 */ return (getUnsignedByte() << 8) + getUnsignedByte();
    }

    public int getUnsignedShortBE() {
        return getUnsignedByte() + (getUnsignedByte() << 8);
    }

    public int getInt() {
        /* 140 */ return (getUnsignedByte() << 24) + (getUnsignedByte() << 16) + (getUnsignedByte() << 8) + getUnsignedByte();
    }

    public int getIntBE() {
        return getUnsignedByte() + (getUnsignedByte() << 8) + (getUnsignedByte() << 16) + (getUnsignedByte() << 24);
    }

    public long getLong() {
        /* 144 */ long l = getInt() & 0xFFFFFFFF;
        /* 145 */ long l1 = getInt() & 0xFFFFFFFF;
        /* 146 */ return (l << 32) + l1;
    }

    public String getString() {
        /* 150 */ String s = "";
        int b;
        /* 152 */ while ((b = getByte()) != 0) {
            /* 153 */ s = s + (char) b;
        }
        /* 155 */ return s;
    }

    public int getSmart() {
        /* 159 */ int i = ((Byte) this.buffer.get(this.offset)).byteValue() & 0xFF;
        /* 160 */ if (i < 128) {
            /* 161 */ return getUnsignedByte();
        }
        /* 163 */ return getUnsignedShort() - 32768;
    }

    public int getOpcode() {
        /* 168 */ return getUnsignedByte();
    }

    public float getFloat() {
        /* 172 */ return Float.intBitsToFloat(getInt());
    }

    public void addFailFloat(float f) {
        /* 176 */ addInt((int) (f * 1000.0F));
    }

    public float getFailFloat() {
        /* 180 */ return getInt() / 1000.0F;
    }

    public void add8bMask(boolean b0, boolean b1, boolean b2, boolean b3, boolean b4, boolean b5, boolean b6, boolean b7) {
        /* 191 */ int mask = 0;
        /* 192 */ if (b0) {
            /* 193 */ mask = (byte) (mask | 0x1);
        }
        /* 195 */ if (b1) {
            /* 196 */ mask = (byte) (mask | 0x2);
        }
        /* 198 */ if (b2) {
            /* 199 */ mask = (byte) (mask | 0x4);
        }
        /* 201 */ if (b3) {
            /* 202 */ mask = (byte) (mask | 0x8);
        }
        /* 204 */ if (b4) {
            /* 205 */ mask = (byte) (mask | 0x10);
        }
        /* 207 */ if (b5) {
            /* 208 */ mask = (byte) (mask | 0x20);
        }
        /* 210 */ if (b6) {
            /* 211 */ mask = (byte) (mask | 0x40);
        }
        /* 213 */ if (b7) {
            /* 214 */ mask = (byte) (mask | 0x80);
        }
        /* 216 */ addByte(mask);
    }

    public boolean[] get8bMask() {
        /* 220 */ int mask = getByte();
        /* 221 */ boolean[] b = new boolean[8];

        /* 223 */ if ((mask & 0x1) != 0) {
            /* 224 */ b[0] = true;
        }
        /* 226 */ if ((mask & 0x2) != 0) {
            /* 227 */ b[1] = true;
        }
        /* 229 */ if ((mask & 0x4) != 0) {
            /* 230 */ b[2] = true;
        }
        /* 232 */ if ((mask & 0x8) != 0) {
            /* 233 */ b[3] = true;
        }
        /* 235 */ if ((mask & 0x10) != 0) {
            /* 236 */ b[4] = true;
        }
        /* 238 */ if ((mask & 0x20) != 0) {
            /* 239 */ b[5] = true;
        }
        /* 241 */ if ((mask & 0x40) != 0) {
            /* 242 */ b[6] = true;
        }
        /* 244 */ if ((mask & 0x80) != 0) {
            /* 245 */ b[7] = true;
        }
        /* 247 */ return b;
    }

    public String getCodedString() {
        /* 251 */ String s = getString();
        /* 252 */ String s2 = "";
        /* 253 */ for (int i = 0; i < s.length(); i++) {
            /* 254 */ char c = s.charAt(i);
            /* 255 */ if ((byte) c != 0) {
                /* 256 */ char c2 = (char) ((byte) c - 1);
                /* 257 */ s2 = s2 + c2;
            }
        }
        /* 260 */ return s2;
    }

    public void addCodedString(String s) {
        /* 264 */ String s2 = "";
        /* 265 */ for (int i = 0; i < s.length(); i++) {
            /* 266 */ char c = s.charAt(i);
            /* 267 */ if ((byte) c != 255) {
                /* 268 */ char c2 = (char) ((byte) c + 1);
                /* 269 */ s2 = s2 + c2;
            }
        }
        /* 272 */ addString(s2);
    }

    public float getFailFloat2() {
        /* 276 */ return getShort() / 1000.0F;
    }

    public void addFailFloat2(float f) {
        int i = (int) (f * 1000.0F);
        addShort((short) i);
    }

    public float getFloat3() {
        return getShort() / 10.0F;
    }

    public void addFailFloat3(float f) {
        int i = (int) (f * 10.0F);
        addShort((short) i);
    }

    public void add8bMask(boolean[] maskToSend) {
        add8bMask(maskToSend[0], maskToSend[1], maskToSend[2], maskToSend[3], maskToSend[4], maskToSend[5], maskToSend[6], maskToSend[7]);
    }

    public void loadUpFromFile(File file) {
        try {
            FileInputStream in = new FileInputStream(file);
            byte[] data = new byte[in.available()];
            in.read(data);
            in.close();
            addBytes(data);
        } catch (IOException ex) {
            Logger.getLogger(AncientKemetRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
        setOffset(0);
    }

    public void writeToFile(File file) {
        try {
            FileOutputStream out = new FileOutputStream(file);
            out.write(buffer());
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(AncientKemetRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
