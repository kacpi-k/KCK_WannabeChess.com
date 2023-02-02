package chess.Network;

import java.io.IOException;
import java.net.Socket;

public class Client {
        public boolean connectionEstablished = false;

        public void run() throws IOException {
                System.out.println("Podłączam się do serwera");
                Socket client = new Socket("localhost", 8);
                connectionEstablished = true;
                System.out.println("Sukces");

        }

}


