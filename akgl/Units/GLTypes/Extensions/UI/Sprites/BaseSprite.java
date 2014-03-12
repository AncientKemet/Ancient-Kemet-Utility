package akgl.Units.GLTypes.Extensions.UI.Sprites;

import akgl.Units.Buffers.Texture.Texture;
import akgl.Units.GLTypes.Extensions.Renderer.*;
import akgl.Units.GLTypes.Extensions.UI.*;
import akgl.Units.Geometry.Vectors.*;

/**
 * @author Robert Kollar
 */
public abstract class BaseSprite extends UIBaseObject {

    private Vec2 scale = new Vec2(100, 100);

    private Texture texture;

    /**
     * When an extension is added.
     */
    @Override
    public final void onExtensionAdded() {
        super.onExtensionAdded();

        //create an renderer
        final SpriteRenderer renderer = new SpriteRenderer();

        //set this sprite as the render target
        renderer.setSprite(this);

        //set renderer to the object
        getgLObject().setRenderer(renderer);
        SetupSprite();
    }

    @Override
    public final void onRender2D() {
        super.onRender2D(); //To change body of generated methods, choose Tools | Templates.
        OnPreRender();
    }

    public final void setTexture(Texture texture) {
        this.texture = texture;
    }

    public final Texture getTexture() {
        return texture;
    }

    public final Vec2 getScale() {
        return scale;
    }

    public final boolean isMouseHoveringOver(Vec2 mousePos) {
        Vec3 myPosition = getgLObject().getTransform().getPosition();
        if (mousePos.getX() > myPosition.getX() - scale.getX() / 2f && mousePos.getX() < myPosition.getX() + scale.getX() / 2f) {
            if (mousePos.getY() > myPosition.getY() - scale.getY() / 2f && mousePos.getY() < myPosition.getY() + scale.getY() / 2f) {
                return true;
            }
        }
        return false;
    }

    public abstract void SetupSprite();

    public abstract void OnPreRender();

}
