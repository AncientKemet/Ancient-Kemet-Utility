/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aku.SYNC;

import aku.Data;
import java.util.LinkedList;

/**
 *
 * @author Robert Kollar
 */
public abstract class SyncManager<T> {

    SyncFile file;
    String fileName;

    public boolean decoded = false;

    private Data outBuffer = new Data(0);
    private Data inBuffer = new Data(0);

    public SyncManager(String fileName) {
        this.file = new SyncFile(fileName);
        this.fileName = fileName;
        Synchronization.list.add(this);
    }

    private LinkedList<T> list = new LinkedList<T>();

    public T get(int id) {
        if (file.isSynchronizationNeeded()) {
            return null;
        }
        if (list.get(id) == null) {
            file.setReady(false);
        }
        return list.get(id);
    }

    public void SyncrhonizationProgress() {
        file.progressIO();
        if (file.isSynchronizationNeeded()) {
            if (!file.requestForSyncrhonizationSent) {
                file.requestForSyncrhonizationSent = true;
                outBuffer.addBytes(SynchronizationPackets.getSyncFileRequest(file));
            }
        } else if (file.isReady()) {
            if (!decoded) {
                decodeData();
            }
        }
    }

    public Data getOutBuffer() {
        return outBuffer;
    }

    public Data getInBuffer() {
        return inBuffer;
    }

    public abstract void decodeData();

}
