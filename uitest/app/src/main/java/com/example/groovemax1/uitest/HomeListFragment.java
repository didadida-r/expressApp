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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

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

        HomeListAdapter homeListAdapter = new HomeListAdapter(getActivity());
        this.setListAdapter(homeListAdapter);
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

    /*
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
     */

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

    private static class HomeListAdapter extends BaseAdapter {

        private LayoutInflater inflater;
        private Context context;

        private static final String[] IMAGE_URLS = {"http://img3.imgtn.bdimg.com/it/u=3086269874,568125913&fm=206&gp=0.jpg"
                ,"http://a.hiphotos.baidu.com/image/h%3D200/sign=febfa19b4ded2e73e3e9812cb700a16d/f7246b600c338744233d6163560fd9f9d72aa031.jpg"
                ,"http://e.hiphotos.baidu.com/image/h%3D200/sign=55d8a67b514e9258b93481eeac83d1d1/b7fd5266d0160924f7e3ec04d30735fae7cd34ee.jpg"
                ,"http://img3.imgtn.bdimg.com/it/u=3841157212,2135341815&fm=206&gp=0.jpg"
                ,"http://h.hiphotos.baidu.com/image/h%3D300/sign=ece3e0add658ccbf04bcb33a29d8bcd4/aa18972bd40735fab9f007a699510fb30f2408a8.jpg"
                ,"http://h.hiphotos.baidu.com/image/h%3D300/sign=2de3af29758b4710d12ffbccf3cec3b2/b64543a98226cffc8955e8babe014a90f603eabf.jpg"};
        private DisplayImageOptions options;

        HomeListAdapter(Context context){
            this.context = context;
            inflater = LayoutInflater.from(context);

            options = new DisplayImageOptions.Builder().showImageOnLoading(R.mipmap.ic_stub)
                    .showImageForEmptyUri(R.mipmap.ic_empty)
                    .showImageOnFail(R.mipmap.ic_error)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .build();
        }

        @Override
        public int getCount() {
            return IMAGE_URLS.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            final ViewHolder holder;
            if (convertView == null) {
                view = inflater.inflate(R.layout.home_listview_item, parent, false);
                holder = new ViewHolder();
                holder.nameLeft = (TextView) view.findViewById(R.id.nameLeft);
                holder.nameRight = (TextView) view.findViewById(R.id.nameRight);
                holder.imageLeft = (ImageView) view.findViewById(R.id.imageLeft);
                holder.imageRight = (ImageView) view.findViewById(R.id.imageRight);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            holder.nameLeft.setText("表白" + position );
            holder.nameRight.setText("表白" + position );
            ImageLoader.getInstance().displayImage(IMAGE_URLS[position], holder.imageLeft, options);
            ImageLoader.getInstance().displayImage(IMAGE_URLS[position], holder.imageRight, options);

            return view;
        }

    }
    static class ViewHolder {
        TextView nameLeft;
        TextView nameRight;
        ImageView imageLeft;
        ImageView imageRight;
    }

}
