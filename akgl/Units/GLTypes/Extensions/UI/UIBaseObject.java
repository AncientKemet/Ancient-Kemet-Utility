package akgl.Units.GLTypes.Extensions.UI;

import akgl.Units.GLTypes.GLObjectExtension;

/**
 * @author Robert Kollar
 */
public class UIBaseObject extends GLObjectExtension {

    public enum UIAnchor {

        Center
    }

    @Override
    protected void onExtensionAdded() {
        super.onExtensionAdded();
        UIManager.getInstance().registerUIObject(this);
    }

    @Override
    protected void onExtensionRemoved() {
        super.onExtensionRemoved();
        UIManager.getInstance().unRegisterUIObject(this);
    }

}
