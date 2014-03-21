package akgl.Units.GLTypes.Extensions.Camera;

import akgl.Units.GLSettings.GLEnableDisable;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;

/**
 * @author Robert Kollar
 */
public class Camera2D extends CameraBase {

    @Override
    protected void onExtensionAdded() {
        getViewportSize().setX(Display.getWidth());
        getViewportSize().setY(Display.getHeight());
    }

    @Override
    public void onRender2D() {
        getViewportSize().setX(Display.getWidth());
        getViewportSize().setY(Display.getHeight());

        glViewport((int) viewportStart.getX(), (int) viewportStart.getY(), (int) viewportSize.getX(), (int) viewportSize.getY());
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0.0f, (int) viewportSize.getX(), 0.0f, (int) viewportSize.getY(), -100f, 100f);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        GLEnableDisable.Disable(GL_DEPTH_TEST);
        glTranslatef(viewportSize.getX() / 2f, viewportSize.getY() / 2f, 0);

        /*
         * glBegin(GL_QUADS); { glVertex2f(0, 0); glVertex2f(100, 0); glVertex2f(100,
         * 100); glVertex2f(0, 100); } glEnd();
         */
    }

}
