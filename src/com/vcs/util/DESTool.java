package com.vcs.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * created by 吴震煌 on 2017/3/10 10:32
 */

/**
 * 这个类采用对称加密，用来加密/解密文件的名字
 */
public class DESTool {

    private static final String KEY = "030z5Qa1";

    private SecretKeyFactory factory;

    private DESKeySpec keySpec;

    private SecretKey key;

    private Cipher cipher;

    private SecureRandom random;

    private BASE64Encoder base64Encoder;

    private BASE64Decoder base64decoder;

    public DESTool(){
        try {
            this.factory = SecretKeyFactory.getInstance("DES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            this.keySpec = new DESKeySpec(KEY.getBytes());
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        try {
            key = factory.generateSecret(keySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        try {
            try {
                cipher = Cipher.getInstance("DES");
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        random = new SecureRandom();
        base64Encoder = new BASE64Encoder();
        base64decoder = new BASE64Decoder();
    }

    public String encrypt(String s) throws BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException {

        cipher.init(Cipher.ENCRYPT_MODE, key, random);

        byte[] cryptBytes = cipher.doFinal(s.getBytes());

        String result = base64Encoder.encode(cryptBytes);

        return result;
    }

    public String decrypt(String s) throws InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException {
        cipher.init(Cipher.DECRYPT_MODE, key, random);
        return new String(cipher.doFinal(base64decoder.decodeBuffer(s)));
    }

}
