/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aku.SYNC;

import aku.AncientKemetRegistry;
import aku.Data;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robert Kollar
 */
public class AncientKemetFile extends File {

    FileInputStream inputStream;
    FileOutputStream outputStream;

    public AncientKemetFile(String pathname) {
        super(AncientKemetRegistry.getFolderDir() + pathname);
    }

    public Data getData() {
        try {
            return getData(getInputStream().available());
        } catch (IOException ex) {
            Logger.getLogger(AncientKemetFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void writeData(byte[] data, int start, int lenght) {
        writeData(data, start, lenght, 512);
    }

    public void writeData(byte[] data, int lenght) {
        writeData(data, 0, lenght);
    }

    public void writeData(byte[] data) {
        writeData(data, data.length);
    }

    public void writeData(byte[] data, int start, int lenght, int step) {
        try {
            getOutputStream();
            if (step + start > data.length) {
                step = data.length - start;
            }
            for (int i = 0; i < lenght; i += step) {
                outputStream.write(data, start, step);
            }
        } catch (IOException ex) {
            Logger.getLogger(AncientKemetFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Data getData(int lenght) {
        return getData(0, lenght);
    }

    public Data getData(int start, int lenght) {
        return getData(start, lenght, 512);
    }

    public Data getData(int start, int lenght, int step) {
        try {
            getInputStream();
            if (lenght > available) {
                lenght = available;
            }
            if (step + start > lenght) {
                step = lenght - start;
                //throw new Error("The step is too big here, lenght: " + lenght + " while step: " + step);
            }
            Data d = new Data(0);
            inputStream.skip(start);
            byte[] bytes = new byte[step];
            for (int i = 0; i < lenght; i += step) {
                inputStream.read(bytes);
                d.addBytes(bytes);
            }
            d.setOffset(0);
            return d;
        } catch (IOException ex) {
            Logger.getLogger(AncientKemetFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    protected FileInputStream getInputStream() {
        if (!exists()) {
            return null;
        } else {
            if (inputStream == null) {
                try {
                    inputStream = new FileInputStream(this);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(AncientKemetFile.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                available = inputStream.available();
            } catch (IOException ex) {
                Logger.getLogger(AncientKemetFile.class.getName()).log(Level.SEVERE, null, ex);
            }
            return inputStream;
        }
    }

    protected FileOutputStream getOutputStream() {
        if (!getParentFile().exists()) {
            getParentFile().mkdirs();
        }
        if (outputStream == null) {
            try {
                outputStream = new FileOutputStream(this);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(AncientKemetFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return outputStream;
    }

    private int available = -1;

    public int available() {
        if (available == -1) {
            getInputStream();
        }
        return available;
    }

    public void flushOut() {
        try {
            getOutputStream().flush();
        } catch (IOException ex) {
            Logger.getLogger(AncientKemetFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
