package resources.scenes.GameObjects;

import akgl.Units.GLTypes.Extensions.Renderer.TerrainRenderer;
import akgl.Units.GLTypes.GLObject;
import akgl.Units.GLTypes.GLObjectExtension;
import aku.IO.MeshLoader;

/**
 * @author Robert Kollar
 */
public class Terrain extends GLObjectExtension {

    private static Terrain instance;

    public static Terrain getInstance() {
        if (instance == null) {
            instance = new Terrain();
            GLObject terrainHolder = new GLObject(akgl.AKGL.getRoot3D());
            terrainHolder.addExtension(instance);
        }
        return instance;
    }

    private Terrain() {
    }

    @Override
    protected void onExtensionAdded() {
        TerrainRenderer renderer = new TerrainRenderer();

        renderer.setMesh(MeshLoader.getInstance().getMesh(1));

        getgLObject().setRenderer(renderer);

    }

}
