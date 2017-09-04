package com.vcs.http;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.StringTokenizer;

/**
 * Created by nice on 2017/9/4.
 */
public class VcsHttpService implements Runnable{

    private ServerSocket serverSocket;

    private Socket client;

    public static final int PORT = 8083;

    public VcsHttpService(){
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(serverSocket == null){
            System.out.println("can not start server. quit");
            System.exit(1);
        }
        new Thread(this).start();
        System.out.println("server is starting ......");
    }

    @Override
    public void run() {

        while (true){
            try {
                client = serverSocket.accept();
                if(client != null){
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    System.out.println("客户端发送过来的信息===========================");
                    String firstLine = in.readLine();
                    System.out.println(firstLine);
                    String resource = URLDecoder.decode(firstLine.substring(firstLine.indexOf('/'),
                            firstLine.lastIndexOf('/')-5), "UTF-8");

                    String method = new StringTokenizer(firstLine).nextElement().toString();
                    String line ;
                    while ( (line = in.readLine()) != null){
                            if("".equals(line))
                                break;
                        System.out.println(line);
                    }

                    System.out.println("resource = "+resource);
                    String fileName = resource.substring(resource.lastIndexOf('/')+1);
                    System.out.println("fileName = "+fileName);
                    if(!fileName.endsWith("jpg")) {

                        PrintWriter writer = new PrintWriter(client.getOutputStream(),true);
                        writer.print("HTTP/1.1 404 NOT found\n");
                        writer.print("\n"); //根据HTTP的协议，空行将结束信息
                        writer.close();
                        disConnect(client);
                        continue;
                    }
                    response(fileName,client);
                    //disConnect(client);

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void disConnect(Socket socket){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(socket + "disconnect from server");
    }

    public void response(String fileName ,Socket client){

        try {
            PrintStream out = new PrintStream(client.getOutputStream(),true);
            File file = new File("E:\\"+fileName);
            if(file.exists() && !file.isDirectory()){
                out.print("HTTP/1.1 200 OK\n");
                out.print("Content-Type:application/binary\n");
                out.print("Content-Length:"+file.length()+"\n");
                out.println();
                FileInputStream fis = new FileInputStream(file);
                byte[] data = new byte[fis.available()];
                fis.read(data);
                out.write(data);
                out.close();
                fis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new VcsHttpService();
    }
}
