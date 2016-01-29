package com.example.groovemax1.uitest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.groovemax1.uitest.tools.RecordClass;


/**
 * Created by GROOVEMAX1 on 2016/1/25.
 * 我要表白
 */

public class ExpressActivity extends Activity implements View.OnClickListener{
    private RecordClass recordClass;

    private AlertDialog alertDialog;
    private CheckBox checkBox;
    private EditText titleText;
    private EditText expressText;
    private Handler handler;
    private int timeCount = 3;
    private int recordFlag = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.express);
        initUi();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0X05:
                        if (--timeCount > 0) {
                            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setText("确定（"+timeCount+"s)");
                            Message message = handler.obtainMessage(0X03);
                            //延迟1s发送消息,自循环计时
                            handler.sendMessageDelayed(message, 1000);
                        } else {
                            alertDialog.dismiss();
                            finish();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        }
                        super.handleMessage(msg);
                }
            }
        };
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backBtn:
                startActivity(new Intent(this, HomeActivity.class));
                break;
            //录音
            case R.id.recordBtn:
            //重录
            case R.id.repeatBtn:
                if((recordFlag = ++recordFlag%2) == 1)
                    recordClass.startRecord();

                else{
                    recordClass.stopRecord();
                    recordClass.startPlay();
                }
                break;

            //发布,自定义监听事件实现倒计时
            case R.id.publishBtn:
                if(recordClass.playState == 1)
                     recordClass.stopPlay();
                alertDialog = new AlertDialog.Builder(this)
                        .setTitle("发布成功")
                        .setMessage("即将跳转至主页面")
                        .setPositiveButton("确定", null)
                        .create();
                alertDialog.show();
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setText("确定（"+timeCount+"s)");
                        Message message = handler.obtainMessage(0X03);
                        handler.sendMessageDelayed(message, 1000);
                    }
                });
                break;
            //选择表白图片
            case R.id.image:
                break;
            default:
                break;
        }
    }

    private void initUi(){
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        titleText = (EditText) findViewById(R.id.titleText);
        expressText = (EditText) findViewById(R.id.expressText);
        if(recordClass == null)
            recordClass = new RecordClass(this);
    }
}

