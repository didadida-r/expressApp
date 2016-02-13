package com.example.groovemax1.uitest;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.groovemax1.uitest.tools.CommentAdapter;
import com.example.groovemax1.uitest.tools.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private ListView commList = null;
    private Handler handler;
    private Message message;
    private View content;
    private PopupWindow popupWindow;
    private ColorDrawable background;
    private List<Map<String, Object>> list = null;

    private EditText editCommEt;

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
            case R.id.storeBtn:
                break;
            case R.id.likeBtn:
                break;
            case R.id.commentBtn:
            case R.id.shareBtn:
                showPopupWindow(v);
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
            case R.id.weixingIv:
                message = new Message();
                message.obj = "comment";
                message.what = 0;
                handler.sendMessage(message);
                popupWindow.dismiss();
                break;
            case R.id.getCommBtn:
                message = new Message();
                message.obj = editCommEt.getText().toString();
                message.what = 1;
                handler.sendMessage(message);
                popupWindow.dismiss();
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

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what == 0)
                    Toast.makeText(getApplication(), ""+msg.obj.toString(), Toast.LENGTH_SHORT).show();
                if(msg.what == 1){
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("commImage", R.drawable.ic_launcher);
                    map.put("commName", "Kiki");
                    map.put("commText", msg.obj.toString());
                    map.put("likeBtn", R.mipmap.expression_comment_like_ib);
                    map.put("likeNum", "12345");
                    list.add(map);
                    commList.setAdapter(new CommentAdapter(getApplication(), list));
                    setListViewHeightBasedOnChildren(commList);
                }
            }
        };

        //转载评论数据
        commList = (ListView) findViewById(R.id.commList);
        List<Map<String, Object>> list = getData();
        commList.setAdapter(new CommentAdapter(this, list));
        setListViewHeightBasedOnChildren(commList);
        commList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    //跳转框（分享、评论）
    private void showPopupWindow(View v){
        switch (v.getId()){
            case R.id.shareBtn:
                content = LayoutInflater.from(this).inflate(R.layout.popupwindow_share, null);
                background = new ColorDrawable(0x80f3f3f3);

                popupWindow = new PopupWindow(content,
                        ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
                popupWindow.setTouchable(true);

                // 这里如果返回true的话，touch事件将被拦截
                // popUpwWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
                popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        int height = content.findViewById(R.id.shareLayout).getTop();
                        int y = (int) event.getY();
                        if (y < height)
                            popupWindow.dismiss();
                        return false;
                    }
                });
                break;
            case R.id.commentBtn:
                content = LayoutInflater.from(this).inflate(R.layout.popupwindow_comment, null);
                editCommEt = (EditText) content.findViewById(R.id.editCommEt);
                background = new ColorDrawable(0x01000000);

                popupWindow = new PopupWindow(content,
                        ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
                popupWindow.setTouchable(true);
                popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        int height = content.findViewById(R.id.commLayout).getTop();
                        int y = (int) event.getY();
                        if (y < height)
                            popupWindow.dismiss();
                        return false;
                    }
                });
                break;
            default:
                break;
        }
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(background);

        //将popUpwWindow置于titleLayout之下
        View view = findViewById(R.id.titleLayout);
        popupWindow.showAsDropDown(view);
    }

    private List<Map<String, Object>> getData(){
        list = new ArrayList<Map<String, Object>>();
        for(int i = 0;i<10;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("commImage",R.drawable.ic_launcher);
            map.put("commName","Coco");
            map.put("commText","很喜欢这篇文字"+i);
            map.put("likeBtn",R.mipmap.expression_comment_like_ib);
            map.put("likeNum","12345");
            list.add(map);
        }

        return list;
    }

    //重新绘制commList
    private void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
