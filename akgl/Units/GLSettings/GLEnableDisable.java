package akgl.Units.GLSettings;

import java.util.ArrayList;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;

/**
 * @author Robert Kollar
 */
public class GLEnableDisable {

    private static boolean[] enabledGLStates = new boolean[5000];

    /**
     * Makes sure that he argument parameters are glEnabled.
     *
     * @param GL_STATES the parameters
     */
    public static void Enable(Integer... GL_STATES) {
        for (int integer : GL_STATES) {
            if (!enabledGLStates[integer]) {
                glEnable(integer);
                enabledGLStates[integer] = true;
            }
        }
    }

    /**
     * Makes sure that the argument parameters are glDisabled/
     *
     * @param GL_STATES the parameters
     */
    public static void Disable(Integer... GL_STATES) {
        for (int integer : GL_STATES) {
            if (enabledGLStates[integer]) {
                glDisable(integer);
                enabledGLStates[integer] = false;
            }
        }
    }

}
