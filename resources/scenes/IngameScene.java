package resources.scenes;

import akgl.AKGL;
import akgl.Units.GLTypes.*;
import akgl.Units.Geometry.Vectors.Vec3;
import aku.IO.XML.XMLLoader;
import java.util.Random;
import java.util.logging.*;
import resources.scenes.GameObjects.Player;
import resources.scenes.GameObjects.Terrain;

/**
 * @author Robert Kollar
 */
public class IngameScene {

    private GLObject sceneRoot3D;
    private GLObject sceneRoot2D;

    /**
     * Hooking up on scene roots
     */
    public IngameScene() {
        sceneRoot3D = new GLObject(AKGL.getRoot3D());
        sceneRoot2D = new GLObject(AKGL.getRoot2D());
        constructScene();
    }

    /**
     * Constructing this scene in it's own space
     */
    private void constructScene() {
        Terrain.getInstance();

        Player myPlayer = new Player();

        GLObject playerHolder = new GLObject(sceneRoot3D);

        playerHolder.addExtension(myPlayer);

        playerHolder.getTransform().getScale().add(new Vec3(-0.5f, -0.5f, -0.5f));
        playerHolder.getTransform().getLocalPosition().add(new Vec3(0, 0, 0));

        try {
            XMLLoader.loadCompositionFromKXML("test");
        } catch (Exception ex) {
            Logger.getLogger(IngameScene.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
