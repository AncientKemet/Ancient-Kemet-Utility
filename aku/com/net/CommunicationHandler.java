/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aku.com.net;

import aku.com.net.packets.SuperPacket;
import aku.Data;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Von Bock
 */
public class CommunicationHandler {

    private Socket socket;
    private PacketExecutor executor;
    
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private Data inBuffer = new Data();

    private int currentInDataRate = 0;
    private int currentOutDataRate = 0;
    private int inTarget = -1;

    public CommunicationHandler(Socket socket, PacketExecutor executor) {
        this.socket = socket;
        this.executor = executor;
        if (socket == null || socket.isClosed()) {
            throw new Error("ERROR null socket.");
        } else {
            try {
                dataInputStream = new DataInputStream(this.socket.getInputStream());
                dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
            } catch (IOException ex) {
                Logger.getLogger(CommunicationHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void readAndExecute() {
        Data in = readRawData();
        for (SuperPacket packet : PacketParsingHelper.parsePacketsFromData(in)) {
            executor.executePacket(packet);
        }
    }

    /*
     * Returns null, if the whole packet wasn't loaded, else returns the packet
     * and clears it.
     */
    private Data readRawData() {
        synchronized (dataInputStream) {
            try {

                int ava = dataInputStream.available();
                if (ava > 0) {
                    if (ava > 4 && inTarget == -1) {
                        inTarget = dataInputStream.readInt();
                        ava -= 4;
                    }
                    if (inTarget != -1) {
                        int toRead = ava;
                        if (inBuffer.offset() + toRead > inTarget) {
                            toRead = inTarget - inBuffer.offset();
                        }
                        byte[] in = new byte[toRead];
                        dataInputStream.read(in);
                        inBuffer.addBytes(in);
                        currentInDataRate = toRead;

                        if (inBuffer.offset() == inTarget) {
                            inTarget = -1;
                            Data ret = inBuffer;
                            ret.setOffset(0);
                            inBuffer = new Data();
                            return ret;
                        }
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(CommunicationHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public void write(SuperPacket packet) {
        Data d = packet.getDataOfPacket();
        writeRawData(d.buffer());
    }

    /**
     * Writes certain data and flushes out.
     *
     * @param data - the data
     */
    private void writeRawData(byte[] data) {
        synchronized (dataOutputStream) {
            try {
                currentOutDataRate = data.length;
                dataOutputStream.writeInt(data.length);
                dataOutputStream.write(data);
                dataOutputStream.flush();
            } catch (IOException ex) {
                Logger.getLogger(CommunicationHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public int getCurrentInDataRate() {
        return currentInDataRate;
    }

    public int getCurrentOutDataRate() {
        return currentOutDataRate;
    }

}
