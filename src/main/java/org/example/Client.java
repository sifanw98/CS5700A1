package org.example;
import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        String serverName = "localhost";
        int port = 8080;

        try (Socket clientSocket = new Socket(serverName, port);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.print("Enter an integer between 1 and 100: ");
            String number = stdIn.readLine();
            String message = "Client of Sifan Wei, " + number;

            out.println(message);

            String response = in.readLine();
            System.out.println("Server response: " + response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

