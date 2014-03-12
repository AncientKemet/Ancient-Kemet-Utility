package aku;

import aku.IO.AdvancedFileOperations.Folders.Folders;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class AKFTPCLIENT {

  private static FTPClient[] clients = new FTPClient[10];
  public static List<FTPClient> ClientPool = new LinkedList();
  public static List<FTPFile> FilesToDownload = new LinkedList();
  public static List<String> FilesToDownloadPaths = new LinkedList();
  public static long AmountOfDataToDownload = 0L;
  public static long AmountOfDataDownloaded = 0L;
  public static String FTPSTATE = "null";
  
  public static boolean RESTART_REQUIRED = false;

  public static boolean UpdateFinished() {
    /*  32 */ synchronized (ClientPool) {
      /*  33 */ for (int i = 0; i < ClientPool.size(); i++) {
        /*  34 */ if (((FTPClient) ClientPool.get(i)).isConnected()) {
          /*  35 */ return false;
        }
      }
      /*  38 */ return true;
    }
  }

  private static FTPClient getFreeClient() throws IOException {
    /*  43 */ synchronized (ClientPool) {
      /*  44 */ FTPClient client = new FTPClient();
      /*  45 */ client.connect(AncientKemetNET.FTP_SERVER_ADRESS, AncientKemetNET.FTP_SERVER_PORT);
      /*  46 */ client.login("akftpclient", "update");
      /*  47 */ client.enterLocalPassiveMode();
      /*  48 */ ClientPool.add(client);
      /*  49 */ return client;
    }
  }

  private static FTPClient getAdminClient() throws IOException {
    /*  54 */ synchronized (ClientPool) {
      /*  55 */ FTPClient client = new FTPClient();
      /*  56 */ client.connect(AncientKemetNET.FTP_SERVER_ADRESS, AncientKemetNET.FTP_SERVER_PORT);
      /*  57 */ if (!client.login(AncientKemetRegistry.DeveloperUsername, AncientKemetRegistry.DeveloperPassword)) {
        /*  60 */ throw new Error("FAILED TO CONNECT TO FTP");
      }
      /*  62 */ client.enterLocalPassiveMode();
      /*  63 */ ClientPool.add(client);
      /*  64 */ return client;
    }
  }

  public static void upgrade() throws IOException {
    /*  69 */ String downloadDir = AncientKemetRegistry.AKInstallFolder;
    /*  70 */ File dir = new File(downloadDir);
    /*  71 */ if (!dir.exists()) /*  72 */ {
      dir.mkdir();
    } else {
      /*  74 */ System.out.println("AncientKemetRegistry.AKInstallFolder exists");
    }
    /*  76 */ System.out.println("UPDATING...");
    /*  77 */ downloadFolder(downloadDir, "/home/akftpclient/client");
  }

  public static void commitBuild() throws IOException {
    /*  82 */ FTPClient client = getAdminClient();
    /*  83 */ String localDir = "C:/NetBeansProjects/ANCIENT WARS/build";

    /*  85 */ System.out.println(client.getStatus());
    /*  86 */ System.out.println("Current ftp folder: " + client.printWorkingDirectory());
    /*  87 */ File dir = new File(localDir);
    /*  88 */ if (!dir.exists()) {
      /*  89 */ throw new Error("UNABLE TO FIND local directory: " + localDir);
    }
    /*  91 */ System.out.println("AncientKemetRegistry.AKInstallFolder exists");

    /*  93 */ Folders.fixLastTimeModification(localDir);
    /*  94 */ uploadFolder(localDir, "/home/akftpclient/client/build");
    /*  95 */ client.disconnect();
    /*  96 */ commitLib();
  }

  public static void commitLib() throws IOException {
    /* 100 */ FTPClient client = getAdminClient();
    /* 101 */ String localDir = "C:/NetBeansProjects/ANCIENT WARS/lib";

    /* 103 */ System.out.println(client.getStatus());
    /* 104 */ System.out.println("Current ftp folder: " + client.printWorkingDirectory());
    /* 105 */ File dir = new File(localDir);
    /* 106 */ if (!dir.exists()) {
      /* 107 */ throw new Error("UNABLE TO FIND local directory: " + localDir);
    }
    /* 109 */ System.out.println("AncientKemetRegistry.AKInstallFolder exists");

    /* 111 */ Folders.fixLastTimeModification(localDir);
    /* 112 */ uploadFolder(localDir, "/home/akftpclient/client/lib");
    /* 113 */ client.disconnect();
  }

  public static void downloadFolder(final String localDir, final String oldftpDir)
          throws IOException {
    /* 122 */ new Thread(new Runnable() {
      public void run() {
        try {
          /* 126 */ FTPClient client = AKFTPCLIENT.getFreeClient();
          /* 127 */ String ftpDir = new String(oldftpDir.getBytes());
          /* 128 */ while (client == null) {
            /* 129 */ client = AKFTPCLIENT.getFreeClient();
            try {
              /* 131 */ Thread.sleep(10);
            } catch (InterruptedException ex) {
              /* 133 */ Logger.getLogger(AKFTPCLIENT.class.getName()).log(Level.SEVERE, null, ex);
            }
          }

          /* 137 */ AKFTPCLIENT.FTPSTATE = (int) (AKFTPCLIENT.AmountOfDataToDownload - AKFTPCLIENT.AmountOfDataDownloaded) / 1000000 + "MB | Searching for updates...";
          /* 138 */ FileOutputStream fos = null;
          /* 139 */ client.changeWorkingDirectory(ftpDir);

          /* 152 */ for (final FTPFile ftpFile : client.listFiles()) {
            /* 154 */ if (ftpFile.isFile()) {
              /* 155 */ final String filename = ftpFile.getName();
              /* 156 */ boolean needsToBeDownloaded = false;
              /* 157 */ final String oldftpdir = new String(ftpDir.getBytes());

              /* 159 */ String bonusPath = ftpDir.replace("/home/akftpclient/client", "/");
              /* 160 */ ftpDir = new String(oldftpdir.getBytes());
              /* 161 */ final File f = new File(localDir + bonusPath + "/" + filename);

              /* 164 */ if (!f.exists()) {
                /* 165 */ needsToBeDownloaded = true;
              } /* 167 */ else if ((ftpFile.getTimestamp().getTimeInMillis() > f.lastModified()) || (ftpFile.getSize() != f.length())) {
                /* 168 */ needsToBeDownloaded = true;
              }

              /* 171 */ if ((needsToBeDownloaded) && (ftpFile.getName().endsWith(".db"))) {
                /* 172 */ needsToBeDownloaded = false;
              }
              /* 174 */ if (needsToBeDownloaded) {
                /* 177 */ AKFTPCLIENT.FilesToDownload.add(ftpFile);
                /* 178 */ AKFTPCLIENT.FilesToDownloadPaths.add(ftpDir + "/" + filename + " with fileoutputstream " + f.getAbsolutePath());
                /* 179 */ AKFTPCLIENT.AmountOfDataToDownload += ftpFile.getSize();
                /* 180 */ new Thread(new Runnable() {
                  public void run() {
                    /* 183 */ FTPClient c = null;
                    try {
                      /* 185 */ c = AKFTPCLIENT.getFreeClient();
                      /* 186 */ AKFTPCLIENT.downloadFile(new String(oldftpdir.getBytes()) + "/" + filename, f.getAbsolutePath(), ftpFile, c);
                      /* 187 */ c.disconnect();
                    } catch (IOException ex) {
                      try {
                        /* 190 */ c.disconnect();
                      } catch (IOException ex1) {
                        /* 192 */ Logger.getLogger(AKFTPCLIENT.class.getName()).log(Level.SEVERE, null, ex1);
                      }
                      /* 194 */ Logger.getLogger(AKFTPCLIENT.class.getName()).log(Level.SEVERE, null, ex);
                    }
                  }
                }).start();
              }

            }

          }

          /* 206 */ for (FTPFile ftpDirectory : client.listDirectories()) {
            /* 207 */ if (ftpDirectory.isDirectory()) {
              /* 208 */ String filename = ftpDirectory.getName();
              /* 209 */ File fil = new File(localDir + ftpDir + filename);
              /* 210 */ if (!fil.exists()) {
                /* 211 */ fil.mkdir();
              }
              /* 213 */ AKFTPCLIENT.downloadFolder(localDir, ftpDir + "/" + filename);
            }
          }
          /* 216 */ client.disconnect();
        } catch (IOException e) {
          /* 218 */ e.printStackTrace();
        }
      }
    }).start();
  }

  public static void downloadFileFromFileStack(int id, FTPClient client) throws IOException {
    FileOutputStream fos = null;
    FTPFile ftpFile = (FTPFile) FilesToDownload.get(id);
    try {
      String fileString = (String) FilesToDownloadPaths.get(id);
      String[] split = fileString.split(" with fileoutputstream ");
      File localFile = new File(split[1]);

      if (!localFile.getParentFile().exists()) {
        localFile.getParentFile().mkdirs();
      }
      if (!localFile.exists()) {
        localFile.createNewFile();
      }
      fos = new FileOutputStream(split[1]);
      FTPSTATE = (float) (AmountOfDataToDownload - AmountOfDataDownloaded) / 1000000.0F + "MB | Downloading file: " + ftpFile.getName();
      if (!client.retrieveFile(split[0], fos)) {
        throw new Error("ERROR RECIEVING FILE : " + split[0] + "\n" + "");
      }

      localFile.setLastModified(ftpFile.getTimestamp().getTimeInMillis());
    } catch (IOException ex) {
      Logger.getLogger(AKFTPCLIENT.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      AmountOfDataDownloaded += ftpFile.getSize();
      client.disconnect();
      try {
        fos.close();
      } catch (IOException ex) {
        Logger.getLogger(AKFTPCLIENT.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }

  public static void downloadFile(String ftp, String local, FTPFile ftpFile, FTPClient client) throws IOException {
    FileOutputStream fos = null;

      File localFile = new File(local);

      if (!localFile.getParentFile().exists()) {
        localFile.getParentFile().mkdirs();
      }
      if (!localFile.exists()) {
        new FileOutputStream(localFile);
      }
      fos = new FileOutputStream(localFile);
      FTPSTATE = (float) (AmountOfDataToDownload) / 1000000.0F + "/"+ (float) (AmountOfDataDownloaded) / 1000000.0F +"MB | Downloading file: " + ftpFile.getName();
      if (!client.retrieveFile(ftp, fos)) {
        client.disconnect();
        fos.close();
        downloadFile(ftp, local, ftpFile, getFreeClient());   
        return;
      }
      localFile.setLastModified(ftpFile.getTimestamp().getTimeInMillis());

      AmountOfDataDownloaded += ftpFile.getSize();
      System.out.println("Downloaded file: " + local);
      if (local.endsWith(".class") || local.endsWith(".jar")){
        RESTART_REQUIRED = true;
      }
      client.disconnect();
      fos.close();
  }

  public static void uploadFolder(final String localDir, final String ftpDir) throws IOException {
    FTPClient client = getAdminClient();

    if (client.makeDirectory(ftpDir));
    File ThisFolder = new File(localDir);

    /* 318 */ client.changeWorkingDirectory(ftpDir);

    /* 320 */ for (final File f : new File(localDir).listFiles()) {
      /* 321 */ if (f.isFile()) {
        /* 323 */ uploadFile(f, client);
      }

      /* 326 */ if ((f.isDirectory())
              && /* 327 */ (f.lastModified() > client.mlistFile(ftpDir).getTimestamp().getTimeInMillis())) {
        /* 328 */ new Thread(new Runnable() {
          public void run() {
            try {
              /* 332 */ AKFTPCLIENT.uploadFolder(localDir + "/" + f.getName(), ftpDir + "/" + f.getName());
            } catch (IOException ex) {
              /* 334 */ Logger.getLogger(AKFTPCLIENT.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
        }).start();
      }

    }

    /* 342 */ ThisFolder.setLastModified(1000000000L);
    /* 343 */ client.disconnect();
  }

  private static void uploadFile(File f, FTPClient client) throws IOException {
    /* 347 */ boolean needsToBeUploaded = true;
    /* 348 */ for (FTPFile ftpFile : client.listFiles()) {
      /* 349 */ if ((ftpFile.isFile())
              && /* 350 */ (ftpFile.getName().equals(f.getName()))) {
        /* 351 */ if ((ftpFile.getTimestamp().getTimeInMillis() < f.lastModified()) && (ftpFile.getSize() != f.length())) {
          /* 353 */ f.setLastModified(1000000000L);
          /* 354 */ needsToBeUploaded = true;
          /* 355 */ break;
        }
        /* 357 */ needsToBeUploaded = false;
        /* 358 */ break;
      }

    }

    /* 363 */ if (needsToBeUploaded) {
      /* 364 */ System.out.println("uploading file: " + f.getName());

      /* 366 */ if (client.storeFile(f.getName(), new FileInputStream(f))) {
        /* 367 */ for (FTPFile ftpFile : client.listFiles()) /* 368 */ {
          if ((ftpFile.isFile())
                  && /* 369 */ (ftpFile.getName().equals(f.getName()))) {
            /* 370 */ Calendar calendar = Calendar.getInstance();
            /* 371 */ calendar.setTimeInMillis(System.currentTimeMillis());
            /* 372 */ ftpFile.setTimestamp(calendar);
          }
        }
      } else {
        /* 377 */ System.out.println("ERROR: " + client.stor(f.getName()));
        /* 378 */ throw new Error("ERROR UPLOADING FILE: " + f.getAbsolutePath() + "");
      }
    }
  }
}
