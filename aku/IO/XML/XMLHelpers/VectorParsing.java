package aku.IO.XML.XMLHelpers;

import akgl.Units.Geometry.Vectors.*;
import org.w3c.dom.Node;

/**
 * @author Robert Kollar
 */
public class VectorParsing {

    public static Vec2 parseVec2(Node node) {
        String[] split = node.getNodeValue().split(" ");
        return new Vec2(Float.parseFloat(split[0]), Float.parseFloat(split[1]));
    }

    public static Vec3 parseVec3(Node node) {
        String[] split = node.getNodeValue().split(" ");
        return new Vec3(Float.parseFloat(split[0]), Float.parseFloat(split[1]), Float.parseFloat(split[2]));
    }

    public static Vec4 parseVec4(Node node) {
        String[] split = node.getNodeValue().split(" ");
        return new Vec4(Float.parseFloat(split[0]), Float.parseFloat(split[1]), Float.parseFloat(split[2]), Float.parseFloat(split[3]));
    }

}
