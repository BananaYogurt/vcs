import jdk.internal.util.xml.impl.Input;

import java.io.*;

/**
 * created by wuzh on 2017/5/22 7:24
 */
public class EncodingTest {
    public static void main(String[] args) throws IOException{
        File file = new File("E:\\temp\\g.txt");
        FileInputStream in = new FileInputStream(file);
        InputStreamReader reader = new InputStreamReader(in) ;
        System.out.println(reader.getEncoding());

    }
}
