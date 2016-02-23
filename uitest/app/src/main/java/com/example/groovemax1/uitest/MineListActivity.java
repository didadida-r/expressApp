package com.example.groovemax1.uitest;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.WeakHashMap;

/**
 * 文件名：
 * 描述：
 * 作者：
 * 时间：
 * bug: 当加载时退出fragment会出错，java.lang.IllegalStateException: Content view not yet created
 *      通过判断getView()来解决
 */
public class MineListActivity extends Activity implements MyListFragment.MyListFragmentClickListener, View.OnClickListener{

    private TextView titleTv;

    private MyListFragment myListFragment;
    private static final int LOAD_DATA_FINISH = 10;//上拉刷新
    private static final int REFRESH_DATA_FINISH = 11;//下拉刷新

    private static final String TAG = "debug";

    /**
     * 接口：MineListTouchListener
     * 描述：用于将MineListActivity的监听事件分发给fragment
     */
    public interface MineListTouchListener{
        void onTouchEvent(MotionEvent event);
    }

    /**
     * 保存MineListTouchListener接口的列表
     */
    private ArrayList<MineListTouchListener> mineListTouchListeners = new ArrayList<MineListActivity.MineListTouchListener>();

    /**
     * 注册触摸事件
     */
    public void registerMineListTouchListener(MineListTouchListener listener){
        mineListTouchListeners.add(listener);
    }

    /**
     * 取消触摸事件
     */
    public void unRegisterMineListListener(MineListTouchListener listener){
        mineListTouchListeners.remove(listener);
    }

    /**
     * 分发触摸事件到所有注册的接口
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for(MineListTouchListener listener : mineListTouchListeners){
            listener.onTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_listview_contain);
        initUi();

    }

    public void initUi(){
        titleTv = (TextView) findViewById(R.id.titleTv);
        Intent intent = getIntent();
        titleTv.setText(intent.getStringExtra("title"));

        setDefaultFragment();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.backBtn)
            finish();
    }

    /**
     * 初始化fragment,监听下拉刷新和上拉加载
     */
    private void setDefaultFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        myListFragment = new MyListFragment();

        //------------------------------
        myListFragment.setOnRefreshListener(new MyListFragment.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 下拉刷新
                Log.e(TAG, "onRefresh");
                loadData(0);
            }
        });
        myListFragment.setOnLoadListener(new MyListFragment.OnLoadMoreListener() {

            @Override
            public void onLoadMore() {
                //  加载更多
                Log.e(TAG, "onLoad");
                loadData(1);
            }
        });
        //关闭下拉刷新
        //myListFragment.setCanRefresh(!myListFragment.isCanRefresh());
        //关闭上拉刷新
        //myListFragment.setCanLoadMore(!myListFragment.isCanLoadMore());
        //------------------------------
        transaction.replace(R.id.mineListFrameContain, myListFragment);
        transaction.commit();
    }

    /*
     上下拉刷新加载数据方法
   */
    public void loadData(final int type){
        new Thread(){
            @Override
            public void run() {
                switch (type) {
                    case 0://这里是下拉刷新

                        break;

                    case 1:
                        //这里是上拉刷新
                        //int _Index = mCount + 10;
                        //mCount = _Index;
                        break;
                }

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(type==0){//下拉刷新
                    //通知Handler
                    if(myListFragment.getView() != null)
                        myHandler.sendEmptyMessage(REFRESH_DATA_FINISH);
                }else if(type==1){//上拉刷新
                    //通知Handler
                    if(myListFragment.getView() != null)
                        myHandler.sendEmptyMessage(LOAD_DATA_FINISH);
                }
            }
        }.start();
    }

    /*
       handler
     */
    private Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
                switch (msg.what) {
                    case REFRESH_DATA_FINISH:

                        Constant.IMAGE_URLS.clear();
                        String[] a = {"http://a.hiphotos.baidu.com/image/h%3D300/sign=58f3b1720ef79052f01f413e3cf2d738/caef76094b36acaf568a549d7bd98d1001e99c6d.jpg",
                                "http://img3.imgtn.bdimg.com/it/u=3086269874,568125913&fm=206&gp=0.jpg",
                                "KIKI", "MIMI", "123", "456"};
                        String[] b = {"http://img0.imgtn.bdimg.com/it/u=4191831375,138217075&fm=206&gp=0.jpg",
                                "http://img3.imgtn.bdimg.com/it/u=3841157212,2135341815&fm=206&gp=0.jpg",
                                "KOKO", "MIMI", "123", "456"};
                        String[] c = {"http://img0.imgtn.bdimg.com/it/u=1578511521,3006221653&fm=206&gp=0.jpg",
                                "http://img3.imgtn.bdimg.com/it/u=3841157212,2135341815&fm=206&gp=0.jpg",
                                "KOKO", "MIMI", "123", "456"};
                        String[] d = {"http://img5.imgtn.bdimg.com/it/u=1386927282,2586759857&fm=206&gp=0.jpg",
                                "http://h.hiphotos.baidu.com/image/h%3D300/sign=ece3e0add658ccbf04bcb33a29d8bcd4/aa18972bd40735fab9f007a699510fb30f2408a8.jpg",
                                "KOKO", "MEMI", "123", "456"};
                        String[] e = {"http://img0.imgtn.bdimg.com/it/u=78102664,4177054856&fm=11&gp=0.jpg",
                                "http://h.hiphotos.baidu.com/image/h%3D300/sign=ece3e0add658ccbf04bcb33a29d8bcd4/aa18972bd40735fab9f007a699510fb30f2408a8.jpg",
                                "KOKO", "MAMI", "123", "156"};
                        String[] f = {"http://img1.imgtn.bdimg.com/it/u=1052336373,862158976&fm=21&gp=0.jpg",
                                "http://h.hiphotos.baidu.com/image/h%3D300/sign=ece3e0add658ccbf04bcb33a29d8bcd4/aa18972bd40735fab9f007a699510fb30f2408a8.jpg",
                                "KOKI", "MOMI", "123", "456"};

                        Constant.IMAGE_URLS.add(a);
                        Constant.IMAGE_URLS.add(b);
                        Constant.IMAGE_URLS.add(c);
                        Constant.IMAGE_URLS.add(d);
                        Constant.IMAGE_URLS.add(e);
                        Constant.IMAGE_URLS.add(f);

                        Log.v(TAG, "Constant.IMAGE_URLS" + Constant.IMAGE_URLS);
                        myListFragment.onRefreshComplete();	//下拉刷新完成
                        //重新加载fragment
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.remove(myListFragment);
                        transaction.commit();
                        setDefaultFragment();
                        break;
                    case LOAD_DATA_FINISH:
                        String[] a1 = {"http://img0.imgtn.bdimg.com/it/u=3710103736,733712610&fm=21&gp=0.jpg",
                                "http://img3.imgtn.bdimg.com/it/u=3086269874,568125913&fm=206&gp=0.jpg",
                                "KIKI", "MIMI", "123", "456"};
                        String[] b1 = {"http://img1.imgtn.bdimg.com/it/u=1853916932,391038869&fm=21&gp=0.jpg",
                                "http://img3.imgtn.bdimg.com/it/u=3841157212,2135341815&fm=206&gp=0.jpg",
                                "KOKO", "MIMI", "123", "456"};
                        String[] c1 = {"http://img4.imgtn.bdimg.com/it/u=351229423,2450736669&fm=21&gp=0.jpg",
                                "http://img3.imgtn.bdimg.com/it/u=3841157212,2135341815&fm=206&gp=0.jpg",
                                "KOKO", "MIMI", "123", "456"};
                        String[] d1 = {"http://img1.imgtn.bdimg.com/it/u=1568149620,3143746205&fm=21&gp=0.jpg",
                                "http://h.hiphotos.baidu.com/image/h%3D300/sign=ece3e0add658ccbf04bcb33a29d8bcd4/aa18972bd40735fab9f007a699510fb30f2408a8.jpg",
                                "KOKO", "MEMI", "123", "456"};
                        String[] e1 = {"http://img0.imgtn.bdimg.com/it/u=78102664,4177054856&fm=11&gp=0.jpg",
                                "http://img4.imgtn.bdimg.com/it/u=4060590219,1674588155&fm=206&gp=0.jpg",
                                "KOKO", "MAMI", "123", "156"};
                        String[] f1 = {"http://img1.imgtn.bdimg.com/it/u=1052336373,862158976&fm=21&gp=0.jpg",
                                "http://h.hiphotos.baidu.com/image/h%3D300/sign=ece3e0add658ccbf04bcb33a29d8bcd4/aa18972bd40735fab9f007a699510fb30f2408a8.jpg",
                                "KOKI", "MOMI", "123", "456"};

                        Constant.IMAGE_URLS.add(a1);
                        Constant.IMAGE_URLS.add(b1);
                        Constant.IMAGE_URLS.add(c1);
                        Constant.IMAGE_URLS.add(d1);
                        Constant.IMAGE_URLS.add(e1);
                        Constant.IMAGE_URLS.add(f1);

                        myListFragment.getListView().setVisibility(View.GONE);
                        myListFragment.getMyListAdapter().notifyDataSetChanged();
                        myListFragment.getListView().setVisibility(View.VISIBLE);
                        myListFragment.onLoadMoreComplete();	//加载更多完成
                        break;
                    default:
                        break;
                }
        }
    };


    /**
     * MyListFragment的点击事件在这里回调处理
     */
    @Override
    public void onMyListFragmentClick(int i) {
        if(i == 0)
            Toast.makeText(this, "left", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "right", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(this, ExpressionDetailActivity.class));
    }
}
