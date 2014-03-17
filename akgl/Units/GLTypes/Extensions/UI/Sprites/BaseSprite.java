package akgl.Units.GLTypes.Extensions.UI.Sprites;

import akgl.Units.Buffers.Mesh;
import akgl.Units.Buffers.Texture.*;
import akgl.Units.GLTypes.Extensions.Renderer.*;
import akgl.Units.GLTypes.Extensions.UI.*;
import akgl.Units.Geometry.HardCodedGeometry.Quad2DGenerator;
import akgl.Units.Geometry.Vectors.*;
import akgl.Units.Shaders.extensions.SpriteShader.SpriteShader;
import aku.IO.*;

/**
 * @author Robert Kollar
 */
public abstract class BaseSprite extends UIBaseObject {

    private Vec2 scale = new Vec2(1, 1);
    private Vec4 color = new Vec4(1f, 1f, 1f, 1f);

    private Texture texture;
    protected Mesh mesh;

    private SpriteRenderer renderer;

    /**
     * When the extension has been added.
     */
    @Override
    public final void onExtensionAdded() {
        super.onExtensionAdded();

        renderer = new SpriteRenderer();

        //set this sprite as the onRender3D target
        renderer.setSprite(this);

        //set renderer to the object
        getgLObject().setRenderer(renderer);

        SetupSprite();
    }

    @Override
    public final void onRender2D() {
        super.onRender2D();
        OnPreRender();
        SpriteShader.getInstance().setColor(color);
    }

    public final void setTexture(String textureName) {
        this.texture = TextureLoader.getTexture("sprites/" + textureName + ".png");
    }

    public final Texture getTexture() {
        return texture;
    }

    public final Vec2 getScale() {
        return scale;
    }

    public final Vec4 getColor() {
        return color;
    }

    public Mesh getMesh() {
        if (mesh == null) {
            if (texture != null) {
                mesh = Quad2DGenerator.generateQuadForTexture(texture);
            }
        }
        return mesh;
    }

    /**
     * Is user hovering his mouse over this sprite.
     *
     * @param mousePos the position of mouse
     * @return true mouse is in bounds of this sprite, else false.
     */
    public boolean isMouseHoveringOver(Vec2 mousePos) {
        Vec3 myPosition = getgLObject().getTransform().getLocalPosition();
        if (texture != null) {
            if (mousePos.getX() > myPosition.getX() - scale.getX() * (float) texture.getImageWidth() / 2f
                    && mousePos.getX() < myPosition.getX() + scale.getX() * (float) texture.getImageWidth() / 2f) {
                if (mousePos.getY() > myPosition.getY() - scale.getY() * (float) texture.getImageHeight() / 2f
                        && mousePos.getY() < myPosition.getY() + scale.getY() * (float) texture.getImageHeight() / 2f) {
                    return true;
                }
            }
        }
        return false;
    }

    public abstract void SetupSprite();

    public abstract void OnPreRender();

}
