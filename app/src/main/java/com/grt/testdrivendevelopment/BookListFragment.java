package com.grt.testdrivendevelopment;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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

import com.grt.testdrivendevelopment.dataprocessor.Book;
import com.grt.testdrivendevelopment.dataprocessor.DataBank;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookListFragment extends Fragment {
    public BookListFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookListFragment newInstance(String param1, String param2) {
        BookListFragment fragment = new BookListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static final int CONTEXT_MENU_ITEM_NEW = 1;
    public static final int CONTEXT_MENU_ITEM_APPEND = CONTEXT_MENU_ITEM_NEW+1;
    public static final int CONTEXT_MENU_ITEM_UPDATE = CONTEXT_MENU_ITEM_APPEND+1;
    public static final int CONTEXT_MENU_ITEM_DELETE = CONTEXT_MENU_ITEM_UPDATE+1;
    public static final int REQUEST_CODE_ADD_BOOK = 100;
    private static final int REQUEST_CODE_APPEND_BOOK = REQUEST_CODE_ADD_BOOK + 1;
    public static final int REQUEST_CODE_UPDATE_BOOK = REQUEST_CODE_APPEND_BOOK + 1;
    private ListView Booklists;
    private DataBank databank;
    private BookAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);
        // Inflate the layout for this fragment
        initBooks();
        initView(view);
        return view;
    }

    private void initBooks() {
        databank = new DataBank(this.getContext());
        //现在的数据是要从文件中下载下来的，而不是我们给他写死
        databank.Load();
        //防止空数据，添一条作为测试
        if(databank.getBooks().size()==0){
            databank.getBooks().add(new Book("请添加一本书",R.drawable.book_no_name));
            databank.Save();
        }
    }

    private void initView(View view) {
        adapter = new BookAdapter(view.getContext(), R.layout.item_books, databank.getBooks());
        Booklists =  view.findViewById(R.id.list_view_books);
        Booklists.setAdapter(adapter);
        this.registerForContextMenu(Booklists);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String NewBookName;
        int position;
        switch (requestCode) {
            case REQUEST_CODE_ADD_BOOK:
                if (resultCode == RESULT_OK) {
                    NewBookName = data.getStringExtra("book_name");
                    position = data.getIntExtra("book_position",0);
                    databank.getBooks().add(position,new Book(NewBookName,R.drawable.book_no_name));
                    databank.Save();
                    adapter.notifyDataSetChanged();
                }
                break;
            case REQUEST_CODE_UPDATE_BOOK:
                if (resultCode == RESULT_OK) {
                    NewBookName = data.getStringExtra("book_name");
                    position = data.getIntExtra("book_position",0);
                    databank.getBooks().get(position).setBook_Name(NewBookName);
                    databank.getBooks().get(position).setBook_Image(R.drawable.book_no_name);
                    databank.Save();
                    adapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int position = menuInfo.position;
        Intent intent;
        switch (item.getItemId()){
            case CONTEXT_MENU_ITEM_NEW:
                intent = new Intent(this.getContext(), EditBookActivity.class);
                intent.putExtra("position",position);
                startActivityForResult(intent, REQUEST_CODE_ADD_BOOK);
                break;
            case CONTEXT_MENU_ITEM_APPEND:
                intent = new Intent(this.getContext(),EditBookActivity.class);
                intent.putExtra("position",position+1);
                startActivityForResult(intent, REQUEST_CODE_ADD_BOOK);
                break;
            case CONTEXT_MENU_ITEM_UPDATE:
                intent = new Intent(this.getContext(),EditBookActivity.class);
                intent.putExtra("position",position);
                intent.putExtra("book_name",databank.getBooks().get(position).getBook_Name());
                startActivityForResult(intent, REQUEST_CODE_UPDATE_BOOK);
                break;
            case CONTEXT_MENU_ITEM_DELETE:
                AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
                builder.setTitle("请确认是否删除");
                builder.setMessage("确认要删除这本书吗？");
                builder.setCancelable(true);
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databank.getBooks().remove(position);
                        databank.Save();
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.create().show();
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(v == this.getActivity().findViewById(R.id.list_view_books));{
            menu.setHeaderTitle("操作");
            menu.add(1, CONTEXT_MENU_ITEM_NEW,1,"增加");
            menu.add(1, CONTEXT_MENU_ITEM_APPEND,1,"追加");
            menu.add(1, CONTEXT_MENU_ITEM_UPDATE,1,"修改");
            menu.add(1, CONTEXT_MENU_ITEM_DELETE,1,"删除");
        }

    }

    private static class BookAdapter extends ArrayAdapter<com.grt.testdrivendevelopment.dataprocessor.Book> {
        private final int resourceId;
        public BookAdapter(@NonNull Context context, int resource, @NonNull List<com.grt.testdrivendevelopment.dataprocessor.Book> objects) {
            super(context, resource, objects);
            this.resourceId=resource;
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Book book = getItem(position);
            View view ;
            if(null==convertView)
                view = LayoutInflater.from(getContext()).inflate(R.layout.item_books, parent, false);
            else
                view =convertView;

            assert book != null;
            ((TextView) view.findViewById(R.id.text_view_book_title)).setText(book.getBook_Name());
            ((ImageView) view.findViewById(R.id.image_view_book_cover)).setImageResource(book.getBook_Image());

            return view;
        }
         }
}