package akgl.Units.GLTypes.Extensions.Camera;

import akgl.Units.GLTypes.GLObjectExtension;
import akgl.Units.Geometry.Vectors.Vec2;
import org.lwjgl.input.Mouse;

/**
 * @author Robert Kollar
 */
public class CameraBase extends GLObjectExtension {

    protected final Vec2 viewportStart = new Vec2();
    protected final Vec2 viewportSize = new Vec2();

    public Vec2 getViewportSize() {
        return viewportSize;
    }

    public Vec2 getViewportStart() {
        return viewportStart;
    }

    public static Vec2 GetMousePosition2D() {
        Vec2 position = new Vec2(Mouse.getX(), Mouse.getY());

        return position;
    }

}
