package akgl.Units.GLTypes.Extensions;

import akgl.Units.Shaders.extensions.DefaultShader.Program;
import akgl.Units.Shaders.SuperShaderProgram;

/**
 *
 * @author Robert Kollar
 */
public abstract class GLRenderer {

    private SuperShaderProgram program = Program.getInstance();

    public void render3D() {
        program.useShaderProgram();
        onRender3D();
    }

    public void render2D() {
        program.useShaderProgram();
        onRender2D();
    }

    public void setProgram(SuperShaderProgram program) {
        this.program = program;
    }

    protected abstract void onRender3D();

    protected abstract void onRender2D();

}
