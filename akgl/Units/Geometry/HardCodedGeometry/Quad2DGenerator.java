package akgl.Units.Geometry.HardCodedGeometry;

import akgl.Units.Buffers.*;
import akgl.Units.Buffers.Texture.Texture;
import akgl.Units.Geometry.*;
import akgl.Units.Geometry.Vectors.*;
import java.util.*;

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
        texcoords.add(new Vec2(0, 0));
        texcoords.add(new Vec2(1, 0));

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

    public static Mesh generateQuadForTexture(Texture texture) {
        Mesh quadMesh = new Mesh();
        List<Vec3> vertices = new ArrayList<Vec3>();
        List<Vec2> texcoords = new ArrayList<Vec2>();
        List<Triangle> triangles = new ArrayList<Triangle>();

        // vertices
        vertices.add(new Vec3(-0.5f * texture.getImageWidth(), -0.5f * texture.getImageHeight(), 0)); // 0
        vertices.add(new Vec3(0.5f * texture.getImageWidth(), -0.5f * texture.getImageHeight(), 0)); // 1
        vertices.add(new Vec3(-0.5f * texture.getImageWidth(), 0.5f * texture.getImageHeight(), 0)); // 2
        vertices.add(new Vec3(0.5f * texture.getImageWidth(), 0.5f * texture.getImageHeight(), 0)); // 3

        // texcoords
        texcoords.add(new Vec2(0, texture.getHeight()));
        texcoords.add(new Vec2(texture.getWidth(), texture.getHeight()));
        texcoords.add(new Vec2(0, 0));
        texcoords.add(new Vec2(texture.getWidth(), 0));

        //triangles
        triangles.add(new Triangle(0, 1, 2));
        triangles.add(new Triangle(1, 3, 2));

        quadMesh.setVertices(vertices);
        quadMesh.setTexCoords(texcoords);
        quadMesh.setTriangles(triangles);

        return quadMesh;
    }

    public static Mesh generateQuadForSubTexture(Texture texture, float startX, float startY, float width, float height) {
        Mesh quadMesh = new Mesh();
        List<Vec3> vertices = new ArrayList<Vec3>();
        List<Vec2> texcoords = new ArrayList<Vec2>();
        List<Triangle> triangles = new ArrayList<Triangle>();

        float minX = startX / texture.getImageWidth();
        float minY = startY / texture.getImageHeight();

        float maxX = minX + width / texture.getImageWidth();
        float maxY = minY + height / texture.getImageHeight();

        // vertices
        vertices.add(new Vec3(0, 0, 0)); // 0
        vertices.add(new Vec3(width, 0, 0)); // 1
        vertices.add(new Vec3(0, height, 0)); // 2
        vertices.add(new Vec3(width, height, 0)); // 3

        // texcoords
        texcoords.add(new Vec2(minX, maxY));
        texcoords.add(new Vec2(maxX, maxY));
        texcoords.add(new Vec2(minX, minY));
        texcoords.add(new Vec2(maxX, minY));

        //triangles
        triangles.add(new Triangle(0, 1, 2));
        triangles.add(new Triangle(1, 3, 2));

        quadMesh.setVertices(vertices);
        quadMesh.setTexCoords(texcoords);
        quadMesh.setTriangles(triangles);

        return quadMesh;
    }

}
