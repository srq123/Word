package com.example.word;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class Find2 extends AppCompatActivity {
    private ListView listView = null;
    public static SQLiteDatabase database = null;
    List<Word> list = null;
    Word word = null;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chde,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final DatabaseHelper dbHelper;
        dbHelper = new DatabaseHelper(this, "Land.db", null, 1);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final ContentValues values = new ContentValues();
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.change, null);
        switch (item.getItemId()) {
            case R.id.change:
                AlertDialog.Builder dialog = new AlertDialog.Builder(Find2.this);
                dialog.setTitle("修改单词");
                dialog.setView(layout);
                Intent intent = getIntent();
                String worddata = intent.getStringExtra("dataword");
                String meandata = intent.getStringExtra("datamean");
                final String exampledata = intent.getStringExtra("dataexample");
                final TextView word = (TextView) layout.findViewById(R.id.word);
                final EditText mean = (EditText)layout.findViewById(R.id.mean);
                final  EditText example = (EditText)layout.findViewById(R.id.example);
                word.setText(worddata);
                mean.setText(meandata);
                example.setText(exampledata);
                dialog.setPositiveButton("提交", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        TextView word = (TextView)layout.findViewById(R.id.recc);
                        String sql = "update WordBook set mean = ?, example = ? where word = ?";
//                        values.put("mean", mean.getText().toString());
//                        values.put("example", example.getText().toString());
                        db.execSQL(sql,new String[]{mean.getText().toString(),example.getText().toString(),word.getText().toString()});
                        Intent intent2 = new Intent(Find2.this,MainActivity.class);
                        startActivity(intent2);
                        Toast.makeText(Find2.this,"修改成功", Toast.LENGTH_LONG).show();
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
            case R.id.delete:
                AlertDialog.Builder dialog1 = new AlertDialog.Builder(Find2.this);
                LayoutInflater inflater1 = getLayoutInflater();
                final View layout1 = inflater1.inflate(R.layout.delete, null);
                dialog1.setView(layout1);
                dialog1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        final SQLiteDatabase db = dbHelper.getWritableDatabase();
//                        final ContentValues values = new ContentValues();
//                        db.delete("WordBook",R.layout.find2,);
                        TextView word = (TextView)findViewById(R.id.recword);
                        String sql = "delete from WordBook where word = ?";
                        db.execSQL(sql, new String[]{word.getText().toString()});
                        Intent intent2 = new Intent(Find2.this,MainActivity.class);
                        startActivity(intent2);
                        Toast.makeText(Find2.this, "删除成功", Toast.LENGTH_LONG).show();
                    }
                });
                dialog1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
             dialog1.show();
             break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find2);
        Intent intent = getIntent();
        String worddata = intent.getStringExtra("dataword");
        String meandata = intent.getStringExtra("datamean");
        String exampledata = intent.getStringExtra("dataexample");
        final TextView wordtext = (TextView) findViewById(R.id.recword);
        final TextView meantext = (TextView)findViewById(R.id.recmean);
        final TextView exampletext = (TextView)findViewById(R.id.recexample);
        wordtext.setText(worddata);
        meantext.setText(meandata);
        exampletext.setText(exampledata);
        listView = (ListView) findViewById(R.id.listview);
        wordtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (wordtext.getText().toString().length() == 0) {
                    listView.setAdapter(null);
                } else {
                    String sql = "select  * from WordBook where word like ? order by word asc limit 0, 10 ";
                    String[] data = new String[]{wordtext.getText().toString() + "%"};
                    Word word = null;
                    list = new ArrayList<Word>();
                    Log.e("text", wordtext.getText().toString());
                    Cursor cursor = database.rawQuery(sql, new String[]{wordtext.getText().toString() + "%"});
                    while (cursor.moveToNext()) {
                        word = new Word(cursor.getString(0), cursor.getString(1), cursor.getString(2));
                        Log.e("word", word.toString());
                        list.add(word);
                    }
                    WordAdapter adapter = new WordAdapter(Find2.this, R.layout.find2, list);
                    listView.setAdapter(adapter);
                }
            }
        });
    }
}
