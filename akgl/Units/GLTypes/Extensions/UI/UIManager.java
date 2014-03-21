package akgl.Units.GLTypes.Extensions.UI;

import akgl.Units.GLTypes.Extensions.Camera.*;
import akgl.Units.GLTypes.Extensions.UI.Controls.*;
import akgl.Units.GLTypes.*;
import akgl.Units.GLTypes.Extensions.Camera.CameraBase;
import akgl.Units.GLTypes.Extensions.UI.Sprites.BaseSprite;
import akgl.Units.Geometry.Vectors.*;
import com.google.common.collect.UnmodifiableIterator;
import java.util.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

/**
 * @author Robert Kollar
 */
public class UIManager extends GLObjectExtension {

    /**
     * Singleton
     */
    private static UIManager instance;

    public static UIManager getInstance() {
        if (instance == null) {
            instance = new UIManager();
            GLObject holder = new GLObject(akgl.AKGL.getRoot2D());
            holder.addExtension(instance);
        }
        return instance;
    }

    private final ArrayList<UIBaseObject> objects = new ArrayList<>();
    private final ArrayList<UIControl> buttons = new ArrayList<>();
    private UIControl lastControl;

    private UIManager() {
    }

    public Collection<UIBaseObject> getRegisterdObjects() {
        return Collections.unmodifiableCollection(objects);
    }

    void registerUIObject(UIBaseObject object) {
        objects.add(object);
        if (UIControl.class.isAssignableFrom(object.getClass())) {
            buttons.add((UIControl) object);
        }
    }

    void unRegisterUIObject(UIBaseObject object) {
        objects.remove(object);
        if (UIControl.class.isAssignableFrom(object.getClass())) {
            buttons.remove(object);
        }
    }

    @Override
    public void onRender2D() {
        Vec2 mousePosition = CameraBase.GetMousePosition2D();

        boolean[] mouseDown = new boolean[]{Mouse.isButtonDown(0), Mouse.isButtonDown(1), Mouse.isButtonDown(2)};

        UIControl highestControl = null;

        for (UIControl control : buttons) {
            if (control.isHover(mousePosition)) {
                if (highestControl == null) {
                    highestControl = control;
                } else if (control.getgLObject().getTransform().getWorldPosition().getZ()
                        < highestControl.getgLObject().getTransform().getWorldPosition().getZ()) {
                    highestControl = control;
                }
            }
        }

        if (highestControl != null) {
            highestControl.onMouseHover();
            highestControl.onMouseDown(mouseDown);

            lastControl = highestControl;
        }

    }

}
