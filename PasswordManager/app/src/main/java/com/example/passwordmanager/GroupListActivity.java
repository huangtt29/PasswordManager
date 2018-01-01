package com.example.passwordmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GroupListActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private GroupAdapter groupAdapter;
    private List<Grouping_item> groupList=new ArrayList<>();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.group_RecyclerView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        groupAdapter=new GroupAdapter(groupList);
        recyclerView.setAdapter(groupAdapter);


        groupAdapter.setOnItemClickListener(new GroupAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                String title=groupList.get(position).getGrouping_title();
                Intent intent = new Intent(GroupListActivity.this, DetailsActivity.class);
                intent.putExtra("login",title);
                startActivity(intent);
            }

            @Override
            public void onLongClick(int position) {

            }
        });

    }




}
