package com.example.groovemax1.uitest.AppContext;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * 文件名：
 * 描述：
 * 作者：
 * 时间：
 */
public class MyApplication extends Application {

    private static MyApplication myAppliction = null;
    private static DisplayImageOptions options;
    private boolean isLogin;

    public static  MyApplication getMyAppliction(){
        return myAppliction;
    }

    public boolean isLogin(){
        return isLogin;
    }

    public void setIsLogin(boolean a){
        isLogin = a;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myAppliction = this;

        initImageLoader(getApplicationContext());
    }

    public static void initImageLoader(Context context){
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, "imageloader/Cache");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()      //设置拒绝缓存在内存中一个图片多个大小 默认为允许
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO)     // 设置图片加载和显示队列处理的类型
                .writeDebugLogs()
                .build();

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }
}
