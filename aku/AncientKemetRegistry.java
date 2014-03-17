package aku;

import java.io.File;
import java.io.IOException;

public class AncientKemetRegistry {

    private static String folderDir = System.getProperty("user.home") + "/Documents/Ancient Kemet";
    private static String fileDir = folderDir + "/registry.txt";

    public static String AKInstallFolder = System.getProperty("user.home") + "/Documents/Ancient Kemet/";
    public static String AKRunningFolder = System.getProperty("user.home") + "/Documents/Ancient Kemet/";
    public static String AKDataFolder = System.getProperty("user.home") + "/Documents/Ancient Kemet/data";
    public static String DeveloperUsername = "user";
    public static String DeveloperPassword = "password";
    private static String AKServerDataFolder = System.getProperty("user.home") + "/Documents/Ancient Kemet/server/data/";
    private static int expansion = 1;
    private static int chapter = 0;
    private static int patch = 0;
    private static String version = "missing version";

    public static void AppendRunningFolder(String path) {
        AKInstallFolder = path;
        AKRunningFolder = path;
        AKDataFolder = AKInstallFolder + File.separator + "data";
    }

    public static void checkFolder() {
        File folder = new File(folderDir);
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    public static void checkFile() {
        File file = new File(fileDir);
        if (!file.exists()) {
            try {
                file.createNewFile();
                updateRegister();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void loadRegister() {
        checkFolder();
        checkFile();

        File file = new File(fileDir);
        Data buffer = new Data(0);
        buffer.loadUpFromFile(file);

        expansion = buffer.getInt();
        chapter = buffer.getInt();
        patch = buffer.getInt();
        updateVersion();

        DeveloperUsername = buffer.getString();
        DeveloperPassword = buffer.getString();

        System.out.println("Loaded registry: v" + version + " du: " + DeveloperUsername + " dp: " + DeveloperPassword);
    }

    public static void updateRegister() {
        checkFolder();
        checkFile();

        File file = new File(fileDir);

        Data buffer = new Data(0);

        buffer.addInt(expansion);
        buffer.addInt(chapter);
        buffer.addInt(patch);
        buffer.addString(DeveloperUsername);
        buffer.addString(DeveloperPassword);
        buffer.writeToFile(file);
    }

    private static void updateVersion() {
        /*
         * 127
         */ version = expansion + "." + chapter + "." + patch;
        /*
         * 128
         */ System.out.println("Updated version to: " + version);
    }

    public static String getVersion() {
        /*
         * 132
         */ updateVersion();
        /*
         * 133
         */ return version;
    }

    public static int getPatch() {
        /*
         * 137
         */ return patch;
    }

    public static int getExpansion() {
        /*
         * 141
         */ return expansion;
    }

    public static int getRevision() {
        /*
         * 145
         */ return chapter;
    }

    public static String getFolderDir() {
        /*
         * 149
         */ return folderDir;
    }

    /**
     *
     * @return http://olblood.mzzhost.com/client-beta/data/
     */
    public static String getDataHost() {
        return "http://olblood.mzzhost.com/client-beta/data/";
    }

    public static String getServerDataFolder() {
        return AKServerDataFolder;
    }
}
