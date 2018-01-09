package com.example.passwordmanager;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SendMailActivity extends AppCompatActivity {

    public boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mail);

        Button send_btn = (Button)findViewById(R.id.send_btn);
        final String password = getIntent().getStringExtra("password");
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText mail_input = (EditText)findViewById(R.id.mail_input);
                String mail = mail_input.getText().toString();
                if(mail.isEmpty()) {
                    Toast.makeText(SendMailActivity.this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
                } else if(!isEmail(mail)) {
                    Toast.makeText(SendMailActivity.this, "邮箱格式不正确", Toast.LENGTH_SHORT).show();
                } else {
//                    Intent data=new Intent(Intent.ACTION_SENDTO);
//                    data.setData(Uri.parse("mailto:810974432@qq.com"));
//                    data.putExtra(Intent.EXTRA_SUBJECT, "这是标题");
//                    data.putExtra(Intent.EXTRA_TEXT, password);
//                    startActivity(data);
                    CharSequence text = "您好！\n欢迎使用PasswordManager！这是您的主密码："+password+"，请做好备份及保密工作。谢谢！\n来自 PasswordManager";
                    Uri uri = Uri.parse("mailto:"+mail);
                    Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                    intent.putExtra(Intent.EXTRA_SUBJECT, "[PasswordManager]请备份您的密码"); // 主题
                    intent.putExtra(Intent.EXTRA_TEXT, text); // 正文
                    startActivity(Intent.createChooser(intent, "请选择邮件类应用"));
                    finish();

                }
            }
        });

        Button cancel_btn = (Button)findViewById(R.id.cancelsend_btn);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
