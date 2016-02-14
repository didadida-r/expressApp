package com.example.groovemax1.uitest;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.groovemax1.uitest.view.MyDialog;

import java.util.ArrayList;

/**
 * Created by GROOVEMAX1 on 2016/1/27.
 * 主页
 */

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, HomeListFragment.HomeListFragmentClickListener{

    private HomeListFragment homeListFragment;
    private ImageView homeIv;

    public interface HomeTouchListener{
        public void onTouchEvent(MotionEvent event);
    }

    //保存HomeTouchListener接口的列表
    private ArrayList<HomeTouchListener> homeTouchListeners = new ArrayList<HomeActivity.HomeTouchListener>();

    //注册触摸事件
    public void registerHomeTouchListener(HomeTouchListener listener){
        homeTouchListeners.add(listener);
    }

    //取消触摸事件
    public void unRegisterHomeListener(HomeTouchListener listener){
        homeTouchListeners.remove(listener);
    }

    //分发触摸事件到所有注册的接口
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for(HomeTouchListener listener : homeTouchListeners){
            listener.onTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        initUi();
    }

    //初始化UI
    public void initUi(){
        setDefaultFragment();
        homeIv = (ImageView) findViewById(R.id.homeIv);
        homeIv.setImageResource(R.mipmap.home_home_selete_iv);

    }

    //初始化fragment
    private void setDefaultFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        homeListFragment = new HomeListFragment();
        transaction.replace(R.id.homeFrameContain, homeListFragment);
        transaction.commit();
    }

    //HomeListFragment点击时的回调,跳转到详细表白页面
    @Override
    public void onHomeListFragmentClick(int i) {
        if(i == 0)
            Toast.makeText(this, "left", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "right", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(this, ExpressionDetailActivity.class));
    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (v.getId()){
            case R.id.homeIv:
                startActivity(new Intent(this, testActivity.class));
                break;
            case R.id.mineIv:
                startActivity((new Intent(HomeActivity.this, MineActivity.class)));
                break;
            case R.id.expressIv:
                startActivity(new Intent(HomeActivity.this, ExpressActivity.class));
                break;
            case R.id.messageBtn:
                startActivity((new Intent(HomeActivity.this, MsgActivity.class)));
                break;
            default:
                break;
        }
        //事务提交
        transaction.commit();
    }

}
