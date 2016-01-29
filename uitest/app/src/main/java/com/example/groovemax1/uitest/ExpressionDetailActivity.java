package com.example.groovemax1.uitest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.example.groovemax1.uitest.tools.Player;

/**
 * Created by GROOVEMAX1 on 2016/1/27.
 * 详细表白
 */
public class ExpressionDetailActivity extends Activity implements View.OnClickListener{
    private Button playBtn;
    private SeekBar seekBar;
    private Player player;
    private int playFlag = -1;

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
                startActivity(new Intent(ExpressionDetailActivity.this, HomeActivity.class));
                break;
            case R.id.commentBtn:
                break;
            case R.id.storeBtn:
                break;
            case R.id.likeBtn:
                break;
            case R.id.shareBtn:
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
                    if(playFlag == 1){
                        player.pause();
                        playBtn.setText("play");
                    }else {
                        player.play();
                        playBtn.setText("pause");
                    }
                }
                break;
            default:
                break;
        }
    }

    private void initUi(){
        playBtn = (Button) findViewById(R.id.playBtn);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        player = new Player(seekBar);
        playBtn.setWidth(8);
        playBtn.setHeight(8);

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
}
