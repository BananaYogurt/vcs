import java.io.*;
import java.util.Properties;

/**
 * created by wuzh on 2017/4/30 11:29
 */
public class PropertiesTest {

    public static void main(String[] args) throws IOException {
        File file = new File("D:\\cuf\\repo\\.vcs\\object\\versions\\cache\\RPU4FYF31N");
        InputStream in =  new FileInputStream(file);
        Properties pro = new Properties();
        pro.load(in);
        in.close();
        System.out.println(pro.getProperty("a").equals(""));
        System.out.println(pro.getProperty("b"));
        //OutputStream os  = new FileOutputStream(file);
        //pro.setProperty("k1","v3");
        //pro.store(os,"");
        //os.close();
    }
}
