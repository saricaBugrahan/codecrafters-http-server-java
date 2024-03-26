import java.util.HashMap;
import java.util.LinkedList;

public class HTTPDecoder {

    /*
    * HashMap used for creating key-value pairs on the HTTP Request
    * Client Handler can reach without class instance
    * */
    public static HashMap<String,String> httpInputKeyValue = new HashMap<>();


    /*
    * This method split the lines by white space for each line from the HTTP Request
    * According to the first keyword (command) adds the values into the HashMap
    * */
    public static void decodeHTTPResponse(LinkedList<String> responseFromClient){
        httpInputKeyValue = new HashMap<>();
        for(String response: responseFromClient){
            String[] splitResponse = response.split(" ");
            if(splitResponse[0].equalsIgnoreCase("GET") || splitResponse[0].equalsIgnoreCase("POST")){
                httpInputKeyValue.put("HTTP_METHOD",splitResponse[0]);
                httpInputKeyValue.put("PATH",splitResponse[1]);
                httpInputKeyValue.put("VERSION",splitResponse[2]);
                String[] commandSplit = splitResponse[1].split("/");
                if(commandSplit.length>1){
                    httpInputKeyValue.put("COMMAND",commandSplit[1]);
                    StringBuilder input = new StringBuilder();
                    for(int i = 2;i< commandSplit.length;i++){
                        input.append(commandSplit[i]).append("/");
                    }
                    if(!input.isEmpty()){
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
            } else if (splitResponse[0].equalsIgnoreCase("Content-Length")) {
                continue;
            } else if(splitResponse[0].equalsIgnoreCase("Accept-Encoding")){
                continue;
            } else{
                httpInputKeyValue.put("POST_DATA",response);
            }
        }

    }
}
