package akgl.Units.GLTypes.Extensions.Renderer;

import akgl.Units.Buffers.Mesh;
import akgl.Units.GLTypes.Extensions.*;
import akgl.Units.GLTypes.Extensions.UI.Sprites.*;
import akgl.Units.Geometry.HardCodedGeometry.Quad2DGenerator;
import org.lwjgl.opengl.GL11;

/**
 * @author Robert Kollar
 */
public class SpriteRenderer extends GLRenderer {

    /**
     * Static quad
     */
    private static Mesh quad;

    private static Mesh getQuad() {
        if (quad == null) {
            quad = Quad2DGenerator.getQuad();
        }
        return quad;
    }

    private BaseSprite sprite;

    public void setSprite(BaseSprite sprite) {
        this.sprite = sprite;
    }

    public BaseSprite getSprite() {
        return sprite;
    }

    @Override
    public void render() {

    }

    @Override
    public void render2D() {
        if (sprite != null) {
            GL11.glPushMatrix();
            GL11.glScalef(sprite.getScale().getX(), sprite.getScale().getY(), 1);
            //sprite.getTexture().bind();
            getQuad().render();
            GL11.glPopMatrix();
        }
    }

}
