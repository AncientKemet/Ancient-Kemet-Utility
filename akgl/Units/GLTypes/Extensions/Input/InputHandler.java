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

    private KeyBoardEvent[] events = new KeyBoardEvent[1024];
    private Function[] functions = new Function[1024];

    public void registerFunction(KeyBoardEvent theEvent, Function theFunction) {

        functionMap.put(theEvent, theFunction);
    }

    public void unRegisterFunction(Function theFunction) {
        functionMap.remove(theFunction);
    }

    public void eventHappened(KeyBoardEvent theEvent) {
        functionMap.get(theEvent);
    }

}
