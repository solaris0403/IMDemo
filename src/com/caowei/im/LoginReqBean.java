package com.caowei.im;

import java.io.Serializable;

/**
 * 登录请求实体
 */
public class LoginReqBean extends BaseBean implements Serializable {
    private Integer userid;//用户ID
    private String username;//用户名称
    @Override
    public Byte code() {
        return 1;//业务指令
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
