package aku.com.net;

import aku.com.net.packets.SuperPacket;
import aku.Data;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.reflections.Reflections;

/**
 * This class will create a static map of <OPCODE, CLASS> of type SuperPacket.
 * Then it will help a lot. :)
 * 
 * @author Robert Kollar
 */
public class PacketParsingHelper {

    private static Reflections reflections = new Reflections("aks.com.net.packets");    
    private static Set<Class<? extends SuperPacket>> classes = reflections.getSubTypesOf(SuperPacket.class);
    private static Map<Integer, Class<? extends SuperPacket>> opcodeTypeMap = new HashMap<Integer, Class<? extends SuperPacket>>();
    static{
        for (Class<? extends SuperPacket> class1 : classes) {
            if(Modifier.isAbstract(class1.getModifiers())){
                classes.remove(class1);
            }else{
                try {
                    opcodeTypeMap.put(class1.newInstance().getOpcode(), class1);
                } catch (InstantiationException | IllegalAccessException ex) {
                    Logger.getLogger(PacketParsingHelper.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    
    public static LinkedList<SuperPacket> parsePacketsFromData(Data d){
        LinkedList<SuperPacket> list = new LinkedList<>();
        if(d == null){
            return list;
        }
        
        while(d.offset() < d.getBuffer().size()){
            int opcode = d.getOpcode();
            if(opcode == 0){
                break;
            }
            SuperPacket packet;
            try {
                packet = opcodeTypeMap.get(opcode).newInstance();
                packet.readPacket(d);
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(PacketParsingHelper.class.getName()).log(Level.SEVERE, null, ex);
                throw new Error("Cant recognize opcode: "+opcode);
            }
            if(packet == null){
                System.exit(-101);
            }else{
                list.add(packet);
            }
        }
        return list;
    }
    
}
