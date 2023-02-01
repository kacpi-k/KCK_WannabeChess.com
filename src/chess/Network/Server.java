//package chess.Network;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.ServerSocket;
//import java.net.Socket;
//
//public class Server{
//    public void run() throws IOException {
//        ServerSocket server = new ServerSocket("7");
//
//        Socket client = server.accept();
//
//        InputStream in = client.getInputStream();
//        OutputStream out = client.getOutputStream();
//
//        while(true) {
//            //odbierz dane
//            int data = in.read();
//
//            //przetworz dane
//
//            //wyslij do klienta
//            out.write(data);
//        }
//
//        //zamykanie połączenia
//        in.close();
//        out.close();
//        client.close();
//        server.close();
//    }
//}
