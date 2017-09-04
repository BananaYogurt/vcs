package com.vcs.util;

import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4FastDecompressor;

import java.io.*;
import java.security.NoSuchAlgorithmException;

/**
 * created by 吴震煌 on 2017/3/13 10:53
 */

/**
 * 采用lz4j来对文件进行压缩和解压，实现文件的快照备份
 */
public class CompressTool {

    private LZ4Factory factory = LZ4Factory.safeInstance();
    private byte[] src;
    private byte[] data;
    private File outputFile;
    private String outputFileName;
    private FileOutputStream fos;
    private OutputStreamWriter out;
    private BufferedWriter writer;

    //压缩算法
    public String compress(File file,String depository) throws IOException, NoSuchAlgorithmException {

        long start = System.currentTimeMillis();

        LZ4Compressor compressor = factory.highCompressor();

        src = FileToByte.fileToByte(file);

        //压缩文件的名字为文件经过SHA1算法计算的数字签名
        outputFileName = depository+SHA1Tool.encrypt(file)+"@"+src.length;
        //压缩后的文件
        outputFile = new File(outputFileName);
        //
        fos = new FileOutputStream(outputFile);
        //压缩后的字符数组
        data = compressor.compress(src);
        //
        fos.write(data);
        out = new OutputStreamWriter(fos);
        writer = new BufferedWriter(out);
        writer.flush();
        fos.close();
        out.close();
        writer.close();

        long end = System.currentTimeMillis();

        System.out.println("【"+this.getClass().getName()+"】log : 压缩 '"+file.getAbsolutePath()+"' 耗时"+(end - start)+"毫秒");

        return  outputFileName;

    }

    //解压算法
    public File decompress(File file,String absolutePath) throws IOException{

        long start = System.currentTimeMillis();

        //要解压的字符数组
        src = FileToByte.fileToByte(file);

        LZ4FastDecompressor decompresser = factory.fastDecompressor();

        String[] temp = file.getName().split("@");

        int length = Integer.parseInt(temp[1]);

        data = decompresser.decompress(src,length);

        File outputFile = ByteToFile.byteToFile(data,absolutePath);

        long end = System.currentTimeMillis();

        System.out.println("【"+this.getClass().getName()+"】log : 解压 '"+absolutePath+"' 耗时"+(end - start)+"毫秒");

        return outputFile;
    }

}
