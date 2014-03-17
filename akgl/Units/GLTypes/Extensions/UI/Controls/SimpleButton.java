package akgl.Units.GLTypes.Extensions.UI.Controls;

import akgl.Units.GLTypes.Extensions.UI.Sprites.SlicedSprite;
import akgl.Units.GLTypes.Extensions.UI.Sprites.Sprite;
import akgl.Units.Geometry.Vectors.Vec2;
import aku.IO.BMFontLoader;
import akgl.Units.GLTypes.GLObject;

/**
 * @author Robert Kollar
 */
public class SimpleButton extends UIControl {

    protected SlicedSprite slicedSprite;
    private boolean[] down = new boolean[3];

    @Override
    protected void onExtensionAdded() {
        super.onExtensionAdded();
        slicedSprite = new SlicedSprite();
        slicedSprite.getDimesion().setX(50);
        slicedSprite.getDimesion().setY(0);
        slicedSprite.forceBuild();
        getgLObject().addExtension(slicedSprite);
    }

    @Override
    public void onMouseHover() {
        slicedSprite.getColor().setX(1.5f);
        slicedSprite.getColor().setY(1.5f);
        slicedSprite.getColor().setZ(1.5f);
        slicedSprite.getColor().setW(1);
    }

    @Override
    public final void onMouseDown(boolean[] buttons) {
        for (int i = 0; i < buttons.length; i++) {
            if (down[i] != buttons[i]) {
                if (down[i]) {
                    onMouseRelease(i);
                } else {
                    onMouseJustDown(i);
                }
            }
            down[i] = buttons[i];
            if (down[i]) {
                onMouseIsDown(i);
            }
        }
    }

    @Override
    public final boolean isHover(Vec2 mousePosition) {
        return slicedSprite.isMouseHoveringOver(mousePosition);
    }

    protected void onMouseRelease(int button) {
        System.out.println("unhandled event on mouse release = " + button);
    }

    protected void onMouseJustDown(int button) {
        System.out.println("unhandled event on mouse just down =  " + button);
    }

    protected void onMouseIsDown(int button) {
        slicedSprite.getColor().setX(0.75f);
        slicedSprite.getColor().setY(0.75f);
        slicedSprite.getColor().setZ(0.75f);
        slicedSprite.getColor().setW(1);
    }

    @Override
    public void onRender2D() {
        super.onRender2D();

        slicedSprite.getColor().setX(slicedSprite.getColor().getX() + (1 - slicedSprite.getColor().getX()) / 10f);
        slicedSprite.getColor().setY(slicedSprite.getColor().getY() + (1 - slicedSprite.getColor().getY()) / 10f);
        slicedSprite.getColor().setZ(slicedSprite.getColor().getZ() + (1 - slicedSprite.getColor().getZ()) / 10f);
        slicedSprite.getColor().setW(1);
    }

}
