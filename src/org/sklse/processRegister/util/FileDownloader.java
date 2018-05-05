package org.sklse.processRegister.util;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class FileDownloader {

    // download with http
    public static File httpDownload(String httpUrl, File saveFile)
                        throws MalformedURLException, IOException, FileNotFoundException {
        int bytesum = 0;
        int byteread = 0;
        URL url = null;
        
        url = new URL(httpUrl);     // throws MalformedURLException when failed
        
        URLConnection conn = url.openConnection();              // throws IOException
        InputStream iStream = conn.getInputStream();            // throws IOException
        FileOutputStream fs = new FileOutputStream(saveFile);   // throws FileNotFoundException
        byte[] buffer = new byte[1024];
        while((byteread = iStream.read(buffer)) != -1) {        // throws IOException
            bytesum += byteread;
            fs.write(buffer, 0, byteread);                      // throws IOException
        }
        return saveFile;
    }
    
    public static void main(String args[]) {     //test
        try {
            File file = httpDownload("http://imgsrc.baidu.com/forum/pic/item/a023d4160924ab1822e8e1be35fae6cd7a890b06.jpg", new File("\\WebRoot\\process\\temp\\test.jpg"));
            System.out.println(file.getAbsolutePath());
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
