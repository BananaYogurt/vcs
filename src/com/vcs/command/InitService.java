package com.vcs.command;

import com.vcs.command.inf.BaseCommandImpl;
import com.vcs.util.Result;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;

import static com.vcs.util.RemoteTool.receiveFile;
import static com.vcs.util.RemoteTool.sendFile;

/**
 * created by wuzh on 2017/5/26 8:43
 */
public class InitService extends BaseCommandImpl{

    @Override
    public Result execute(String input) {


        ServerThread st = new ServerThread();
        st.start();

        result.setOutput("server star");
        //return super.execute(input);
        return result;
    }

    class ServerThread extends Thread{

        @Override
        public void run() {
            Selector selector = null;

            ServerSocketChannel serverSocketChannel = null;

            try {
                // Selector for incoming time requests
                selector = Selector.open();

                // Create a new server socket and set to non blocking mode
                serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.configureBlocking(false);

                serverSocketChannel.socket().setReuseAddress(true);

                serverSocketChannel.socket().bind(new InetSocketAddress(8080));

                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

                while (selector.select() > 0) {
                    Iterator<SelectionKey> it = selector.selectedKeys().iterator();

                    while (it.hasNext()) {
                        SelectionKey readyKey = it.next();
                        it.remove();
                        doit((ServerSocketChannel) readyKey.channel());
                    }
                }
            } catch (ClosedChannelException cclx) {

            } catch (IOException ie) {

            } finally {
                try {
                    selector.close();
                } catch(Exception ex) {}
                try {
                    serverSocketChannel.close();
                } catch(Exception ex) {}
            }
        }
    }

    private void doit(final ServerSocketChannel serverSocketChannel) throws IOException {
        SocketChannel socketChannel = null;
        try {
            socketChannel = serverSocketChannel.accept();

            //receiveFile(socketChannel, new File("E:/annotation/server_receive.log"));
            sendFile(socketChannel, new File("E:/temp/abc.txt"));
        } finally {
            try {
                socketChannel.close();
            } catch(Exception ex) {}
        }

    }


}
