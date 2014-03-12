/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aku.IO.AdvancedFileOperations.Folders.Selecting;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Von Bock
 */
public class FileSelecting {

  private boolean finished = false;
  private String basePath;
  private FileOpenGUI gui;

  public void setBasePath(String basePath) {
    this.basePath = basePath;
  }

  public FileSelecting(String basePath) {
    this.basePath = basePath;
    gui = new FileOpenGUI(this, basePath);
  }

  private FileOpenGUI getGui() {
    if (gui == null) {
      gui = new FileOpenGUI(this, basePath);
    }
    return gui;
  }

  public File SelectFile() {
    File fileToReturn = null;
    while (!finished) {
      try {
        Thread.sleep(10);
      } catch (InterruptedException ex) {
        Logger.getLogger(FileSelecting.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    if (finished) {
      fileToReturn = new File(getGui().last_opened_path);
    }

    return fileToReturn;
  }

  void finish_select_directory() {
    finished = true;
  }

  public static void main(String[] args) {
    
    System.out.println("1");
    FileSelecting s = new FileSelecting("C:/");
    System.out.println("file: " + s.SelectFile().getAbsolutePath());
  }
}
