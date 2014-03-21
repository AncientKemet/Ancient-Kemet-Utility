package akgl.Units.GLTypes.Extensions.UI.Containers;

import akgl.Units.GLTypes.Extensions.UI.UIBaseObject;
import aku.IO.XML.XMLHelpers.VectorParsing;
import org.w3c.dom.Node;

/**
 * @author Robert Kollar
 */
public class UserInterface extends UIBaseObject {

    private String name = "missing interface name";

    @Override
    public void loadFromXML(Node node) {
        super.loadFromXML(node);
        setName(node.getTextContent().trim());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Bounds2D getBounds() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
