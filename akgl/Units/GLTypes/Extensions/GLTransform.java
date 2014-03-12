package akgl.Units.GLTypes.Extensions;

import akgl.Units.GLTypes.GLObjectExtension;
import akgl.Units.Geometry.Vectors.Vec3;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author Robert Kollar
 */
public class GLTransform extends GLObjectExtension {

    private Vec3 position = new Vec3();
    private Vec3 rotation = new Vec3();
    private Vec3 scale = Vec3.getOne();

    public Vec3 getPosition() {
        return position;
    }

    public Vec3 getRotation() {
        return rotation;
    }

    public Vec3 getScale() {
        return scale;
    }

    /**
     *
     * @deprecated uses deprecated OpenGL.
     */
    @Deprecated
    public void pushMatrixLocal() {
        GL11.glPushMatrix();
        GL11.glTranslatef(position.getX(), position.getY(), position.getZ());
        GL11.glRotatef(rotation.getY(), 0, 1, 0);
        GL11.glScalef(scale.getX(), scale.getY(), scale.getZ());
    }

    /**
     *
     * @deprecated uses deprecated OpenGL.
     */
    @Deprecated
    public void popMatrixLocal() {
        GL11.glPopMatrix();
    }

}
