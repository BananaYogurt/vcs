package com.vcs.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * created by wuzh on 2017/5/26 9:31
 */
public class RemoteTool {

    public static void receiveFile(SocketChannel socketChannel,File file) throws IOException {

        FileOutputStream fos = null;

        FileChannel channel = null;

        try {
            fos = new FileOutputStream(file);

            channel = fos.getChannel();

            ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

            int size = 0;

            while ((size = socketChannel.read(buffer)) != -1) {

                buffer.flip();

                if (size > 0) {
                    buffer.limit(size);
                    channel.write(buffer);
                    buffer.clear();
                }
            }
        } finally {

            try {
                channel.close();
            } catch(Exception ex) {}
            try {
                fos.close();
            } catch(Exception ex) {}
        }

    }

    public  static void sendFile(SocketChannel socketChannel, File file) throws IOException {

        FileInputStream fis = null;

        FileChannel channel = null;
        try {
            fis = new FileInputStream(file);
            channel = fis.getChannel();
            ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
            int size = 0;
            while ((size = channel.read(buffer)) != -1) {
                buffer.rewind();
                buffer.limit(size);
                socketChannel.write(buffer);
                buffer.clear();
            }
            socketChannel.socket().shutdownOutput();
        } finally {
            try {
                channel.close();
            } catch(Exception ex) {}
            try {
                fis.close();
            } catch(Exception ex) {}
        }
    }
}
