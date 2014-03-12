package akgl.Units;

/**
 * Class GLUnit.<br>
 *
 * GLUnit is a OpenGL related object, that has to implement some functions.
 *
 * @author Robert Kollar
 */
public abstract class GLUnit {

    private boolean hasStarted = false;

    private boolean active = true;

    /**
     * Chain update
     *
     * @deprecated is already implemented in AKGL.
     */
    @Deprecated
    public final void Update() {
        if (isActive()) {
            if (!hasStarted) {
                onStart();
            }
            onRender();
        }
    }

    /**
     * Chain update2d
     *
     * @deprecated is already implemented in AKGL.
     */
    @Deprecated
    public final void Update2D() {
        if (isActive()) {
            if (!hasStarted) {
                onStart();
            }
            onRender2D();
        }
    }

    public final boolean isActive() {
        return active;
    }

    public final void setActive(boolean active) {
        this.active = active;
    }

    public abstract void onStart();

    public abstract void onRender();

    public abstract void onRender2D();

    public abstract void fixedUpdate();

    public abstract void onUNUSED();

    public abstract void onDestroy();

}
