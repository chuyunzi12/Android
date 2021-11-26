package com.casper.testdrivendevelopment.dataprocessor;

import android.content.Context;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
//这个类的作用就是为了上传或下载关于猫的arraylist数据
public class DataBank {
    public static final String BOOK_FILE = "Book_message.txt";
    private ArrayList<Book> arrayListBooks = new ArrayList<>();
    private final Context context;
    public DataBank(Context context){
        this.context = context;
    }
    public ArrayList<Book> getBooks() {
        return arrayListBooks;
    }
    //保存数据
    public void Save(){
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(context.openFileOutput(BOOK_FILE, Context.MODE_PRIVATE));
            oos.writeObject(arrayListBooks);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
    //下载数据
    public void Load(){
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(context.openFileInput(BOOK_FILE));
            arrayListBooks = (ArrayList<Book>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    };



}
