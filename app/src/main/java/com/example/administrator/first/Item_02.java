package com.example.administrator.first;

/**
 * Created by Administrator on 2017/8/6.
 */

public class Item_02 {
    private String item_name;
    private int item_src;
    private int item_c;

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getItem_src() {
        return item_src;
    }

    public void setItem_src(int item_src) {
        this.item_src = item_src;
    }

    public int getItem_c() {
        return item_c;
    }

    public void setItem_c(int item_c) {
        this.item_c = item_c;
    }

    @Override
    public String toString() {
        return "Item_02{" +
                "item_name='" + item_name + '\'' +
                ", item_src=" + item_src +
                ", item_c=" + item_c +
                '}';
    }
}
