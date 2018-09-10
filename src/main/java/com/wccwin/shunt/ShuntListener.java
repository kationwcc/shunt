package com.wccwin.shunt;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 分流监听器
 * @param <T> 处理数据的类型
 * @author Kation Wang
 */
public class ShuntListener<T extends List> {

    /**
     * 处理数据批次总数
     */
    private int count;

    /**
     * 数据cashe
     */
    private ConcurrentHashMap<Integer, T> result = new ConcurrentHashMap();

    /**
     * 回调接口
     */
    private ShuntRelay shuntRelay;

    public ShuntListener(int count, ShuntRelay shuntRelay) {
        this.count = count;
        this.shuntRelay = shuntRelay;
    }

    /**
     * 得到数据处理结果
     * 加锁处理添加结果集，防止异步造成的数据错乱
     * @param id 执行id
     * @param t 结果数据
     */
    public synchronized void addSesult(int id, T t){
        result.put(id, t);

        if(result.size() == count){
            //所有业务执行完毕,按id顺序重新重构所有的结果集顺序
            List list = new ArrayList();
            TreeSet<Integer> keySet = new TreeSet(result.keySet());
            for(Integer i : keySet){
                list.addAll(result.get(i));
            }
            //处理完毕，通知回调（分流接力）
            shuntRelay.calback(list);
        }
    }

}
