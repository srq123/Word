package com.example.word;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class WordAdapter extends ArrayAdapter<Word> {
    private int wordId;

    public WordAdapter(Context context, int textViewwordId, List<Word> object){
        super(context,textViewwordId,object);
        wordId = textViewwordId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Word word = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(wordId,parent,false);
        TextView textView = (TextView)view.findViewById(R.id.text_word);
        textView.setText(word.getWord());
        return textView;
//        return super.getView(position, convertView, parent);
    }
}
