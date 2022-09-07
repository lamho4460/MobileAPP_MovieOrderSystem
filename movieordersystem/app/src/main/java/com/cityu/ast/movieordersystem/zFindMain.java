package com.cityu.ast.movieordersystem;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.cityu.ast.movieordersystem.R;
import com.cityu.ast.movieordersystem.zExampleAdapter;
import com.cityu.ast.movieordersystem.zExampleItem;

import java.util.ArrayList;


public class zFindMain extends AppCompatActivity {
    private ArrayList<zExampleItem> mExampleList;

    private RecyclerView mRecyclerView;
    private zExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zactivity_findmain);

        createExampleList();
        buildRecyclerView();

        EditText editText = findViewById(R.id.edittext);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

    }

    private void filter(String text) {
        ArrayList<zExampleItem> filteredList = new ArrayList<>();

        for (zExampleItem item : mExampleList) {
            if (item.getText1().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        mAdapter.filterList(filteredList);
    }

    private void createExampleList() {
        mExampleList = new ArrayList<>();
        mExampleList.add(new zExampleItem(R.drawable.poster1, "Aquaman", "James Wan", "Adventure, Action, Science Fiction, Fantasy"));
        mExampleList.add(new zExampleItem(R.drawable.poster2, "TOY STORY 4", "Josh Cooley", "Kids, Comedy, Animation"));
        mExampleList.add(new zExampleItem(R.drawable.poster3, "Frozen", "Chris Buck, Jennifer Lee", "Animation, Kids"));
        mExampleList.add(new zExampleItem(R.drawable.poster4, "Avengers: Endgame", "Anthony Russo, Joe Russo", "Action, Adventure, Science Fiction"));
        mExampleList.add(new zExampleItem(R.drawable.poster5, "Alita: Battle Angel", "Robert Rodriguez", "Action, Adventure, Science Fiction"));

    }

    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new zExampleAdapter(mExampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

}



