package akgl.Units.GLTypes.Extensions.Input;

import akgl.AKGL;
import akgl.Units.GLTypes.*;
import java.util.HashMap;

/**
 * @author Robert Kollar
 */
public class InputHandler extends GLObjectExtension {

    private static InputHandler instance;

    /**
     * Returns an singleton.
     *
     * @return the static singleton.
     */
    public static InputHandler getInstance() {
        if (instance == null) {
            instance = new InputHandler();
            GLObject glo = new GLObject(AKGL.getRoot2D());
            glo.addExtension(instance);
        }
        return instance;
    }
    
    private Function[] functions = new Function[1024];

    public void registerFunction(KeyBoardEvent theEvent, Function theFunction, boolean location) {

            functions[theEvent.getKey()] = theFunction;
        }

    public void unRegisterFunction(int key) {
        functions[key]=null;
    }

    public void eventHappened(KeyBoardEvent theEvent) {
        if( functions[theEvent.getKey()]!=null)
        functions[theEvent.getKey()].run();
    }
}
