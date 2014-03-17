package akgl.Units.Shaders.extensions.SpriteShader;

import akgl.Units.Geometry.Vectors.Vec4;
import akgl.Units.Shaders.extensions.DefaultShader.*;
import akgl.Units.Shaders.SuperShaderProgram;
import org.lwjgl.opengl.GL20;

/**
 * @author Robert Kollar
 */
public class SpriteShader extends SuperShaderProgram {

    private static SpriteShader instance;

    public static SpriteShader getInstance() {
        if (instance == null) {
            instance = new SpriteShader();
        }
        return instance;
    }

    private Vec4 color = new Vec4(1, 1, 1, 1);
    private boolean colorChanged = true;

    private SpriteShader() {
        setFragmentShader(new Fragment());
        setVertexShader(new Vertex());
    }

    public void setColor(Vec4 color) {
        this.color = color;
        colorChanged = true;
    }

    @Override
    protected void onUpdateUniforms() {
        super.onUpdateUniforms();
        if (colorChanged) {
            colorChanged = false;
            GL20.glUniform4f(getUniformLocation("multColor"), color.getX(), color.getY(), color.getZ(), color.getW());
        }
    }

}
