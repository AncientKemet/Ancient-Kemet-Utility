package resources.scenes;

import akgl.Units.GLTypes.Extensions.UI.Sprites.Sprite;
import akgl.AKGL;
import akgl.Units.GLTypes.Extensions.UI.Controls.SimpleButton;
import akgl.Units.GLTypes.Extensions.UI.Controls.ButtonWithText;
import akgl.Units.GLTypes.Extensions.UI.Controls.TextField;
import akgl.Units.GLTypes.Extensions.UI.Controls.textfields.PasswordTextfield;
import akgl.Units.GLTypes.Extensions.UI.Sprites.SlicedSprite;
import akgl.Units.GLTypes.GLObject;

/**
 * @author Robert Kollar
 */
public class LoginScene {

    private GLObject sceneRoot3D;
    private GLObject sceneRoot2D;

    /**
     * Hooking up on scene roots
     */
    public LoginScene() {
        sceneRoot3D = new GLObject(AKGL.getRoot3D());
        sceneRoot2D = new GLObject(AKGL.getRoot2D());
        constructScene();
    }

    /**
     * Constructing this scene in it's own space
     */
    private void constructScene() {
        //creating an simple sprite
        login_button button = new login_button();

        //creating an object that will hold this sprite
        GLObject buttonHolder = new GLObject(sceneRoot2D);

        //push button a bit away from camera
        buttonHolder.getTransform().getLocalPosition().setZ(-1);
        buttonHolder.getTransform().getLocalPosition().setX(500);
        buttonHolder.getTransform().getLocalPosition().setY(500);
        //adding the button to the object
        buttonHolder.addExtension(button);


        {
            TextField textfield = new TextField();

            //creating an object that will hold this sprite
            GLObject o = new GLObject(sceneRoot2D);

            //push button a bit away from camera
            o.getTransform().getLocalPosition().setZ(-1);
            o.getTransform().getLocalPosition().setX(500);
            o.getTransform().getLocalPosition().setY(400);
            //adding the button to the object
            o.addExtension(textfield);
            textfield.setText("");
            button.setUsername(textfield);
        }

        {
            PasswordTextfield textfield = new PasswordTextfield();

            //creating an object that will hold this sprite
            GLObject o = new GLObject(sceneRoot2D);

            //push button a bit away from camera
            o.getTransform().getLocalPosition().setZ(-1);
            o.getTransform().getLocalPosition().setX(500);
            o.getTransform().getLocalPosition().setY(300);
            //adding the button to the object
            o.addExtension(textfield);
            textfield.setText("");
            button.setPassword(textfield);
        }

    }

}
