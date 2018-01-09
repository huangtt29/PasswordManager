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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private Button setting_btn;
    private String[] title = {"所有","账户","邮箱","钱包"};
    private int[] icon = {R.mipmap.all2,R.mipmap.people,R.mipmap.email,R.mipmap.wallet2};
    private int[] num = {0,0,0,0};
    private LinearLayout changePassword_Layout;
    private Button submit_btn;
    private EditText originalPassword;
    private EditText setnewPassword;
    private EditText comfirmnewPassword;

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
//                    此处排序,asc desc
                    recentList.clear();
                    List<Password> records = DataSupport.order("recent_time desc").find(Password.class);
                    for(Password record :records)
                    {
                        Detail_item detail_item = new Detail_item(record.getBaseObjId(),record.getIcon(),record.getType(), record.getTitle(), record.getAcount());
                        recentList.add(detail_item);
                    }
                    recentAdapter.notifyDataSetChanged();
                    return true;
                case R.id.navigation_setting:
//                    mTextMessage.setText(R.string.title_notifications);
                    group_page.setVisibility(View.GONE);
                    recent_page.setVisibility(View.GONE);
                    setting_page.setVisibility(View.VISIBLE);
                    titlebar.setText("设置");

                    setting_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setting_btn.setVisibility(View.GONE);
                            changePassword_Layout.setVisibility(View.VISIBLE);

                        }
                    });

                    return true;
            }
            return false;
        }
    };

    private void getNum() {
        List<Password> r1 = DataSupport.where("group_id=?" , String.valueOf(0)).find(Password.class);
        int account_num = r1.size();
        List<Password> r2 = DataSupport.where("group_id=?" , String.valueOf(1)).find(Password.class);
        int mail_num = r2.size();
        List<Password> r3 = DataSupport.where("group_id=?" , String.valueOf(2)).find(Password.class);
        int wallet_num = r3.size();
        int all_num = account_num+mail_num+wallet_num;
        num[0] = all_num;
        num[1] = account_num;
        num[2] = mail_num;
        num[3] = wallet_num;
    }

    private void grouplist_init() {
        for(int i = 0; i < 4; i++) {
            int count=0;
            Log.d("count", "grouplist_init: "+count);
            Grouping_item item = new Grouping_item(icon[i],title[i],num[i]);
            groupList.add(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getNum();
        grouplist_init();
//        测试
//        Detail_item item = new Detail_item(1,R.mipmap.ic_launcher_round,"常规邮箱","常规邮箱","Jill");
//        recentList.add(item);

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
        submit_btn=(Button)findViewById(R.id.submit_btn);
        setting_btn=(Button)findViewById(R.id.setting_btn);
        changePassword_Layout=(LinearLayout)findViewById(R.id.changePassword_layout);
        originalPassword=(EditText)findViewById(R.id.origanalPassword);
        setnewPassword=(EditText)findViewById(R.id.setnewPassword);
        comfirmnewPassword=(EditText)findViewById(R.id.comfirmnewPassword);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String original=originalPassword.getText().toString();
                String newPass=setnewPassword.getText().toString();
                String comfirm=comfirmnewPassword.getText().toString();

                LoginPassword newloginPassword=new LoginPassword();
                newloginPassword.setLoginPassword(original);
                newloginPassword.save();


                LoginPassword loginPassword=DataSupport.findFirst(LoginPassword.class);
                String firstpassword =loginPassword.getLoginPassword();

                LoginPassword newloginPass=DataSupport.findLast(LoginPassword.class);
                String lastpassword=newloginPass.getLoginPassword();
                Log.d("Passwordfirst", firstpassword);
                Log.d("Passwordlast", lastpassword);
                if(firstpassword.equals(lastpassword))
                {
                    if(!newPass.equals(comfirm))
                    {
                        Toast.makeText(GroupListActivity.this, "Password Mismatch", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        DataSupport.deleteAll(LoginPassword.class);
                        LoginPassword log=new LoginPassword();
                        log.setLoginPassword(newPass);
                        log.save();

                        Toast.makeText(GroupListActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
                        setting_btn.setVisibility(View.VISIBLE);
                        changePassword_Layout.setVisibility(View.GONE);
                    }

                }
                else
                {
                    Toast.makeText(GroupListActivity.this, "密码不正确", Toast.LENGTH_SHORT).show();
                }


            }
        });

        groupAdapter.setOnItemClickListener(new GroupAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                String title=groupList.get(position).getGrouping_title();
                Intent intent = new Intent(GroupListActivity.this, DetailsActivity.class);
                intent.putExtra("title",title);
                startActivityForResult(intent,1);
            }

            @Override
            public void onLongClick(int position) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if(resultCode == RESULT_OK) {
                    getNum();
                    for(int i = 0; i < 4; i ++) {
                        groupList.get(i).setGrouping_num(num[i]);
                    }
                    groupAdapter.notifyDataSetChanged();
                }
                break;
        }
    }
}
