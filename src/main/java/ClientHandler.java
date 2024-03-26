import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public class ClientHandler implements Runnable{

    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            BufferedReader socketInputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream socketOutputStream = new DataOutputStream(clientSocket.getOutputStream());
            String messageFromClient;
            LinkedList<String> responseFromClient = new LinkedList<>();
            while ((messageFromClient = socketInputStream.readLine()) != null && !messageFromClient.isEmpty()){
                System.out.println(messageFromClient);
                responseFromClient.add(messageFromClient);
                if (messageFromClient.startsWith("Content-Length")){
                    responseFromClient.add(messageFromClient);
                    int len = Integer.parseInt(messageFromClient.split("Content-Length: ")[1]);
                    socketInputStream.readLine();
                    socketInputStream.readLine();
                    char[] t = new char[len];
                    socketInputStream.read(t);
                    responseFromClient.add(new String(t));
                    break;
                }
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

                } else if(HTTPDecoder.httpInputKeyValue.getOrDefault("COMMAND","NULL").equalsIgnoreCase("files")){
                    if(HTTPDecoder.httpInputKeyValue.getOrDefault("HTTP_METHOD","NULL").equalsIgnoreCase("GET")){
                        FileHandler.setFolder(HTTPDecoder.httpInputKeyValue.get("INPUT"));
                        byte[] fileContent = FileHandler.search();
                        if(fileContent == null){
                            socketOutputStream.write(HTTPEncoder.ERROR.getBytes(StandardCharsets.UTF_8));

                        }
                        else{
                            socketOutputStream.write("HTTP/1.1 200 OK\r\nContent-Type: application/octet-stream\r\n".getBytes(StandardCharsets.UTF_8));
                            socketOutputStream.write(("Content-Length: "+fileContent.length+"\r\n\r\n").getBytes(StandardCharsets.UTF_8));
                            socketOutputStream.write(fileContent);
                        }
                    } else if(HTTPDecoder.httpInputKeyValue.getOrDefault("HTTP_METHOD","NULL").equalsIgnoreCase("POST")){
                        FileHandler.setFolder(HTTPDecoder.httpInputKeyValue.get("INPUT"));
                        System.out.println(HTTPDecoder.httpInputKeyValue.get("POST_DATA"));
                        FileHandler.write(HTTPDecoder.httpInputKeyValue.get("POST_DATA"));
                        socketOutputStream.write(HTTPEncoder.FILE_CREATE.getBytes());
                        socketOutputStream.write(HTTPEncoder.CRLF.getBytes(StandardCharsets.UTF_8));

                    }

                }
                else{
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
