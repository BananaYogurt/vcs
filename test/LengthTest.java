import com.vcs.util.FileToByte;

import java.io.File;
import java.io.IOException;

/**
 * created by wuzh on 2017/5/3 22:42
 */
public class LengthTest {

    public static void main(String[] args) throws IOException {
        File file  =  new File("E:\\t\\压缩前.txt");
        byte[] src = FileToByte.fileToByte(file);
        System.out.println(src.length);
    }
}
