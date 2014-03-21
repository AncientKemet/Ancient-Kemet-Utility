package akgl.Units.GLTypes.Extensions.UI.Controls.textfields;

import akgl.Units.GLTypes.Extensions.UI.Controls.TextField;
import akgl.Units.Geometry.Vectors.Vec2;

/**
 * @author Robert Kollar
 */
public class PasswordTextfield extends TextField {

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

}
