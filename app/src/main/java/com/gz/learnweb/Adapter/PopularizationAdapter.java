package com.gz.learnweb.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.androidquery.AQuery;
import com.ibm.gz.learnweb.R;
import com.gz.learnweb.entire.PopularizationCourse;

import java.util.List;

/**
 * Created by gz on 15/9/12.
 */
public class PopularizationAdapter extends BaseAdapter {
    private AQuery aq;
    private Context context;
    private List<PopularizationCourse> courses;
    private LayoutInflater inflater;
    public PopularizationAdapter(Context context, List<PopularizationCourse> courses){
        this.context = context;
        this.courses=courses;
        this.inflater=LayoutInflater.from(context);
    }
    public void setCourses(List<PopularizationCourse> courses){
        this.courses=courses;
    }
    @Override
    public int getCount() {
        return courses.size();
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
        PopularizationCourse course=courses.get(position);
        if(convertView==null) {
            convertView = this.inflater.inflate(R.layout.adapter_popular, parent, false);
//            ImageView imageView=(ImageView)convertView.findViewById(R.id.popularization_image);
//            ViewGroup.LayoutParams params = imageView.getLayoutParams();
//            params.width= DensityUtil.dip2px(context,320);
//            params.height=params.width/4*3;
        }
        aq=new AQuery(convertView);
        aq.id(R.id.popularization_image).image(course.getPageImage());
        aq.id(R.id.popularization_title).text(course.getTitle());
        aq.id(R.id.popularization_detail).text(course.getDetail());
        return convertView;
    }
}
