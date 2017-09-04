package com.vcs.util;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * created by wuzh on 2017/5/3 10:46
 */
public class PropertiesTool {


    public static void addProperty(File file,String key ,String value ,String message){

        Properties prop = new Properties();

        try {

            FileInputStream fis = new FileInputStream(file);

            String oldValue;

            prop.load(fis);

            if(null != prop.getProperty(key)){

                oldValue = prop.getProperty(key);

                fis.close();

                if(!oldValue.equals(value)){

                    prop.replace(key,oldValue + "?" + value);

                }

            }else {

                fis.close();

                prop.setProperty(key,value);
            }

            fis.close();

            FileOutputStream fos = new FileOutputStream(file);

            prop.store(fos,message);

        }  catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @param file
     * @param fileName
     * @param i
     * @param message
     * @return 0正确执行 1没有找到该文件 2异常
     */
    public static int resetProperty(File file,String fileName,int i,String message){

        Properties prop = new Properties();

        String tempList ;

        try {
            FileInputStream fis = new FileInputStream(file);

            prop.load(fis);

            if(null != prop.getProperty(fileName)){

                tempList = prop.getProperty(fileName);

                String[] temps = tempList.split("\\?");

                int length = temps.length;

                if(length - 1 > i){

                    fis.close();


                }else{
                    return 0;
                }

            }else{
                return 1;
            }

            return 0;
        } catch (IOException ie) {
            ie.printStackTrace();
            return 2;
        }
    }


    /**
     *
     * @param file
     * @param fileName
     * @return 1没有该文件  0删除成功
     */
    public static int delProperty(File file ,String fileName,String message){

        Properties prop = new Properties();

        try {
            FileInputStream fis = new FileInputStream(file);

            prop.load(fis);

            if(null == prop.getProperty(fileName)){
                return 1;
            }else{
                fis.close();
                prop.remove(fileName);
                FileOutputStream fos = new FileOutputStream(file);
                prop.store(fos,"");
                fos.close();
                return 0;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;

    }


    //将f2的内容添加到f1   f2 --> f1
    public static void merge(File f1,File f2,String message) throws IOException{


        FileOutputStream fos = new FileOutputStream(f1,true);

        Properties prop = new Properties();

        Map<String,String> map = getAllKV(f2);

        Map<String,String> newMap = new HashMap<>();

        String[] names = {};

        for(Map.Entry<String,String> entry :map.entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();

            if(!value.equals("")){
                names = value.split("\\?");
                newMap.put(key,names[names.length-1]);
            }
        }

        prop.putAll(newMap);

        //prop.store(fos,"commit message = '"+message+"';commit from v("+f2.getName()+") to v("+f1.getName()+")");

        prop.store(fos,message);

        fos.close();
    }

    public static Map<String,String> getAllKV(File file) throws IOException {

        Properties prop = new Properties();

        FileInputStream fis = new FileInputStream(file);

        prop.load(fis);

        Map<String,String> map = new HashMap<>();

        Iterator<String> it = prop.stringPropertyNames().iterator();

        while (it.hasNext()){

            String key = it.next();

            String value = prop.getProperty(key);

            map.put(key,value);
        }

        fis.close();

        return map;

    }

    public static void MapToFile(Map<String,String> map,File content,String message)throws IOException{

        Properties prop = new Properties();

        FileOutputStream fos = new FileOutputStream(content);

        prop.putAll(map);

        prop.store(fos,message);

        fos.close();

    }


}
