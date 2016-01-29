package com.example.groovemax1.uitest;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by GROOVEMAX1 on 2016/1/26.
 */

public class HomeListFragment extends ListFragment{

    private HomeActivity.HomeTouchListener homeTouchListener;
    private int i;
    public interface HomeListFragmentClickListener{
        void onHomeListFragmentClick(int i);
    }

    //建立fragment视图
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_listview, container, false);
    }

    //建立fragment对象,并绑定数据适配器
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String[] from = new String[]{"image1", "text1", "image2", "text2"};
        final int[] to = new int[]{R.id.imageLeft, R.id.textLeft,
                R.id.imageRight, R.id.textRight};
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), getData(),
                R.layout.home_listview_sample1, from, to);
        this.setListAdapter(simpleAdapter);
        register();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //fragment与activity通信
        //如果宿主activity实现了该接口,则调用相应的接口函数
        if(getActivity() instanceof HomeListFragmentClickListener){
            ((HomeListFragmentClickListener) getActivity()).onHomeListFragmentClick(i);
        }
    }

    //获取数据
    private List<Map<String, Object>> getData(){
        List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
        for(int i = 0;i<10;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image1", R.drawable.ic_launcher);
            map.put("text1", "表白"+i);
            map.put("image2", R.drawable.ic_launcher);
            map.put("text2", "表白" + i);
            list.add(map);
        }
        return list;
    }

    //获取屏幕的宽度
    private int getScreenWidth(Context context){
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public void register(){
        homeTouchListener = new HomeActivity.HomeTouchListener() {
            @Override
            public void onTouchEvent(MotionEvent event) {
                if(event.getX() < getScreenWidth(getActivity())/2)
                    i = 0;
                else
                    i = 1;
            }
        };

        //在该Fragment的构造函数中注册mTouchListener的回调
        ((HomeActivity)this.getActivity()).registerHomeTouchListener(homeTouchListener);
    }

}
