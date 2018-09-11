package com.wccwin.test;

import com.wccwin.shunt.Executer;
import com.wccwin.shunt.ShuntExecuter;
import com.wccwin.shunt.ShuntRelay;

import java.util.ArrayList;
import java.util.List;

public class ShuntTest {

    public static long time = 0l;

    public static void main(String[] args) throws Exception{
        int size = 1 ;

        List<String> data = new ArrayList<String>(size);
        for (int i = 0; i < size ; i++ ){
            data.add("test data ("+ i +")");
        }

        ShuntTest.time = System.currentTimeMillis();
        ShuntTest.test1(data);
        //ShuntTest.test2(data);
    }

    /**
     * 高性能执行
     * @param data
     */
    public static void test1(List<String> data){

        ShuntRelay shuntRelay = (list) -> {
            for (int i = 0; i < list.size() ; i++ ){
                System.out.println(list.get(i));
            }
            System.out.println(System.currentTimeMillis() - ShuntTest.time);
            System.out.println(list.size());

        };

        ShuntExecuter<Integer, ExecuterImpl> shuntExecuter =
                new ShuntExecuter(data, ExecuterImpl.class, shuntRelay);
        shuntExecuter.shuntExecute();
    }
}
