package com.example.passwordmanager;

import org.litepal.annotation.Encrypt;
import org.litepal.crud.DataSupport;

import java.util.Date;


/**
 * Created by HTT on 2018/1/6.
 */

public class Password extends DataSupport{
    private String title;
    private String type;
    private String acount;
    private String decription;
    private String merchant;
    private int group_id;
    private Date recent_time;
    private int icon;
//    private long baseObjId;
    @Encrypt(algorithm = "AES")
    private String password;

    @Override
    protected long getBaseObjId() {
        return super.getBaseObjId();
    }

    public Password(){};
    public Password(String type, String title, String acount, String decription, String merchant, int group_id, String password,int icon)
    {
        this.type = type;
        this.acount=acount;
        this.title=title;
        this.decription=decription;
        this.group_id=group_id;
        this.password=password;
        this.merchant=merchant;
        this.icon = icon;
        recent_time = new  Date(System.currentTimeMillis());
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getTitle() {
        return title;
    }

    public int getIcon() {
        return icon;
    }

    public Date getRecent_time() {
        return recent_time;
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

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setRecent_time(Date recent_time) {
        this.recent_time = recent_time;
    }
}
