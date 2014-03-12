/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aku.IO.AdvancedByteOperations;

/**
 *
 * @author Von Bock
 */

import java.io.*;
import java.util.zip.*;

public class Compressor{
    public static void main(String[] args) {
      byte[] dat = new byte[]{120,0,0,0,125,124};
      byte[] comppressed = compress(dat);
      byte[] deComp = decompress(comppressed);
      System.out.println(""+comppressed[0]);
    }
    
    public static byte[] compress(byte[] content){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try{
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gzipOutputStream.write(content);
            gzipOutputStream.close();
        } catch(IOException e){
            throw new RuntimeException(e);
        }
        System.out.printf("Compression ratio %f\n ", (1.0f * content.length/byteArrayOutputStream.size()));
        
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] decompress(byte[] contentBytes){
      byte[] out = null;

        try{
          GZIPInputStream in = new GZIPInputStream(new ByteArrayInputStream(contentBytes));
          System.out.println(in.available());
          out = new byte[in.available()];
          in.read(out, 0, in.available());
          System.out.println("");
        } catch(IOException e){
            throw new RuntimeException(e);
        }
        return out;
    }

}