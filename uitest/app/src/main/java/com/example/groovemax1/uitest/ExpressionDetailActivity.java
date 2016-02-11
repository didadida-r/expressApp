package com.example.groovemax1.uitest;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.groovemax1.uitest.tools.Player;

/**
 * Created by GROOVEMAX1 on 2016/1/27.
 * 详细表白
 */
public class ExpressionDetailActivity extends Activity implements View.OnClickListener{
    private SeekBar seekBar;
    private ImageButton playBtn;
    private Player player;
    private int playFlag = -1;

    private RelativeLayout shareLayout;
    private RelativeLayout expressionlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expression_detail);
        initUi();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backBtn:
                player.stop();
                ExpressionDetailActivity.this.finish();
                break;
            case R.id.commentBtn:
                break;
            case R.id.storeBtn:
                break;
            case R.id.likeBtn:
                break;
            case R.id.shareBtn:
                showPopupWindow();
                break;
            //播放
            case R.id.playBtn:
                //第一次加载音乐，之后不再进入player.playUrl进行加载
                if(playFlag == -1){
                    String url= Environment.getExternalStorageDirectory().getAbsolutePath() + "/audioRecordTest.3gp";
                    player.playUrl(url);
                    playFlag++;
                }else {
                    playFlag = ++playFlag % 2;
                    if(playFlag == 1)
                        player.pause();
                    else
                        player.play();
                }
                break;
            default:
                break;
        }
    }

    private void initUi(){
        LayoutInflater inflater = LayoutInflater.from(this);
        expressionlayout = (RelativeLayout) inflater.inflate(R.layout.expression_detail, null);
        shareLayout = (RelativeLayout) findViewById(R.id.shareLayout);

        playBtn = (ImageButton) findViewById(R.id.playBtn);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        player = new Player(seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress;   //播放进度
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                this.progress = progress * player.mediaPlayer.getDuration()
                        / seekBar.getMax();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            // seekTo()的参数是相对于音频总时间的数字，而不是与seekBar.getMax()相对的数字
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                player.mediaPlayer.seekTo(progress);
            }
        });
    }

    //分享页面
    private void showPopupWindow(){
        View content = LayoutInflater.from(this).inflate(R.layout.share, null);
        View view = findViewById(R.id.titleLayout);

        final PopupWindow popupWindow = new PopupWindow(content,
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(getApplication(), "popupwindow", Toast.LENGTH_SHORT).show();
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_launcher));
        popupWindow.showAsDropDown(view);
    }

}
