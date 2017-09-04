package com.vcs.algorithm;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * created by wuzh on 2017/4/27 19:56
 */
public class Merge {


    private int pre = 1;

    private int current = 1;

    private boolean conflict = false;

    private boolean success = true;

    private List<LineHashCode> temp = new LinkedList<>();

    private List<LineHashCode> result = new ArrayList<>();

    public List<LineHashCode> merge(List<LineHashCode> list1,List<LineHashCode> list2){

        int length1 = list1.size();

        int length2 = list2.size();

        int matrix[][] = new int[length1 + 1][length2 + 1];

        long t1 = System.currentTimeMillis();
        for( int i = length1 - 1; i >= 0 ; i--){

            for( int j = length2 - 1 ; j >= 0 ; j--){

                if( list1.get(i).getHashCode().equals(list2 .get(j).getHashCode()) ){

                    matrix[i][j] =  matrix[i + 1][j + 1] + 1;
                }else{

                    matrix[i][j] = Math.max(matrix[i][j + 1] ,matrix[i+1][j]);
                }
            }
        }
        long t2 = System.currentTimeMillis();

        System.out.println("【com.vcs.algorithm.Merge】log : 初始化矩阵的时间为"+(t2-t1)+"ms!");
        int a = 0 ,b = 0;

        LineHashCode lhc;

        while ( a < list1.size() && b < list2.size()){

            pre = current;

            if( list1.get(a).getHashCode().equals(list2.get(b).getHashCode())){

                current = 1;

                if(temp.size() != 0){

                    handleConflict();

                    temp.clear();

                }

                lhc = list1.get(a);

                lhc.setOperation(Operation.EQUAL);

                result.add(lhc);

                a++;

                b++;

                conflict = false;

            }else if( matrix[a+1][b] >= matrix[a][b+1]){

                current = 2;

                if( pre == 3){
                    conflict = true;
                }

                lhc  =  list1.get(a);

                lhc.setOperation(Operation.DELETE);

                temp.add(lhc);

                a++;


            }else{

                current = 3;

                if(pre == 2){
                    conflict = true;
                }
                lhc  =  list2.get(b);

                lhc.setOperation(Operation.INSERT);

                temp.add(lhc);
                b++;

            }

        }

        if(temp.size() != 0){

            handleConflict();

            temp.clear();

        }

        return result;
    }

    public void execute(File f1,File f2) throws IOException{

        long start = System.currentTimeMillis();

        long end = System.currentTimeMillis();

        List<LineHashCode> l1 = FileToHashList.hashList(f1);

        List<LineHashCode> l2 = FileToHashList.hashList(f2);

        List<LineHashCode> result = merge(l1,l2);

        FileWriter writer = new FileWriter(f1,false);

        writer.append("");

        writer.flush();

        writer.close();

        writer = new FileWriter(f1,true);

        for(LineHashCode lhc: result){
            writer.append(lhc.getText());
            writer.append("\n");
            writer.flush();
        }
        writer.close();

    }

    public void handleConflict(){
        if(conflict){

            success = false;

            for(LineHashCode lineHashCode : temp){

                if(lineHashCode.getOperation() == Operation.INSERT){

                    if(!lineHashCode.getText().trim().equals("")) {

                        lineHashCode.insertText();
                    }
                }else if(lineHashCode.getOperation() == Operation.DELETE){

                    if(!lineHashCode.getText().trim().equals("")) {

                        lineHashCode.deleteText();
                    }
                }
            }
            result.addAll(temp);

        }else{

            result.addAll(temp);
        }

    }

    public boolean isSuccess() {
        return success;
    }

    /*public static void main(String[] args) throws IOException {
        Merge merge  =  new Merge();
        File f1 = new File("D:\\cuf\\bcd.txt");
        File f2 = new File("D:\\cuf\\bce.txt");
        merge.execute(f1,f2);
        System.out.println(merge.isSuccess());
    }*/
}
