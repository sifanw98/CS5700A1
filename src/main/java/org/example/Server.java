package org.example;

import java.io.*;
import java.net.*;
import java.util.Random;

public class Server {
    public static void main(String[] args) {
        int port = 8080;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server listening on port " + port);

            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    String[] parts = inputLine.split(", "); // Split by ", " to separate the name and the number
                    if (parts.length < 2) {
                        out.println("Error: Invalid input format. Expected name and number.");
                        break;
                    }
                    try {
                        int clientNumber = Integer.parseInt(parts[1].trim());
                        if (clientNumber < 1 || clientNumber > 100) {
                            out.println("Error: Number out of range. Connection closing.");
                            break;
                        }
                        int serverNumber = new Random().nextInt(100) + 1;
                        out.println("Server Number: " + serverNumber + ", Client Number: " + clientNumber + ", Sum: " + (clientNumber + serverNumber));
                    } catch (NumberFormatException e) {
                        out.println("Error: Invalid input. Please enter an integer.");
                    }
                }
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
