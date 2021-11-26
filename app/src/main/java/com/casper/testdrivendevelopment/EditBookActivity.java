package com.casper.testdrivendevelopment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static java.sql.Types.NULL;
public class EditBookActivity extends AppCompatActivity {
    private int position;
    private String bookname;
    private EditText editTextBookName;
    private Intent intent = new Intent();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this,"按下了返回键",Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        return super.onKeyDown(keyCode, event);
    }
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
//            Toast.makeText(this, "按下了back键   onKeyDown()", Toast.LENGTH_SHORT).show();
//            return false;
//        }else {
//            return super.onKeyDown(keyCode, event);
//        }
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_update);

        position = getIntent().getIntExtra("position",0);
        bookname = getIntent().getStringExtra("book_name");

        editTextBookName =  EditBookActivity.this.findViewById(R.id.edit_text_newbook_name);
        if(bookname!=null)
            editTextBookName.setText(bookname);

        Button buttonOk = (Button)findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打包需要传递的数据
                //先初始化一个对象来获取文本框控件
                //从文本框获取用户输入的数据,然后放入intent中（相当于打包数据）
                intent.putExtra("book_name",editTextBookName.getText().toString());
                intent.putExtra("book_position",position);
                //设置回调，RESULT_OK表示执行
                setResult(RESULT_OK, intent);
                //销毁当前活动
                finish();
            }
        });
        Button buttonCANCEL = (Button)findViewById(R.id.button_cancel);
        buttonCANCEL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

    }



}