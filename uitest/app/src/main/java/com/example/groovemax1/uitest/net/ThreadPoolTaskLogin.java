package com.example.groovemax1.uitest.net;

import android.util.Log;

import com.example.groovemax1.uitest.Constant;

/**
 * 文件名：
 * 描述：
 * 作者：
 * 时间：
 */
public class ThreadPoolTaskLogin extends ThreadPoolTask {
    private static final String TAG = "ThreadPoolTaskBitmap";

    private String userName;
    private String userCode;
    private String result;
    private int type;

    private CallBack callBack;

    public ThreadPoolTaskLogin(String url, int type, String userName, String userCode, CallBack callBack) {
        super(url);
        this.callBack = callBack;
        this.userName = userName;
        this.userCode = userCode;
        this.type = type;
    }

    @Override
    public void run() {
        //降低优先级
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_LOWEST);

        //result = LoginHelper.loginByPost(url, type, userName, userCode);
        if(type == 0)
            result = "0";
        else{
            result = "0";

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

        Log.i(TAG, "loaded: " + url);

        if(callBack != null){
            callBack.onReady(result);
        }

    }

    public interface CallBack {
        void onReady(String result);
    }

}
