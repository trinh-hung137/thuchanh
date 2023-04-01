package com.example.recycleviewdemocrud.model;

public class Cat {
    private int img;
    private String name, des;
    private double price;

    public Cat(int img, String name, String des, double price) {
        this.img = img;
        this.name = name;
        this.des = des;
        this.price = price;
    }

    public Cat() {

    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
