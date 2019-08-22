package com.xiupeilian.carpart.vo;

public class LoginVo {
    private String loginName;
    private String password;
    private String validate;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getPassword() {
        return password;
    }

    public String getValidate() {
        return validate;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setValidate(String validate) {
        this.validate = validate;
    }

}
