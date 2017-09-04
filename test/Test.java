import java.io.*;
import java.util.Random;

/**
 * created by 吴震煌 on 2017/3/9 13:21
 */
public class Test {
    public static void main(String[] args) throws IOException{
        /*File file = new File("E:\\t\\压缩前.txt");
        if(!file.exists())
            file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        OutputStreamWriter writer = new OutputStreamWriter(fos,"UTF-8");
        BufferedWriter buffered = new BufferedWriter(writer);

        Random random = new Random();

        for(long i = 1L;i<3000000L;i++){
            for(int j = 1;j<10;j++){
                buffered.write(Integer.toString(random.nextInt(1000)));
                buffered.write("-");
            }
            buffered.newLine();
            if(i %100000 == 0){
                buffered.flush();
            }
        }
        buffered.close();
        System.out.println("end ");*/

        /*String a = "\t\nb";
        String b = "\t\t\nb";
        String a1 = a.trim();
        String b1 = b.trim();
        String s = "";
        String k = "\t\n";
        System.out.println(a1.equals(b1));
        System.out.println(s.equals(k.trim()));*/
        //System.out.println("a22d363e9121c5697d2735e7560d01f79b918f3c".length());
        /*File f = new File("d:\\cuf\\utf\\kk.txt");
        System.out.println(f.getParent());*/
        /*String s = "123?123?zz";
        String[] strings = s.split("\\?");
        System.out.println(strings[0]);*/
        /*String s = "head-7";
        //int i = s.indexOf("head-");
        String r = s.substring(5,s.length());
        System.out.println(r);*/
        File f = new File("D:\\cuf\\js\\a.txt");
    }


}
