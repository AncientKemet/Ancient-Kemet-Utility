/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package akgl.Units.GLTypes.Extensions.Input;

import akgl.Units.GLTypes.GLObjectExtension;
import org.lwjgl.input.Keyboard;


public class KeyboardHandler extends GLObjectExtension{
/**
 *
 * @author User
 */
private static KeyboardHandler instance;


public static  KeyboardHandler getinstance() {

    while ((KeyboardInputHandler(Keyboard.next()))!=null) {}
 

    return instance;
}

}