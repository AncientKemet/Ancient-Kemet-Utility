package aku.com.net.packets.Login;

import aku.com.net.packets.SuperPacket;
import aku.Data;

/**
 * @author Robert Kollar
 */
public class LoginResultPacket extends SuperPacket {
    
    private boolean approved = false;

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    @Override
    public void writePacket(Data data) {
        data.addByte(isApproved() ? 1 : 0);
    }

    @Override
    public void readPacket(Data data) {
        setApproved(data.getUnsignedByte() == 1);
    }

    @Override
    public int getOpcode() {
        return 102;
    }

}
