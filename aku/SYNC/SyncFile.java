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
 * @author Von Bock
 */
public class SyncFile extends AncientKemetFile {
  
  private enum state {
    
  }
  
  private boolean readyToRead = false;
  private byte[] bytesToWrite = null;
  private byte[] bytesToSend = null;
  public boolean requestForSyncrhonizationSent = false;

  public void setBytesToWrite(byte[] bytesToWrite) {
    this.bytesToWrite = bytesToWrite;
  }
  
  public boolean isSynchronizationNeeded() {
    return readyToRead == false;
  }

  public boolean isReady() {
    return readyToRead;
  }

  public void setReady(boolean ready) {
    this.readyToRead = ready;
  }
  
  public SyncFile(String fileName) {
    super("/SYNC/"+fileName);
  }
  
  public byte[] getBytesToSend(){
    return bytesToSend;
  }
  
  
  public boolean sending = false;
  public void progressIO(){
    if(available() <= 0){
      sending = false;
    }
    if(sending)
      bytesToSend = getData(0,512).buffer();
    
    if(bytesToWrite != null)
      writeData(bytesToWrite);
    bytesToWrite = null;
  }
  
  
  /*public static void main(String[] args) {
    AncientKemetRegistry.loadRegister();
    SyncFile file = new SyncFile("LOL.txt");
    file.sending = true;
    SyncFile file2 = new SyncFile("LOL2.txt");
    file.writeData("WATGF OMFG".getBytes());

      file.progressIO();
      System.out.println("fff: "+file.getBytesToSend().length);
      file2.progressIO();
      file2.bytesToWrite = file.getBytesToSend();
      file2.progressIO();
    file2.flushOut();
  }*/
}
