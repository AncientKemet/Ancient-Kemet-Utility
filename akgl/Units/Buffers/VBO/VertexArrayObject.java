package akgl.Units.Buffers.VBO;

import static org.lwjgl.opengl.GL15.glGenBuffers;

/**
 * @author Robert Kollar
 */
public class VertexArrayObject {

    private int vboId = -1;

    public int getVboId() {
        if (vboId == -1) {
            vboId = glGenBuffers();
        }
        return vboId;
    }

}
