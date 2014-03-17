package aku.com.net.packets;

import aku.Data;

/**
 * @author Robert Kollar
 */
public abstract class SuperPacket {
   
    public final Data getDataOfPacket(){
        Data data = new Data();
        
        data.addByte(getOpcode());
        
        writePacket(data);
        
        return data;
    }
    
    public abstract void writePacket(Data data);

    public abstract void readPacket(Data data);
    
    public abstract int getOpcode();

}
