package com.example.passwordmanager;

import org.litepal.annotation.Encrypt;
import org.litepal.crud.DataSupport;

/**
 * Created by HTT on 2018/1/6.
 */

public class LoginPassword extends DataSupport{
    @Encrypt(algorithm = "MD5")
    private String loginPassword;
    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }
}
