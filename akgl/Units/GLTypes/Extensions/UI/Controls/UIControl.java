package akgl.Units.GLTypes.Extensions.UI.Controls;

import akgl.Units.GLTypes.Extensions.UI.UIBaseObject;
import akgl.Units.Geometry.Vectors.Vec2;

/**
 * @author Robert Kollar
 */
public abstract class UIControl extends UIBaseObject {

    public abstract void onMouseHover();

    public abstract void onMouseDown(boolean[] buttons);

    public abstract boolean isHover(Vec2 mousePosition);

}
