package com.wccwin.shunt;

import java.util.List;

/**
 * 分流处理后回调接口
 * @param <T> 处理的数据类型
 */
@FunctionalInterface
public interface ShuntRelay<T extends List> {

    /**
     * 处理回调
     * @param t
     */
    public void calback(T t);

}
