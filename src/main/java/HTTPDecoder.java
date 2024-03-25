import java.util.HashMap;
import java.util.LinkedList;

public class HTTPDecoder {

    public static HashMap<String,String> httpInputKeyValue = new HashMap<>();

    public static void decodeHTTPResponse(LinkedList<String> responseFromClient){
        httpInputKeyValue = new HashMap<>();
        for(String response: responseFromClient){
            String[] splitResponse = response.split(" ");
            if(splitResponse[0].equalsIgnoreCase("GET")){
                httpInputKeyValue.put("HTTP_METHOD",splitResponse[0]);
                httpInputKeyValue.put("PATH",splitResponse[1]);
                httpInputKeyValue.put("VERSION",splitResponse[2]);
                String[] commandSplit = splitResponse[1].split("/");
                if(commandSplit.length>1){
                    httpInputKeyValue.put("COMMAND",commandSplit[1]);
                    String input = "";
                    for(int i = 2;i< commandSplit.length;i++){
                        System.out.println("Command Piece "+commandSplit[i]);
                        input+=commandSplit[i]+"/";
                    }
                    if(input.length() != 0){
                        httpInputKeyValue.put("INPUT",input.substring(0,input.length()-1));
                    }
                }
            }
            else if(splitResponse[0].equalsIgnoreCase("Host:")){
                httpInputKeyValue.put("HOST_ADR",splitResponse[1].split(":")[0]);
                httpInputKeyValue.put("HOST_PORT",splitResponse[1].split(":")[1]);
            }
            else if(splitResponse[0].equalsIgnoreCase("User-Agent:")){
                httpInputKeyValue.put("USER-AGENT",splitResponse[1]);
            }
        }

    }
}
