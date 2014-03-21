package akserver;

import aku.com.net.CommunicationHandler;
import java.net.Socket;

/**
 * @author Robert Kollar
 */
public class MasterClient {

    private final Socket socket;

    private final CommunicationHandler communicationHandler;

    private String username, password;

    public MasterClient(Socket socket) {
        this.socket = socket;
        this.communicationHandler = new CommunicationHandler(socket, new MasterClientPacketExecutor(this));
    }

    public void progress() {
        communicationHandler.readAndExecute();
    }

    public CommunicationHandler getCommunicationHandler() {
        return communicationHandler;
    }

}
