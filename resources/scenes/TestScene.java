package resources.scenes;

import akgl.AKGL;
import akgl.Units.GLTypes.GLObject;

/**
 * @author Robert Kollar
 */
public class TestScene {

    private GLObject sceneRoot3D;
    private GLObject sceneRoot2D;

    /**
     * Hooking up on scene roots
     */
    public TestScene() {
        sceneRoot3D = new GLObject(AKGL.getRoot3D());
        sceneRoot2D = new GLObject(AKGL.getRoot2D());
        constructScene();
    }

    /**
     * Constructing this scene in it's own space
     */
    private void constructScene() {
        //creating an simple sprite
        CustomizedSprite sprite = new CustomizedSprite();

        //setting sprite size
        sprite.getScale().setX(200);
        sprite.getScale().setY(200);

        //creating an object that will hold this sprite
        GLObject spriteHolder = new GLObject(sceneRoot2D);

        //push sprite a bit away from camera
        spriteHolder.getTransform().getPosition().setZ(-1);

        //adding the sprite to the object
        spriteHolder.addExtension(sprite);
    }

}
