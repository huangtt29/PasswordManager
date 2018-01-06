package com.example.passwordmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class AddActivity extends AppCompatActivity {

    private Button add_cancelBtn;
    private TextView add_type;
    private Button add_saveBtn;
    private EditText add_title;
    private EditText add_acount;
    private EditText add_password;
    private EditText add_description;
    private EditText add_merchant;
    private ImageView add_icon;
    private int group_id=0;
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


        add_saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// public Password(String title,String acount,String decription,String merchant,int group_id,String password)
                String title=add_title.getText().toString();
                String acount=add_acount.getText().toString();
                String password=add_password.getText().toString();
                String description=add_description.getText().toString();
                String merchant=add_merchant.getText().toString();
//               加入数据库
                Password record=new Password(title,acount,description,merchant, group_id , password);
                record.save();
                Intent intent=new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        add_cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}
