package com.example.groovemax1.uitest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Comment;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by GROOVEMAX1 on 2016/1/27.
 * 我的
 */

public class mineActivity extends Activity implements View.OnClickListener{
    private static final int NONE = 0;
    private static final int PHOTO_ZOOM = 2; // 缩放
    private static final int PHOTO_RESULT = 3;// 结果
    private static final String IMAGE_UNSPECIFIED = "image/*";

    private ImageView image;
    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine);
        initUi();
    }

    private void initUi(){
        image = (ImageView) findViewById(R.id.image);
        name = (TextView) findViewById(R.id.name);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //待定：用什么实现后面的页面
            case R.id.backBtn:
                mineActivity.this.finish();
                startActivity(new Intent(mineActivity.this, HomeActivity.class));
                break;
            case R.id.seenBtn:
                break;
            case R.id.commentedBtn:
                break;
            case R.id.storedBtn:
                break;
            case R.id.publishedBtn:
                break;
            case R.id.image:
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
                startActivityForResult(intent, PHOTO_ZOOM);Toast.makeText(this, "this", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //没有选择照片，返回
        if (resultCode == NONE)
            return;
        if (data == null)
            return;
        // 读取相册缩放图片
        if (requestCode == PHOTO_ZOOM) {
            startPhotoZoom(data.getData());
        }
        // 处理结果
        if (requestCode == PHOTO_RESULT) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0-100)压缩文件
                //此处可以把Bitmap保存到sd卡中，具体请看：http://www.cnblogs.com/linjiqin/archive/2011/12/28/2304940.html
                image.setImageBitmap(photo); //把图片显示在ImageView控件上
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");//调用Android系统自带的一个图片剪裁页面,
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");//进行修剪
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 100);
        intent.putExtra("outputY", 100);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_RESULT);
    }
}
