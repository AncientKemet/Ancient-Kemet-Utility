package aku.IO.XML;

import akgl.Units.GLTypes.Extensions.UI.Sprites.SlicedSprite;
import akgl.Units.GLTypes.Extensions.UI.Text.TextLabel;
import akgl.Units.GLTypes.GLObject;
import aku.AncientKemetRegistry;
import aku.IO.XML.UIComponents.XMLComponentHandler;
import aku.IO.XML.XMLHelpers.RootHelper;
import aku.IO.XML.XMLHelpers.VectorParsing;
import java.net.URL;
import java.util.List;
import javax.xml.parsers.*;
import org.w3c.dom.*;

/**
 * @author Robert Kollar
 */
public class XMLLoader {

    public static GLObject loadCompositionFromKXML(String filename) throws Exception {
        //Get the DOM Builder Factory
        DocumentBuilderFactory factory
                = DocumentBuilderFactory.newInstance();

        //Get the DOM Builder
        DocumentBuilder builder = factory.newDocumentBuilder();

        //Load and Parse the XML document
        //document contains the complete XML as a Tree.
        Document document
                = builder.parse(new URL(AncientKemetRegistry.getDataHost() + "xml/" + filename + ".xml").openStream());

        //Iterating through the nodes and extracting the data.
        return parseNode(document.getDocumentElement());
    }

    public static GLObject parseNode(Node node) {
        GLObject glo = new GLObject(null);
        /*
         * parse atributes
         */
        //tranforms
        if (node.hasAttributes()) {
            Node position = node.getAttributes().getNamedItem("pos");
            Node scale = node.getAttributes().getNamedItem("scale");
            if (position != null) {
                glo.getTransform().getLocalPosition().set(VectorParsing.parseVec3(position));
            }
            if (scale != null) {
                glo.getTransform().getScale().set(VectorParsing.parseVec3(scale));
            }
        }
        //use helpers for the rest
        switch (node.getNodeName()) {
            case "root":
                RootHelper.parseRoot(glo, node);
                break;
            default:
                XMLComponentHandler.loadComponentToObject(glo, node);

        }


        /*
         * parse children
         */
        if (node.hasChildNodes()) {
            NodeList nodeList = node.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                if (!nodeList.item(i).getNodeName().equalsIgnoreCase("#text")) {
                    GLObject child = parseNode(nodeList.item(i));
                    child.setParent(glo);
                }
            }
        }
        return glo;
    }

}
