package com.example.word;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.help:
                Intent intent = new Intent(MainActivity.this,Help.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private DatabaseHelper dbHelper;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //c创建数据库
        dbHelper = new DatabaseHelper(this, "Land.db", null, 1);
        Button createDatabase = (Button) findViewById(R.id.create_database);
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();
            }
        });
        //添加单词
        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final SQLiteDatabase db = dbHelper.getWritableDatabase();
                final ContentValues values = new ContentValues();
                LayoutInflater inflater = getLayoutInflater();
                final View layout = inflater.inflate(R.layout.add, null);
                switch (v.getId()) {
                    case R.id.add:
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                        dialog.setTitle("添加单词");
                        dialog.setView(layout);
                        dialog.setPositiveButton("提交", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               final EditText  word = (EditText) layout.findViewById(R.id.word);
                                values.put("word", word.getText().toString());
                                final EditText mean = (EditText)layout.findViewById(R.id.mean) ;
                                values.put("mean",mean.getText().toString());
                                final EditText example = (EditText)layout.findViewById(R.id.example) ;
                                values.put("example",example.getText().toString());
                                db.insert("WordBook",null,values);
                            }
                        });
                        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        dialog.show();
                        break;
                    default:
                        break;
                }
            }
        });
//        Button find = (Button)findViewById(R.id.find);
//        find.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SQLiteDatabase db = dbHelper.getWritableDatabase();
//               // Cursor cursor = db.query("WordBook",null,null,null,null,null,null);
//                String sql = "select * from WordBook";
//                Cursor cursor = db.rawQuery(sql, new String[]{});
//                if(cursor.moveToFirst()){
//                    do{
//                        String word = cursor.getString(cursor.getColumnIndex("word"));
//                        String mean = cursor.getString(cursor.getColumnIndex("mean"));
//                        String example = cursor.getString(cursor.getColumnIndex("example"));
//                        Log.d("word",word);
//                        Log.d("mean",mean);
//                        Log.d("example",example);
//                    }while(cursor.moveToNext());
//                }
//                cursor.close();
//            }
//        });

        Button find = (Button)findViewById(R.id.find);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Find1.class);
                startActivity(intent);
            }
        });
    }

}