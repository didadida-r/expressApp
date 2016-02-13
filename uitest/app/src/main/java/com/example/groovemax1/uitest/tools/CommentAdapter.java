package com.example.groovemax1.uitest.tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.groovemax1.uitest.R;

import java.util.List;
import java.util.Map;

/**
 * 文件名：
 * 描述：
 * 作者：
 * 时间：
 */
public class CommentAdapter extends BaseAdapter {

    private List<Map<String,Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;
    private ImageView commImage;
    private TextView commName;
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
            convertView = layoutInflater.inflate(R.layout.expression_detail_comment_sample, null);
            commImage = (ImageView) convertView.findViewById(R.id.commImage);
            commName = (TextView) convertView.findViewById(R.id.commName);
            commText = (TextView) convertView.findViewById(R.id.commText);
            likeBtn = (ImageButton) convertView.findViewById(R.id.likeBtn);
            likeNum = (TextView) convertView.findViewById(R.id.likeNum);
        }
        //绑定数据
        commImage.setBackgroundResource((Integer) data.get(position).get("commImage"));
        commName.setText((String) data.get(position).get("commName"));
        commText.setText((String) data.get(position).get("commText"));
        likeBtn.setBackgroundResource((Integer) data.get(position).get("likeBtn"));
        likeNum.setText((String) data.get(position).get("likeNum"));
        return convertView;
    }

}
