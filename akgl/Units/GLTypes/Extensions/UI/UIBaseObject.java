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

    public enum UIAnchor {

        Center
    }

    protected Vec4 color = Vec4.getOne();

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
        if (colorNode != null) {
            color.set(VectorParsing.parseVec4(colorNode));
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
