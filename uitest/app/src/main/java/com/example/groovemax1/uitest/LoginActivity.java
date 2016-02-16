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

import com.example.groovemax1.uitest.AppContext.MyApplication;
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

public class LoginActivity extends Activity implements View.OnClickListener{
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
                new Thread(){
                    @Override
                    public void run() {
                        loginByPost(name, code);
                    }
                }.start();

                MyApplication myApplication = (MyApplication) getApplication();
                myApplication.setIsLogin(true);

                break;
            case R.id.registerTv:
                finish();
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            default:
                break;
        }
    }

    //用于登录
    public void loginByPost(String userName, String userCode){
        try{
            URL url = new URL("");
            //只是建立tcp连接，没有发送http请求
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setReadTimeout(5000);
            urlConnection.setConnectTimeout(5000);
            //允许输入输出
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
            //建立tcp连接，所有set的设置必须在此之前完成
            urlConnection.connect();

            //传递用户名与密码,这里也可以用StringBuffer
            String data = "userName=" + URLEncoder.encode(userName, "UTF-8")
                    + "&userCode=" + URLEncoder.encode(userCode,"UTF-8");
            int len;
            byte buffer[] = new byte[1024];

            //将输出流与输出数据绑定
            OutputStream os = urlConnection.getOutputStream();
            os.write(data.getBytes());
            os.flush();

            //用于接收服务器数据
            String result;
            //获取服务器传送过来的数据
            if(urlConnection.getResponseCode() == 200){
                InputStream in = urlConnection.getInputStream();
                //创建字节输出流对象，用于接收服务器数据
                ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                //定义读取的字节流长度
                while((len = in.read(buffer)) != -1){
                    //根据读入数据长度写byteOut对象
                    byteOut.write(buffer, 0 ,len);
                }
                //将数据转为字符串
                result = new String(byteOut.toByteArray());
                in.close();
                byteOut.close();
            }else {
                Log.v("debugTag", "连接失败");
            }
        }catch(MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
