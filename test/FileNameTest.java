import java.io.File;
import java.io.IOException;

/**
 * created by 吴震煌 on 2017/3/13 11:02
 */
public class FileNameTest {
    public static void main(String[] args) throws IOException {
        /*File f = new File("E:\\annotation\\abc\\");
        System.out.println(f.getAbsolutePath());
        //idea的D盘 File("D:")不是正的在地盘下面
        File absoluteFile = new File(f.getAbsolutePath());
        System.out.println(absoluteFile.getParent());
        System.out.println(f.getCanonicalPath());*/


        /*Cd cd = new Cd();
        System.out.println(cd.formatPath("c:\\"));
        System.out.println(cd.formatPath("e:\\annotation\\abc\\"));
        System.out.println(cd.formatPath("d:"));
        System.out.println(cd.formatPath("ab?,,,c"));*/
        File f = new File("D:\\myCode");
        System.out.println(f.isDirectory());
        /*String s = "612345\\\\\\\\";
        s = cd.formatString(s);
        System.out.println(s);*/
       /* String s = "1234\\\\\\";
        System.out.println(s.charAt(s.length()-1));
        System.out.println(s.substring(0,s.length()-1));*/
       // System.out.println(cd.formatPath("abc\\"));;
    }
}
