package com.vcs.command;

import com.vcs.command.inf.BaseCommandImpl;
import com.vcs.util.Result;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;

import static com.vcs.util.RemoteTool.receiveFile;
import static com.vcs.util.RemoteTool.sendFile;

/**
 * created by wuzh on 2017/5/26 9:23
 */
public class Remote extends BaseCommandImpl implements Runnable {

    @Override
    public Result execute(String input) {

        run();
        //return super.execute(input);
        result.setOutput("client star");
        return result;
    }

    @Override
    public void run() {
        SocketChannel socketChannel = null;
        try {
            socketChannel = SocketChannel.open();
            SocketAddress socketAddress = new InetSocketAddress("localhost", 8080);
            socketChannel.connect(socketAddress);

            receiveFile(socketChannel, new File("E:/temp/cba.txt"));
            //RemoteTool.receiveFile(socketChannel, new File("E:/annotation/client_receive.log"));
        } catch (Exception ex) {

        } finally {
            try {
                socketChannel.close();
            } catch(Exception ex) {}
        }
    }
}
