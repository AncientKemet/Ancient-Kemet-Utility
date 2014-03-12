/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aku.SYNC;

import aku.Data;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Robert Kollar
 */
public class Synchronization {

    public static List<SyncManager> list = new LinkedList<SyncManager>();

    private static int PTR = 0;
    private static Data outBuffer = new Data(0);

    public static Data getOutBuffer() {
        return outBuffer;
    }

    public static boolean inData(String fileName, byte[] data) {
        for (SyncManager sm : list) {
            if (sm.fileName.equals(fileName)) {
                sm.file.setBytesToWrite(data);
                return true;
            }
        }
        return false;
    }

    public static void progress() {
        if (list.size() > PTR) {
            list.get(PTR).SyncrhonizationProgress();
            outBuffer.addBytes(list.get(PTR).getOutBuffer().buffer());
        }
        PTR++;
    }

}
