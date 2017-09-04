import com.vcs.util.ByteToFile;
import com.vcs.util.FileToByte;
import net.jpountz.lz4.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * created by wuzh on 2017/5/3 14:20
 */
public class Lz4jTest {
    public static byte[] compress(byte srcBytes[]) throws IOException {
        long start =  System.currentTimeMillis();
        LZ4Factory factory = LZ4Factory.fastestInstance();
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        LZ4Compressor compressor = factory.fastCompressor();
        LZ4BlockOutputStream compressedOutput = new LZ4BlockOutputStream(
                byteOutput, 2048, compressor);
        compressedOutput.write(srcBytes);
        compressedOutput.close();
        long end = System.currentTimeMillis();
        System.out.println("log | 压缩一共耗时 "+(end - start)+"毫秒");
        return byteOutput.toByteArray();

    }

    public static byte[] uncompress(byte[] bytes) throws IOException {
        long start =  System.currentTimeMillis();
        LZ4Factory factory = LZ4Factory.fastestInstance();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        LZ4FastDecompressor decompresser = factory.fastDecompressor();
        LZ4BlockInputStream lzis = new LZ4BlockInputStream(
                new ByteArrayInputStream(bytes), decompresser);
        int count;
        byte[] buffer = new byte[2048];
        while ((count = lzis.read(buffer)) != -1) {
            baos.write(buffer, 0, count);
        }
        lzis.close();
        long end = System.currentTimeMillis();
        System.out.println("log | 解压共耗时 "+(end - start)+"毫秒");
        return baos.toByteArray();
    }

    public static void main(String[] args) throws IOException {
        File file = new File("E:\\t\\压缩前.txt");
        byte[] src = FileToByte.fileToByte(file);
        byte[] data = compress(src);
        ByteToFile.byteToFile(data,"E:\\t\\压缩后.rar");
    }

}
