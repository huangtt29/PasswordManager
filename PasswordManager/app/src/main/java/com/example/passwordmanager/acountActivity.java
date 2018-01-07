package com.example.passwordmanager;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class acountActivity extends AppCompatActivity {

    private String group_title;
    private String type_title;
    private String account_title;
    private int pass_id;
    private int group_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acount);

        Intent intent = getIntent();
        group_title = intent.getStringExtra("group_title");
        //type_title = intent.getStringExtra("type");
        //account_title = intent.getStringExtra("title");
        pass_id = intent.getIntExtra("pass_id",0);


        TextView titlebar = (TextView)findViewById(R.id.titlebar3);
        TextView group_title_tv = (TextView)findViewById(R.id.group_title);
        TextView edit_content = (TextView)findViewById(R.id.edit_content);
        TextView ac_title = (TextView)findViewById(R.id.account_title);
        TextView ac_password = (TextView)findViewById(R.id.ac_password);
        TextView ac_name = (TextView)findViewById(R.id.account);
        TextView desc_t = (TextView)findViewById(R.id.desc_t);
        TextView ac_desc = (TextView)findViewById(R.id.ac_desc);
        TextView merchant_t = (TextView)findViewById(R.id.merchant_t);
        TextView ac_merchant = (TextView)findViewById(R.id.ac_merchant);
        final ImageView ac_icon = (ImageView)findViewById(R.id.account_icon);

        //////////////////////////////////测试界面//////需要数据库取信息显示
        titlebar.setText("常规邮箱");
        ac_title.setText("常规邮箱");
        ac_name.setText("Jill");
        ac_password.setText("1234");
        ac_icon.setImageResource(R.mipmap.ic_launcher_round);
        group_id = 1;
        /////////////////////////////

        group_title_tv.setText(group_title);
        if(group_id == 0 | group_id == 1) {
            merchant_t.setVisibility(View.GONE);
            ac_merchant.setVisibility(View.GONE);
        } else {
            merchant_t.setVisibility(View.VISIBLE);
            ac_merchant.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams layout=(RelativeLayout.LayoutParams)desc_t.getLayoutParams();
            layout.setMargins(0,200,0,0);
            desc_t.setLayoutParams(layout);
        }



        group_title_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        edit_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(acountActivity.this,AddActivity.class);
                intent.putExtra("mode",1);   //编辑信息模式
                intent.putExtra("pass_id",pass_id);
                startActivityForResult(intent,2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 2:
                if(resultCode == RESULT_OK) {
                    ///数据库操作，刷新信息页面
                }
        }
    }
}
