import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;

import java.io.*;
import java.nio.file.Files;

/**
 * created by 吴震煌 on 2017/3/9 13:37
 */
public class Test2 {
    public static void main(String[] args) throws Exception {
        File file = new File("E:\\t\\压缩前.txt");
        File outputFile = new File("E:\\t\\压缩后.txt");
        byte[] data = Files.readAllBytes(file.toPath());

        LZ4Factory factory = LZ4Factory.safeInstance();
        LZ4Compressor compressor = factory.highCompressor();
        FileOutputStream fos= new FileOutputStream(outputFile);
        Long start = System.currentTimeMillis();
        byte[] result = compressor.compress(data);
        Long end = System.currentTimeMillis();
        System.out.println("end .the compress process spend "+(end - start)+" millis");
        fos.write(result);
        OutputStreamWriter out = new OutputStreamWriter(fos);
        BufferedWriter writer = new BufferedWriter(out);
        writer.flush();
        writer.close();
    }
}
