package com.caowei.im;

import java.io.Serializable;

/**
 * 消息响应
 */
public class MsgResBean extends BaseBean implements Serializable {
    private Integer status;//响应状态，0发送成功，1发送失败
    private String msg;//响应信息
    @Override
    public Byte code() {
        return 4;//业务指令
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
