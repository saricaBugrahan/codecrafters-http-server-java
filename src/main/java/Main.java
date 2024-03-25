import java.io.IOException;
import java.net.ServerSocket;

public class Main {
  public static void main(String[] args) {
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.out.println("Logs from your program will appear here!");
    for(int i = 0;i<args.length;i++){
        if(args[i].equalsIgnoreCase("--directory")){
            FolderChecker.setFolderPath(args[++i]);
        }
    }

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
