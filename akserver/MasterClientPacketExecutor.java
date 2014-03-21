package akserver;

import aku.com.net.PacketExecutor;
import aku.com.net.packets.SuperPacket;

/**
 * @author Robert Kollar
 */
public class MasterClientPacketExecutor extends PacketExecutor {

    private final MasterClient client;

    public MasterClientPacketExecutor(MasterClient client) {
        this.client = client;
    }

    @Override
    public void executePacket(SuperPacket packet) {
        System.out.println("Recieved packet: " + packet.getClass());
    }

}
