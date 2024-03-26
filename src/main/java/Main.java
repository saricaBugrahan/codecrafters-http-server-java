import java.io.IOException;
import java.net.ServerSocket;

public class Main {
  public static void main(String[] args) {

        ArgumentHandler argumentHandler = new ArgumentHandler(args);
        ServerSocket serverSocket;

     try {
       serverSocket = new ServerSocket(argumentHandler.getServerPortNumber());
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
