package com.example.groovemax1.uitest;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by GROOVEMAX1 on 2016/1/25.
 */
//消息
//使用fragment需要将最小api版本改为11
public class MsgActivity extends Activity implements View.OnClickListener{

    private MsgUserListFragment msgUserFragment;
    private MsgSysListFragment msgSysFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.msg_layout);
        initUi();

    }
    private void initUi(){
        //设置默认的fragment
        setDefaultFragment();
    }

    private void setDefaultFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        msgUserFragment = new MsgUserListFragment();
        transaction.replace(R.id.msgFrameContain, msgUserFragment);
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (view.getId()){
            case R.id.backBtn:
                MsgActivity.this.finish();
                startActivity(new Intent(MsgActivity.this, HomeActivity.class));
                break;
            case R.id.userMsgBtn:
                if(msgUserFragment == null)
                    msgUserFragment = new MsgUserListFragment();
                transaction.replace(R.id.msgFrameContain, msgUserFragment);
                break;
            case R.id.sysMsgBtn:
                if(msgSysFragment == null)
                    msgSysFragment = new MsgSysListFragment();
                transaction.replace(R.id.msgFrameContain, msgSysFragment);
                break;
            default:
                break;
        }
        //事务提交
        transaction.commit();
    }

}
