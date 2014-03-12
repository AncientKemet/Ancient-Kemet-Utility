package aku.IO.AdvancedFileOperations.Files;

/**
 * @author Robert Kollar
 */
public class FileDownloader {

    public static void ParseError(Error error){
        if(error instanceof java.lang.UnsatisfiedLinkError){
            String message = error.getMessage();
            if(message.startsWith("no ")){
                
                String fileName = message.split(" ")[1];
                
                //downloads all the missing libaries
                AKFile libary = new AKFile(fileName+".dll");
                
                new AKFile("lwjgl.dll");
                new AKFile("lwjgl64.dll");
                
                System.out.println("Downloaded missing libary: "+libary.getName());
            }else{
                throw new Error("Unable to handle error: "+error.getMessage());
            }
        }
    }
}
