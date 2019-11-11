package com.example.account_book;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class newAdapter extends RecyclerView.Adapter<newAdapter.ViewHolder>  {
    private List<ListActivity> mFruitList;
    ListActivity fruit;
    Context context;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView seemc,seezh;
        Button copzh,copymm,delete,see;

        public ViewHolder (View view)
        {
            super(view);
            seemc = (TextView) view.findViewById(R.id.seeMC);
            seezh = (TextView) view.findViewById(R.id.seeZH);
            copzh = (Button) view.findViewById(R.id.copyZH);
            copymm = (Button) view.findViewById(R.id.copyMM);
            delete = (Button) view.findViewById(R.id.delete);
            see = (Button) view.findViewById(R.id.see);
        }
    }

    public  newAdapter (Context context,List <ListActivity> fruitList){
        this.context = context;
        mFruitList = fruitList;
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_add,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position){

        fruit = mFruitList.get(position);
        holder.seemc.setText(fruit.getAddId());
        holder.seezh.setText(fruit.getUser());
        holder.copzh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData mClipData = ClipData.newPlainText("Label",mFruitList.get(position).getUser());
                cm.setPrimaryClip(mClipData);
                Snackbar.make(v, "账号已经复制", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
        holder.copymm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData mClipData = ClipData.newPlainText("Label",mFruitList.get(position).getPassword());
                cm.setPrimaryClip(mClipData);

                Snackbar.make(v, "密码已经复制", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder  = new AlertDialog.Builder(context);
                builder.setTitle("删除"+mFruitList.get(position).getAddId() ) ;
                builder.setMessage("确认要删除这条账号信息吗？" ) ;
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Database database = new Database(context, "my.db", null, 1);
                        SQLiteDatabase db = database.getWritableDatabase();
                        Log.v("datas删除id为",mFruitList.get(position).getId());
                        db.delete("mydata","id=?",new  String[]{mFruitList.get(position).getId()});
                        mFruitList.remove(position);
                        notifyDataSetChanged();
                        //Snackbar.make(MainActivity, "删除成功", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                });
                builder.setNegativeButton("否", null);
                builder.show();
            }
        });
        holder.see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChangeActivity.class);
                intent.putExtra("id",mFruitList.get(position).getId());
                intent.putExtra("addId",mFruitList.get(position).getAddId());
                intent.putExtra("user",mFruitList.get(position).getUser());
                intent.putExtra("password",mFruitList.get(position).getPassword());
                intent.putExtra("note",mFruitList.get(position).getNote());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount(){
        return mFruitList.size();
    }
}

