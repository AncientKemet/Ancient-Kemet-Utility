package akgl.Units.GLTypes.Extensions.Renderer;

import akgl.Units.GLTypes.Extensions.GLRenderer;
import java.util.ArrayList;

/**
 * @author Robert Kollar
 */
public class RendererController {

    private static ArrayList<GLRenderer> renderers = new ArrayList<GLRenderer>();

    public static ArrayList<GLRenderer> getRenderers() {
        return renderers;
    }

    public static void render() {
        for (GLRenderer gLRenderer : renderers) {
            gLRenderer.render();
        }
    }

}
