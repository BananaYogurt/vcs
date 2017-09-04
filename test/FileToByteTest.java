import com.vcs.util.SHA1Tool;

import java.io.*;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;

/**
 * created by 吴震煌 on 2017/3/17 9:33
 */
public class FileToByteTest {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        File file = new File("D:\\annotation\\mygit\\cev2.txt");
        byte[] bytes = new byte[(int)file.length()];
        FileInputStream fis = new FileInputStream(file);
        File file2 = new File("D:\\annotation\\mygit\\cev3.txt");
        FileOutputStream fos = new FileOutputStream(file2);
        fis.read(bytes);
        for(int i = 0 ; i <bytes.length;i++){
            System.out.print((char) bytes[i]);
            fos.write(bytes[i]);
        }
        SHA1Tool sha1Tool = new SHA1Tool();
        String s = sha1Tool.encrypt(bytes);
        System.out.println("\n sha1 = "+s);
        System.out.println(s.length());

        System.out.println();
        fis.close();
        byte[] b = Files.readAllBytes(file.toPath());
        for (int j = 0 ; j < b.length ; j++){
            System.out.print( (char )b[j]);
        }

        fos.close();

    }
}
