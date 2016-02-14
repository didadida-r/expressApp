package com.example.groovemax1.uitest;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by GROOVEMAX1 on 2016/1/26.
 */
public class MsgListFragment extends ListFragment {
    public static final String ARGUMENT_STATE = "argument_state";

    private String state;
    private int listLayout;
    private String[] from;
    private int[] to;

    //建立fragment视图
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.listview, container, false);
    }

    //建立fragment对象
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //接收数据
        Bundle bundle = getArguments();
        if(bundle != null){
            state = bundle.getString(ARGUMENT_STATE);
            switch (state){
                case "msgUserFragment":
                    listLayout = R.layout.msg_listview_user_item;
                    from = new String[]{"title", "message"};
                    to = new int[]{R.id.titleTv, R.id.contentTv};
                    break;
                case "msgSysFragment":
                    listLayout = R.layout.msg_listview_sys_item;
                    from = new String[]{"title", "message"};
                    to = new int[]{R.id.titleTv, R.id.contentTv};
                    break;
                default:
                    break;
            }
        }
        // 建立SimpleAdapter，将from和to对应起来
        SimpleAdapter adapter = new SimpleAdapter(
                this.getActivity(), getSimpleData(),
                listLayout, from, to);
        this.setListAdapter(adapter);
    }

    //点击显示详细评论
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Toast.makeText(getActivity(),
                "You have selected " + position,
                Toast.LENGTH_SHORT).show();
    }

    //获取显示评论的数据
    private List<Map<String, Object>> getSimpleData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for(int i = 0;i<10;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title","sys"+i);
            map.put("message","message"+i);
            list.add(map);
        }
        return list;
    }

}
