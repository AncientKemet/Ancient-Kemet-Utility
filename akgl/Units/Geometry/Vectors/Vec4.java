/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package akgl.Units.Geometry.Vectors;

/**
 *
 * @author Robert Kollar
 */
public class Vec4 extends Vec3 {

    private float w;

    public Vec4(float x, float y, float z, float w) {
        super(x, y, z);
        setW(w);
    }

    public float getW() {
        return w;
    }

    public void setW(float w) {
        this.w = w;
    }

    public Vec4() {
    }

    public static Vec4 getOne() {
        return new Vec4(1, 1, 1, 1);
    }

    public void set(Vec4 vec) {
        super.set(vec);
        setW(vec.getW());
    }

}
