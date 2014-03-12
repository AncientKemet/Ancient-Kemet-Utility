/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package akgl.Units.Geometry.Animation;

import java.nio.FloatBuffer;
import java.util.LinkedList;
import java.util.List;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;

/**
 *
 * @author Von Bock
 */
public class Bone {

  private Bone parent;
  private List<Bone> children = new LinkedList<Bone>();
  private int _id;

  public List<Bone> getChildren() {
    return children;
  }

  public void setId(int id) {
    this._id = id;
  }

  public int getId() {
    return _id;
  }
  private Matrix4f matrix;

  public Bone() {
  }

  public void setParent(Bone parent) {
    this.parent = parent;
    if (parent != null) {
      parent.getChildren().add(this);
    }
  }

  public Bone getParent() {
    return parent;
  }

  public Matrix4f getMatrix() {
    if (matrix == null) {
      matrix = new Matrix4f();
      matrix.setIdentity();
    }
    return matrix;
  }

  public FloatBuffer getMatrixBuffer() {
    FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
    Matrix4f mat = new Matrix4f();
    this.matrix.transpose(mat);
    mat.store(buffer);
    buffer.flip();
    return buffer;
  }

  public void setMatrix(float[] matrix) {
    FloatBuffer buffer = BufferUtils.createFloatBuffer(matrix.length).put(matrix);
    buffer.flip();
    getMatrix().load(buffer);
  }

  public void setMatrix(Matrix4f matrix) {
    this.matrix = matrix;
  }

  public Bone copy() {
    Bone b = new Bone();
    b.setId(getId());
    b.setMatrix(getMatrix());
    return b;
  }
}
