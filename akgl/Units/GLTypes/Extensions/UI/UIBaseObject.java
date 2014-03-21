package akgl.Units.GLTypes.Extensions.UI;

import akgl.Units.GLTypes.GLObjectExtension;
import akgl.Units.Geometry.Vectors.Vec2;
import akgl.Units.Geometry.Vectors.Vec4;
import aku.IO.XML.XMLComponent;
import aku.IO.XML.XMLHelpers.VectorParsing;
import org.w3c.dom.Node;

/**
 * @author Robert Kollar
 */
public class UIBaseObject extends GLObjectExtension implements XMLComponent {

    /**
     * @return the enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the color
     */
    public Vec4 getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Vec4 color) {
        this.color = color;
    }

    public enum UIAnchor {

        Center
    }

    private Vec4 color = Vec4.getOne();

    private boolean enabled;

    public Bounds2D getBounds() {
        return null;
    }

    @Override
    protected void onExtensionAdded() {
        super.onExtensionAdded();
        UIManager.getInstance().registerUIObject(this);
    }

    @Override
    protected void onExtensionRemoved() {
        super.onExtensionRemoved();
        UIManager.getInstance().unRegisterUIObject(this);
    }

    @Override
    public void loadFromXML(Node node) {
        Node colorNode = node.getAttributes().getNamedItem("color");
        Node isEnabledNode = node.getAttributes().getNamedItem("enabled");
        if (colorNode != null) {
            getColor().set(VectorParsing.parseVec4(colorNode));
        }
        if (isEnabledNode != null) {
            setEnabled(true);
        }
    }

    @Override
    public Node saveToXML(Node node) {
        return null;
    }

    public class Bounds2D {

        private final Vec2 center;
        private final Vec2 size;

        public Bounds2D(Vec2 center, Vec2 size) {
            this.center = center;
            this.size = size;
        }

        public Vec2 getCenter() {
            return center;
        }

        public Vec2 getSize() {
            return size;
        }

    }

}
