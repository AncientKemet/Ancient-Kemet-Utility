/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aku.SYNC;

import aku.Data;

/**
 *
 * @author Robert Kollar
 */
public class SynchronizationPackets {

    static int STREAM_DATA_LENGHT = 512;

    static byte[] getSyncFileRequest(SyncFile sf) {
        Data d = new Data(0);
        d.addByte(1);
        d.addString(sf.getName());
        return d.buffer();
    }

    static byte[] getStreamedData(SyncFile sf) {
        Data d = new Data(0);
        d.addByte(2);
        d.addString(sf.getName());
        d.addShort(STREAM_DATA_LENGHT);

        return d.buffer();
    }

}
