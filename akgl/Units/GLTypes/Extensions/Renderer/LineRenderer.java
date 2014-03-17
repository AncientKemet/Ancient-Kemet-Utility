package akgl.Units.GLTypes.Extensions.Renderer;

import akgl.Units.GLTypes.Extensions.GLRenderer;
import akgl.Units.Geometry.Vectors.*;
import java.util.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Deprecated use only for test purpose.
 *
 * @author Robert Kollar
 */
@Deprecated
public class LineRenderer extends GLRenderer {

    private ArrayList<Vec3> vertices = new ArrayList<Vec3>();

    @Override
    public void onRender3D() {
        glBegin(GL_LINE_STRIP);
        for (Vec3 vec3 : vertices) {
            glVertex3f(vec3.getX(), vec3.getY(), vec3.getZ());
        }
        glEnd();
    }

    @Override
    public void onRender2D() {
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
