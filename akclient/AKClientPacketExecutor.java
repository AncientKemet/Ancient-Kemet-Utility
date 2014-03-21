package akclient;

import aku.com.net.PacketExecutor;
import aku.com.net.packets.SuperPacket;

/**
 * @author Robert Kollar
 */
public class AKClientPacketExecutor extends PacketExecutor {

    @Override
    public void executePacket(SuperPacket packet) {
        System.out.println("Recieved packet: " + packet.getClass());
    }

}
