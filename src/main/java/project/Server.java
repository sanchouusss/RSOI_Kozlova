package project;


import project.server.ServerAuthorizationOperations;

import java.io.IOException;
import java.net.*;


public class Server {



    public static void main(String[] args) throws IOException {
        try (var serverSocket = new ServerSocket(8080)) {

            while (true) {
                var socket = serverSocket.accept();

                var thread = new Thread(new ServerAuthorizationOperations(socket));
                thread.start();
            }

        }
    }
}

