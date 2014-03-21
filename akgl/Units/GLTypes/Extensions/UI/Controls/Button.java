package akgl.Units.GLTypes.Extensions.UI.Controls;

import akgl.Units.GLTypes.Extensions.UI.Text.TextLabel;
import akgl.Units.GLTypes.GLObject;
import akgl.Units.Geometry.Vectors.Vec2;
import org.w3c.dom.Node;

/**
 * @author Robert Kollar
 */
public class Button extends BaseButton {

    private TextLabel textLabel = new TextLabel();

    @Override
    protected void onExtensionAdded() {
        super.onExtensionAdded();

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

        Vec2 textBounds = textLabel.getBounds().getSize();

        slicedSprite.getDimesion().setX(textBounds.getX() / 2f - 8);
        slicedSprite.getDimesion().setY(textBounds.getY() / 2f - 8);

        slicedSprite.forceBuild();
    }

    @Override
    public void loadFromXML(Node node) {
        super.loadFromXML(node);
        setButtonText(node.getTextContent().trim());
    }

}
