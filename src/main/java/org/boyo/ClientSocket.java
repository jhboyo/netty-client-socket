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

    public void sendFixedLength(int messageLength) {
        int delimiterLength = 256;

        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0; i<messageLength; i++) {
            stringBuilder.append("A");
        }
        byte[] totalData = stringBuilder.toString().getBytes();

        System.out.println("Sending messages.....");

        try {
            OutputStream os = socket.getOutputStream();

            //서버가 정해진 길이만큼 데이터가 들어오기를 기다리는지 확인하기 위해 바이트 배열을 delimiterLength씩 나누어 전송합니다.
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

        // 보낸 데이터를 그대로 전송하는지 확인하기 위해 InputStream으로 데이터를 받습니다.
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
