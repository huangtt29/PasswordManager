package com.example.passwordmanager;

import android.content.Context;
import android.content.Intent;

import android.media.Image;

import android.os.Build;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private DetailAdapter detailAdapter;
    private DetailAdapter searchAdapter;
    private VarietyAdapter varietyAdapter;
    private List<Detail_item> detailList=new ArrayList<>();
    private List<Detail_item> searchList=new ArrayList<>();
    private List<variety> varietyList = new ArrayList<>();
    private List<variety> accountList = new ArrayList<>();
    private List<variety> mailList = new ArrayList<>();
    private List<variety> walletList = new ArrayList<>();
    private TextView titlebar;
    private ConstraintLayout search_area;
    private int group_id;
    private String group_title;
    private TextView edit;
    private EditText search_et;
    private String title;
    private LinearLayout details_page;
    private LinearLayout choose_page;
    private String[] mail = {"常规邮箱","谷歌邮箱","雅虎邮箱","网易邮箱","QQ邮箱"};
    private String[] account = {"常规登录","微信","QQ","新浪微博","知乎","淘宝","京东","亚马逊","Facebook","Twitter","Instergram"};
    private String[] wallet = {"银行卡","信用卡","身份证","驾驶证","护照","会员卡"};

    void variety_init() {
        int j=0;
        for(int i = 0; i < account.length; i++) {
            variety v = new variety(R.mipmap.ic_launcher_round,account[i]);
            accountList.add(v);
        }
        for(int i = 0; i < mail.length; i++) {
            variety v = new variety(R.mipmap.ic_launcher_round,mail[i]);
            mailList.add(v);
        }
        for(int i = 0; i < wallet.length; i++) {
            variety v = new variety(R.mipmap.ic_launcher_round,wallet[i]);
            walletList.add(v);
        }
    }

    void getPasswordsFromDB() {
        List<Password> records;
        detailList.clear();
        if(group_title.equals("所有"))
        {
            records=DataSupport.findAll(Password.class);
        }
        else
        {
            if(group_title.equals("账户"))
                group_id=0;
            else if(group_title.equals("邮箱"))
                group_id=1;
            else
                group_id=2;
//            Log.d("hello", String.valueOf(group_id));
            records=DataSupport.where("group_id=?" , String.valueOf(group_id)).find(Password.class);
        }

        for(Password p : records)
        {
            Detail_item item = new Detail_item(p.getBaseObjId(),p.getIcon(),p.getType(), p.getTitle(),p.getAcount());
            Log.d("id", "onCreate: "+p.getBaseObjId());
            detailList.add(item);
            detailAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        details_page = (LinearLayout)findViewById(R.id.details_page);
        choose_page = (LinearLayout)findViewById(R.id.chooseVariety_page);

        final RecyclerView details_RecyclerView=(RecyclerView)findViewById(R.id.details_RecyclerView);
        LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(this);
        details_RecyclerView.setLayoutManager(linearLayoutManager1);
        detailAdapter=new DetailAdapter(detailList);
        details_RecyclerView.setAdapter(detailAdapter);

        final RecyclerView search_RecyclerView=(RecyclerView)findViewById(R.id.search_RecyclerView);
        LinearLayoutManager linearLayoutManager3=new LinearLayoutManager(this);
        search_RecyclerView.setLayoutManager(linearLayoutManager3);
        searchAdapter=new DetailAdapter(searchList);
        search_RecyclerView.setAdapter(searchAdapter);

//        测试
//        Detail_item item = new Detail_item(p.getBaseObjId(),p.getIcon(),p.getType(),p.getTitle(),p.getAcount());
//        searchList.add(item);
//
        RecyclerView variety_RecyclerView = (RecyclerView)findViewById(R.id.variety_recyclerView);
        LinearLayoutManager linearLayoutManager2=new LinearLayoutManager(this);
        variety_RecyclerView.setLayoutManager(linearLayoutManager2);
        varietyAdapter = new VarietyAdapter(varietyList);
        variety_RecyclerView.setAdapter(varietyAdapter);

        variety_init();

        titlebar = (TextView)findViewById(R.id.titlebar2);
        group_title = getIntent().getStringExtra("title");
        titlebar.setText(group_title);

        getPasswordsFromDB();

        detailAdapter.setOnItemClickListener(new DetailAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                long id=detailList.get(position).getPass_id();
                List<Password> list=DataSupport.where("id = ?",String.valueOf(id)).find(Password.class);
                for(Password p: list)
                {
                    p.setRecent_time(new Date(System.currentTimeMillis()));
                    p.save();
                }
                Intent intent = new Intent(DetailsActivity.this,acountActivity.class);
                intent.putExtra("group_title",group_title);
                intent.putExtra("icon",detailList.get(position).getDetail_icon());
                intent.putExtra("pass_id",detailList.get(position).getPass_id());
                startActivityForResult(intent,2);
            }

            @Override
            public void onLongClick(int position) {

            }

            @Override
            public void onDeleteClick(int position) {

                ///数据库删除
                Detail_item item=detailList.get(position);
                Log.d("hello", "onDeleteClick: "+item.getDetail_username());
                DataSupport.deleteAll(Password.class,"acount = ?",item.getDetail_username());
                List<Password> list=DataSupport.findAll(Password.class);
                for(Password p :list)
                {
                    Log.d("hello", "onDeleteClick: "+p.getAcount());
                }
                detailList.remove(position);
                detailAdapter.notifyDataSetChanged();
            }
        });

        search_area = (ConstraintLayout)findViewById(R.id.search_area);
        search_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView search_icon = (ImageView)findViewById(R.id.search_icon);
                TextView search_hint = (TextView)findViewById(R.id.search_hint);
                TextView search_cancel = (TextView)findViewById(R.id.search_cancel);
                EditText search_input = (EditText)findViewById(R.id.search_input);
                ConstraintLayout.LayoutParams layout=(ConstraintLayout.LayoutParams)search_icon.getLayoutParams();
                layout.setMargins(10,0,0,0);
                search_icon.setLayoutParams(layout);
                search_hint.setVisibility(View.INVISIBLE);
                search_input.setVisibility(View.VISIBLE);
                search_input.requestFocus();
                search_cancel.setVisibility(View.VISIBLE);
                search_area.setMaxWidth(600);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(search_input,0);


            }
        });

        TextView search_cancel = (TextView)findViewById(R.id.search_cancel);
        search_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView search_icon = (ImageView)findViewById(R.id.search_icon);
                TextView search_hint = (TextView)findViewById(R.id.search_hint);
                TextView search_cancel = (TextView)findViewById(R.id.search_cancel);
                EditText search_input = (EditText)findViewById(R.id.search_input);
                ConstraintLayout.LayoutParams layout=(ConstraintLayout.LayoutParams)search_icon.getLayoutParams();
                layout.setMargins(250,0,0,0);
                search_icon.setLayoutParams(layout);
                search_hint.setVisibility(View.VISIBLE);
                search_input.setVisibility(View.GONE);
                search_cancel.setVisibility(View.GONE);
                search_area.setMaxWidth(700);
                search_RecyclerView.setVisibility(View.GONE);
                details_RecyclerView.setVisibility(View.VISIBLE);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

        search_et = (EditText)findViewById(R.id.search_input);
        search_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH){
                    search_RecyclerView.setVisibility(View.VISIBLE);
                    details_RecyclerView.setVisibility(View.GONE);

                    String input = search_et.getText().toString();
                    List<Password> r = DataSupport.where("title = ?",String.valueOf(input)).find(Password.class);
                    if(!r.isEmpty()) {
                        for(Password p :r) {
                            Boolean isok=true;
                            for(int j=0;j<searchList.size();j++)
                            {
                                if(searchList.get(j).getPass_id()==p.getBaseObjId())
                                {
                                    isok=false;break;
                                }

                            }
                            if(isok)
                            {
                                Detail_item item = new Detail_item(p.getBaseObjId(),p.getIcon(),p.getType(),p.getTitle(),p.getAcount());
                                searchList.add(item);
                            }

                        }
                    }
                    List<Password> r2 = DataSupport.where("acount = ?",String.valueOf(input)).find(Password.class);
                    if(!r2.isEmpty()) {
                        for(Password p :r2) {
                            Boolean isok=true;
                            for(int j=0;j<searchList.size();j++)
                            {
                                if(searchList.get(j).getPass_id()==p.getBaseObjId())
                                {
                                    isok=false;break;
                                }

                            }
                            if(isok)
                            {
                                Detail_item item = new Detail_item(p.getBaseObjId(),p.getIcon(),p.getType(),p.getTitle(),p.getAcount());
                                searchList.add(item);
                            }

                        }
                    }
                    searchAdapter.notifyDataSetChanged();

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);


                    return true;
                }
                return false;
            }
        });

        TextView back = (TextView)findViewById(R.id.back_to_grouplist);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        edit = (TextView)findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            boolean flag = false;
            @Override
            public void onClick(View view) {
                flag = !flag;
                if(flag) {  //编辑状态中
                    detailAdapter.isDeleteMode = true;
                    detailAdapter.notifyDataSetChanged();
                    edit.setText("完成");
                }
                else {   //退出编辑
                    if(!detailList.isEmpty()) {
                        ImageView delete = (ImageView)findViewById(R.id.delete);
                        ImageView right_icon = (ImageView)findViewById(R.id.right_icon2);
                        delete.setVisibility(View.GONE);
                        right_icon.setVisibility(View.VISIBLE);
                    }
                    detailAdapter.isDeleteMode = false;
                    detailAdapter.notifyDataSetChanged();
                    edit.setText("编辑");
                }
            }
        });


        ImageView add = (ImageView)findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                details_page.setVisibility(View.GONE);
                choose_page.setVisibility(View.VISIBLE);
                varietyList.clear();
                switch (group_title) {
                    case "所有":
                        for(variety v:accountList) {
                            varietyList.add(v);
                        }
                        for(variety v:mailList) {
                            varietyList.add(v);
                        }
                        for(variety v:walletList) {
                            varietyList.add(v);
                        }
                        varietyAdapter.notifyDataSetChanged();
                        break;
                    case "账户":
                        for(variety v:accountList) {
                            varietyList.add(v);
                        }
                        varietyAdapter.notifyDataSetChanged();
                        break;
                    case "邮箱":
                        for(variety v:mailList) {
                            varietyList.add(v);
                        }
                        varietyAdapter.notifyDataSetChanged();
                        break;
                    case "钱包":
                        for(variety v:walletList) {
                            varietyList.add(v);
                        }
                        varietyAdapter.notifyDataSetChanged();
                        break;
                }
            }
        });

        TextView choose_cancel = (TextView)findViewById(R.id.variety_cancel);
        choose_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                details_page = (LinearLayout)findViewById(R.id.details_page);
                choose_page = (LinearLayout)findViewById(R.id.chooseVariety_page);
                details_page.setVisibility(View.VISIBLE);
                choose_page.setVisibility(View.GONE);
            }
        });

        varietyAdapter.setOnItemClickListener(new VarietyAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                String type = varietyList.get(position).getVariety_name();
                int icon = varietyList.get(position).getVariety_icon();
                Intent intent=new Intent(DetailsActivity.this,AddActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("icon",icon);
                intent.putExtra("mode",0); //添加模式
                startActivityForResult(intent,1);
            }

            @Override
            public void onLongClick(int position) {

            }
        });
    }
    @Override
    protected void onActivityResult(int requstCode, int resultCode, Intent data) {
        switch(requstCode) {
            case 1:
                if(resultCode == RESULT_OK) {
                    Password record = DataSupport.findLast(Password.class);
                    Detail_item item = new Detail_item(record.getBaseObjId(),record.getIcon(),record.getType(), record.getTitle(), record.getAcount());
                    detailList.add(item);
                    detailAdapter.notifyDataSetChanged();
                }
                LinearLayout details_page = (LinearLayout)findViewById(R.id.details_page);
                LinearLayout choose_page = (LinearLayout)findViewById(R.id.chooseVariety_page);
                details_page.setVisibility(View.VISIBLE);
                choose_page.setVisibility(View.GONE);
                break;
//            此处改动
            case 2:
                getPasswordsFromDB();
                break;
            default:
                break;
        }
    }
}
