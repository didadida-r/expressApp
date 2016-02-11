package com.example.groovemax1.uitest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.groovemax1.uitest.tools.RecordClass;


/**
 * Created by GROOVEMAX1 on 2016/1/25.
 * 我要表白
 */

public class ExpressActivity extends Activity implements View.OnClickListener{
    private RecordClass recordClass;

    private Button recordBtn;
    private AlertDialog jumpDialog;
    private AlertDialog recordDialog;
    private CheckBox checkBox;
    private EditText titleText;
    private EditText expressText;
    private Handler handler;
    private int jumpTime = 3;
    private int recordTime = 0;
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
                    case 0003:
                        if (--jumpTime > 0) {
                            jumpDialog.getButton(AlertDialog.BUTTON_POSITIVE).setText("确定（"+ jumpTime +"s)");
                            Message message = handler.obtainMessage(0003);
                            //延迟1s发送消息,自循环计时
                            handler.sendMessageDelayed(message, 1000);
                        } else {
                            jumpDialog.dismiss();
                            finish();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        }
                        super.handleMessage(msg);
                        break;
                    //录音时长超过5分钟，直接进入停止录音进入试听
                    case 0500:
                        if(++recordTime > 5){
                            Log.v("debug", "new AlertDialog");
                            recordDialog = new AlertDialog.Builder(ExpressActivity.this)
                                    .setTitle("提示")
                                    .setMessage("录音时间请勿超过5分钟")
                                    .setPositiveButton("确定", null)
                                    .create();
                            recordDialog.show();
                            Log.v("debug", "stopRecord()");
                            recordClass.stopRecord();
                            recordBtn.setText("试听");
                            recordFlag = 3;

                        }else{
                            Message message = handler.obtainMessage(0500);
                            handler.sendMessageDelayed(message, 60000);
                        }
                        break;
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backBtn:
                finish();
                break;

            //录音
            case R.id.recordBtn:
                switch (++recordFlag){
                    case 1:
                        Log.v("debug", "recordFlag1");
                        recordClass.startRecord();
                        recordBtn.setText("停止");
                        Message message = handler.obtainMessage(0500);
                        handler.sendMessageDelayed(message, 60000);
                        break;
                    case 2:
                        Log.v("debug", "recordFlag2");
                        recordClass.stopRecord();
                        recordBtn.setText("试听");
                        break;
                    default:
                        Log.v("debug", "recordFlagN");
                        recordClass.startPlay();
                        recordClass.reset();
                        break;
                }
                break;

            //重录
            case R.id.repeatBtn:
                recordFlag = 0;
                if(recordClass.isMediaPlayer() == 1)
                    recordClass.stopPlay();
                if(recordClass.isMediaRecorder() == 1)
                    recordClass.stopRecord();
                recordBtn.setText("录音");
                recordTime = 0;
                break;

            //发布,自定义监听事件实现倒计时
            case R.id.publishBtn:
                if(recordClass.playState == 1)
                     recordClass.stopPlay();
                jumpDialog = new AlertDialog.Builder(this)
                        .setTitle("发布成功")
                        .setMessage("即将跳转至主页面")
                        .setPositiveButton("确定", null)
                        .create();
                jumpDialog.show();
                jumpDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jumpDialog.getButton(AlertDialog.BUTTON_POSITIVE).setText("确定（"+ jumpTime +"s)");
                        Message message = handler.obtainMessage(0003);
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
        recordBtn = (Button) findViewById(R.id.recordBtn);
        if(recordClass == null)
            recordClass = new RecordClass(this);
    }
}

