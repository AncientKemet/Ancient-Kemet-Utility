package akgl.Units.GLTypes.Extensions.Renderer;

import akgl.Units.GLSettings.*;
import akgl.Units.GLTypes.Extensions.*;
import akgl.Units.GLTypes.Extensions.UI.Sprites.*;
import akgl.Units.Shaders.extensions.SpriteShader.*;
import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;

/**
 * @author Robert Kollar
 */
public class SpriteRenderer extends GLRenderer {

    public SpriteRenderer() {
        setProgram(SpriteShader.getInstance());
    }

    private BaseSprite sprite;

    public void setSprite(BaseSprite sprite) {
        this.sprite = sprite;
    }

    public BaseSprite getSprite() {
        return sprite;
    }

    @Override
    public void onRender3D() {

    }

    @Override
    public void onRender2D() {
        if (sprite != null && sprite.getMesh() != null && sprite.getTexture() != null) {
            GLEnableDisable.Enable(GL_TEXTURE_2D, GL_BLEND);

            GL11.glPushMatrix();
            GL11.glScalef(sprite.getScale().getX(), sprite.getScale().getY(), 1);
            sprite.getTexture().bind();
            sprite.getMesh().render();
            GL11.glPopMatrix();
        }
    }

}
