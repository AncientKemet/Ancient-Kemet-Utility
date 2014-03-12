package aku.IO.AdvancedFileOperations.Files;

import aku.AncientKemetRegistry;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Robert Kollar
 */
public class AKSFile extends File {

    private String path;

    public AKSFile() {
         super(AncientKemetRegistry.getServerDataFolder());
         this.path = "/";
    }
    
    public AKSFile(String path) {
        super(AncientKemetRegistry.getServerDataFolder() + "/" + path);
        this.path = "/" + path;
    }

    private void MakeSureItExists() {
        if (!exists()) {
            try {
                getParentFile().mkdirs();
                createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(AKSFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public String toString() {
        return "AKSFile : "+super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
    

}
