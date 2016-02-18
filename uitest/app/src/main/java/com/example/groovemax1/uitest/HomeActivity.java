package com.example.groovemax1.uitest;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.groovemax1.uitest.view.MyDialog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by GROOVEMAX1 on 2016/1/27.
 * 主页
 */

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, HomeListFragment.HomeListFragmentClickListener{

    private HomeListFragment homeListFragment;
    private ImageView homeIv;

    private static final String PREFERENCE_NAME = "saveInfo";   //文件名称
    private static final int MODE = MODE_PRIVATE;         //操作模式
    private static final String USER_NAME = "USER_NAME";
    private static final String USER_CODE = "USER_CODE";
    private static final String LOGIN_STATE = "LOGIN_STATE";

    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction transaction = fragmentManager.beginTransaction();

   // private int mCount = 10;//加载数据条数
    private static final int LOAD_DATA_FINISH = 10;//上拉刷新
    private static final int REFRESH_DATA_FINISH = 11;//下拉刷新

    private static final String TAG = "debug";

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

        String[] a = {"http://img3.imgtn.bdimg.com/it/u=3086269874,568125913&fm=206&gp=0.jpg",
                "http://img3.imgtn.bdimg.com/it/u=3086269874,568125913&fm=206&gp=0.jpg",
                "KIKI", "MIMI", "123", "456"};
        String[] b = {"http://e.hiphotos.baidu.com/image/h%3D200/sign=55d8a67b514e9258b93481eeac83d1d1/b7fd5266d0160924f7e3ec04d30735fae7cd34ee.jpg",
                "http://img3.imgtn.bdimg.com/it/u=3841157212,2135341815&fm=206&gp=0.jpg",
                "KOKO", "MIMI", "123", "456"};
        String[] c = {"http://img3.imgtn.bdimg.com/it/u=3841157212,2135341815&fm=206&gp=0.jpg",
                "http://img3.imgtn.bdimg.com/it/u=3841157212,2135341815&fm=206&gp=0.jpg",
                "KOKO", "MIMI", "123", "456"};
        String[] d = {"http://e.hiphotos.baidu.com/image/h%3D200/sign=55d8a67b514e9258b93481eeac83d1d1/b7fd5266d0160924f7e3ec04d30735fae7cd34ee.jpg",
                "http://h.hiphotos.baidu.com/image/h%3D300/sign=ece3e0add658ccbf04bcb33a29d8bcd4/aa18972bd40735fab9f007a699510fb30f2408a8.jpg",
                "KOKO", "MEMI", "123", "456"};
        String[] e = {"http://e.hiphotos.baidu.com/image/h%3D200/sign=55d8a67b514e9258b93481eeac83d1d1/b7fd5266d0160924f7e3ec04d30735fae7cd34ee.jpg",
                "http://h.hiphotos.baidu.com/image/h%3D300/sign=ece3e0add658ccbf04bcb33a29d8bcd4/aa18972bd40735fab9f007a699510fb30f2408a8.jpg",
                "KOKO", "MAMI", "123", "156"};
        String[] f = {"http://e.hiphotos.baidu.com/image/h%3D200/sign=55d8a67b514e9258b93481eeac83d1d1/b7fd5266d0160924f7e3ec04d30735fae7cd34ee.jpg",
                "http://h.hiphotos.baidu.com/image/h%3D300/sign=ece3e0add658ccbf04bcb33a29d8bcd4/aa18972bd40735fab9f007a699510fb30f2408a8.jpg",
                "KOKI", "MOMI", "123", "456"};

        Constant.IMAGE_URLS.add(a);
        Constant.IMAGE_URLS.add(b);
        Constant.IMAGE_URLS.add(c);
        Constant.IMAGE_URLS.add(d);
        Constant.IMAGE_URLS.add(e);
        Constant.IMAGE_URLS.add(f);
    }

    //初始化fragment
    private void setDefaultFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        homeListFragment = new HomeListFragment();

        //------------------------------
        homeListFragment.setOnRefreshListener(new HomeListFragment.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 下拉刷新
                Log.e(TAG, "onRefresh");
                loadData(0);
            }
        });
        homeListFragment.setOnLoadListener(new HomeListFragment.OnLoadMoreListener() {

            @Override
            public void onLoadMore() {
                //  加载更多
                Log.e(TAG, "onLoad");
                loadData(1);
            }
        });
//        //关闭下拉刷新
//        homeListFragment.setCanRefresh(!homeListFragment.isCanRefresh());
//        //关闭上拉刷新
//        homeListFragment.setCanLoadMore(!homeListFragment.isCanLoadMore());
        //------------------------------
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
                SharedPreferences sp=this.getPreferences(MODE_PRIVATE);
                if(sp.getBoolean(LOGIN_STATE, false)){
                    loginByPost(sp.getString(USER_NAME, "error"), sp.getString(USER_CODE, "error"));
                }
                else
                    startActivity(new Intent(this, LoginActivity.class));
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
                    myHandler.sendEmptyMessage(REFRESH_DATA_FINISH);
                }else if(type==1){//上拉刷新
                    //通知Handler
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
                    homeListFragment.onRefreshComplete();	//下拉刷新完成
                    //重新加载fragment
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.remove(homeListFragment);
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

                    homeListFragment.getListView().setVisibility(View.GONE);
                    homeListFragment.getHomeListAdapter().notifyDataSetChanged();
                    homeListFragment.getListView().setVisibility(View.VISIBLE);
                    homeListFragment.onLoadMoreComplete();	//加载更多完成
                    break;
                default:
                    break;
            }
        }

    };

}
