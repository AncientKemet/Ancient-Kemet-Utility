/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AKSERVER;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Von Bock
 */
public class WPPassword {
  
  private static String itoa64 = "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

  public static boolean CheckPassword(String password, String stored_hash) {
    try {
      String hash = cryptPrivate(password, stored_hash);
      
      return hash.equals(stored_hash);
    } catch (NoSuchAlgorithmException ex) {
      Logger.getLogger(WPPassword.class.getName()).log(Level.SEVERE, null, ex);
    } catch (UnsupportedEncodingException ex) {
      Logger.getLogger(WPPassword.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
  }

  private static String cryptPrivate(String password, String setting) throws NoSuchAlgorithmException, UnsupportedEncodingException {

    int count_log2 = itoa64.indexOf(setting.charAt(3));

    if (count_log2 < 7 || count_log2 > 30) {
    }
    int count = 1 << count_log2;

    String salt = setting.substring(4, 4 + 8);
    if (salt.length() != 8) {

    }
    String hash = MD5(salt + password);
    
    while (count-- != 0) {
      hash = MD5(hash + password);
    }
    
    String output = setting.substring(0, 12);
    output += encode64(new String(hash), 16);
    
    return output;
  }

  private static String encode64(String input, int count) {
    String output = "";
    int i = 0;
    while (i < count) {
      int value = input.getBytes()[i++];
      output += (char)itoa64.getBytes()[value & 0x3f];
      if (i < count) {
        value |= input.getBytes()[i] << 8;
      }
      output += (char)itoa64.getBytes()[(value >> 6) & 0x3f];
      if(i++ >= count){
        break;
      }
      if(i < count){
        value |= input.getBytes()[i] << 16;
      }
      output += (char)itoa64.getBytes()[(value >> 12) & 0x3f];
      if(i++ >= count){
        break;
      }
      output += (char)itoa64.getBytes()[(value >> 18) & 0x3f];
    }
    return output;
  }

  
  public static String MD5(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    String result = input;
    if(input != null) {
        MessageDigest md = MessageDigest.getInstance("MD5"); //or "SHA-1"
        md.update(input.getBytes());
        BigInteger hash = new BigInteger(1, md.digest());
        result = hash.toString(16);
        while(result.length() < 32) { //40 for SHA-1
            result = "0" + result;
        }
    }
    
    return result;
  }
  
  static String HexToBinary(String hex) {
    StringBuilder output = new StringBuilder();
    for (int i = 0; i < hex.length(); i+=2) {
        String str = hex.substring(i, i+2);
        output.append((char)Integer.parseInt(str, 16));
    }
    return output.toString();
  }
}
