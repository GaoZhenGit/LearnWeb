package com.gz.learnweb.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.androidquery.AQuery;
import com.ibm.gz.learnweb.R;
import com.gz.learnweb.entire.Course;

import java.util.List;

/**
 * Created by host on 2015/8/9.
 */
public class CourseAdapter extends BaseAdapter {

    private Context context;
    private List<Course> videoList;
    private LayoutInflater listContainer;
    private AQuery aq;

    public CourseAdapter(Context context,List<Course> items){
        videoList=items;
        this.context=context;
        listContainer=LayoutInflater.from(context);
        aq=new AQuery(context);
    }

    @Override
    public int getCount() {
        return videoList.size();
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
        if(convertView==null) {
            convertView = listContainer.inflate(R.layout.course_adapter, null);
        }
        aq=new AQuery(convertView);
        Course course=videoList.get(position);
        aq.id(R.id.img_video).image(course.getCourse_img());
        aq.id(R.id.video_name_tv).text(course.getCourse_name());
        aq.id(R.id.video_detail_tv).text(course.getDetail());
        return convertView;
    }

    public void refresh(){
        this.notifyDataSetChanged();
    }
}
