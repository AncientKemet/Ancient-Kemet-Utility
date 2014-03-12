/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package akgl.Units.Geometry.Vectors;

/**
 *
 * @author Robert Kollar
 */
public class Vec3 extends Vec2 {

    public Vec3(float x, float y, float z) {
        super(x, y);
        setZ(z);
    }

    public Vec3() {
    }

    private float z;

    public void setZ(float z) {
        this.z = z;
    }

    public float getZ() {
        return z;
    }

    public static Vec3 getOne() {
        Vec3 v = new Vec3();
        {
            v.setX(1);
            v.setY(1);
            v.setZ(1);
        }
        return v;
    }

}
