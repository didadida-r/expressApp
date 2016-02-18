package com.example.groovemax1.uitest.net;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * 文件名：
 * 描述：
 * 作者：
 * 时间：
 */
public class LoginHelper {

    public static String loginByPost(final String url, String userName, String userCode){
        /** 接收服务器数据 */
        String result = "";

        try{
            //只是建立tcp连接，没有发送http请求
            HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
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

        return result;
    }
}
