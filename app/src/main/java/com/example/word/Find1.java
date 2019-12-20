package com.example.word;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class Find1 extends AppCompatActivity {
    ListView listView = null;
    List<Word> list = null;
    Word word = null;
    private DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find1);
        final EditText editText = findViewById(R.id.findword) ;
        listView=findViewById(R.id.listview);
        Button button = (Button)findViewById(R.id.findbutton);
        dbHelper = new DatabaseHelper(this, "Land.db", null, 1);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        if(editText.getText().toString().length() == 0){
                            listView.setAdapter(null);
                        }
                        else {
                            //where word like ? order by word asc limit 0, 10
                            String sql = "select * from WordBook where word like ? order by word asc limit 0, 10";
                            String[] data = new String[]{editText.getText().toString() + "%"};
                            Word word = null;
                            list = new ArrayList<Word>();
                            Cursor cursor = db.rawQuery(sql, data);
                            while (cursor.moveToNext()) {
//                                cursor.getString(cursor.getColumnIndex("word"))
                                word = new Word(cursor.getString(0), cursor.getString(1), cursor.getString(2));
                                Log.e("xxx",word.getWord());
                                list.add(word);
                            }
                            Log.e("xxx",list.size()+"");
                            WordAdapter adapter = new WordAdapter(getApplicationContext(), R.layout.list, list);
                            listView.setAdapter(adapter);
                        }
                    }
                });
        Configuration mConfiguration = this.getResources().getConfiguration();
        int ori = mConfiguration.orientation;
        if(ori == mConfiguration.ORIENTATION_LANDSCAPE) {//横屏时，将单词的详细信息送到右侧fragment
            Log.d("xxx","hello");
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.e("xxxlist",list.size()+" "+position);
                    RightFragment rightFragment = (RightFragment) getSupportFragmentManager().findFragmentById(R.id.rightfragment);
                    rightFragment.refresh(list.get(position));
                    Log.e("xxxlist",list.size()+" "+position);
                }
            });
        }else {
            final ListView listView = (ListView) findViewById(R.id.listview);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Word word = list.get(position);
                    Log.e("click", word.getWord());
                    Intent intent = new Intent(Find1.this, Find2.class);
//                intent.putExtra("data","word: "+word.getWord()+"\n释义: "+word.getMean()+"\n例句:"+word.getExample());
                    intent.putExtra("dataword", word.getWord());
                    intent.putExtra("datamean", word.getMean());
                    intent.putExtra("dataexample", word.getExample());
                    startActivity(intent);
                }
            });
        }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.listview:
//                replaceFragment(new RightFragment());
//                break;
//                default:
//                    break;
//        }
//    }
//    private void replaceFragment(Fragment fragment){
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.replace(R.id.right_layout,fragment);
//        transaction.commit();
//    }
}
}
