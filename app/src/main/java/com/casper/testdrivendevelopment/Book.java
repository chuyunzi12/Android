package com.casper.testdrivendevelopment;

/**
 * Created by jszx on 2019/9/24.
 */

public class Book {

    private int Book_Image;
    private String Book_Name;

    public int getBook_Image() {
        return Book_Image;
    }

    public String getBook_Name() {
        return Book_Name;
    }



    public Book(String Book_Name, int Book_Image) {
        this.Book_Name = Book_Name;
        this.Book_Image = Book_Image;
    }

    public void setBook_Image(int book_Image) {
        Book_Image = book_Image;
    }

    public void setBook_Name(String book_Name) {
        Book_Name = book_Name;
    }
}
