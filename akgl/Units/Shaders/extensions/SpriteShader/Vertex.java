package akgl.Units.Shaders.extensions.SpriteShader;

import akgl.Units.Shaders.extensions.DefaultShader.*;
import akgl.Units.Shaders.SuperShader;

/**
 * @author Robert Kollar
 */
public class Vertex extends SuperShader {

    public Vertex() {
        super(ShaderType.VERTEX_SHADER);
        downloadGLSL("sprite/vertex.txt");
    }

}
