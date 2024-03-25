import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public class Server implements Runnable{

    private Socket clientSocket;

    private LinkedList<String> responseFromClient;

    public Server(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            BufferedReader socketInputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream socketOutputStream = new DataOutputStream(clientSocket.getOutputStream());
            String messageFromClient;
            responseFromClient = new LinkedList<>();
            while (socketInputStream.ready()){
                messageFromClient = socketInputStream.readLine();
                responseFromClient.add(messageFromClient);
                String[] parsedInput = messageFromClient.split(" ");
                if(parsedInput[0].equalsIgnoreCase("GET")){
                    if(parsedInput[1].equalsIgnoreCase("/")){
                        socketOutputStream.write(HTTPEncoder.OK.getBytes(StandardCharsets.UTF_8));
                    }else{
                        socketOutputStream.write(HTTPEncoder.ERROR.getBytes(StandardCharsets.UTF_8));
                    }
                }
            }


            socketOutputStream.flush();
            this.clientSocket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
