package com.example.passwordmanager;

/**
 * Created by HTT on 2018/1/1.
 */

public class Detail_item {
    private long pass_id;
    private int detail_icon;
    private String detail_type;
    private String detail_title;
    private String detail_username;

    public Detail_item(long id1,int icon,String type,String title,String username) {
        this.pass_id = id1;
        this.detail_type = type;
        this.detail_icon = icon;
        this.detail_title = title;
        this.detail_username = username;
    }

    public long getPass_id() {
        return pass_id;
    }

    public int getDetail_icon() {
        return detail_icon;
    }

    public String getDetail_type() {
        return detail_type;
    }

    public String getDetail_title() {
        return detail_title;
    }

    public String getDetail_username() {
        return detail_username;
    }

    public void setPass_id(long id) {
        pass_id = id;
    }

    public void setDetail_icon(int detail_icon) {
        this.detail_icon = detail_icon;
    }

    public void setDetail_type(String detail_type) {
        this.detail_type = detail_type;
    }

    public void setDetail_title(String detail_title) {
        this.detail_title = detail_title;
    }

    public void setDetail_username(String detail_username) {
        this.detail_username = detail_username;
    }
}
