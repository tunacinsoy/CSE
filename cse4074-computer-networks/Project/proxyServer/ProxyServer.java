/*
 * This program aims to create a Multi-Threaded Proxy Server
 * Authors: Enver ASLAN & Tuna CİNSOY
 */


import java.net.ServerSocket;
import java.net.Socket;

public class ProxyServer  {

    public static void main(String[] args) {
        int port = 8888; // listening port
        ServerSocket server;
        try{
            server = new ServerSocket(port);
            System.out.println("Proxy server is listening on port " + port);

            while(true) {
                Socket conn = server.accept();
                Proxy proxy = new Proxy(conn);

                Thread thread = new Thread(proxy);
                thread.start();
            }
        } catch (Exception ex) {
            //System.out.println(ex);
        }

    }
}
