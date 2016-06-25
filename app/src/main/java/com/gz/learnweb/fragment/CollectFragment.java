package com.gz.learnweb.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.gz.learnweb.Utils.VolleyUtils;
import com.gz.learnweb.activity.CourseActivity;
import com.gz.learnweb.listener.LeftHideShow;
import com.gz.learnweb.Adapter.CourseAdapter;
import com.gz.learnweb.Constant;
import com.gz.learnweb.R;
import com.gz.learnweb.Utils.LogUtil;
import com.gz.learnweb.entire.Course;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class CollectFragment extends ListFragment implements LeftHideShow {
    private AQuery aq;
    private List<Course> collectList;
    private CourseAdapter courseAdapter;
    private Gson gson;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aq = new AQuery(getActivity());
        gson = new GsonBuilder().disableHtmlEscaping().create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contextView = inflater.inflate(R.layout.fragment_collect, container, false);
        initListView();
        leftOn();
        return contextView;
    }


    private void initListView() {
        collectList = new ArrayList<>();
        courseAdapter = new CourseAdapter(getActivity(), collectList);
        setListAdapter(courseAdapter);
        getCollection();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.DataKey.COURSE, collectList.get(position));
        Intent intent = new Intent(getActivity(), CourseActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    //从服务器获取已收藏课程
    private void getCollection() {
        VolleyUtils.post(Constant.URL.GetCollection, null, new VolleyUtils.NetworkListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    List<Course> courses = gson.fromJson(jsonArray.toString(), new TypeToken<List<Course>>() {
                    }.getType());
                    collectList.clear();
                    collectList.addAll(courses);
                    courseAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String error) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getCollection();
    }

    @Override
    public void leftOff() {
        LogUtil.i("left", "collect off");
        if (aq == null) {
            aq = new AQuery(getActivity());
        }
        aq.id(R.id.btn_collect_course).background(R.color.white);//背景色
        aq.id(R.id.img_collect).image(R.drawable.collect_gray);//图标
        aq.id(R.id.text_collect).getTextView().setTextColor(getResources().getColor(R.color.grey));
    }

    @Override
    public void leftOn() {
        LogUtil.i("left", "collect on");
        if (aq == null) {
            aq = new AQuery(getActivity());
        }
        aq.id(R.id.btn_collect_course).background(R.color.light_grey);
        aq.id(R.id.img_collect).image(R.drawable.collect_red);
        aq.id(R.id.text_collect).getTextView().setTextColor(getResources().getColor(R.color.text_red));
        aq.id(R.id.title_mid_text).text("收藏课程");
    }
}