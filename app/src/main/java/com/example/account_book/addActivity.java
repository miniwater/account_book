package com.example.account_book;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class addActivity extends AppCompatActivity {
    private Database database;
    EditText addId, user, password, note;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ActionBar actionBar = getSupportActionBar();//左上角返回
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        database = new Database(this, "my.db", null, 1);

        addId = findViewById(R.id.addId);
        user = findViewById(R.id.user);
        password = findViewById(R.id.password);
        note = findViewById(R.id.note);
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = addId.getText().toString();
                String b = user.getText().toString();
                String c = password.getText().toString();
                String d = note.getText().toString();
                hideKeyboard(addActivity.this);
                if (a.length() == 0) {
                    Snackbar.make(v, "名称不能为空", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else {
                    SQLiteDatabase db = database.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("addId", a);
                    values.put("user", b);
                    values.put("password", c);
                    values.put("note", d);
                    db.insert("mydata", null, values);
                    db.close();
                    values.clear();
                    addId.setText("");
                    user.setText("");
                    password.setText("");
                    note.setText("");
                    Snackbar.make(v, "保存成功", Snackbar.LENGTH_LONG).setAction("查看", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    }).show();
                }
            }
        });
    }

    //监听左上角
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public static void hideKeyboard(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        imm.hideSoftInputFromWindow(context.getWindow().getDecorView().getWindowToken(), 0);
    }

}


