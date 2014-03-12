package akgl.Units.GLTypes;

/**
 *
 * @author Von Bock
 */
public class GLObjectExtension {

    private GLObject gLObject;

    public final GLObject getgLObject() {
        return gLObject;
    }

    public final void setgLObject(GLObject gLObject) {
        if (gLObject == null) {
            onExtensionRemoved();
        } else {
            this.gLObject = gLObject;
            onExtensionAdded();
            return;
        }
        this.gLObject = gLObject;
    }

    protected void onExtensionAdded() {
    }

    protected void onExtensionRemoved() {
    }

    public void onRender() {
    }

    public void onRender2D() {
    }

}
