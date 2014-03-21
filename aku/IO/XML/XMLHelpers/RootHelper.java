package aku.IO.XML.XMLHelpers;

import akgl.AKGL;
import akgl.Units.GLTypes.*;
import org.w3c.dom.*;

/**
 * @author Robert Kollar
 */
public class RootHelper {

    public static void parseRoot(GLObject object, Node node) {
        if (node.hasAttributes() && node.getAttributes().getNamedItem("type").getNodeValue().equalsIgnoreCase("2D")) {
            object.setParent(AKGL.getRoot2D());
        } else {
            object.setParent(AKGL.getRoot2D());
        }
    }
}
