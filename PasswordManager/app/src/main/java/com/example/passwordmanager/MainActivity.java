package com.example.passwordmanager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.lang.reflect.Field;
import java.util.List;

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
//        DataSupport.deleteAll(LoginPassword.class);
        List<LoginPassword> list=DataSupport.findAll(LoginPassword.class);

        for(LoginPassword l:list)
        {
            mode=PASSWORD_SETED;
            String passwordInDB =l.getLoginPassword();
            Log.d("Passwordorigal", passwordInDB);
            break;
        }
        if(mode==PASSWORD_SETED)
        {
            passwordSeted_page.setVisibility(View.VISIBLE);
            setPassword_page.setVisibility(View.GONE);
        }

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
                        Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    }
                    else if(!newPasswordText.equals(comfirmPasswordText))
                    {

                        Toast.makeText(this, "密码不匹配", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        setPassword_page.setVisibility(View.GONE);
                        passwordSeted_page.setVisibility(View.VISIBLE);
                        password=comfirmPasswordText;

//                        此处需要存密码进数据库
                        LoginPassword loginPassword=new LoginPassword();
                        loginPassword.setLoginPassword(password);
                        loginPassword.save();

                        mode=PASSWORD_SETED;

                        LayoutInflater factor = LayoutInflater.from(MainActivity.this);
                        View view = factor.inflate(R.layout.dialoglayout,null);
                        final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                        dialog.setView(view);
                        dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(MainActivity.this, SendMailActivity.class);
                                        intent.putExtra("password",password);
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                })
                                .show();
                    }
                }
                else if(mode==PASSWORD_SETED)
                {
                    String inputpassword=passwordEdit.getText().toString();

                    if(inputpassword.equals(password))
                    {
                        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(MainActivity.this,GroupListActivity.class);
                        startActivity(intent);

                    }
                    else
                    {
                        LoginPassword loginPassword=new LoginPassword();
                        loginPassword.setLoginPassword(inputpassword);
                        loginPassword.save();

                        String firstpassword=DataSupport.findFirst(LoginPassword.class).getLoginPassword();
                        String lastpassword=DataSupport.findLast(LoginPassword.class).getLoginPassword();
                        Log.d("passwordfirst",firstpassword);
                        Log.d("passwordlast", lastpassword);
                        if(lastpassword.equals(firstpassword))
                        {
                            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(MainActivity.this,GroupListActivity.class);
                            startActivity(intent);
                            DataSupport.deleteAll(LoginPassword.class);

                            LoginPassword log=new LoginPassword();
                            log.setLoginPassword(inputpassword);
                            log.save();

                        }
                        else {
                            Toast.makeText(this, "密码不正确，请再次输入", Toast.LENGTH_SHORT).show();
                            DataSupport.deleteAll(LoginPassword.class,"loginPassword=?",lastpassword);

                        }

                    }


                }

                break;

        }
    }
}
