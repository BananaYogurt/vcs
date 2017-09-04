import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4FastDecompressor;

import java.io.*;
import java.nio.file.Files;

/**
 * created by 吴震煌 on 2017/3/9 14:34
 */
public class Test3 {



    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        File file = new File("E:\\t\\压缩后.txt");
        LZ4Factory factory = LZ4Factory.fastestInstance();
        LZ4FastDecompressor decompressor = factory.fastDecompressor();
        byte[] data = Files.readAllBytes(file.toPath());
        byte[] src = Files.readAllBytes(new File("E:\\t\\压缩前.txt").toPath());
        byte[] result = decompressor.decompress(data,src.length);

        File f = new File("E:\\t\\解压后.txt");
        if(!f.exists()){
            f.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(result);
        OutputStreamWriter out = new OutputStreamWriter(fos);
        BufferedWriter writer = new BufferedWriter(out);

        writer.flush();
        writer.close();
        long end  = System.currentTimeMillis();
        System.out.println("it spend" + (end - start));
    }
}
