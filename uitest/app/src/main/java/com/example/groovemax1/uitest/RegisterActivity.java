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
public class RegisterActivity extends Activity implements View.OnClickListener{

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

    private int REGISTER_CODE;

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
                    new Thread(){
                        @Override
                        public void run() {
                            loginByPost(name, code);
                            if(REGISTER_CODE == 0)
                                finish();
                        }
                    }.start();
                }
                break;
            case R.id.loginTv:
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }

    //用于登录
    public void loginByPost(String userName, String userCode){
        try{
            URL url = new URL(ServerUrl);
            //只是建立tcp连接，没有发送http请求
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setReadTimeout(5000);
            urlConnection.setConnectTimeout(5000);
            //允许输入输出
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
            /*
            //设置请求体的类型是文本类型
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //设置请求体的长度
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
             */
            //建立tcp连接，所有set的设置必须在此之前完成
            urlConnection.connect();

            Log.v(LOG_INFO, userName + "" + userCode);
            //传递用户名与密码,这里也可以用StringBuffer
            String data = "userName=" + URLEncoder.encode(userName, "UTF-8")
                    + "&userCode=" + URLEncoder.encode(userCode,"UTF-8");
            int len;
            byte buffer[] = new byte[1024];

            //将输出流与输出数据绑定
            OutputStream os = urlConnection.getOutputStream();
            os.write(data.getBytes());
            os.flush();

            /*上传图片
                FileInputStream fileInputStream = new FileInputStream(
                    Environment.getExternalStorageDirectory().getAbsolutePath() + "/XXX.jpg");
                while ((len = fileInputStream.read(buffer, 0, 1024)) != -1){
                    os.write(buffer);
                }
                os.flush();
                os.close();
                fileInputStream.close();
             */

            //用于接收服务器数据
            String result;
            //获取服务器传送过来的数据,成功的响应码是200
            if(urlConnection.getResponseCode() == 200){
                InputStream in = urlConnection.getInputStream();
                //创建字节输出流对象，用于接收服务器数据
                ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                //定义读取的字节流长度
                while((len = in.read(buffer)) != -1){
                    //根据读入数据长度写byteOut对象
                    byteOut.write(buffer, 0 ,len);
                }
                //get the register_code
                result = new String(byteOut.toByteArray());
                REGISTER_CODE = Integer.parseInt(result);
                in.close();
                byteOut.close();
                Log.v("debugTag", "输出结果"+result);
            }else {
                Log.v("debugTag", "连接失败");
            }
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
}
