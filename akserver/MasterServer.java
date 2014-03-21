package akserver;

import aku.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.*;

/**
 * @author Robert Kollar
 */
public class MasterServer {

    private ServerSocket serverSocket;

    private List<MasterClient> clients = new ArrayList<>();

    public MasterServer() {
        try {
            serverSocket = new ServerSocket(AncientKemetNET.GAME_SERVER_PORT);
            serverThread.start();
        } catch (IOException ex) {
            System.out.println("Failed to create socket.");
            Logger.getLogger(MasterServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    Thread serverThread = new Thread(new Runnable() {

        @Override
        public void run() {
            while (true) {

                for (MasterClient client : clients) {
                    client.progress();
                }

                try {
                    //accept connections
                    Socket socket = serverSocket.accept();

                    if (socket != null) {
                        MasterClient newClient = new MasterClient(socket);
                        System.out.println("New client");
                        clients.add(newClient);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(MasterServer.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MasterServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    });

    public static void main(String[] args) {
        new MasterServer();

    }

}
