package com.example.word;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RightFragment extends Fragment {
    private TextView wordname = null;
    private TextView mean = null;
    private TextView example = null;
    private Word word = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.right_fragment, container, false);
        wordname = (TextView) view.findViewById(R.id.recword);
        mean = (TextView) view.findViewById(R.id.recmean);
        example = (TextView) view.findViewById(R.id.recexample);
        return  view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void refresh(Word word){
        Log.e("RightFragment",word.getWord()+" "+word.getMean()+" "+word.getExample());
        wordname.setText("word: "+ word.getWord());
        mean.setText("释义: "+ word.getMean());
        example.setText("例句: "+word.getExample());
    }
}
