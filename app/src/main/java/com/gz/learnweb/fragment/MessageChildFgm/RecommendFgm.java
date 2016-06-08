package com.gz.learnweb.fragment.MessageChildFgm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.gz.learnweb.Adapter.CourseAdapter;
import com.gz.learnweb.Constant;
import com.gz.learnweb.Utils.VolleyUtils;
import com.gz.learnweb.activity.CourseActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ibm.gz.learnweb.R;
import com.gz.learnweb.entire.Course;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 推荐课程
 * Created by host on 2015/10/2.
 */
public class RecommendFgm extends Fragment {
    private View contextView;
    private CourseAdapter courseAdapter;
    private PullToRefreshListView listView;
    private List<Course> courseList;
    private Gson gson;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson=new GsonBuilder().disableHtmlEscaping().create();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contextView =inflater.inflate(R.layout.fragment_recommend,container,false);
        initListView();
        return contextView;
    }

    private void initListView() {
        listView=(PullToRefreshListView)contextView.findViewById(R.id.listview);
        requestNet();
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                requestNet();
            }
        });
        // 设置PullRefreshListView下拉加载时的加载提示
        listView.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新...");
        listView.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在刷新...");
        listView.getLoadingLayoutProxy(true, false).setReleaseLabel("松开刷新...");
    }

    //请求内部刷新
    private void requestRefresh(List<Course> courses){
        if(this.courseList==null){
            this.courseList=new ArrayList<>();
        }
        this.courseList.clear();
        this.courseList.addAll(courses);
        if(courseAdapter==null){//
            courseAdapter=new CourseAdapter(getActivity(),this.courseList);
            listView.setAdapter(courseAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constant.DataKey.COURSE, courseList.get(position));
                    Intent intent = new Intent(getActivity(), CourseActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }else {
            courseAdapter.notifyDataSetChanged();
        }
        listView.onRefreshComplete();
    }
    //请求网络数据（刷新）
    private void requestNet(){
        Map<String, String> param = new HashMap<>();
        param.put("type", "firstpagecourse");
        VolleyUtils.post(Constant.URL.RequestCourse,param, new VolleyUtils.NetworkListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    List<Course> courses = gson.fromJson(jsonArray.toString(), new TypeToken<List<Course>>() {
                    }.getType());
                    requestRefresh(courses);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String error) {
                listView.onRefreshComplete();
                Toast.makeText(getActivity(), "网络情况不佳，请稍后再试", Toast.LENGTH_SHORT).show();
            }
        });
    }
}