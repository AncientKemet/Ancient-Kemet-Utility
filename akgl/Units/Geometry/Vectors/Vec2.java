/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package akgl.Units.Geometry.Vectors;

/**
 *
 * @author Robert Kollar
 */
public class Vec2 extends Vec1 {

    private float y;

    public Vec2() {
    }

    public Vec2(float x, float y) {
        super(x);
        this.y = y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getY() {
        return y;
    }
}
