package com.caowei.im;

import java.io.Serializable;

/**
 * 消息发送实体
 */
public class MsgReqBean extends BaseBean implements Serializable {
    private Integer fromuserid;//发送人ID
    private Integer touserid;//接受人ID
    private String msg;//发送消息

    @Override
    public Byte code() {
        return 3;//业务指令
    }

    public Integer getFromuserid() {
        return fromuserid;
    }

    public void setFromuserid(Integer fromuserid) {
        this.fromuserid = fromuserid;
    }

    public Integer getTouserid() {
        return touserid;
    }

    public void setTouserid(Integer touserid) {
        this.touserid = touserid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
