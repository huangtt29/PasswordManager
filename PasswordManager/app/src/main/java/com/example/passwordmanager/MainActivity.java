package com.example.passwordmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button okBtn;
    private EditText newPasswordEdit;
    private EditText comfirmPasswordEdit;
    private EditText passwordEdit;
    private ConstraintLayout setPassword_page;
    private ConstraintLayout passwordSeted_page;
    private int SET_NEWPASSWORD=1;
    private int PASSWORD_SETED=2;
    private int mode=SET_NEWPASSWORD;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newPasswordEdit=(EditText)findViewById(R.id.newpassword);
        comfirmPasswordEdit=(EditText)findViewById(R.id.confirmpassword);
        passwordEdit=(EditText)findViewById(R.id.password);
        setPassword_page = (ConstraintLayout)findViewById(R.id.setPassword_page);
        passwordSeted_page = (ConstraintLayout)findViewById(R.id.passwordSeted_page);
        okBtn=(Button)findViewById(R.id.okBtn);

        okBtn.setOnClickListener(this);


//-------此处需要读取数据库看是否是第一次登录，根据是否是第一次登录选择mode。 mode=SET_NEWPASSWORD;mode=PASSWORD_SETED;
//        和设置可见或不可见

    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.okBtn:
                if(mode==SET_NEWPASSWORD)
                {
                    String newPasswordText=newPasswordEdit.getText().toString();
                    String comfirmPasswordText=comfirmPasswordEdit.getText().toString();
                    if(newPasswordText.equals("")||comfirmPasswordText.equals(""))
                    {
                        Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                    else if(!newPasswordText.equals(comfirmPasswordText))
                    {
                        Toast.makeText(this, "Password Mismatch", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        setPassword_page.setVisibility(View.GONE);
                        passwordSeted_page.setVisibility(View.VISIBLE);
                        password=comfirmPasswordText;

//                        此处需要存密码进数据库
                        mode=PASSWORD_SETED;
                        Toast.makeText(this, "Password saved", Toast.LENGTH_SHORT).show();
//                        Log.d("hello", "onClick: "+mode);
                    }
                }
                else if(mode==PASSWORD_SETED)
                {
                    String inputpassword=passwordEdit.getText().toString();
//                    Log.d("hello", inputpassword);
                    if(inputpassword.equals(password))
                    {
                        Toast.makeText(this, "Password Match", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(MainActivity.this,GroupListActivity.class);
                        startActivity(intent);

                    }
                    else
                        Toast.makeText(this, "Password Mismathch", Toast.LENGTH_SHORT).show();
                }

                break;

        }
    }
}
