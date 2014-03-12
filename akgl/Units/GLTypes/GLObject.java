package akgl.Units.GLTypes;

import akgl.Units.GLTypes.Extensions.GLRenderer;
import akgl.Units.GLTypes.Extensions.GLTransform;
import akgl.Units.GLUnit;
import java.util.ArrayList;

/**
 *
 * @author Robert Kollar
 *
 * UNITY AKA GameObject
 */
public final class GLObject extends GLUnit {

    private GLObject parent;
    private ArrayList<GLObject> children = new ArrayList<GLObject>();

    private final ArrayList<GLObjectExtension> extensions = new ArrayList<GLObjectExtension>();

    private GLTransform transform;
    private GLRenderer renderer;

    public GLObject(GLObject parent) {
        setParent(parent);
    }

    /**
     * Returns parent of this GLUnit.
     *
     * @return the parent
     */
    public GLUnit getParent() {
        return parent;
    }

    /**
     * Sets a parent of this GLUnit.
     *
     * @param parent new parent
     */
    public void setParent(GLObject parent) {
        if (parent == this) {
            throw new Error("Cannot be parent to it self.");
        }
        //removing from old parent
        if (this.parent != null) {
            this.parent.children.remove(this);
        }
        //adding to new parent
        this.parent = parent;
        if (parent != null) {
            parent.children.add(this);
        }
    }

    /**
     * Returns a list of children GLUnits.
     *
     * @return the list
     */
    public ArrayList<GLObject> getChildren() {
        return children;
    }

    @Override
    public void onStart() {
    }

    public GLRenderer getRenderer() {
        return renderer;
    }

    public final void setRenderer(GLRenderer renderer) {
        if (this.renderer != null) {
            System.err.println("Warning changing renderer on GLObject.");
        }
        this.renderer = renderer;
    }

    public final GLTransform getTransform() {
        if (transform == null) {
            setTransform(new GLTransform());
        }
        return transform;
    }

    public final void setTransform(GLTransform transform) {
        if (this.transform != null) {
            System.err.println("Warning changing transform on GLObject.");
        }
        this.transform = transform;
        this.transform.setgLObject(this);
    }

    /**
     * Every frame render.
     */
    @Override
    public final void onRender() {
        if (getTransform() != null) {
            getTransform().pushMatrixLocal();
            {
                for (GLObjectExtension extension : extensions) {
                    extension.onRender();
                }
                if (renderer != null) {
                    renderer.render();
                }
                updateChildren();
            }
            getTransform().popMatrixLocal();
        } else {
            updateChildren();
        }
    }

    /**
     * Every frame render 2d.
     */
    @Override
    public void onRender2D() {
        if (getTransform() != null) {
            getTransform().pushMatrixLocal();
            {
                for (GLObjectExtension extension : extensions) {
                    extension.onRender2D();
                }
                if (renderer != null) {
                    renderer.render2D();
                }
                update2DChildren();
            }
            getTransform().popMatrixLocal();
        } else {
            update2DChildren();
        }
    }

    @Override
    public final void fixedUpdate() {
    }

    @Override
    public final void onUNUSED() {
    }

    /**
     * Is called when is being removed from AKGL.
     */
    @Override
    public final void onDestroy() {
        setParent(null);
    }

    public final void addExtension(GLObjectExtension extension) {
        extensions.add(extension);
        extension.setgLObject(this);
    }

    public final void removeExtension(GLObjectExtension extension) {
        extension.setgLObject(null);
        extensions.remove(extension);
    }

    private final void updateChildren() {
        for (GLUnit child : getChildren()) {
            child.Update();
        }
    }

    private final void update2DChildren() {
        for (GLUnit child : getChildren()) {
            child.Update2D();
        }
    }
}
