package com.caowei.im;

import java.io.Serializable;

/**
 * 主要定义 tag 字段，标识协议的标识符，
 * code () 抽象方法，主要表示的是业务指令，不同的业务对应不同的指令。
 */
public abstract class BaseBean implements Serializable {
    private Integer tag = 1;//固定值，标识的是一个协议类型，不同协议对应不同的值

    public abstract Byte code();//业务指令抽象方法

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }
}
