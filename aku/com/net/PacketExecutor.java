package aku.com.net;

import aku.com.net.packets.SuperPacket;

/**
 * 
 * @author Robert Kollar
 */
public abstract class PacketExecutor {

    public abstract void executePacket(SuperPacket packet);
    
}
