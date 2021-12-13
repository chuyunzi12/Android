package com.grt.testdrivendevelopment.data;

import android.content.Context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
//这个类的作用就是为了上传或下载关于的arraylist数据
public class DataBank {
    public static final String BOOK_FILE = "Book_message.txt";
    private ArrayList<ShopItem> arrayListShopItems = new ArrayList<>();
    private final Context context;
    public DataBank(Context context){
        this.context = context;
    }
    public ArrayList<ShopItem> getBooks() {
        return arrayListShopItems;
    }
    //保存数据
    public void Save(){
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(context.openFileOutput(BOOK_FILE, Context.MODE_PRIVATE));
            oos.writeObject(arrayListShopItems);
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
            arrayListShopItems = (ArrayList<ShopItem>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    };



}
