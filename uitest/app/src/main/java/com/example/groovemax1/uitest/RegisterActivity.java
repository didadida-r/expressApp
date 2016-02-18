package com.example.groovemax1.uitest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.groovemax1.uitest.AppContext.MyApplication;
import com.example.groovemax1.uitest.net.ThreadPoolTaskLogin;
import com.example.groovemax1.uitest.tools.SHA1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;

/**
 * 文件名：
 * 描述：注册
 * 作者：
 * 时间：
 */
public class RegisterActivity extends Activity implements View.OnClickListener, ThreadPoolTaskLogin.CallBack{

    private static final String ServerUrl = "http://audioexpress.applinzi.com/register";

    private EditText userName;
    private EditText userCode;
    private EditText checkCode;
    private Button registerBtn;
    private String name;
    private String code;
    private String check;
    private boolean nameFlag = false;
    private boolean codeFlag = false;
    private boolean checkFlag = false;

    private static final String LOG_INFO = "debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_register);
        initUi();
    }

    private void initUi(){
        userName = (EditText) findViewById(R.id.accountEd);
        userCode = (EditText) findViewById(R.id.codeEd);
        checkCode = (EditText) findViewById(R.id.checkEd);
        registerBtn = (Button) findViewById(R.id.registerBtn);

        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {
                    nameFlag = true;
                    name = userName.getText().toString();
                } else
                    nameFlag = false;
                if (checkFlag & codeFlag & nameFlag) {
                    registerBtn.setBackgroundColor(getResources().getColor(R.color.MyRed));
                    registerBtn.setEnabled(true);
                }else {
                    registerBtn.setBackgroundColor(getResources().getColor(R.color.GRAY));
                    registerBtn.setEnabled(false);
                }
            }
        });

        userCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {
                    codeFlag = true;
                    code = userCode.getText().toString();
                } else
                    codeFlag = false;
                if (checkFlag & codeFlag & nameFlag) {
                    registerBtn.setBackgroundColor(getResources().getColor(R.color.MyRed));
                    registerBtn.setEnabled(true);
                } else {
                    registerBtn.setBackgroundColor(getResources().getColor(R.color.GRAY));
                    registerBtn.setEnabled(false);
                }
            }
        });

        checkCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {
                    checkFlag = true;
                    check = checkCode.getText().toString();
                } else
                    checkFlag = false;
                if (checkFlag & codeFlag & nameFlag) {
                    registerBtn.setBackgroundColor(getResources().getColor(R.color.MyRed));
                    registerBtn.setEnabled(true);
                }else {
                    registerBtn.setBackgroundColor(getResources().getColor(R.color.GRAY));
                    registerBtn.setEnabled(false);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerBtn:
                //需要添加用户名检测
                if(code.matches("[0-9]+")){
                    Toast.makeText(this, "密码不能输入纯数字", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(code.length()<6){
                    Toast.makeText(this, "密码长度不能小于6", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(check.equals(code)){
                    //use SHA1 to encode the code
                    try{
                        code = SHA1.getSHA1(code);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                        Log.v(LOG_INFO, "fail to getSHA1");
                    }

                    MyApplication.getThreadPoolManager().addAsyncTask(new ThreadPoolTaskLogin(ServerUrl, 0, name, code, this));
                }
                break;
            case R.id.loginTv:
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }

    /** T */
    @Override
    public void onReady(String result) {
        if(result.equals("0"))
            finish();
    }

}
