package com.example.passwordmanager;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;

public class GenerateActivity extends AppCompatActivity {

    private TextView back;
    private TextView length_textview;
    private int length;
    private ImageView minusBtn;
    private ImageView plusBtn;
    private CheckBox isUpperBox;
    private CheckBox isLowerBox;
    private CheckBox isNumBox;
    private CheckBox isCharBox;
    private boolean isUpper;
    private boolean isLower;
    private boolean isNum;
    private boolean isChar;
    private TextView password_textview;
    private ImageView fleshBtn;
    private Button okBtn;
    private String password;
    private static final char[] upper = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };
    private static final char[] lower = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm',
            'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };
    private static final char[] num = {'0','1','2', '3', '4', '5', '6', '7', '8', '9'};
    private static final char[] chars = {'~','!','@','#','$','%','^','&','*','(',')','_'};

    String generate() {
        StringBuilder buffer = new StringBuilder();
        Random rand = new Random();
        boolean isStart = false;
        if(isUpper) {
                for (int i = 0; i < length; i++) {
                    buffer.append(upper[rand.nextInt(upper.length)]);
                }
                isStart = true;
        }
        if(isLower) {
            if(!isStart) {
                for (int i = 0; i < length; i++) {
                    buffer.append(lower[rand.nextInt(lower.length)]);
                }
                isStart = true;
            }
            else  {
                boolean isChange;
                for(int i = 0; i < length; i++) {
                    isChange = rand.nextBoolean();
                    if(isChange) {
                        buffer.setCharAt(i,lower[rand.nextInt(lower.length)]);
                    }
                }
            }
        }
        if(isNum) {
            if(!isStart) {
                for (int i = 0; i < length; i++) {
                    buffer.append(num[rand.nextInt(num.length)]);
                }
                isStart = true;
            }
            else  {
                boolean isChange;
                for(int i = 0; i < length; i++) {
                    isChange = rand.nextBoolean();
                    if(isChange) {
                        buffer.setCharAt(i,num[rand.nextInt(num.length)]);
                    }
                }
            }
        }
        if(isChar) {
            if(!isStart) {
                for (int i = 0; i < length; i++) {
                    buffer.append(chars[rand.nextInt(chars.length)]);
                }
                isStart = true;
            }
            else  {
                double isChange;
                for(int i = 0; i < length; i++) {
                    isChange = rand.nextDouble();
                    if(isChange < 0.4) {
                        buffer.setCharAt(i,chars[rand.nextInt(chars.length)]);
                    }
                }
            }
        }
        return buffer.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate);

        back = (TextView)findViewById(R.id.generate_back);
        length_textview = (TextView)findViewById(R.id.length);
        minusBtn = (ImageView)findViewById(R.id.minus_btn);
        plusBtn = (ImageView)findViewById(R.id.plus_btn);
        isUpperBox = (CheckBox)findViewById(R.id.isUpper);
        isLowerBox = (CheckBox)findViewById(R.id.isLower);
        isNumBox = (CheckBox)findViewById(R.id.isNum);
        isCharBox = (CheckBox)findViewById(R.id.isChar);
        password_textview = (TextView)findViewById(R.id.generated_password);
        fleshBtn = (ImageView)findViewById(R.id.flesh_btn);
        okBtn = (Button)findViewById(R.id.generate_ok);
        //length = Integer.parseInt(length_textview.getText().toString());
        password = password_textview.getText().toString();
//        isUpper = isUpperBox.isChecked();
//        isLower = isLowerBox.isChecked();
//        isNum = isNumBox.isChecked();
//        isChar = isCharBox.isChecked();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                length = Integer.parseInt(length_textview.getText().toString());
                if(length > 6) {
                    length--;
                    length_textview.setText(Integer.toString(length));
                }
            }
        });

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                length = Integer.parseInt(length_textview.getText().toString());
                if(length < 30) {
                    length++;
                    length_textview.setText(Integer.toString(length));
                }
            }
        });

        fleshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                length = Integer.parseInt(length_textview.getText().toString());
                isUpper = isUpperBox.isChecked();
                isLower = isLowerBox.isChecked();
                isNum = isNumBox.isChecked();
                isChar = isCharBox.isChecked();
                String tmp = generate();
                password_textview.setText(tmp);
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password = password_textview.getText().toString();
                Intent intent2 = new Intent();
                intent2.putExtra("password",password);
                setResult(RESULT_OK, intent2);
                finish();
            }
        });
    }
}
