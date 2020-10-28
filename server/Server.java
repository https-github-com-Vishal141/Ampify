package server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    ServerSocket serverSocket;
    Socket socket;

    public void getClient()
    {
        //System.out.print(files[0]);
        try {
            serverSocket = new ServerSocket(4870);
            System.out.println("waiting");
            while (true)
            {
                socket = serverSocket.accept();
                System.out.println("Connected");
                new Thread(new clientHandler(socket)).start();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server().getClient();
    }
    
}
