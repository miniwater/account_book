package com.example.account_book;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import static com.example.account_book.addActivity.hideKeyboard;

public class ChangeActivity extends AppCompatActivity {
    private Database database;
    EditText addIdd,userr,passwordd,notee;
    Button addButton;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        ActionBar actionBar = getSupportActionBar();//左上角返回
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Bundle bundle = this.getIntent().getExtras();
        id = bundle.getString("id");
        final String addId = bundle.getString("addId");
        String user = bundle.getString("user");
        String password = bundle.getString("password");
        String note = bundle.getString("note");

        addIdd =findViewById(R.id.addIdd);
        userr =findViewById(R.id.userr);
        passwordd =findViewById(R.id.passwordd);
        notee =findViewById(R.id.notee);
        addButton=findViewById(R.id.addButtonn);

        addIdd.setText(addId);
        userr.setText(user);
        passwordd.setText(password);
        notee.setText(note);

        database = new Database(this,"my.db",null,1);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String a =addIdd.getText().toString();
                String b =userr.getText().toString();
                String c =passwordd.getText().toString();
                String d =notee.getText().toString();
                hideKeyboard(ChangeActivity.this);
                if (a.length()==0){
                    Snackbar.make(v, "名称不能为空", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }else {
                    ContentValues values=new ContentValues();
                    values.put("addId",a);
                    values.put("user",b);
                    values.put("password",c);
                    values.put("note",d);
                    SQLiteDatabase db = database.getWritableDatabase();
                    db.update("mydata",values,"id=?",new String[]{id});
                    db.close();
                    values.clear();
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
}
