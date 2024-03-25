public class HTTPEncoder {

    //public final static String OK = "200 OK";
    public final static String CRLF = "\r\n";
    public final static String HEADER = "HTTP/1.1";

    public static String OK = "HTTP/1.1 200 OK\r\n\r\n";

    public static String parseIntoHTTPResponse (String message){
        return HEADER+message+CRLF+CRLF;
    }

}
