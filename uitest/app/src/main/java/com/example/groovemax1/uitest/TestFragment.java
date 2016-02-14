package com.example.groovemax1.uitest;

import android.app.ListFragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groovemax1.uitest.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;

/**
 * 文件名：
 * 描述：
 * 作者：
 * 时间：
 */
public class TestFragment extends ListFragment {

    //建立fragment视图
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.listview, container, false);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageListAdapter imageListAdapter = new ImageListAdapter(getActivity());
        this.setListAdapter(imageListAdapter);
    }

    private static class ImageListAdapter extends BaseAdapter{

        private LayoutInflater inflater;
        private Context context;

        private static final String[] IMAGE_URLS = {"http://img3.imgtn.bdimg.com/it/u=3086269874,568125913&fm=206&gp=0.jpg"
        ,"http://img3.imgtn.bdimg.com/it/u=3086269874,568125913&fm=206&gp=0.jpg"
        ,"http://img3.imgtn.bdimg.com/it/u=3086269874,568125913&fm=206&gp=0.jpg"
        ,"http://img3.imgtn.bdimg.com/it/u=3841157212,2135341815&fm=206&gp=0.jpg"};
        private DisplayImageOptions options;

        ImageListAdapter(Context context){
            this.context = context;
            inflater = LayoutInflater.from(context);

            options = new DisplayImageOptions.Builder().showImageOnLoading(R.mipmap.ic_stub)
                    .showImageForEmptyUri(R.mipmap.ic_empty)
                    .showImageOnFail(R.mipmap.ic_error)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .build();
        }

        @Override
        public int getCount() {
            return IMAGE_URLS.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            final ViewHolder holder;
            if (convertView == null) {
                view = inflater.inflate(R.layout.test_layout, parent, false);
                holder = new ViewHolder();
                holder.text = (TextView) view.findViewById(R.id.testTv);
                holder.image = (ImageView) view.findViewById(R.id.testIv);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            holder.text.setText("Item " + (position + 1));
            //holder.image.setImageResource(R.drawable.ic_launcher);

            ImageLoader.getInstance().displayImage(IMAGE_URLS[position], holder.image, options);

            return view;
        }

    }
    static class ViewHolder {
        TextView text;
        ImageView image;
    }
}
