package resources.scenes.GameObjects;

import akgl.Units.GLTypes.Extensions.Renderer.MeshRenderer;
import akgl.Units.GLTypes.GLObjectExtension;
import akgl.Units.Geometry.HardCodedGeometry.Quad2DGenerator;
import akgl.Units.Geometry.Vectors.Vec3;
import aku.IO.MeshLoader;
import aku.IO.TextureLoader;
import org.lwjgl.input.Keyboard;

/**
 * @author Robert Kollar
 */
public class Player extends GLObjectExtension {

    @Override
    protected void onExtensionAdded() {
        MeshRenderer renderer = new MeshRenderer();

        renderer.setMesh(MeshLoader.getInstance().getMesh(0));
        renderer.setTexture(TextureLoader.getTexture("meshes/122-1.png"));

        getgLObject().setRenderer(renderer);

    }

    @Override
    public void onRender() {
        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            getTransform().getLocalPosition().add(new Vec3(0.1f, 0, 0));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            getTransform().getLocalPosition().add(new Vec3(-0.1f, 0, 0));
        }
    }

}
