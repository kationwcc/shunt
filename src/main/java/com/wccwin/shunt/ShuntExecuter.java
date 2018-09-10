package com.wccwin.shunt;

import java.util.List;

/**
 * 分流执行器
 * @param <V> 处理数据的数据类型
 * @param <T> 数据处理类
 * @author Kation Wang
 */
public class ShuntExecuter<V, T extends Executer>{

    /**
     * 单次处理数据的数据集合
     */
    private List<V> data;

    /**
     * 执行数据处理处理者
     */
    private Class<T> executerClass;

    /**
     * 分流最终监听器
     */
    private ShuntListener shuntListener;

    /**
     * 分流结果接受器
     */
    private ShuntRelay shuntRelay;

    public ShuntExecuter(List<V> data, Class<T> executerClass, ShuntRelay shuntRelay) {
        this.data = data;
        this.executerClass = executerClass;
        this.shuntRelay = shuntRelay;
    }

    public void shuntExecute(){

        //总共需要处理的数据数量
        int count = data.size();

        //获取可用的cpu核心数
        int itemCount = Runtime.getRuntime().availableProcessors();

        if(itemCount < 2){
            itemCount = 1;
        } else {
            itemCount = (int)(itemCount * 0.8);
        }

        //当总共需要处理的数据量小于可用的cpu核心数时，处理批次数量等于cpu核心数量
        if(count < itemCount) itemCount = count;

        //数据总量 / 处理批次数量 = 每批次需要处理数据量
        int itemSize = count / itemCount;

        //当总数不能整除次数时，每次处理数+1
        if(count % itemCount != 0 ) itemSize ++;

        //当数据量大于0并且数据量不满单次处理数据量时，单批处理量等于总数量
        if(itemSize == 0 && count > 0) itemSize = count;

        //执行单个分支的逻辑
        Executer executer;

        //构建分流监听器
        shuntListener = new ShuntListener(itemCount, this.shuntRelay);//分流处理数据监听器
        List<V> itemData;//单次需要处理的数据

        for (int i = 0; i < itemCount ; i++ ){

            //单批次处理数据的开始下标
            int fromIndex = itemSize * i ;

            //单批次处理数据的结束下标
            int toIndex = itemSize * (i + 1);

            //如果结束下标大于总数时 这是结束下标为总数-1
            if(toIndex > count) toIndex = count;

            //获取此次分流处理的数据集合
            itemData = data.subList(fromIndex, toIndex);
            try{
                executer = executerClass.newInstance();

                //设置线程的执行顺序
                executer.setId(i);

                //设置此次处理数据
                executer.setData(itemData);

                //配置负责监听结果的监听器
                executer.setShuntListener(shuntListener);

                //开始吧！
                Thread thread = new Thread(executer);
                thread.start();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }


}
