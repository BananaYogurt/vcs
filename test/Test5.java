import java.io.File;
import java.io.IOException;

/**
 * created by wuzh on 2017/5/9 15:33
 */
public class Test5 {

    public static void main(String[] args) throws IOException {
        File file = new File("E:\\bbc.txt");
        //file.createNewFile();
        file.delete();
    }
}
