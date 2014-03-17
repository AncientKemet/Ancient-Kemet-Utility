package akgl.Units.GLTypes.Extensions.UI.Controls;

import akgl.Units.GLTypes.Extensions.UI.Text.TextLabel;
import akgl.Units.GLTypes.GLObject;
import akgl.Units.Geometry.Vectors.Vec2;
import java.awt.RenderingHints;
import org.lwjgl.input.Keyboard;

/**
 * @author Robert Kollar
 */
public class TextField extends SimpleButton {

    private static TextField inputingOn = null;

    protected TextLabel textLabel;
    protected String text = "";
    protected boolean multipleLinesAllowed = false;
    protected int maxCharacters = -1;

    @Override
    protected void onExtensionAdded() {
        super.onExtensionAdded(); //To change body of generated methods, choose Tools | Templates.
        textLabel = new TextLabel();

        GLObject textLabelHolder = new GLObject(getgLObject());
        textLabelHolder.addExtension(textLabel);
        textLabel.getScale().setX(0.75f);
        textLabel.getScale().setY(0.75f);
        textLabel.getTransform().getLocalPosition().setZ(1);
        textLabel.setAnchor(UIAnchor.Center);
    }

    public void setText(String text) {
        this.text = text;
        textLabel.setText(text);
        textLabel.setAnchor(textLabel.getAnchor());

        Vec2 textBounds = textLabel.getBounds();

        slicedSprite.getDimesion().setX(textBounds.getX() / 2f - 8);
        slicedSprite.getDimesion().setY(textBounds.getY() / 2f - 8);

        slicedSprite.forceBuild();
    }

    public String getText() {
        return text;
    }

    public void setMaxCharacters(int maxCharacters) {
        this.maxCharacters = maxCharacters;
    }

    public int getMaxCharacters() {
        return maxCharacters;
    }

    public void onCharacter(char c) {
        setText(text + c);
    }

    @Override
    public void onRender2D() {
        super.onRender2D();
        if (inputingOn != null && inputingOn.equals(this)) {
            if (Keyboard.next()) {
                if (Keyboard.getEventKeyState()) {
                    //backspace
                    System.out.println(Keyboard.getEventKey());
                    if (Keyboard.getEventKey() == 14) {
                        if (text.length() > 0) {
                            setText(text.substring(0, text.length() - 1));
                        }
                    } else if (Keyboard.getEventKey() == 28) { // new line
                        onCharacter('\n');
                    } else {
                        if (Character.isDefined(Keyboard.getEventCharacter())) {
                            onCharacter(Keyboard.getEventCharacter());
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void onMouseRelease(int button) {
        if (inputingOn == this) {
            inputingOn = null;
        } else {
            inputingOn = this;
        }
    }

}
