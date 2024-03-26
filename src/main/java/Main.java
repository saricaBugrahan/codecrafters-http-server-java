import java.io.IOException;
import java.net.ServerSocket;

public class Main {
  public static void main(String[] args) {

        new ArgumentHandler(args);
        ServerSocket serverSocket;

     try {
       serverSocket = new ServerSocket(4221);
       serverSocket.setReuseAddress(true);
       System.out.println("accepted new connection");
       while (true){
           new Thread(new ClientHandler(serverSocket.accept())).start();
       }
     } catch (IOException e) {
       System.out.println("IOException: " + e.getMessage());
     }
  }
}
