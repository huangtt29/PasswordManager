package com.example.passwordmanager;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.List;

public class acountActivity extends AppCompatActivity {

    private String group_title;
    private String type_title;
    private String account_title;
    private long pass_id;
    private int group_id;
    private TextView titlebar;
    private TextView ac_title;
    private TextView ac_password;
    private TextView ac_name;
    private ImageView ac_icon;
    private TextView ac_desc;
    private TextView ac_merchant;
    private ImageView showPassword;

    void getDetailsFromDB() {
        List<Password> list= DataSupport.where("id = ?",String.valueOf(pass_id)).find(Password.class);
        for(Password p :list)
        {
            Log.d("test", "onCreate: "+p.getBaseObjId());
            titlebar.setText(p.getType());
            ac_title.setText(p.getTitle());
            ac_name.setText(p.getAcount());
            ac_password.setText(p.getPassword());
            ac_desc.setText(p.getDecription());
            ac_icon.setImageResource(p.getIcon());
            group_id = p.getGroup_id();
            if(group_id == 2) {
                ac_merchant.setText(p.getMerchant());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acount);

        Intent intent = getIntent();
        group_title = intent.getStringExtra("group_title");
        //type_title = intent.getStringExtra("type");
        //account_title = intent.getStringExtra("title");

        pass_id = intent.getLongExtra("pass_id",-1);
        Log.d("hello", "onCreate: "+pass_id);

        titlebar = (TextView)findViewById(R.id.titlebar3);//type
        TextView group_title_tv = (TextView)findViewById(R.id.group_title);
        TextView edit_content = (TextView)findViewById(R.id.edit_content);
        ac_title = (TextView)findViewById(R.id.account_title);//title
        ac_password = (TextView)findViewById(R.id.ac_password);//
        ac_name = (TextView)findViewById(R.id.account);//account
        TextView desc_t = (TextView)findViewById(R.id.desc_t);
        ac_desc = (TextView)findViewById(R.id.ac_desc);//description
        TextView merchant_t = (TextView)findViewById(R.id.merchant_t);
        ac_merchant = (TextView)findViewById(R.id.ac_merchant);//merchant
        ac_icon = (ImageView)findViewById(R.id.account_icon);//icon

        ///////////////////////////////////////数据库取信息显示
        getDetailsFromDB();
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
                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
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

        showPassword = (ImageView) findViewById(R.id.showPassword);
        showPassword.setOnClickListener(new View.OnClickListener() {
            boolean isVisible = false;
            @Override
            public void onClick(View view) {
                isVisible = !isVisible;
                if(isVisible) {
                    ac_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showPassword.setImageResource(R.mipmap.lock);
                } else {
                    ac_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showPassword.setImageResource(R.mipmap.unlock);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 2:
                if(resultCode == RESULT_OK) {
                    ///数据库操作，刷新信息页面
                    getDetailsFromDB();
//                    pass_id = data.getLongExtra("pass_id",-1);
//                    List<Password> list= DataSupport.where("id = ?",String.valueOf(pass_id)).find(Password.class);
//                    for(Password p :list)
//                    {
//                        Log.d("test", "onCreate: "+p.getBaseObjId());
//                        titlebar.setText(p.getType());
//                        ac_title.setText(p.getTitle());
//                        ac_name.setText(p.getAcount());
//                        ac_password.setText(p.getPassword());
//                        ac_icon.setImageResource(R.mipmap.ic_launcher_round);
//                        ac_desc.setText(p.getDecription());
//                        group_id = p.getGroup_id();
//                        if(group_id == 2) {
//                            ac_merchant.setText(p.getMerchant());
//                        }
//                    }
                }
        }
    }
}
