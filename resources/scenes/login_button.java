/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package resources.scenes;

import akgl.Units.GLTypes.Extensions.UI.Controls.ButtonWithText;
import akgl.Units.GLTypes.Extensions.UI.Controls.TextField;

/**
 *
 * @author User
 */
public class login_button extends ButtonWithText{

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
        time=System.currentTimeMillis();
        
    }

    @Override
    protected void onMouseRelease(int button) {
        time-=System.currentTimeMillis();
        if(time<200)
         if(username.getText()!=null && password.getText()!=null)
         System.out.println("username = " + username.getText() + " , password = " + password.getText());
        
    }

    public void setUsername(TextField username) {
        this.username = username;
    }

    public void setPassword(TextField password) {
        this.password = password;
    }
    
    
}
