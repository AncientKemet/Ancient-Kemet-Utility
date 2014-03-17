package akgl.Units.Shaders.extensions.SpriteShader;

import akgl.Units.Shaders.extensions.DefaultShader.*;
import akgl.Units.Shaders.SuperShader;

/**
 * @author Robert Kollar
 */
public class Fragment extends SuperShader {

    public Fragment() {
        super(ShaderType.FRAGMENT_SHADER);
        downloadGLSL("sprite/fragment.txt");
    }

}
