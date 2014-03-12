package akgl.Units.GLTypes;

/**
 * GLObjectExtension is a component to any<br>
 * GLObject, giving it any additional processing.
 *
 * @author Robert Kollar
 */
public class GLObjectExtension {

    private GLObject gLObject;

    /**
     * Returns the parent object.
     *
     * @return the object
     */
    public final GLObject getgLObject() {
        return gLObject;
    }

    /**
     * Set or move this extension to other GLObject
     *
     * @param gLObject the GLObject
     */
    public final void setgLObject(GLObject gLObject) {
        if (gLObject != null) {
            gLObject.removeExtension(this);
        }
        if (gLObject == null) {
            onExtensionRemoved();
        } else {
            this.gLObject = gLObject;
            onExtensionAdded();
            return;
        }
        this.gLObject = gLObject;
    }

    /**
     * Is called after this extension was added to new GLObject.
     */
    protected void onExtensionAdded() {
    }

    /**
     * Is called before this extension was removed from current GLObject.
     */
    protected void onExtensionRemoved() {
    }

    /**
     * Is called before 3d render.
     */
    public void onRender() {
    }

    /**
     * Is called before 2d render.
     */
    public void onRender2D() {
    }

}
