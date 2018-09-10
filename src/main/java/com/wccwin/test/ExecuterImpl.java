package com.wccwin.test;

import com.wccwin.shunt.Executer;

import java.util.List;

public class ExecuterImpl extends Executer<String> {

    public List<String> execute(List<String> data) {
        for (int i = 0; i < data.size() ; i++ ){
            String str = data.get(i).toString() + " | handle success! ";
            data.set(i, str);
            try {
                Thread.sleep(1);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return data;
    }

}
