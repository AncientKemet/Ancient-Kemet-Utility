package aku.IO.AdvancedFileOperations.Files;

import aku.AncientKemetRegistry;
import java.io.File;

/**
 * @author Robert Kollar
 */
public class AKFile extends File {

    private String path;
   
    public AKFile(String path) {
        super(AncientKemetRegistry.AKInstallFolder+"/"+path);
        this.path = "/"+path;
        MakeSureItExists();
    }

    private void MakeSureItExists() {
        if (!exists()) {
            
                
                getParentFile().mkdirs();
        }
    }

}
