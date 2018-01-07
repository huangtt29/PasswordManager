package com.example.passwordmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class GroupListActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private List<Grouping_item> groupList = new ArrayList<>();
    private List<Detail_item> recentList = new ArrayList<>();
    private RelativeLayout group_page;
    private RelativeLayout recent_page;
    private RelativeLayout setting_page;
    private TextView titlebar;
    private RecyclerView group_recyclerview;
    private RecyclerView recent_recyclerview;
    private RecyclerView setting_recyclerview;
    private GroupAdapter groupAdapter;
    private DetailAdapter recentAdapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_group:
//                    mTextMessage.setText(R.string.title_home);
                    group_page.setVisibility(View.VISIBLE);
                    recent_page.setVisibility(View.GONE);
                    setting_page.setVisibility(View.GONE);
                    titlebar.setText("分组列表");
                    return true;
                case R.id.navigation_recent:
//                    mTextMessage.setText(R.string.title_dashboard);
                    group_page.setVisibility(View.GONE);
                    recent_page.setVisibility(View.VISIBLE);
                    setting_page.setVisibility(View.GONE);
                    titlebar.setText("最近查看");
                    return true;
                case R.id.navigation_setting:
//                    mTextMessage.setText(R.string.title_notifications);
                    group_page.setVisibility(View.GONE);
                    recent_page.setVisibility(View.GONE);
                    setting_page.setVisibility(View.VISIBLE);
                    titlebar.setText("设置");
                    return true;
            }
            return false;
        }
    };

    private void grouplist_init() {
        String[] title = {"所有","账户","邮箱","钱包","其他"};
        int[] icon = {R.mipmap.all,R.mipmap.account,R.mipmap.mail,R.mipmap.wallet,R.mipmap.other};
        for(int i = 0; i < 5; i++) {
            int count=0;
//            List<Password> passwordList=DataSupport.where("group_id = ?",String.valueOf(i)).find(Password.class);
//            count=passwordList.size();
            Log.d("count", "grouplist_init: "+count);
            Grouping_item item = new Grouping_item(icon[i],title[i],0);
            groupList.add(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        grouplist_init();
        Detail_item item = new Detail_item(1,R.mipmap.ic_launcher_round,"常规邮箱","常规邮箱","Jill");
        recentList.add(item);

        group_recyclerview=(RecyclerView)findViewById(R.id.group_RecyclerView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        group_recyclerview.setLayoutManager(linearLayoutManager);
        groupAdapter=new GroupAdapter(groupList);
        group_recyclerview.setAdapter(groupAdapter);

        recent_recyclerview = (RecyclerView)findViewById(R.id.recent_RecyclerView);
        LinearLayoutManager linearLayoutManager2=new LinearLayoutManager(this);
        recent_recyclerview.setLayoutManager(linearLayoutManager2);
        recentAdapter = new DetailAdapter(recentList);
        recent_recyclerview.setAdapter(recentAdapter);

        group_page = (RelativeLayout)findViewById(R.id.grouplist_page);
        recent_page = (RelativeLayout)findViewById(R.id.recent_page);
        setting_page = (RelativeLayout)findViewById(R.id.setting_page);
        titlebar = (TextView)findViewById(R.id.titlebar1);

        groupAdapter.setOnItemClickListener(new GroupAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                String title=groupList.get(position).getGrouping_title();
                Intent intent = new Intent(GroupListActivity.this, DetailsActivity.class);
                intent.putExtra("title",title);
                startActivity(intent);
            }

            @Override
            public void onLongClick(int position) {

            }
        });

    }




}
