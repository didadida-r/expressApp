package com.example.groovemax1.uitest;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

/**
 * Created by GROOVEMAX1 on 2016/1/25.
 * 消息
 * 传入的应该只是state来判别什么样的listFragment，到里面直接根据state添加layout等
 */

//使用fragment需要将最小api版本改为11
public class MsgActivity extends Activity implements View.OnClickListener{
    public static final String ARGUMENT_STATE = "argument_state";
    private Bundle bundle;

    private MsgListFragment msgFragment;


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

        bundle = new Bundle();
        bundle.putString(ARGUMENT_STATE, "msgUserFragment");
        msgFragment = new MsgListFragment();
        //传入bundle
        msgFragment.setArguments(bundle);

        transaction.replace(R.id.msgFrameContain, msgFragment);
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (view.getId()){
            case R.id.backBtn:
                MsgActivity.this.finish();
                break;
            case R.id.userMsgBtn:
                //将需要从activity传入到fragment的数据封装到bundle中
                bundle = new Bundle();
                bundle.putString(ARGUMENT_STATE, "msgUserFragment");
                msgFragment = new MsgListFragment();
                //传入bundle
                msgFragment.setArguments(bundle);

                transaction.replace(R.id.msgFrameContain, msgFragment);
                break;
            case R.id.sysMsgBtn:
                //将需要从activity传入到fragment的数据封装到bundle中
                bundle = new Bundle();
                bundle.putString(ARGUMENT_STATE, "msgSysFragment");
                msgFragment = new MsgListFragment();
                //传入bundle
                msgFragment.setArguments(bundle);

                transaction.replace(R.id.msgFrameContain, msgFragment);
                break;
            default:
                break;
        }
        //事务提交
        transaction.commit();
    }

}
