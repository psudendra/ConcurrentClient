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
 */
public class ConcurrentClient {

        // call  constructor to start the program
        public static void main(String[] args)
        {
            new ConcurrentClient();
        }

        public ConcurrentClient()
        {
            String serverName = "localhost";
            int port = 8080;
            try
            {
                // open a socket
                Socket socket = openSocket(serverName, port);

                // write to and read from socket
                String result = writeToAndReadFromSocket(socket, "GET /\n\n");

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
                    sb.append(str + "\n");
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