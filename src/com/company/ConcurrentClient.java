package com.company;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;

/**
 * Created by priya on 3/29/15.
 *
 */
public class ConcurrentClient {

        // call  constructor to start the program


        public ConcurrentClient()
        {
            String serverName = "10.0.0.199";
            int port = 8080;
            String htmlGetRequest = "GET / HTTP/1.1\n" +
                    "Host: " + serverName + ":"+ port + "\n" +
                    "Accept-Encoding: gzip, deflate\n" +
                    "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\n" +
                    "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_3) AppleWebKit/600.5.15 (KHTML, like Gecko) Version/8.0.5 Safari/600.5.15\n" +
                    "Accept-Language: en-us\n" +
                    "DNT: 1\n" +
                    "Connection: keep-alive";

            try
            {
                // open a socket
                Socket socket = openSocket(serverName, port);

                // write to and read from socket
                String result = writeToAndReadFromSocket(socket, htmlGetRequest);

                // print out the result we got back from the server
                System.out.println(result);

                // close the socket
                socket.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        private String writeToAndReadFromSocket(Socket socket, String writeTo) throws Exception
        {
            try
            {
                // write text to the socket
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                bufferedWriter.write(writeTo);
                bufferedWriter.flush();

                // read text from the socket
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String str;
                while ((str = bufferedReader.readLine()) != null)
                {
                    sb.append(str).append("\n");
                }

                // close the reader, and return the results as a String
                bufferedReader.close();
                return sb.toString();
            }
            catch (IOException e)
            {
                e.printStackTrace();
                throw e;
            }
        }

        /**
         * Open a socket connection to the given server on the given port.
         */
        private Socket openSocket(String server, int port) throws Exception
        {
            Socket socket;

            // create a socket with timeout
            try
            {
                InetAddress inetAddress = InetAddress.getByName(server);
                SocketAddress socketAddress = new InetSocketAddress(inetAddress, port);

                // create socket
                socket = new Socket();

                int timeoutInMs = 10*1000;   // 10 seconds
                socket.connect(socketAddress, timeoutInMs);

                return socket;
            }
            catch (SocketTimeoutException ste)
            {
                System.err.println("Timed out waiting for the socket.");
                ste.printStackTrace();
                throw ste;
            }
        }

    }