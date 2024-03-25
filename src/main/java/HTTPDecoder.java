import java.util.HashMap;
import java.util.LinkedList;

public class HTTPDecoder {

    String COMMANDS[] = {"GET"};

    private static HashMap<String,String> httpInputKeyValue;


    public static void decodeHTTPResponse(LinkedList<String> responseFromClient){
        httpInputKeyValue = new HashMap<>();
        for(String response: responseFromClient){
            String[] splitResponse = response.split(" ");
            if(splitResponse[0].equalsIgnoreCase("GET")){
                httpInputKeyValue.put("HTTP_METHOD",splitResponse[0]);
                httpInputKeyValue.put("PATH",splitResponse[1]);
                httpInputKeyValue.put("VERSION",splitResponse[2]);
            }
            else if(splitResponse[0].equalsIgnoreCase("Host:")){
                httpInputKeyValue.put("HOST_ADR",splitResponse[1].split(":")[0]);
                httpInputKeyValue.put("HOST_PORT",splitResponse[1].split(":")[1]);
            }
            else if(splitResponse[0].equalsIgnoreCase("User-Agent")){
                httpInputKeyValue.put("USER-AGENT",splitResponse[1]);
            }
        }

    }
}
