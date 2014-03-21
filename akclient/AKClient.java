package akclient;

import akserver.*;
import aku.AncientKemetNET;
import aku.com.net.CommunicationHandler;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.*;

/**
 * @author Robert Kollar
 */
public class AKClient {

    private static AKClient instance;

    public static AKClient getInstance() {
        if (instance == null) {
            instance = new AKClient();
        }
        return instance;
    }

    private Socket masterServerSocket;

    private CommunicationHandler masterServerComHandler;

    private AKClient() {
        try {
            masterServerSocket = new Socket("89.177.126.149", AncientKemetNET.GAME_SERVER_PORT);
            masterServerComHandler = new CommunicationHandler(masterServerSocket, new AKClientPacketExecutor());
        } catch (IOException ex) {
            Logger.getLogger(AKClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    Thread serverThread = new Thread(new Runnable() {

        @Override
        public void run() {
            while (true) {

                masterServerComHandler.readAndExecute();

                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MasterServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    });

    public CommunicationHandler getComHandler() {
        return masterServerComHandler;
    }

}
