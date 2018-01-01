package com.example.passwordmanager;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private GroupAdapter detailAdapter;
    private List<Grouping_item> detailList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.details_RecyclerView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        detailAdapter=new GroupAdapter(detailList);
        recyclerView.setAdapter(detailAdapter);



    }

}
