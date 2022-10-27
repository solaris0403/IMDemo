package com.caowei.im;

import java.io.Serializable;

/**
 * 消息接受实体
 */
public class MsgRecBean extends BaseBean implements Serializable {
    private Integer fromuserid;//发送人ID
    private String msg;//消息

    @Override
    public Byte code() {
        return 5;//
    }

    public Integer getFromuserid() {
        return fromuserid;
    }

    public void setFromuserid(Integer fromuserid) {
        this.fromuserid = fromuserid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
