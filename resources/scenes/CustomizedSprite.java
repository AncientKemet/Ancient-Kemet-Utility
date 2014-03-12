package resources.scenes;

import akgl.Units.GLTypes.Extensions.Camera.CameraBase;
import akgl.Units.GLTypes.Extensions.UI.Sprites.BaseSprite;
import akgl.Units.Geometry.Vectors.Vec2;
import org.lwjgl.input.Keyboard;

/**
 * @author Robert Kollar
 */
//notice that im extending base sprite
public class CustomizedSprite extends BaseSprite {

    @Override
    public void SetupSprite() {
        System.out.println("My sprite has been constructed NOW!");
    }

    @Override
    public void OnPreRender() {
        // here we can manipulate the sprite easily coz im op

        Vec2 mousePositon = CameraBase.GetMousePosition2D();

        // really simpile function example
        {
            //follow the cursor if space is down
            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                getgLObject().getTransform().getPosition().setX(mousePositon.getX());
                getgLObject().getTransform().getPosition().setY(mousePositon.getY());
            }

            // resize the sprite to half if a mouse is over it
            if (isMouseHoveringOver(mousePositon)) {
                getScale().setX(100);
                getScale().setY(100);
            } else {
                getScale().setX(200);
                getScale().setY(200);
            }
        }
    }

}
