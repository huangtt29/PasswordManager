package com.example.passwordmanager;

import org.litepal.annotation.Encrypt;
import org.litepal.crud.DataSupport;

/**
 * Created by HTT on 2018/1/6.
 */

public class Password extends DataSupport{
    private String title;
    private String acount;
    private String decription;
    private String merchant;
    private int group_id;

    @Encrypt(algorithm = "AES")
    private String password;

    public Password(String title,String acount,String decription,String merchant,int group_id,String password)
    {
        this.acount=acount;
        this.title=title;
        this.decription=decription;
        this.group_id=group_id;
        this.password=password;
        this.merchant=merchant;
    }


    public long getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getAcount() {
        return acount;
    }

    public void setAcount(String acount) {
        this.acount = acount;
    }

    public String getMerchant() {
        return merchant;
    }

    public String getPassword() {
        return password;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
