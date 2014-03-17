package akgl.Units.Shaders.extensions.DefaultShader;

import akgl.Units.Shaders.SuperShaderProgram;

/**
 * @author Robert Kollar
 */
public class Program extends SuperShaderProgram {

    private static Program instance;

    public static SuperShaderProgram getInstance() {
        if (instance == null) {
            instance = new Program();
        }
        return instance;
    }

    private Program() {
        setFragmentShader(new Fragment());
        setVertexShader(new Vertex());
    }

}
