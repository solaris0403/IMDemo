package com.caowei.im;

import java.io.Serializable;

/**
 * 登录响应实体
 */
public class LoginResBean extends BaseBean implements Serializable {
    private Integer status;//响应状态，0登录成功，1登录失败
    private String msg;//响应信息
    private Integer userid;//用户ID

    @Override
    public Byte code() {
        return 2;//业务指令
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

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}
