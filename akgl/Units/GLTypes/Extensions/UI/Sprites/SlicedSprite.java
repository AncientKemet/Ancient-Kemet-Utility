package akgl.Units.GLTypes.Extensions.UI.Sprites;

import akgl.Units.Buffers.Mesh;
import akgl.Units.GLTypes.Extensions.Camera.CameraBase;
import akgl.Units.Geometry.Triangle;
import akgl.Units.Geometry.Vectors.*;
import akgl.Units.Shaders.extensions.SpriteShader.SpriteShader;
import java.awt.Dimension;
import java.util.*;

/**
 * @author Robert Kollar
 */
public class SlicedSprite extends BaseSprite {

    private Vec2 dimesion = new Vec2(150, 250);
    protected float top = 0.5f;
    protected float left = 0.5f;
    protected float bottom = 0.5f;
    protected float right = 0.5f;

    private boolean rebuildMeshFlag = true;

    @Override
    public void SetupSprite() {
        setTexture("sliced");
        rebuildMeshFlag = true;
    }

    @Override
    public Mesh getMesh() {
        if (mesh == null) {
            mesh = rebuildMesh();
        }
        return mesh;
    }

    @Override
    public void OnPreRender() {
        if (rebuildMeshFlag) {
            if (dimesion.getX() < 0) {
                dimesion.setX(0);
            }
            if (dimesion.getY() < 0) {
                dimesion.setY(0);
            }
            mesh = rebuildMesh();
        }
    }

    private Mesh rebuildMesh() {
        Mesh quadMesh = new Mesh();
        List<Vec3> vertices = new ArrayList<Vec3>();
        List<Vec2> texcoords = new ArrayList<Vec2>();
        List<Triangle> triangles = new ArrayList<Triangle>();

        /**
         * 0-1-------------------2-3<br>
         * 4-5-------------------6-7<br>
         * -------------------------<br>
         * -------------------------<br>
         * 8-9------------------10-11<br>
         * 12-13----------------14-15<br>
         */
        //triangles
        triangles.add(new Triangle(0, 4, 5));
        triangles.add(new Triangle(0, 5, 1));
        triangles.add(new Triangle(1, 5, 6));
        triangles.add(new Triangle(1, 6, 2));
        triangles.add(new Triangle(2, 6, 7));
        triangles.add(new Triangle(2, 7, 3));
        triangles.add(new Triangle(4, 8, 9));
        triangles.add(new Triangle(4, 9, 5));
        triangles.add(new Triangle(5, 9, 10));
        triangles.add(new Triangle(5, 10, 6));
        triangles.add(new Triangle(6, 10, 11));
        triangles.add(new Triangle(6, 11, 7));
        triangles.add(new Triangle(8, 12, 13));
        triangles.add(new Triangle(8, 13, 9));
        triangles.add(new Triangle(9, 13, 14));
        triangles.add(new Triangle(9, 14, 10));
        triangles.add(new Triangle(10, 14, 15));
        triangles.add(new Triangle(10, 15, 11));

        // vertices
        vertices.add(new Vec3(-0.51f * getTexture().getImageWidth() - dimesion.getX(), 0.51f * getTexture().getImageHeight() + dimesion.getY(), 0)); // 0
        texcoords.add(new Vec2(0f, 0f));

        vertices.add(new Vec3(-0.01f * getTexture().getImageWidth() - dimesion.getX(), 0.51f * getTexture().getImageHeight() + dimesion.getY(), 0)); // 1
        texcoords.add(new Vec2(left, 0f));

        vertices.add(new Vec3(0.01f * getTexture().getImageWidth() + dimesion.getX(), 0.51f * getTexture().getImageHeight() + dimesion.getY(), 0)); // 2
        texcoords.add(new Vec2(1f - right, 0f));

        vertices.add(new Vec3(0.51f * getTexture().getImageWidth() + dimesion.getX(), 0.51f * getTexture().getImageHeight() + dimesion.getY(), 0)); // 3
        texcoords.add(new Vec2(1f, 0f));

        vertices.add(new Vec3(-0.51f * getTexture().getImageWidth() - dimesion.getX(), 0.01f * getTexture().getImageHeight() + dimesion.getY(), 0)); // 4
        texcoords.add(new Vec2(0, top));

        vertices.add(new Vec3(-0.01f * getTexture().getImageWidth() - dimesion.getX(), 0.01f * getTexture().getImageHeight() + dimesion.getY(), 0)); // 5
        texcoords.add(new Vec2(left, top));

        vertices.add(new Vec3(0.01f * getTexture().getImageWidth() + dimesion.getX(), 0.01f * getTexture().getImageHeight() + dimesion.getY(), 0)); // 6
        texcoords.add(new Vec2(1f - right, top));

        vertices.add(new Vec3(0.51f * getTexture().getImageWidth() + dimesion.getX(), 0.01f * getTexture().getImageHeight() + dimesion.getY(), 0)); // 7
        texcoords.add(new Vec2(1f, top));

        vertices.add(new Vec3(-0.51f * getTexture().getImageWidth() - dimesion.getX(), -0.01f * getTexture().getImageHeight() - dimesion.getY(), 0)); // 8
        texcoords.add(new Vec2(0, 1 - bottom));

        vertices.add(new Vec3(-0.01f * getTexture().getImageWidth() - dimesion.getX(), -0.01f * getTexture().getImageHeight() - dimesion.getY(), 0)); // 9
        texcoords.add(new Vec2(left, 1 - bottom));

        vertices.add(new Vec3(0.01f * getTexture().getImageWidth() + dimesion.getX(), -0.01f * getTexture().getImageHeight() - dimesion.getY(), 0)); // 10
        texcoords.add(new Vec2(1f - right, 1 - bottom));

        vertices.add(new Vec3(0.51f * getTexture().getImageWidth() + dimesion.getX(), -0.01f * getTexture().getImageHeight() - dimesion.getY(), 0)); // 11
        texcoords.add(new Vec2(1f, 1 - bottom));

        vertices.add(new Vec3(-0.51f * getTexture().getImageWidth() - dimesion.getX(), -0.51f * getTexture().getImageHeight() - dimesion.getY(), 0)); // 12
        texcoords.add(new Vec2(0, 1));

        vertices.add(new Vec3(-0.01f * getTexture().getImageWidth() - dimesion.getX(), -0.51f * getTexture().getImageHeight() - dimesion.getY(), 0)); // 13
        texcoords.add(new Vec2(left, 1));

        vertices.add(new Vec3(0.01f * getTexture().getImageWidth() + dimesion.getX(), -0.51f * getTexture().getImageHeight() - dimesion.getY(), 0)); // 14
        texcoords.add(new Vec2(1f - right, 1f));

        vertices.add(new Vec3(0.51f * getTexture().getImageWidth() + dimesion.getX(), -0.51f * getTexture().getImageHeight() - dimesion.getY(), 0)); // 15
        texcoords.add(new Vec2(1f, 1f));

        quadMesh.setVertices(vertices);
        quadMesh.setTexCoords(texcoords);
        quadMesh.setTriangles(triangles);

        rebuildMeshFlag = false;

        return quadMesh;
    }

    @Override
    public boolean isMouseHoveringOver(Vec2 mousePos) {
        Vec3 myPosition = getgLObject().getTransform().getLocalPosition();
        if (getTexture() != null) {
            if (mousePos.getX() > myPosition.getX() - getScale().getX() * (float) getTexture().getImageWidth() / 2f - dimesion.getX()
                    && mousePos.getX() < myPosition.getX() + getScale().getX() * (float) getTexture().getImageWidth() / 2f + dimesion.getX()) {
                if (mousePos.getY() > myPosition.getY() - getScale().getY() * (float) getTexture().getImageHeight() / 2f - dimesion.getY()
                        && mousePos.getY() < myPosition.getY() + getScale().getY() * (float) getTexture().getImageHeight() / 2f + dimesion.getY()) {
                    return true;
                }
            }
        }
        return false;
    }

    public Vec2 getDimesion() {
        return dimesion;
    }

    public void forceBuild() {
        this.rebuildMeshFlag = true;
    }

}
