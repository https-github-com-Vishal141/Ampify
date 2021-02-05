package server;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileInputStream;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import java.nio.file.Files;
import java.util.Collections;

        
public class httpServer {
    public static boolean status;
    
    private static HttpServer server;
    
    
    public static void startServer() throws IOException
    {
      status= true;
      server = HttpServer.create(new InetSocketAddress("localhost",8500), 0);
      uploadFiles();
      
    //  server.setExecutor(null);
      server.start();
      System.out.println("Http Connected");
    }
    
    public static void uploadFiles(){
        File[] files = new File("E:/nbProjects/Server/src/server/song").listFiles();
        int id=1;
        HttpContext context;
        for(File file:files){
           // System.out.print(id);
            context = server.createContext("/ampify/song"+id+".mp3",new MyHttpHandler(file));
            id++;
        }
    }
    
     
     public static void closeServer()
     {
         server.stop(0);
         status = false;
         System.out.println("closed");
     }
}
