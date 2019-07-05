package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException{
        ServerSocket socket;
        try{
            socket = new ServerSocket(5555);
            while(true){
                System.out.println("Servidor rodando na porta:" +socket.getLocalPort());
                Socket received = socket.accept();
                System.out.println("Conectado.");
                new Thread(new ProtocolProcessor(received)).start();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
