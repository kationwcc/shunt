package com.wccwin.test;

import com.wccwin.shunt.ShuntRelay;

import java.util.List;

public class ShuntRelayImpl<T extends List> implements ShuntRelay<T> {

    public void calback(T list) {
        for (int i = 0; i < list.size() ; i++ ){
            System.out.println(list.get(i));
        }
        System.out.println(System.currentTimeMillis() - ShuntTest.time);
    }
}
