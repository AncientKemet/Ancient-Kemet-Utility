package akgl.Units.GLTypes.Extensions.Camera;

import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glViewport;
import org.lwjgl.util.glu.*;

/**
 * @author Robert Kollar
 */
public class Camera3D extends CameraBase {

    @Override
    protected void onExtensionAdded() {
        getViewportSize().setX(Display.getWidth());
        getViewportSize().setY(Display.getHeight());
    }

    @Override
    public void onRender() {
        getgLObject().getTransform().getRotation().setY(getgLObject().getTransform().getRotation().getY() + 0.05f);

        glViewport((int) viewportStart.getX(), (int) viewportStart.getY(), (int) viewportSize.getX(), (int) viewportSize.getY());
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        GLU.gluPerspective(45, 1.5f, 0.01f, 100);

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        glEnable(GL_DEPTH_TEST);

        GLU.gluLookAt(
                getgLObject().getTransform().getPosition().getX(),
                getgLObject().getTransform().getPosition().getY() + 5,
                getgLObject().getTransform().getPosition().getZ() + 5,
                getgLObject().getTransform().getPosition().getX(),
                getgLObject().getTransform().getPosition().getY(),
                getgLObject().getTransform().getPosition().getZ(), 0, 1, 0);

        glRotatef(getgLObject().getTransform().getRotation().getY(), 0, 1, 0);

    }

    @Override
    public void onRender2D() {
    }

}
