package com.example.word;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class Find extends AppCompatActivity {

    private List<Word> list=new ArrayList<Word>();
    private DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find);
        final EditText editText = (EditText)findViewById(R.id.findword);
        dbHelper = new DatabaseHelper(this, "Land.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "select * from WordBook where word like ? order by word asc limit 0,10";
        String[] data = new String[]{editText.getText().toString()+"%"};
        Word word = null;
        list = new ArrayList<>();
        Log.e("text",editText.getText().toString());
        Cursor cursor = db.rawQuery(sql,new String[]{editText.getText().toString()+"%"});
        while(cursor.moveToNext()){
            word = new Word(cursor.getString(0),cursor.getString(1),cursor.getString(2));
            Log.e("word",word.toSting());
            list.add(word);
        }
        WordAdapter wordAdapter = new WordAdapter(Find.this,R.layout.search,list);
        ListView listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(wordAdapter);
    }
}
