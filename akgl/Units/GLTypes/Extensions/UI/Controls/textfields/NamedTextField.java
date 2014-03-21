package akgl.Units.GLTypes.Extensions.UI.Controls.textfields;

import akgl.Units.GLTypes.Extensions.UI.Controls.TextField;
import akgl.Units.GLTypes.Extensions.UI.Text.TextLabel;
import akgl.Units.GLTypes.GLObject;
import akgl.Units.Geometry.Vectors.Vec2;
import org.w3c.dom.Node;

/**
 * @author Robert Kollar
 */
public class NamedTextField extends TextField {

    @Override
    public void setText(String text) {
        this.text = text;
        String hidden = "";
        for (char character : text.toCharArray()) {
            hidden += "*";
        }
        textLabel.setText(hidden);
        textLabel.setAnchor(textLabel.getAnchor());

        slicedSprite.forceBuild();
    }

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

    public String getName() {
        return textLabel.getText();
    }

    public void setName(String newname) {
         textLabel.setText(newname);
    }

    @Override
    public void loadFromXML(Node node) {
        super.loadFromXML(node);
          Node nameoftextfield = node.getAttributes().getNamedItem("namedtextfield");
      
          if (nameoftextfield != null) {
            textLabel.setText(nameoftextfield.getNodeValue());
        }
    }
    
}
