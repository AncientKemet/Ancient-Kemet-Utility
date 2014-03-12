package akgl.Units.Geometry.HardCodedGeometry;

import akgl.Units.Buffers.Mesh;
import akgl.Units.Geometry.Triangle;
import akgl.Units.Geometry.Vectors.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Robert Kollar
 */
public class Quad2DGenerator {

    private static List<Vec3> vertices = new ArrayList<Vec3>();
    private static List<Vec2> texcoords = new ArrayList<Vec2>();
    private static List<Triangle> triangles = new ArrayList<Triangle>();

    private static Mesh quad;

    static {
        quad = new Mesh();

        // vertices
        vertices.add(new Vec3(-0.5f, -0.5f, 0)); // 0
        vertices.add(new Vec3(0.5f, -0.5f, 0)); // 1
        vertices.add(new Vec3(-0.5f, 0.5f, 0)); // 2
        vertices.add(new Vec3(0.5f, 0.5f, 0)); // 3

        // texcoords
        texcoords.add(new Vec2(0, 1));
        texcoords.add(new Vec2(1, 1));
        texcoords.add(new Vec2(1, 0));
        texcoords.add(new Vec2(0, 0));

        //triangles
        triangles.add(new Triangle(0, 1, 2));
        triangles.add(new Triangle(1, 3, 2));

        quad.setVertices(vertices);
        quad.setTexCoords(texcoords);
        quad.setTriangles(triangles);
    }

    /**
     * Do not change the properties else all quads in game might get deformed.
     *
     * @return 1x1 quad with tex coords
     */
    public static Mesh getQuad() {
        return quad;
    }

}
