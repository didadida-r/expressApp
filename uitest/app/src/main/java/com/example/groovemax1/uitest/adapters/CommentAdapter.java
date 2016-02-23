package com.example.groovemax1.uitest.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groovemax1.uitest.ExpressionDetailActivity;
import com.example.groovemax1.uitest.R;

import java.util.List;
import java.util.Map;

/**
 * 文件名：
 * 描述：
 * 作者：
 * 时间：
 * bug:增大likeBtn的点击范围，可以考虑直接嵌套一个layout
 */
public class CommentAdapter extends BaseAdapter {

    private List<Map<String,Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;
    private RelativeLayout commLayout;
    private RelativeLayout likeLayout;
    private ImageView commImage;
    private TextView commName;
    private TextView replyTv;
    private TextView commReplyName;
    private TextView commText;
    private ImageButton likeBtn;
    private TextView likeNum;

    public CommentAdapter(Context context, List<Map<String,Object>> data){
        this.context =context;
        this.data = data;
        this.layoutInflater = layoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //ListView根据convertView来绘制
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.expression_detail_comment_item, null);
            commImage = (ImageView) convertView.findViewById(R.id.commImage);
            commName = (TextView) convertView.findViewById(R.id.commName);
            replyTv = (TextView) convertView.findViewById(R.id.replyTv);
            commReplyName = (TextView) convertView.findViewById(R.id.commReplyName);
            commText = (TextView) convertView.findViewById(R.id.commText);
            likeBtn = (ImageButton) convertView.findViewById(R.id.likeBtn);
            likeNum = (TextView) convertView.findViewById(R.id.likeNum);
            likeLayout = (RelativeLayout) convertView.findViewById(R.id.likeLayout);

        }
        //绑定数据
        commImage.setBackgroundResource((Integer) data.get(position).get("commImage"));
        commName.setText((String) data.get(position).get("commName"));
        commText.setText((String) data.get(position).get("commText"));
        likeBtn.setBackgroundResource((Integer) data.get(position).get("likeBtn"));
        likeNum.setText((String) data.get(position).get("likeNum"));

        if(!data.get(position).get("commReplyName").equals("")){
            replyTv.setVisibility(View.VISIBLE);
            commReplyName.setVisibility(View.VISIBLE);
            commReplyName.setText((String) data.get(position).get("commReplyName"));
        }

        CommOnClickListener commOnClickListener = new CommOnClickListener(position, likeNum, context);
        likeLayout.setOnClickListener(commOnClickListener);
        return convertView;
    }

    private class CommOnClickListener implements View.OnClickListener{
        private int position;
        private Context context;
        private TextView likeNum;
        private int likeFlag = 0;

        public CommOnClickListener(int position, View likeNum, Context context){
            this.position = position;
            this.context = context;
            this.likeNum = (TextView) likeNum;
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
            likeFlag = (++likeFlag)%2;
            int i = Integer.parseInt(likeNum.getText().toString());
            if(likeFlag == 1)
                i++;
            else
                i--;
            likeNum.setText(""+i);
        }
    }

}
