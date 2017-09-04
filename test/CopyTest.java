import java.io.*;

/**
 * created by wuzh on 2017/5/4 16:20
 */
public class CopyTest {
    public static void main(String[] args) throws IOException {
        File f1  = new File("D:\\cuf\\repo\\.vcs\\object\\versions\\cache\\G3I1CBUOI8");
        File f2 = new File("D:\\cuf\\repo\\.vcs\\object\\versions\\content\\4OV6COQOOS");
        InputStream input = new FileInputStream(f1);
        OutputStream out = new FileOutputStream(f2);
        byte[] buffer = new byte[1024];
        int line ;
        while((line = input.read(buffer)) != -1){
            out.write(buffer,0,line);
        }
        input.close();
        out.close();
    }
}
