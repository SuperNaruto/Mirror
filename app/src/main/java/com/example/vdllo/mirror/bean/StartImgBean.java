package com.example.vdllo.mirror.bean;

/**
 * Created by dllo on 16/4/1.
 */
public class StartImgBean {
    private String text,img;

    public StartImgBean(String text, String img) {
        this.text = text;
        this.img = img;
    }

    public StartImgBean() {
        super();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
