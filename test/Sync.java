
import java.io.*;

/**
 * Created by nice on 2017/8/24.
 */
public class Sync implements Runnable{

    @Override
    public void run() {

        MyFile fileFactory = new MyFile("E:\\test.txt");
        File file = fileFactory.getFile();
        FileWriter writer = null;
        if(file.exists()){
            synchronized (this){
                try {
                    int i = 0;
                    for (;i<20;i++){
                        Thread.sleep(100);
                        writer = new FileWriter(file,true);
                        writer.append(Thread.currentThread().getName()+"..."+i+"\n");
                        writer.flush();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(null != writer)
                        try {
                            writer.close();
                            System.out.println("============================");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
            }

        }
    }

    public static void main(String[] args)throws IOException,InterruptedException {
        Sync sync = new Sync();
        Thread t1 = new Thread(sync);
        Thread t2 = new Thread(sync);
        Thread t3 = new Thread(sync);
        Thread t4 = new Thread(sync);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
