/*
 * This program enables us to create functionality for our proxy server
 * Authors: Enver ASLAN & Tuna CİNSOY
 */


import java.io.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;


public class Proxy implements Runnable {
    private BufferedReader readerFromClient; // variable that stores request to read
    private PrintWriter writerToClient; // variable that stores response to write
    private BufferedWriter bufferedWrite; // variable that stores response to write for cached sites
    private Socket socket;
    private HashMap<String, File> cache; // variable that stores cached sites list

    // initialize the variable
    public Proxy(Socket socket) throws IOException {
        cache = new HashMap<String, File>();
        File cachedSites = new File("cachedSites.txt");

        try {
            if(!cachedSites.exists()) {
                cachedSites.createNewFile();
            } else {
                // Read the cache file and load the web pages to memory
                FileInputStream fileInputStream = new FileInputStream(cachedSites);
                ObjectInputStream objInputStream = new ObjectInputStream(fileInputStream);
                cache = (HashMap<String, File>)objInputStream.readObject();
                fileInputStream.close();
                objInputStream.close();
            }
        } catch (Exception ex) {

        }

        this.socket = socket;
        readerFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writerToClient = new PrintWriter(socket.getOutputStream());
        bufferedWrite = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    @Override
    public void run() {
        String req = null; // variable that stores request string
        String res = null; // variable that stores response string
        String fileName=null; // variable that stores file name to create cache file

        File file = null; // variable that used to create cache file
        BufferedWriter cacheBW = null; // variable that used to write cache file

        try{
            // Read request string and parse it
            req = readerFromClient.readLine();
            System.out.println("Req: " + req);
            String[] splitReq = req.split(" ");
            String[] splitURL = splitReq[1].split("/");

            int size = 0;
            // create the file name example 100.html
            if (splitURL.length == 4) {
                size = Integer.parseInt(splitURL[3]);
                fileName = splitURL[3] + ".html";
            }

            if(splitReq[1].substring(0,0).equals("/")) {
                size = Integer.parseInt(splitURL[0]);
            }

            // if the cache file exists, then send the web page as response
            // Conditional get mechanism is also provided.
            file = (File)cache.get(splitReq[1]);
            if(file != null && size % 2 != 0) {
                BufferedReader cachedFileBufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                res = "HTTP/1.0 200 OK\\n" +
                        "Proxy-agent: ProxyServer/1.0\n" +
                        "\r\n";
                bufferedWrite.write(res);
                bufferedWrite.flush();

                String line;
                while((line = cachedFileBufferedReader.readLine()) != null) {
                    bufferedWrite.write(line);
                    bufferedWrite.flush();
                }
            // if the cache file does not exist, get the web page from web site
            } else {

                // if the first element of split request array is Get
                // make process to get web sites
                if (splitReq[0].equals("GET")) {
                    Socket socket;
                    if (req.contains("127.0.0.1") || req.contains("localhost") ||  splitReq[1].substring(0,0).equals("/")){

                        // if URI (document size) is greater than 9999
                        // send the 414 Request-URI Too Long message as a response
                        if (size > 9999) {
                            res = "HTTP/1.0 414 Request-URI Too Long \r\n\r\n";
                            res += "<HTML><HEAD><TITLE>414 Request-URI Too Long </TITLE></HEAD>";
                            res += "<BODY>414 Request-URI Too Long</BODY></HTML>";

                            System.out.println(res);
                            writerToClient.println(res);
                            writerToClient.flush();

                        // if URI (document size) is less than 100
                        // send the 400 Bad Request message as a response
                        } else if (size < 100) {
                            res = "HTTP/1.0 400 Bad Request\r\n\r\n";
                            res += "<HTML><HEAD><TITLE>HTTP/1.0 400 Bad Request</TITLE></HEAD>";
                            res += "<BODY>HTTP/1.0 400 Bad Request</BODY></HTML>";
                            System.out.println(res);
                            writerToClient.println(res);
                            writerToClient.flush();
                        // if the request satisfies contidions,
                        // send the web page as a response
                        } else {
                            BufferedWriter toServer=null;
                            BufferedReader fromServer=null;
                            // separate the URL into address and port number
                            String address = splitURL[2].split(":")[0];
                            int port = Integer.parseInt(splitURL[2].split(":")[1]);
                            // create a connection with determined arguments
                            try {
                                socket = new Socket(address, port);
                                fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                toServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                            } catch(Exception ex) {
                                res = "HTTP/1.0 404 Not Found\r\n\r\n";
                                res += "<HTML><HEAD><TITLE>HTTP/1.0 404 Not Found</TITLE></HEAD>";
                                res += "<BODY>HTTP/1.0 404 Not Found</BODY></HTML>";
                                System.out.println(res);
                                writerToClient.println(res);
                                writerToClient.flush();
                                return;
                            }

                            // Send  a GET request to our web server
                            toServer.write("GET /" + size + " HTTP/1.0\r\n\r\n");
                            toServer.flush();
                            // Create the cache file for requested web page
                            file = new File(fileName);
                            if(!file.exists()) {
                                file.createNewFile();
                            }
                            cacheBW = new BufferedWriter(new FileWriter(file));


                            // Send the web page to client web browser and write the content of it to cache file.
                            while ((res = fromServer.readLine()) != null) {
                                System.out.println(res);
                                if(!(res.contains("HTTP/1.0") || res.contains("Server:") || res.contains("Content") || res.contains("Connection")))
                                    cacheBW.write(res);
                                writerToClient.println(res);
                                writerToClient.flush();
                            }

                            cacheBW.flush();
                            cache.put(splitReq[1], file);

                            if(cacheBW != null) cacheBW.close();
                        }
                    }
                }
            }

            if(writerToClient != null) {
                writerToClient.close();
            }
            if(readerFromClient != null) {
                readerFromClient.close();
            }
            FileOutputStream fileOutputStream = new FileOutputStream("cachedSites.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(cache);
            objectOutputStream.close();
            fileOutputStream.close();

        } catch (Exception ex) {

        }


    }




}
