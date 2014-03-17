package akgl.Units.GLTypes.Extensions.UI.Controls;

import akgl.Units.GLTypes.Extensions.UI.Text.TextLabel;
import akgl.Units.GLTypes.GLObject;
import akgl.Units.Geometry.Vectors.Vec2;

/**
 * @author Robert Kollar
 */
public class ButtonWithText extends SimpleButton {

    private TextLabel textLabel;

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

    public void setButtonText(String text) {
        textLabel.setText(text);
        textLabel.setAnchor(textLabel.getAnchor());

        Vec2 textBounds = textLabel.getBounds();

        slicedSprite.getDimesion().setX(textBounds.getX() / 2f - 8);
        slicedSprite.getDimesion().setY(textBounds.getY() / 2f - 8);

        slicedSprite.forceBuild();
    }

}
