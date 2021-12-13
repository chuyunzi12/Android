package com.grt.testdrivendevelopment.data;

import java.io.Serializable;


//实现序列化接口
public class ShopItem implements Serializable {
    private int Book_Image;
    private String Book_Name;
    private double price;

    public ShopItem(String Book_Name, int Book_Image) {
        this.Book_Name = Book_Name;
        this.Book_Image = Book_Image;
        this.price=price;
    }
    public int getBook_Image() {
        return Book_Image;
    }

    public String getBook_Name() {
        return Book_Name;
    }

    public double getPrice() {
        return price;
    }

    public void setBook_Image(int book_Image) {
        Book_Image = book_Image;
    }

    public void setBook_Name(String book_Name) {
        Book_Name = book_Name;
    }

    public void setPrice(double price) { this.price = price; }
}
