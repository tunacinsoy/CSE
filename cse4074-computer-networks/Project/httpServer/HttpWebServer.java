/*
 * This program aims to create a Multi-Threaded HTTP Web Server
 * Authors: Enver ASLAN & Tuna CİNSOY
 */


import java.net.* ;

public class HttpWebServer {

    public static void main(String[] args) throws Exception {
        // if no argument is specified as a port,
        // we set port number to 3000 as default port
        int port = 8080; // default port

        // if there is a specified port,
        // parse and assign the argument to port variable
        if(args.length != 0) {
            port = Integer.parseInt(args[0]);
        }


        // Initializing the socket
        ServerSocket socket = new ServerSocket(port);
        System.out.println("Web server has started. Listening Port: " + port + " (CTRL-C to quit)");

        // Inside in infinite while loop, we perform
        // requested tasks from clients
        while(true) {
            try {
            Socket connectionSocket = socket.accept();
                HttpRequest request = new HttpRequest(connectionSocket);
                socket.setSoTimeout(15);
                Thread thread = new Thread(request);
                thread.start();

                if (socket.isClosed()) thread.destroy();
            } catch(Exception ex) {

            }

        }
    }
}