/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Collections;

public class MyHttpHandler implements HttpHandler {

    private File file;
    
    private final String METHOD_POST = "POST";
    private final String METHOD_GET = "GET";
    
    public MyHttpHandler(File file)
    {
        this.file = file;
    }
    
    @Override
    public void handle(HttpExchange exchange) throws IOException {       
        FileInputStream stream = new FileInputStream(file);      // Opening a input stream to read the file
        
        exchange.getResponseHeaders().put("Content-Type", Collections.singletonList(("audio/mpeg"))); //for a audio file
        exchange.getResponseHeaders().put("Accept-Ranges", Collections.singletonList("bytes"));
        exchange.getResponseHeaders().put("Content-Length", Collections.singletonList(String.valueOf(file.length())));
        exchange.getResponseHeaders().put("Allow", Collections.singletonList("GET"));
        exchange.getResponseHeaders().put("IM", Collections.singletonList("feed"));
        
        exchange.sendResponseHeaders(200,file.length());
        
        OutputStream os = exchange.getResponseBody();
        byte[] buff = new byte[1024];                           // Creating a small buffer to hold bytes as we read them
        int read = 0;

        while((read = stream.read(buff)) > 0) {                // sending bytes to the client
            os.write(buff, 0, read);
        }
        
        os.flush();
        os.close();
        stream.close();
    }
}
