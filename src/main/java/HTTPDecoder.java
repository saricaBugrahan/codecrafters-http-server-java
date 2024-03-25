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

            }
        }
    }
}
