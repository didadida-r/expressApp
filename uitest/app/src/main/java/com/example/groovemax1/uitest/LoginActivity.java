package com.example.groovemax1.uitest;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.groovemax1.uitest.AppContext.MyApplication;
import com.example.groovemax1.uitest.net.ThreadPoolTaskLogin;
import com.example.groovemax1.uitest.tools.SHA1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;

/**
 * Created by GROOVEMAX1 on 2016/1/26.
 * 登录
 */

public class LoginActivity extends Activity implements View.OnClickListener, ThreadPoolTaskLogin.CallBack{
    private EditText userName;
    private EditText userCode;
    private String name;
    private String code;
    private boolean nameFlag = false;
    private boolean codeFlag = false;
    private CheckBox rememberCode;      //remember the code and account
    private Button loginBtn;

    private static final String PREFERENCE_NAME = "saveInfo";   //文件名称
    private static final int MODE = MODE_PRIVATE;         //操作模式
    private static final String USER_NAME = "USER_NAME";
    private static final String USER_CODE = "USER_CODE";
    private static final String LOGIN_STATE = "LOGIN_STATE";
    private static final String LOG_INFO = "debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initUi();

        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() != 0){
                    name = userName.getText().toString();
                    nameFlag = true;
                }
                else
                    nameFlag = false;
                if(nameFlag &codeFlag){
                    loginBtn.setBackgroundColor(getResources().getColor(R.color.MyRed));
                    loginBtn.setEnabled(true);
                }else {
                    loginBtn.setBackgroundColor(getResources().getColor(R.color.GRAY));
                    loginBtn.setEnabled(false);
                }

            }
        });

        userCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() != 0){
                    code = userCode.getText().toString();
                    codeFlag = true;
                }
                else
                    codeFlag = false;
                if(nameFlag &codeFlag){
                    loginBtn.setBackgroundColor(getResources().getColor(R.color.MyRed));
                    loginBtn.setEnabled(true);
                }else {
                    loginBtn.setBackgroundColor(getResources().getColor(R.color.GRAY));
                    loginBtn.setEnabled(false);
                }
            }
        });
    }

    void initUi(){
        userName = (EditText) findViewById(R.id.accountEd);
        userCode = (EditText) findViewById(R.id.codeEd);
        rememberCode = (CheckBox) findViewById(R.id.rememberCode);
        loginBtn = (Button) findViewById(R.id.loginBtn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginBtn:
                SharedPreferences sharedPreferences = this.getSharedPreferences(PREFERENCE_NAME, MODE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                //store the userName and userCode if wanted
                if(rememberCode.isChecked()){
                    editor.putString(USER_NAME, name);
                    editor.putString(USER_CODE, code);
                    editor.putBoolean(LOGIN_STATE, true);
                    /*注销时使用
                     editor.remove(ACCOUNT);
                     editor.remove(PASSWORD);
                     */
                    /* 获取内容
                        SharedPreferences sp=this.getPreferences(MODE_PRIVATE);
                        String username=sp.getString("username", "error");  //第二个参数为默认参数，即当sp中不存在username时，返回的字符串。
                     */
                }else
                    editor.putBoolean(LOGIN_STATE, false);
                editor.commit();

                try{
                    code = SHA1.getSHA1(code);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                    Log.v(LOG_INFO, "fail to getSHA1");
                }

                String url = "";
                MyApplication.getThreadPoolManager().addAsyncTask(new ThreadPoolTaskLogin(url, 1, name ,code ,this));

                //MyApplication myApplication = (MyApplication) getApplication();
                //myApplication.setIsLogin(true);

                break;
            case R.id.registerTv:
                finish();
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void onReady(String result) {
        if(result.equals("0")){
            finish();
        }

    }
}
