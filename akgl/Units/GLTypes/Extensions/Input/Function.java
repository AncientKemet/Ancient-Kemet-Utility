package akgl.Units.GLTypes.Extensions.Input;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Robert Kollar
 */
public abstract class Function {

    private List<KeyBoardEvent> triggers = new ArrayList<KeyBoardEvent>();

    public abstract void run();

}
