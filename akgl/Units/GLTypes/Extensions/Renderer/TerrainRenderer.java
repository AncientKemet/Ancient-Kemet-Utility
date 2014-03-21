package akgl.Units.GLTypes.Extensions.Renderer;

import akgl.Units.Buffers.*;
import akgl.Units.Buffers.Texture.*;
import akgl.Units.GLSettings.*;
import akgl.Units.GLTypes.Extensions.*;
import akgl.Units.Shaders.*;
import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;

/**
 *
 * @author Robert Kollar
 */
public class TerrainRenderer extends GLRenderer {

    private Mesh mesh;

    private Texture texture;

    public Mesh getMesh() {
        return mesh;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    @Override
    public void onRender3D() {
        SuperShaderProgram.unloadShaderProgram();
        GLEnableDisable.Disable(GL_TEXTURE_2D);
        GLEnableDisable.Enable(GL11.GL_LIGHTING, GL11.GL_LIGHT0);
        if (mesh != null) {
            if (texture != null) {
                texture.bind();
            }
            mesh.render();
        }
    }

    @Override
    public void onRender2D() {
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

}
