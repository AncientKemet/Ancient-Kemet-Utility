package akserver;

import aku.com.net.PacketExecutor;
import aku.com.net.packets.SuperPacket;

/**
 * @author Robert Kollar
 */
public class ClientPacketExecutor extends PacketExecutor {

    private final Client client;

    public ClientPacketExecutor(Client client) {
        this.client = client;
    }

    @Override
    public void executePacket(SuperPacket packet) {
        System.out.println("Recieved packet: " + packet.getClass());
    }

}
