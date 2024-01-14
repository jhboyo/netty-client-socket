package org.boyo;

import java.util.Scanner;

public class ClientSocketApplication {
    public static void main(String[] args) {

        String serverHost = "127.0.0.1";
        int port = 8090;

        try {
            System.out.println("Enter message length: ");
            Scanner scanner = new Scanner(System.in);
            int messageLength = Integer.parseInt(scanner.nextLine());

            NonSslSocket socket = new NonSslSocket(serverHost, port);
            socket.run(messageLength);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}