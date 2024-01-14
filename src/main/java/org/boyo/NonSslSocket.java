package org.boyo;


import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class NonSslSocket {

    private String host;
    private int port;
    public NonSslSocket(String host, int port) {
        this.host = host;
        this.port = port;
    }


    public void run(int messageLength) {

        try {
            Socket socket = new Socket();
            SocketAddress socketAddress = new InetSocketAddress(host, port);
            socket.connect(socketAddress);

            ClientSocket clientSocket = new ClientSocket(socket);
            clientSocket.sendFiexedLength(messageLength);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
