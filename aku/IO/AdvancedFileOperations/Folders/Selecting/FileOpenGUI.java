/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GUI_open.java
 *
 * Created on 14.6.2011, 20:44:54
 */
package aku.IO.AdvancedFileOperations.Folders.Selecting;

import java.io.File;

/**
 *
 * @author Dominik
 */
public class FileOpenGUI extends javax.swing.JFrame {

  private FileSelecting fileSelecting;
  private String currentPath = null;
  public String last_opened_path = null;

  /**
   * Creates new form GUI_open
   */
  public FileOpenGUI(final FileSelecting fs, final String basePath) {
    new Thread(new Runnable() {
      @Override
      public void run() {
        currentPath = basePath;
        initComponents();
        setVisible(true);
        setFileSelecting(fs);
      }
    }).start();
  }

  public void setFileSelecting(FileSelecting fileSelecting) {
    this.fileSelecting = fileSelecting;
  }

  public File getCurrentPath() {
    return new File(currentPath);
  }

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jFileChooser1 = new javax.swing.JFileChooser();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setTitle("Vyber priecinok");

    jFileChooser1.setCurrentDirectory(getCurrentPath());
    jFileChooser1.setFileSelectionMode(javax.swing.JFileChooser.FILES_AND_DIRECTORIES);
    jFileChooser1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
    jFileChooser1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jFileChooser1ActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jFileChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jFileChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void jFileChooser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileChooser1ActionPerformed
    last_opened_path = jFileChooser1.getSelectedFile().getAbsolutePath();
    fileSelecting.finish_select_directory();
    this.dispose();
  }//GEN-LAST:event_jFileChooser1ActionPerformed
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JFileChooser jFileChooser1;
  // End of variables declaration//GEN-END:variables
}
