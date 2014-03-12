package akgl.Configuration;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author Robert Kollar
 */
public class AKGLConfig {

    private static AKGLConfig instance;

    public static AKGLConfig getInstance() {
        if (instance == null) {
            instance = new AKGLConfig();
        }
        return instance;
    }

    /////////////////////////////////////////////////////////////
    private Hashtable<String, Boolean> settings = new Hashtable<String, Boolean>();
    private List<String> properties = new LinkedList<String>();

    private AKGLConfig() {
        properties.add("OpenGL version: " + GL11.glGetString(GL11.GL_VERSION));
        settings.put("debug", true);

        for (String s : properties) {
            System.out.println(s);
        }
    }

    public Hashtable<String, Boolean> getSettings() {
        return settings;
    }

}
