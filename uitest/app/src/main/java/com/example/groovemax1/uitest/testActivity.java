package com.example.groovemax1.uitest;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;

/**
 * 文件名：
 * 描述：
 * 作者：
 * 时间：
 */
public class testActivity extends Activity {

    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_actiyity_layout);
        setDefaultFragment();
        //test();

    }

    private void setDefaultFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        TestFragment testFragment = new TestFragment();
        transaction.replace(R.id.msgFrameContain, testFragment);
        transaction.commit();
    }

    private void test(){
        imageView = (ImageView) findViewById(R.id.changeIv);
        DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(R.mipmap.ic_stub)
                .showImageForEmptyUri(R.mipmap.ic_empty)
                .showImageOnFail(R.mipmap.ic_error)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)       // 设置图片的解码类型,默认值——Bitmap.Config.ARGB_8888
                .build();

        ImageLoader.getInstance().displayImage("http://img3.imgtn.bdimg.com/it/u=3086269874,568125913&fm=206&gp=0.jpg", imageView, options);
    }
}
