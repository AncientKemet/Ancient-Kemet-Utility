package akgl;

import akgl.Configuration.AKGLConfig;
import akgl.Units.GLTypes.*;
import akgl.Units.GLTypes.Extensions.Camera.*;
import static org.lwjgl.opengl.GL11.*;
import resources.scenes.*;

/**
 *
 * @author Robert Kollar
 */
public class AKGL {

    private static boolean rootsHasBeenSetUp = false;

    /*
     * root 3d objects
     */
    private static GLObject root3D = new GLObject(null);
    private static GLObject root2D = new GLObject(null);

    /**
     * Creating scene.
     */
    public static void setupClass() {
        AKGLConfig.getInstance();

        root3D.addExtension(new Camera3D());
        root2D.addExtension(new Camera2D());

        //instancing test scene
        IngameScene testScene = new IngameScene();

        rootsHasBeenSetUp = true;
    }

    /*
     * Called every frame.
     */
    public static void renderFrame() {
        if (!rootsHasBeenSetUp) {
            setupClass();
        }

        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glClearDepth(1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);

        //Chained onRender / renderFrame
        root3D.Update();
        root2D.Update2D();

    }

    public static GLObject getRoot2D() {
        return root2D;
    }

    public static GLObject getRoot3D() {
        return root3D;
    }

}
