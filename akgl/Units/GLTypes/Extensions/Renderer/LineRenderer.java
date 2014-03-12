package akgl.Units.GLTypes.Extensions.Renderer;

import akgl.Units.GLTypes.Extensions.GLRenderer;
import akgl.Units.Geometry.Vectors.*;
import java.util.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * @author Robert Kollar
 */
public class LineRenderer extends GLRenderer {

    private ArrayList<Vec3> vertices = new ArrayList<Vec3>();

    @Override
    public void render() {
        glBegin(GL_LINE_STRIP);
        for (Vec3 vec3 : vertices) {
            glVertex3f(vec3.getX(), vec3.getY(), vec3.getZ());
        }
        glEnd();
    }

    @Override
    public void render2D() {
        glBegin(GL_LINE_STRIP);
        for (Vec3 vec3 : vertices) {
            glVertex2f(vec3.getX(), vec3.getY());
        }
        glEnd();
    }

    public ArrayList<Vec3> getVertices() {
        return vertices;
    }

}
