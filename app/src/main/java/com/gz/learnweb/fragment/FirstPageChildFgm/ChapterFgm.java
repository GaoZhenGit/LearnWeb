package com.gz.learnweb.fragment.FirstPageChildFgm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gz.learnweb.R;
import com.gz.learnweb.Utils.LogUtil;
import com.gz.learnweb.entire.Course;
import com.gz.learnweb.entire.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by host on 2015/8/18.
 */
public class ChapterFgm extends Fragment {
    private Course course;
    private List<Video> videoList;

    private View convertview;
    private ListView listView;
    private ChapterAdapter chapterAdapter;

    public void setVideoList(List<Video> list){
        this.videoList = list;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        convertview = inflater.inflate(R.layout.fragment_charpter, container, false);
        initData();
        initView();
        return convertview;
    }

    private void initData() {
        if (videoList == null){
            videoList = new ArrayList<>();
        }
        listView = (ListView) convertview.findViewById(R.id.charpter_list);
        chapterAdapter = new ChapterAdapter();
        listView.setAdapter(chapterAdapter);
        listView.setDivider(null);
    }

    private void initView() {

    }

    public class ChapterAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return videoList == null ? 0 : videoList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapter_chapter, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.order = (TextView) convertView.findViewById(R.id.order);
                viewHolder.name = (TextView) convertView.findViewById(R.id.name);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (viewHolder == null){
                LogUtil.i("viewHolder");
            }
            viewHolder.order.setText(videoList.get(position).getOrder()+"");
            viewHolder.name.setText(videoList.get(position).getName()+"");
            return convertView;
        }

        public class ViewHolder {
            TextView order;
            TextView name;
        }
    }
}
