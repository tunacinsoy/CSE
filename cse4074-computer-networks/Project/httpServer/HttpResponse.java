/*
 * This class is aimed to create the message that will be sent to client
 * Authors: Enver ASLAN & Tuna CİNSOY
 */


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HttpResponse {

    private static final String VERSION = "HTTP/1.0";
    private int documentSize;
    private String method;
    private List<String> headers;
    private byte[] body;

    public HttpResponse(HttpRequest request) {
        // Initialize the header list that will be sent
        headers = new ArrayList<String>();

        // If the document size is not entered
        // assign the value of it 0
        if (request.getUri().equals("/")) {
            documentSize=0;
        // If the document size is entered
        // parse it to integer and assign it
        }else{
            documentSize = Integer.parseInt(request.getUri().substring(1));
        }
        method = request.getMethod();

        // If the method is GET
        // create the random web page
        if(method.equals("GET")) {
            if(documentSize >= 100 && documentSize <= 20000) {
                fillHeaders("200 OK");
                headers.add("Content-Type: text/html; charset=UTF-8");
                StringBuilder html = new StringBuilder("<HTML>\n<HEAD>\n");
                html.append("<TITLE>I am ");
                html.append(documentSize);
                html.append(" bytes long</TITLE>\n");
                html.append("</HEAD>\n<BODY>\n");
                html.append(getRandomString());
                html.append("\n</BODY>\n");
                html.append("</HTML>");
                headers.add("Content-Length: " + html.toString().length());
                fillResponse(html.toString());

            // if the document size is greater than 200000 or less than 100
            // set the header list for bad request
            } else {
                fillHeaders("400 Bad Request");
                fillResponse("400 Bad Request");
            }
         // if the metho is not GET
         // set the header for not implemented request
        } else {
            fillHeaders("501 Not Implemented");
            fillResponse("501 Not Implemented");
        }
    }

    // This function writes the response that will be sent to client
    public void write(OutputStream os) throws IOException {
        DataOutputStream output = new DataOutputStream(os);
        for (String header : headers) {
            output.writeBytes(header + "\r\n");
            System.out.println(header);
        }
        output.writeBytes("\r\n");
        if (body != null) {
            output.write(body);
            String utf = new String(body,"UTF-8");
            System.out.println(utf);
        }
        output.writeBytes("\r\n");
        output.flush();
    }

    // This function generates a random string content
    private String getRandomString() {
        char c;
        String str="";
        Random rnd = new Random();
        int titleLong  = ("I am " + documentSize + " bytes long").length();
        for (int i = 0; i < documentSize - 61 - titleLong; i++) {
            c = (char)(97 + rnd.nextInt(26));
            str = str + c;
        }
        return str;
    }

    // This function intializes the header list
    private void fillHeaders(String header) {
        headers.add(VERSION + " " + header );
        headers.add("Connection: Close");
        headers.add("Server: HttpServer");
    }


    // This function converts the response message to byte
    // and assign it to the body variable
    private void fillResponse(String response) {
        body = response.getBytes();
    }

    // This function assigns the byte array response message to the body variable
    private void fillResponse(byte[] response) {
        body = response;
    }


}
