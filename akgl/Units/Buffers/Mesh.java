package akgl.Units.Buffers;

import akgl.Units.Geometry.*;
import akgl.Units.Geometry.Vectors.*;
import java.util.*;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_NORMAL_ARRAY;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_COORD_ARRAY;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glDisableClientState;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glNormalPointer;
import static org.lwjgl.opengl.GL11.glNormalPointer;
import static org.lwjgl.opengl.GL11.glVertexPointer;
import static org.lwjgl.opengl.GL11.glVertexPointer;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;

/**
 *
 * @author Robert Kollar
 */
public class Mesh {

    private ArrayBuffer vertexBuffer = new ArrayBuffer();
    private ArrayBuffer normalBuffer = new ArrayBuffer();
    private ArrayBuffer texCoordBuffer = new ArrayBuffer();
    private ArrayBuffer elementArray = new ArrayBuffer();
    private List<Triangle> triangles;
    private List<Vec3> vertices, normals;
    private List<Vec2> texCoords;

    private boolean useNormals, useTexCoords;

    /*
     * is being set true when new triangles are set.
     */
    private boolean reupload = false;

    public void setVertices(List<Vec3> vertices) {
        this.vertices = vertices;
    }

    public void setNormals(List<Vec3> normals) {
        useNormals = normals != null;
        this.normals = normals;
    }

    public void setTexCoords(List<Vec2> texCoords) {
        useTexCoords = texCoords != null;
        this.texCoords = texCoords;
    }

    public void setTriangles(List<Triangle> triangles) {
        this.triangles = triangles;
        reupload = true;
    }

    public void render() {
        if (reupload) {
            uploadDataToGPU();
        }

        glEnableClientState(GL_VERTEX_ARRAY);
        glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer.getBufferId());
        glVertexPointer(3, GL_FLOAT, 0, 0);

        glEnableClientState(GL_TEXTURE_COORD_ARRAY);
        glBindBuffer(GL_ARRAY_BUFFER, texCoordBuffer.getBufferId());
        GL11.glTexCoordPointer(2, GL_FLOAT, 0, 0);

        if (useNormals) {
            glEnableClientState(GL_NORMAL_ARRAY);
            glBindBuffer(GL_ARRAY_BUFFER, normalBuffer.getBufferId());
            glNormalPointer(GL_FLOAT, 0, 0);
        }

        //glEnableClientState(GL_ELEMENT_ARRAY_BUFFER);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, elementArray.getBufferId());
        glDrawElements(GL_TRIANGLES, triangles.size() * 3, GL_UNSIGNED_INT, 0);

        glDisableClientState(GL_VERTEX_ARRAY);
        if (useNormals) {
            glDisableClientState(GL_NORMAL_ARRAY);
        }
        glDisableClientState(GL_ELEMENT_ARRAY_BUFFER);
    }

    public void uploadDataToGPU() {
        reupload = false;
        float[] vertData = new float[vertices.size() * 3];
        float[] normData;
        float[] uvData;
        int[] elementData = new int[triangles.size() * 3];
        int i = 0;

        {
            for (Vec3 vec : vertices) {
                vertData[i + 0] = vec.getX();
                vertData[i + 1] = vec.getY();
                vertData[i + 2] = vec.getZ();
                i += 3;
            }
            vertexBuffer.bufferData(vertData);
        }

        if (useNormals) {
            i = 0;
            normData = new float[normals.size() * 3];
            for (Vec3 normal : normals) {
                normData[i + 0] = normal.getX();
                normData[i + 1] = normal.getY();
                normData[i + 2] = normal.getZ();
                i += 3;
            }
            normalBuffer.bufferData(normData);
        }

        if (useTexCoords) {
            i = 0;
            uvData = new float[texCoords.size() * 2];
            for (Vec2 uv : texCoords) {
                uvData[i + 0] = uv.getX();
                uvData[i + 1] = uv.getY();
                i += 2;
            }
            texCoordBuffer.bufferData(uvData);
        }
        {
            i = 0;
            for (Triangle triangle : triangles) {
                elementData[i + 0] = triangle.getA();
                elementData[i + 1] = triangle.getB();
                elementData[i + 2] = triangle.getC();
                i += 3;
            }
        }
        elementArray.bufferData(elementData);
    }
}
