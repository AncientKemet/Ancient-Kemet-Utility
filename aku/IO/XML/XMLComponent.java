package aku.IO.XML;

import org.w3c.dom.Node;

/**
 * Something like serializable interface, but custom XML.
 *
 * @author Robert Kollar
 */
public interface XMLComponent {

    /**
     * Load from XML node.
     *
     * @param node
     */
    public void loadFromXML(Node node);

    /**
     * TODO toNode
     */
    public Node saveToXML(Node node);

}
