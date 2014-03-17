package aku.com.net.packets.Login;

import aku.com.net.packets.SuperPacket;
import aku.Data;

/**
 * @author Robert Kollar
 */
public class LoginRequestPacket extends SuperPacket {
    
    public static final int OPCODE = 100;

    private String username, password;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }    
    
    @Override
    public void writePacket(Data data) {
        data.addCodedString(username);
        data.addCodedString(password);
    }

    @Override
    public void readPacket(Data data) {
        username = data.getCodedString();
        password = data.getCodedString();
    }

    @Override
    public int getOpcode() {
        return OPCODE;
    }

}
