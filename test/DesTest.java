import com.vcs.util.DESTool;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.Properties;

/**
 * created by 吴震煌 on 2017/3/13 9:13
 */
public class DesTest {
    public static void main(String[] args) throws IllegalBlockSizeException, InvalidKeyException, InvalidAlgorithmParameterException, BadPaddingException, IOException {
       /* DESTool des = new DESTool();
        String s = "C:\\abc\\7979898798好好学习.txt";
        s = des.encrypt(s);
        System.out.println("crypt: "+ s);
        System.out.println(s.length());
        s = des.decrypt(s);
        System.out.println("decrypt: " + s);*/
        //new File("D:\\cuf\\repo\\a+/ad.txt").createNewFile();

        Properties prop = new Properties();

        File pf = new File("D:\\cuf\\prop");

        FileOutputStream fos = new FileOutputStream(pf);

        prop.setProperty("key1","==\\*!fsad\\\\&,sdaf>>?");

        prop.store(fos,"sd");

        fos.close();

        FileInputStream fis = new FileInputStream(pf);

        prop.load(fis);

        System.out.println(prop.getProperty("key1"));


    }
}
