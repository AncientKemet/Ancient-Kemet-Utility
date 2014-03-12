/*    */ package aku.IO.AdvancedFileOperations.Folders;
/*
 *
/*
 *  import java.io.File;
/*
 *
/*
 *  public class Folders /*
 *  {
    /*
     *   */ public static void fixLastTimeModification(String localDir) /*
     *   */ {
        /*
         * 16
         */ File thisFolder = new File(localDir);
        /*
         * 17
         */ long lastTimeMod = 0L;
        /*
         * 18
         */ for (File f : thisFolder.listFiles()) {
            /*
             * 19
             */ if (f.isDirectory()) {
                /*
                 * 20
                 */ fixLastTimeModification(f.getAbsolutePath());
                /*
                 * 21
                 */ if (f.lastModified() > lastTimeMod) {
                    /*
                     * 22
                     */ thisFolder.setLastModified(f.lastModified());
                    /*
                     * 23
                     */ lastTimeMod = thisFolder.lastModified();
                    /*
                     *                   */                }
                /*
                 *               */            }
            /*
             * 26
             */ if ((f.isFile())
                    && /*
                     * 27
                     */ (f.lastModified() > lastTimeMod)) {
                /*
                 * 28
                 */ thisFolder.setLastModified(f.lastModified());
                /*
                 * 29
                 */ lastTimeMod = thisFolder.lastModified();
                /*
                 *               */            }
            /*
             *           */        }
        /*
         *       */    }
    /*
     *   */ }
