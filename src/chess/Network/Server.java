package chess.Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public boolean connectionEstablished = false;
        ServerSocket server = new ServerSocket(8);
    public Server() throws IOException {

    }
        public void run() throws IOException {
                System.out.println("Czekam na klienta");
                Socket client = server.accept();
                connectionEstablished = true;
                System.out.println("Połączono z: " + client.getInetAddress().getHostName());
                System.out.println("Klient podłączony");
            }


}

