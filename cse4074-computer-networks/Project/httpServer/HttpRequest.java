/*
 *  This class is aimed to implement multi-threading functionality for
 *  our HTTP Web Server
 *  Authors: Enver ASLAN & Tuna CİNSOY
 */


import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

final public class HttpRequest implements Runnable {

    private Socket socket;
    private String requestLine;
    private List<String> headers;
    private String method="";
    private String uri="";
    private String version="";

    public HttpRequest (Socket socket) throws Exception {
        // Initialize the headers list to print the request message
        headers = new ArrayList<String>();
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
            processRequest();
            HttpResponse res = new HttpResponse(this);
            res.write(socket.getOutputStream());
            socket.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    // This function reads the request message that sent by browser
    // and print the request message
    private void processRequest() throws Exception {
        BufferedReader buffRead = new BufferedReader( new InputStreamReader(socket.getInputStream()));
        requestLine = buffRead.readLine();
        System.out.println(requestLine);
        parseRequestLine(requestLine);
        while(!requestLine.isEmpty()) {
            requestLine = buffRead.readLine();
            System.out.println(requestLine);
            addRequestHeader(requestLine);
        }
    }

    // This function parses the first line of request message
    // and determines the method of request, uri and HTTP version
    private void parseRequestLine(String str) {
        String[]  split = str.split("\\s+");
        try {
            method = split[0];
        } catch (Exception e) {
            method = null;
        }
        uri = split[1];
        version = split[2];
    }

    // This function adds the other request line to header list to print
    private void addRequestHeader(String str) {
        headers.add(str);
    }

    public List<String> getHeaders() {
        return headers;
    }

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getVersion() {
        return version;
    }
}
