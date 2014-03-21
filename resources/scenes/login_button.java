/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package resources.scenes;

import akclient.AKClient;
import akgl.Units.GLTypes.Extensions.UI.Controls.Button;
import akgl.Units.GLTypes.Extensions.UI.Controls.TextField;
import aku.com.net.packets.Login.LoginRequestPacket;

/**
 *
 * @author User
 */
public class login_button extends Button {

    private long time;
    private TextField username;
    private TextField password;

    @Override
    protected void onExtensionAdded() {
        super.onExtensionAdded();
        setButtonText("Login");
    }

    @Override
    protected void onMouseJustDown(int button) {
        time = System.currentTimeMillis();

    }

    @Override
    protected void onMouseRelease(int button) {
        time -= System.currentTimeMillis();
        if (time < 200) {
            if (username.getText() != null && password.getText() != null) {
                System.out.println("username = " + username.getText() + " , password = " + password.getText());

                LoginRequestPacket packet = new LoginRequestPacket();

                packet.setUsername(username.getText());
                packet.setPassword(password.getText());

                AKClient.getInstance().getComHandler().write(packet);
            }
        }

    }

    public void setUsername(TextField username) {
        this.username = username;
    }

    public void setPassword(TextField password) {
        this.password = password;
    }

}
