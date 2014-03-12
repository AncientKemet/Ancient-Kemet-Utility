 package aku;
 
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.FileOutputStream;
 import java.io.IOException;
 import java.util.Date;
 import java.util.logging.Level;
 import java.util.logging.Logger;
 
 public class AncientKemetPatcher
 {
/* 23 */   public static int currentProgress = 0;
/* 24 */   public static int maxProgress = 1;
 
   public static void createPatch(File[] files, int expansion, int revision, int patch) {
/* 27 */     maxProgress = files.length;
     try {
/* 29 */       File f = new File(AncientKemetRegistry.getFolderDir() + "/AK patch v" + expansion + "." + revision + "." + patch + ".patch");
/* 30 */       if (f.exists()) {
/* 31 */         executePatch(f);
/* 32 */         return;
       }
 
/* 35 */       f.createNewFile();
 
/* 37 */       FileOutputStream out = new FileOutputStream(f, true);
 
/* 40 */       Data d = new Data(0);
 
/* 43 */       String mask = "AncientKemetPatcher created patch: CTM(" + System.currentTimeMillis() + ") date: " + new Date().toGMTString() + "\n" + "\n" + "DU: " + AncientKemetRegistry.DeveloperUsername + "\n" + "DP: " + AncientKemetRegistry.DeveloperPassword + "\n";
 
/* 47 */       d.addString(mask);
 
/* 49 */       d.addInt(files.length);
 
/* 51 */       out.write(d.buffer());
/* 52 */       d.resetBuffer();
 
/* 54 */       for (File infile : files) {
/* 55 */         currentProgress += 1;
         try
         {
/* 58 */           FileInputStream fin = new FileInputStream(infile);
 
/* 60 */           int substringPTR = infile.getCanonicalPath().indexOf("Ancient Kemet") + 14;
/* 61 */           String filePath = infile.getCanonicalPath().substring(substringPTR);
/* 62 */           d.addString(infile.getCanonicalPath().substring(substringPTR));
/* 63 */           System.out.println("ADDED FILE: " + filePath);
 
/* 65 */           d.addInt(fin.available());
 
/* 67 */           byte[] filedata = new byte[fin.available()];
/* 68 */           fin.read(filedata);
/* 69 */           d.addBytes(filedata);
 
/* 71 */           out.write(d.buffer());
/* 72 */           d.resetBuffer();
         } catch (IOException ex) {
/* 74 */           ex.printStackTrace();
         }
 
       }
 
/* 80 */       out.close();
     } catch (IOException ex) {
/* 82 */       ex.printStackTrace();
     }
   }
 
   public static void executePatch(File pathFile) {
/* 87 */     boolean maskProgressed = false; boolean fullProgressed = false;
/* 88 */     int fileAmount = 0;
/* 89 */     Data d = new Data(0);
/* 90 */     Data currentFileBuffer = new Data(0);
/* 91 */     long skip = 0L;
     try {
/* 93 */       FileInputStream in = new FileInputStream(pathFile);
 
/* 95 */       byte[] patchFileData = new byte[16000];
/* 96 */       in.read(patchFileData);
 
/* 100 */       d.addBytes(patchFileData);
 
/* 102 */       d.setOffset(0);
 
/* 105 */       System.out.println(d.getString());
 
/* 107 */       fileAmount = d.getInt();
/* 108 */       maxProgress = fileAmount;
 
/* 111 */       for (int i = 0; i < fileAmount; i++)
       {
/* 113 */         currentProgress += 1;
 
/* 115 */         String filePath = d.getString();
/* 116 */         File f = new File(AncientKemetRegistry.getFolderDir() + "/ak/" + filePath);
/* 117 */         if (!f.exists()) {
           try {
/* 119 */             File dir = new File(f.getCanonicalPath().replace(f.getName(), ""));
/* 120 */             if (!dir.exists()) {
/* 121 */               dir.mkdirs();
             }
/* 123 */             f.createNewFile();
           } catch (IOException ex) {
/* 125 */             Logger.getLogger(AncientKemetPatcher.class.getName()).log(Level.SEVERE, null, ex);
           }
         }
 
/* 129 */         int fileDataLenght = d.getInt();
/* 131 */         byte[] fileData = new byte[0];
/* 132 */         if (fileDataLenght > d.getBuffer().size() - d.offset()) {
 
/* 135 */           fileData = d.subBuffer(d.offset(), d.getBuffer().size());
/* 136 */           d.resetBuffer();
/* 138 */           currentFileBuffer.addBytes(fileData);
/* 139 */           fileData = new byte[fileDataLenght - fileData.length];
/* 141 */           in.read(fileData);
/* 142 */           currentFileBuffer.addBytes(fileData);
/* 143 */           patchFileData = new byte[16000];
/* 144 */           in.read(patchFileData);
 
/* 148 */           d.addBytes(patchFileData);
 
/* 150 */           d.setOffset(0);
         } else {
/* 152 */           fileData = d.subBuffer(d.offset(), d.offset() + fileDataLenght);
/* 153 */           d.setOffset(d.offset() + fileDataLenght);
/* 154 */           currentFileBuffer.addBytes(fileData);
/* 155 */           byte[] restData = d.subBuffer(d.offset(), d.getBuffer().size());
/* 156 */           d.resetBuffer();
/* 157 */           d.addBytes(restData);
/* 158 */           System.out.println("  restdata amount : " + restData.length);
/* 159 */           if (restData.length < 500) {
/* 160 */             System.out.println("      REST DATA TOO LOW ADD MORE INTO BUFFER");
/* 161 */             patchFileData = new byte[16000];
/* 162 */             in.read(patchFileData);
/* 163 */             d.addBytes(patchFileData);
/* 164 */             System.out.println("  NEW AMOUNT : " + d.buffer().length);
           }
 
/* 167 */           d.setOffset(0);
         }
 
/* 172 */         System.out.println("  loaded curretn file buffer size: " + currentFileBuffer.buffer().length);
 
/* 174 */         FileOutputStream out = new FileOutputStream(f);
/* 175 */         out.write(currentFileBuffer.buffer());
/* 176 */         out.close();
/* 177 */         currentFileBuffer.resetBuffer();
       }
 
/* 184 */       in.close();
     } catch (IOException ex) {
/* 186 */       Logger.getLogger(AncientKemetPatcher.class.getName()).log(Level.SEVERE, null, ex);
     }
   }
 }