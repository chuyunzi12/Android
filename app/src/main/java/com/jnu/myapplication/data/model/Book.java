package com.jnu.myapplication.data.model;

import java.io.Serializable;

//book类实现序列化才能在返回的时候再进人能刚才修改的操作界面
public class Book implements Serializable {


    public Book(String title, int coverResourceId) {
        this.setTitle(title);
        this.setCoverResourceId(coverResourceId);
    }

    private String title;
    private int coverResourceId;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCoverResourceId() {
        return coverResourceId;
    }

    public void setCoverResourceId(int coverResourceId) {
        this.coverResourceId = coverResourceId;
    }

    public void getPrice() { }
}
