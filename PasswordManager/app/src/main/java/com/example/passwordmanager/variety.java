package com.example.passwordmanager;

/**
 * Created by lenovo on 2018/1/5.
 */

public class variety {
    private int variety_icon;
    private String variety_name;

    public variety(int icon,String name) {
        this.variety_icon = icon;
        this.variety_name = name;
    }

    public int getVariety_icon() {
        return variety_icon;
    }

    public String getVariety_name() {
        return variety_name;
    }

    public void setVariety_icon(int variety_icon) {
        this.variety_icon = variety_icon;
    }

    public void setVariety_name(String variety_name) {
        this.variety_name = variety_name;
    }
}
