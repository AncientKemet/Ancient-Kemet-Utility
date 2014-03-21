package aku.IO.XML.XMLHelpers;

import akgl.AKGL;
import akgl.Units.GLTypes.GLObject;
import org.w3c.dom.Node;

/**
 * @author Robert Kollar
 */
public class SpriteHelper {

    public static void parse(GLObject object, Node node) {
        if (node.getAttributes().getNamedItem("type").getNodeValue().equalsIgnoreCase("2D")) {
            object.setParent(AKGL.getRoot2D());
        } else {
            object.setParent(AKGL.getRoot2D());
        }
    }
}
