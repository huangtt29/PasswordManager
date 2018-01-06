package com.example.passwordmanager;

/**
 * Created by HTT on 2018/1/1.
 */

public class Grouping_item {
    private int grouping_icon;
    private String grouping_title;
    private int grouping_num;

    public Grouping_item(int icon, String title, int num) {
        this.grouping_icon = icon;
        this.grouping_title = title;
        this.grouping_num = num;
    }

    public int getGrouping_icon() {
        return grouping_icon;
    }

    public void setGrouping_icon(int grouping_icon) {
        this.grouping_icon = grouping_icon;
    }

    public int getGrouping_num() {
        return grouping_num;
    }

    public void setGrouping_num(int grouping_num) {
        this.grouping_num = grouping_num;
    }

    public String getGrouping_title() {
        return grouping_title;
    }

    public void setGrouping_title(String grouping_title) {
        this.grouping_title = grouping_title;
    }
}
