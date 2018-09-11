package com.wccwin.shunt;

import java.util.List;

/**
 * 处理器
 * 执行处理业务逻辑
 * @param <T> 处理的数据类型
 * @author Kation Wang
 */
public abstract class Executer<T> implements Runnable {

    /**
     * 处理的id
     */
    private int id;

    /**
     * 处理的数据
     */
    private List<T> data;

    /**
     * 处理之后的结果监听器
     */
    private ShuntListener<List> shuntListener;

    /**
     * 执行处理数据的业务逻辑
     * @param data
     * @return
     */
    public abstract List execute(List<T> data);

    /**
     * action !!!
     */
    public void run() {
        //调用execute()得到结果集添加到监听器内
        List result = execute(this.getData());
        shuntListener.addSesult(this.getId(), result);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public ShuntListener<List> getShuntListener() {
        return shuntListener;
    }

    public void setShuntListener(ShuntListener<List> shuntListener) {
        this.shuntListener = shuntListener;
    }
}
