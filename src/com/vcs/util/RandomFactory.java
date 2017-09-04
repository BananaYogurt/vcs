package com.vcs.util;

import java.util.Random;

/**
 * created by wuzh on 2017/4/30 21:55
 * 用来生成随机的10位序列号
 */
public class RandomFactory {

    private static final String[] baseFacts = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","I","J",
            "K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

    public static String getSerialString(){

        int length = baseFacts.length;

        StringBuilder serial = new StringBuilder();

        Random random = new Random();

        for(int i = 0 ; i < 10 ; i++){
            int j =  random.nextInt(length);
            serial.append(baseFacts[j]);
        }
        return  serial.toString();

    }

}
