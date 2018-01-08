package com.example.passwordmanager;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.List;

public class AddActivity extends AppCompatActivity {

    private Button add_cancelBtn;
    private TextView add_type;
    private Button add_saveBtn;
    private EditText add_title;
    private EditText add_acount;
    private EditText add_password;
    private EditText add_description;
    private EditText add_merchant;
    private TextView merchant_t;
    private TextView desc_t;
    private ImageView add_icon;
    private int group_id=-1;
    private String type;
    private int mode;
    private int icon;
    private long pass_id;
    private String[] mail = {"常规邮箱","谷歌邮箱","雅虎邮箱","网易邮箱","QQ邮箱"};
    private String[] account = {"常规登录","微信","QQ","新浪微博","知乎","淘宝","京东","亚马逊","Facebook","Twitter","Instergram"};
    private String[] wallet = {"银行卡","信用卡","身份证","驾驶证","护照","会员卡"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        add_cancelBtn=(Button)findViewById(R.id.add_cancelBtn);
        add_type=(TextView)findViewById(R.id.add_type);
        add_saveBtn=(Button)findViewById(R.id.add_saveBtn);
        add_title=(EditText)findViewById(R.id.add_title);
        add_acount=(EditText)findViewById(R.id.add_acount);
        add_password=(EditText)findViewById(R.id.add_password);
        add_description=(EditText)findViewById(R.id.add_description);
        add_merchant=(EditText)findViewById(R.id.add_merchant);
        add_icon=(ImageView)findViewById(R.id.add_icon);
        merchant_t=(TextView)findViewById(R.id.merchant_t2);
        desc_t=(TextView)findViewById(R.id.desc_t2);


        Intent intent = getIntent();
        mode = intent.getIntExtra("mode",0);
        Log.d("mode", "onCreate: "+mode);
        if(mode == 0) {
            type = intent.getStringExtra("type");
            icon = intent.getIntExtra("icon",R.mipmap.ic_launcher_round);
            add_type.setText(type);
            add_icon.setImageResource(icon);
            add_title.setText(type);   ///////title默认等于type
            merchant_t.setVisibility(View.GONE);
            add_merchant.setVisibility(View.GONE);
            ////确定分组
            if(group_id == -1) {
                for(int i = 0; i < account.length; i++) {
                    if(type.equals(account[i])) {
                        group_id=0;
                        break;
                    }
                }
            }
            if(group_id == -1) {
                for(int i = 0; i < mail.length; i++) {
                    if(type.equals(mail[i])) {
                        group_id=1;
                        break;
                    }
                }
            }
            if(group_id == -1) {
                for(int i = 0; i < wallet.length; i++) {
                    if(type.equals(wallet[i])) {
                        group_id=2;
                        break;
                    }
                }
            }
            if(group_id == -1) {
                group_id = 0; //默认
            }
        } else if(mode == 1){
            pass_id = intent.getLongExtra("pass_id",0);
            ////数据库操作，获取信息,并设置在相应控件中
            Log.d("passss", "onCreate: "+pass_id);
            List<Password> list= DataSupport.where("id = ?",String.valueOf(pass_id)).find(Password.class);
            for(Password p :list)
            {
                add_title.setText(p.getTitle());
                add_acount.setText(p.getAcount());
                add_password.setText(p.getPassword());
                add_description.setText(p.getDecription());
                group_id = p.getGroup_id();
                if(group_id==2) {
                    add_merchant.setText(p.getMerchant());
                }
            }
        }

        if(group_id == 0 | group_id == 1) {
            add_merchant.setVisibility(View.GONE);
            merchant_t.setVisibility(View.GONE);
        } else {
            add_merchant.setVisibility(View.VISIBLE);
            merchant_t.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams layout=(RelativeLayout.LayoutParams)desc_t.getLayoutParams();
            layout.setMargins(0,200,0,0);
            desc_t.setLayoutParams(layout);
        }

        add_saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// public Password(String title,String acount,String decription,String merchant,int group_id,String password)
                if(mode == 0) {
                    String title=add_title.getText().toString();
                    String acount=add_acount.getText().toString();
                    String password=add_password.getText().toString();
                    String description=add_description.getText().toString();
                    String merchant=add_merchant.getText().toString();

//                    Log.d("type", "onClick: "+type);
//                  加入数据库
                    Password record=new Password(type,title,acount,description,merchant, group_id , password);
                    record.save();

                }
                else if(mode == 1) {
                    ///数据库操作，修改信息模式,更新数据
                    String title=add_title.getText().toString();
                    String acount=add_acount.getText().toString();
                    String password=add_password.getText().toString();
                    String description=add_description.getText().toString();
                    String merchant=add_merchant.getText().toString();

//                    List<Password> list= DataSupport.where("id = ?",String.valueOf(pass_id)).find(Password.class);

                    Password record=new Password(type,title,acount,description,merchant, group_id , password);
                    record.updateAll("id =? ",String.valueOf(pass_id));

                }
                Intent intent=new Intent();
                intent.putExtra("pass_id",pass_id);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        add_cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(AddActivity.this);
                dialog.setTitle("你尚未保存修改，确定退出吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ;
                            }
                        })
                        .show();
            }
        });


    }
}
