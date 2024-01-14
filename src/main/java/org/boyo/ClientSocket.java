package org.boyo;

import com.google.common.io.ByteStreams;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;


public class ClientSocket {

    private Socket socket;

    public ClientSocket(Socket socket) {
        this.socket = socket;
    }

    public void sendFiexedLength(int messageLength) {
        int delimiterLength = 256;

        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0; i<messageLength; i++) {
            stringBuilder.append("A");
        }
        byte[] totalData = stringBuilder.toString().getBytes();

        System.out.println("Sending messages.....");

        try {
            OutputStream os = socket.getOutputStream();

            for (int i = 0; i < messageLength / delimiterLength; i++) {
                byte[] sending = Arrays.copyOfRange(totalData, i * delimiterLength, (i + 1) * delimiterLength);
                System.out.println("sending... " + (i + 1));
                os.write(sending);
                os.flush();
                Thread.sleep(500);
            }

        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Receiving message");
        try {
            InputStream is = socket.getInputStream();

            byte[] reply = new byte[messageLength];
            ByteStreams.read(is, reply, 0, reply.length);

            System.out.println(new String(reply));
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }
}
