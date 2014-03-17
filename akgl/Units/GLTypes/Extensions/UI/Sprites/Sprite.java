package akgl.Units.GLTypes.Extensions.UI.Sprites;

import akgl.Units.GLTypes.Extensions.Camera.CameraBase;
import akgl.Units.GLTypes.Extensions.UI.Sprites.BaseSprite;
import akgl.Units.Geometry.Vectors.Vec2;
import akgl.Units.Geometry.Vectors.Vec4;
import akgl.Units.Shaders.extensions.SpriteShader.SpriteShader;
import org.lwjgl.input.Keyboard;

/**
 * @author Robert Kollar
 */
public class Sprite extends BaseSprite {

    @Override
    public void SetupSprite() {
        System.out.println("My sprite has been constructed NOW!");
        setTexture("simple");
    }

    @Override
    public void OnPreRender() {
        // here we can manipulate the sprite easily coz im op

        Vec2 mousePositon = CameraBase.GetMousePosition2D();

        // really simpile function example
        {
            //follow the cursor if space is down
            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                getgLObject().getTransform().getLocalPosition().setX(mousePositon.getX());
                getgLObject().getTransform().getLocalPosition().setY(mousePositon.getY());
            }

            // resize the sprite to half if a mouse is over it
            if (isMouseHoveringOver(mousePositon)) {
                SpriteShader.getInstance().setColor(new Vec4(1.5f, 1.5f, 1.5f, 1));
            } else {
                SpriteShader.getInstance().setColor(new Vec4(1, 1, 1, 1));
            }
        }
    }

}
