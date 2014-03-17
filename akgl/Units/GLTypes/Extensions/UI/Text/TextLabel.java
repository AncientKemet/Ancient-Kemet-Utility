package akgl.Units.GLTypes.Extensions.UI.Text;

import akgl.Units.Buffers.Mesh;
import akgl.Units.Buffers.Texture.Texture;
import akgl.Units.GLSettings.*;
import akgl.Units.GLTypes.Extensions.Renderer.SpriteRenderer;
import akgl.Units.GLTypes.Extensions.UI.*;
import akgl.Units.Geometry.HardCodedGeometry.Quad2DGenerator;
import akgl.Units.Geometry.Vectors.*;
import akgl.Units.Shaders.extensions.SpriteShader.SpriteShader;
import aku.IO.*;
import java.awt.Font;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;

/**
 * @author Robert Kollar
 */
public class TextLabel extends UIBaseObject {

    private Vec4 color = new Vec4(1f, 1f, 1f, 1f);

    private BMFontLoader font;
    private String text = "missisng text";
    private UIBaseObject.UIAnchor anchor;

    /**
     * When the extension has been added.
     */
    @Override
    public final void onExtensionAdded() {
        super.onExtensionAdded();
        font = BMFontLoader.getBMFont("button");
    }

    @Override
    public final void onRender2D() {
        super.onRender2D();
        GLEnableDisable.Disable(GL_DEPTH_TEST);
        SpriteShader.getInstance().setColor(color);
        SpriteShader.getInstance().useShaderProgram();
        font.drawString(0, 0, text);
    }

    public final Vec2 getScale() {
        return getgLObject().getTransform().getScale();
    }

    public final Vec4 getColor() {
        return color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAnchor(UIAnchor anchor) {
        this.anchor = anchor;
        updateAnchors();
    }

    public UIAnchor getAnchor() {
        return anchor;
    }

    private void updateAnchors() {
        if (anchor == UIAnchor.Center) {
            getTransform().getLocalPosition().setX((float) -font.getWidth(text) / 2f * getScale().getX());
            getTransform().getLocalPosition().setY(((float) font.getHeight(text) / 2f - font.getLineHeight()) * getScale().getY());
        }
    }

    public Vec2 getBounds() {
        Vec2 bounds = new Vec2(getScale().getX() * (float) font.getWidth(text), getScale().getY() * (float) font.getHeight(text));
        return bounds;
    }

}
