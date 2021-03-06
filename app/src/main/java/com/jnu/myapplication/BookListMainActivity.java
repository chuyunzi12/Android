package com.jnu.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jnu.myapplication.data.BookSaver;
import com.jnu.myapplication.data.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookListMainActivity extends AppCompatActivity {

    public static final int CONTEXT_MENU_DELETE=1;
    public static final int CONTEXT_MENU_NEW=CONTEXT_MENU_DELETE+1;
    public static final int CONTEXT_MENU_UPDATE = CONTEXT_MENU_NEW+1;
    public static final int CONTEXT_MENU_ABOUT=CONTEXT_MENU_UPDATE +1;
    public static final int REQUEST_CODE_NEW_BOOK=901;
    public static final int REQUEST_CODE_UPDATE_BOOK=902;

    //@Override
    private ListView listViewBooks;
    private ArrayList<Book> listBooks= new ArrayList<>();
    private BookAdapter bookAdapter;
    private BookSaver bookSaver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_lest_main_activity);

        bookSaver=new BookSaver(this);
        listBooks=bookSaver.load();
        if(listBooks.size()==0)
            init();

        listViewBooks=(ListView)this.findViewById(R.id.recycler_view);
        bookAdapter = new BookAdapter(BookListMainActivity.this, R.layout.recycle_view_item, listBooks);
        listViewBooks.setAdapter(bookAdapter);

        this.registerForContextMenu(listViewBooks);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bookSaver.save();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        if(v==listViewBooks){
            // ???????????????
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            //????????????
            menu.setHeaderTitle(listBooks.get(info.position).getTitle());
            //????????????
            menu.add(0, CONTEXT_MENU_DELETE, 0, "??????");
            menu.add(0, CONTEXT_MENU_NEW, 0, "??????");
            menu.add(0, CONTEXT_MENU_UPDATE, 0, "??????");
            menu.add(0, CONTEXT_MENU_ABOUT, 0, "??????...");

        }
    }
    //onActivityResult????????????NewBookActiviy?????????BookListActivity???
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode)
        {
            case REQUEST_CODE_NEW_BOOK:
                if(resultCode==RESULT_OK)
                {
                    int position=data.getIntExtra("edit_position",0);
                    String title=data.getStringExtra("book_title");
                    double price =data.getDoubleExtra("book_price",0);
                    getListBooks().add(position, new Book(title,R.drawable.book_no_name));
                    //listBooks.add(position, new Book(title,R.drawable.book_no_name));
                    //????????????????????????
                    bookAdapter.notifyDataSetChanged();

                    Toast.makeText(this, "????????????", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_UPDATE_BOOK:
                if(resultCode==RESULT_OK)
                {
                    int position=data.getIntExtra("edit_position",0);
                    String name=data.getStringExtra("book_title");
                    double price=data.getDoubleExtra("book_price",0);
                    Book book= getListBooks().get(position);
                    // Book book=listBooks.get(position);
                    book.setTitle(name);
                    //book.setPrice(price);book?????????price
                    //????????????????????????
                    bookAdapter.notifyDataSetChanged();

                    Toast.makeText(this, "????????????", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case CONTEXT_MENU_NEW: {
                AdapterView.AdapterContextMenuInfo menuInfo=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

                Intent intent = new Intent(BookListMainActivity.this, EditBookActivity.class);
                intent.putExtra("edit_position",menuInfo.position);
                intent.putExtra("book_title","????????????");
                intent.putExtra("book_price","0.0");

                //startActivityForResult()??????????????????BookListActivity????????????NewBookActivity???
                startActivityForResult(intent,REQUEST_CODE_NEW_BOOK );
                break;
            }
            case CONTEXT_MENU_UPDATE: {
                AdapterView.AdapterContextMenuInfo menuInfo=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                Book book=listBooks.get(menuInfo.position);

                Intent intent = new Intent(BookListMainActivity.this, EditBookActivity.class);
                intent.putExtra("edit_position",menuInfo.position);
                intent.putExtra("book_title",book.getTitle());
                //intent.putExtra("book_price",book.getPrice());
                //startActivityForResult()??????????????????BookListActivity????????????NewBookActivity???
                startActivityForResult(intent, REQUEST_CODE_UPDATE_BOOK);
                break;
            }
            case CONTEXT_MENU_DELETE: {
                AdapterView.AdapterContextMenuInfo menuInfo=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                final int itemPosition=menuInfo.position;
                new android.app.AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("??????")
                        .setMessage("??????????????????????????????")
                        .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                listBooks.remove(itemPosition);
                                bookAdapter.notifyDataSetChanged();
                                Toast.makeText(BookListMainActivity.this, "???????????????", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create().show();
                break;
            }
            case CONTEXT_MENU_ABOUT:
                Toast.makeText(this, "????????????by grt!", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    public List<Book> getListBooks()
    {
        return listBooks;
    }

    private void init(){

        listBooks.add(new Book("????????????????????????????????????4??????", R.drawable.book_2));
        listBooks.add(new Book("??????????????????", R.drawable.book_no_name));
        listBooks.add(new Book("??????????????????????????????2??????", R.drawable.book_1));

    }


    class BookAdapter extends ArrayAdapter<Book> {

        private int resourceId;

        public BookAdapter(Context context, int resource, List<Book> objects) {
            super(context, resource, objects);
            resourceId = resource;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Book book = getItem(position);//????????????????????????
            View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            ((ImageView) view.findViewById(R.id.image_view_book_cover)).setImageResource(book.getCoverResourceId());
            ((TextView) view.findViewById(R.id.text_view_book_title)).setText(book.getTitle());

            return view;
        }
    }
}
