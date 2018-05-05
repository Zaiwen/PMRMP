package org.sklse.processRegister.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

//import static java.nio.file.StandardCopyOption.*;

public class FileHandler {
    public FileHandler() {}
    public static void move(File oldFile, File newFile) throws IOException {
        //File fnewpath = new File(newPath);
        //File fnew = new File( newPath + oldFile.getName());
        //Files.move(oldFile.toPath(), fnew.toPath());
        
        if(newFile.exists()) throw new IOException();
        newFile.getParentFile().mkdirs();
        newFile.createNewFile();
        
        FileInputStream is = new FileInputStream(oldFile);
        FileOutputStream os = new FileOutputStream(newFile);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = is.read(buffer)) != -1) {   
           os.write(buffer, 0, bytesRead);
        }
        is.close();
        os.close();
        oldFile.delete();
    }
}
