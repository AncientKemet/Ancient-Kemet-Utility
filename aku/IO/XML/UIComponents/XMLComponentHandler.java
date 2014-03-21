package aku.IO.XML.UIComponents;

import akgl.Units.GLTypes.Extensions.UI.Controls.*;
import akgl.Units.GLTypes.Extensions.UI.*;
import akgl.Units.GLTypes.*;
import akgl.Units.GLTypes.Extensions.UI.Controls.TextField;
import akgl.Units.GLTypes.Extensions.UI.Controls.textfields.NamedTextField;
import akgl.Units.GLTypes.Extensions.UI.Controls.textfields.PasswordTextfield;
import akgl.Units.GLTypes.Extensions.UI.Sprites.SlicedSprite;
import akgl.Units.GLTypes.Extensions.UI.Sprites.Sprite;
import akgl.Units.GLTypes.Extensions.UI.UIBaseObject;
import akgl.Units.GLTypes.Extensions.UI.UIBaseObject;
import java.util.*;
import java.util.logging.*;
import org.w3c.dom.Node;

/**
 * @author Robert Kollar
 */
public class XMLComponentHandler {

    private static final HashMap<String, Class<? extends UIBaseObject>> xmlComponents = new HashMap<>();

    static {
        xmlComponents.put("text", TextField.class);
        xmlComponents.put("button", Button.class);
        xmlComponents.put("sprite", Sprite.class);
        xmlComponents.put("slicedsprite", SlicedSprite.class);
        xmlComponents.put("password", PasswordTextfield.class);
        xmlComponents.put("textfield", TextField.class);
        xmlComponents.put("textfieldname", NamedTextField.class);
    }

    public static void loadComponentToObject(GLObject glo, Node node) {
        if (!xmlComponents.containsKey(node.getNodeName())) {
            System.err.println("Cannot parse xml component: " + node + " with parent: " + node.getParentNode());
            return;
        }
        try {
            UIBaseObject compenent = xmlComponents.get(node.getNodeName()).newInstance();

            glo.addExtension(compenent);
            if (node.hasAttributes()) {
                compenent.loadFromXML(node);
            }

        } catch (InstantiationException ex) {
            Logger.getLogger(XMLComponentHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(XMLComponentHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
