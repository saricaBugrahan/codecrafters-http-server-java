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
            }
            HTTPDecoder.decodeHTTPResponse(responseFromClient);
            if(HTTPDecoder.httpInputKeyValue.getOrDefault("PATH","NULL").equalsIgnoreCase("/")){
                socketOutputStream.write(HTTPEncoder.OK.getBytes(StandardCharsets.UTF_8));
                socketOutputStream.write(HTTPEncoder.CRLF.getBytes(StandardCharsets.UTF_8));
                return;
            }
            else{
                if(HTTPDecoder.httpInputKeyValue.getOrDefault("COMMAND","NULL").equalsIgnoreCase("echo")){
                    socketOutputStream.write(HTTPEncoder.OK.getBytes(StandardCharsets.UTF_8));
                    socketOutputStream.write(HTTPEncoder.TEXT.getBytes(StandardCharsets.UTF_8));
                    socketOutputStream.write(HTTPEncoder
                            .getParsedContentLength(HTTPDecoder.httpInputKeyValue.get("INPUT")).getBytes(StandardCharsets.UTF_8));
                    socketOutputStream.write(HTTPDecoder.httpInputKeyValue.get("INPUT").getBytes(StandardCharsets.UTF_8));
                    socketOutputStream.write(HTTPEncoder.CRLF.getBytes(StandardCharsets.UTF_8));

                }else if(HTTPDecoder.httpInputKeyValue.getOrDefault("COMMAND","NULL").equalsIgnoreCase("user-agent")){
                    socketOutputStream.write(HTTPEncoder.OK.getBytes(StandardCharsets.UTF_8));
                    socketOutputStream.write(HTTPEncoder.TEXT.getBytes(StandardCharsets.UTF_8));
                    socketOutputStream.write(HTTPEncoder
                            .getParsedContentLength(HTTPDecoder.httpInputKeyValue.get("USER-AGENT")).getBytes(StandardCharsets.UTF_8));

                    socketOutputStream.write(HTTPDecoder.httpInputKeyValue.get("USER-AGENT").getBytes(StandardCharsets.UTF_8));
                    socketOutputStream.write(HTTPEncoder.CRLF.getBytes(StandardCharsets.UTF_8));

                } else{
                    socketOutputStream.write(HTTPEncoder.ERROR.getBytes(StandardCharsets.UTF_8));
                }
            }


            socketOutputStream.flush();
            this.clientSocket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
