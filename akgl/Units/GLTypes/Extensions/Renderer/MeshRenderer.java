package akgl.Units.GLTypes.Extensions.Renderer;

import akgl.Units.Buffers.Mesh;
import akgl.Units.GLTypes.Extensions.GLRenderer;

/**
 *
 * @author Von Bock
 */
public class MeshRenderer extends GLRenderer {

    private Mesh mesh;

    public Mesh getMesh() {
        return mesh;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    @Override
    public void render() {
        if (mesh != null) {
            mesh.render();
        }
    }

    @Override
    public void render2D() {
    }

}
