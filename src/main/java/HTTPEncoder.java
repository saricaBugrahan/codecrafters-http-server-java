public class HTTPEncoder {

    //public final static String OK = "200 OK";
    public final static String CRLF = "\r\n";
    public final static String HEADER = "HTTP/1.1";

    public static String OK = "HTTP/1.1 200 OK\r\n";

    public static String ERROR = "HTTP/1.1 404 Not Found\r\n\r\n";

    public static String TEXT = "Content-Type: text/plain\r\n";

    public static String FILE = "Content-Type: application/octet-stream\r\n";


    public static String getParsedContentLength(String message){
        return "Content-Length: "+message.length()+"\r\n\r\n";
    }

}
