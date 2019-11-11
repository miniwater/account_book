package com.example.account_book;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, addActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }
        if (id == R.id.item_add) {
            Intent intent = new Intent(MainActivity.this, addActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        int count = 0;
        ArrayList<ListActivity> listlist = new ArrayList<>();
        Database database = new Database(this, "my.db", null, 1);
        final SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = db.query("mydata", null, null, null, null, null, null);
        count = cursor.getCount();
        int i = 0;
        final ListActivity[] lists = new ListActivity[count];
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String addId = cursor.getString(cursor.getColumnIndex("addId"));
                String user = cursor.getString(cursor.getColumnIndex("user"));
                String password = cursor.getString(cursor.getColumnIndex("password"));
                String note = cursor.getString(cursor.getColumnIndex("note"));
                lists[i] = new ListActivity();
                lists[i].setId(id);
                lists[i].setAddId(addId);
                lists[i].setUser(user);
                lists[i].setPassword(password);
                lists[i].setNote(note);
                listlist.add(lists[i]);
                Log.v("list", lists[i].getAddId());
                Log.v("list", lists[i].getId());
                Log.v("list", lists[i].getUser());
                Log.v("list", lists[i].getPassword());
                Log.v("list", lists[i].getNote());
                i++;
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        RecyclerView listview2 = findViewById(R.id.listView2);
        newAdapter adapter2 = new newAdapter(MainActivity.this, listlist);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        listview2.setLayoutManager(mLayoutManager);
        listview2.setAdapter(adapter2);

        super.onStart();
    }

}
