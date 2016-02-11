package com.example.groovemax1.uitest.tools;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

/**
 * 类名：RecordClass
 * 描述：录音
 * 时间：20160129
 */
public class RecordClass {
    private Context context;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private String fileName;

    public int recordState = 0;
    public int playState = 0;

    public RecordClass(Context context){
        this.context = context;

        //set the recordFilePath
        fileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        fileName += "/audioRecordTest.3gp";
    }

    public void startRecord(){
        recordState = 1;
        Log.v("debug","startRecord");
        if(mediaRecorder == null){
            Log.v("debug","setAudioSource");
            mediaRecorder = new MediaRecorder();
            //set the record source MICROPHONE
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            //封装格式为3gpp
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            //set the code method AMR_NB
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setOutputFile(fileName);
            try{
                mediaRecorder.prepare();
            }catch (IOException e){
                Toast.makeText(context, "mRecorder.prepare() fails", Toast.LENGTH_SHORT).show();
            }
        }

        try {
            mediaRecorder.start();       // Recording is now started
        } catch (IllegalStateException e){
            e.printStackTrace();
        }
    }

    public void stopRecord(){
        recordState = 0;
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
    }

    //setDataSource()&prepare()初始化调用
    public void startPlay(){
        Log.v("debug", "startPlay");
        playState = 1;
        if(mediaPlayer == null){
            Log.v("debug","mediaPlayer null");
            mediaPlayer = new MediaPlayer();
            try{
                mediaPlayer.setDataSource(fileName);
                mediaPlayer.prepare();
            }catch (IOException e){
                Toast.makeText(context, "mPlayer.setDataSource(fileName) fails", Toast.LENGTH_LONG).show();
            }
        }
        mediaPlayer.start();
    }

    public void stopPlay(){
        playState = 0;
        mediaPlayer.release();
        mediaPlayer = null;
    }

    public void reset(){
        Log.v("debug", "reset");
        playState = 0;
    }

    public int isMediaRecorder(){
        if(mediaRecorder == null)
            return 0;
        return 1;
    }

    public int isMediaPlayer(){
        if(mediaPlayer == null)
            return  0;
        return 1;
    }

}

