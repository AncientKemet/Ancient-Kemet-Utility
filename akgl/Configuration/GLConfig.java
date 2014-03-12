/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package akgl.Configuration;

import java.util.LinkedList;
import java.util.List;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author Von Bock
 */
public class GLConfig {
  
  private static GLConfig instance;

  public static GLConfig getInstance() {
    if(instance == null){
      instance = new GLConfig();
    }
    return instance;
  }
  
  private List<String> properties = new LinkedList<String>();

  private GLConfig() {
    properties.add("OpenGL version: "+ GL11.glGetString(GL11.GL_VERSION));
    
    for(String s : properties){
      System.out.println(s);
    }
  }
  
  
  
}
