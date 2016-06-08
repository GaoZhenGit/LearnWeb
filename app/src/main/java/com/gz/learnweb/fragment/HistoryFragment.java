package com.gz.learnweb.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.gz.learnweb.Adapter.CourseAdapter;
import com.gz.learnweb.Constant;
import com.gz.learnweb.Utils.LogUtil;
import com.gz.learnweb.Utils.VolleyUtils;
import com.gz.learnweb.activity.CourseActivity;
import com.gz.learnweb.listener.LeftHideShow;
import com.ibm.gz.learnweb.R;
import com.gz.learnweb.entire.Course;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * show all the history of coureses have played
 * Created by host on 2015/8/9.
 */
public class HistoryFragment extends Fragment implements LeftHideShow {
    private AQuery aq;
    private List<Course> courseList;
    private ListView listView;
    private CourseAdapter courseAdapter;

    private Gson gson;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aq=new AQuery(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contextView =inflater.inflate(R.layout.fragment_history,container,false);
        listView=(ListView)contextView.findViewById(R.id.listview);
        initListView();
        setListener();
        leftOn();
        return contextView;
    }

    private void initListView() {
        courseList = new ArrayList<>();
        courseAdapter = new CourseAdapter(getActivity(),courseList);
        listView.setAdapter(courseAdapter);
        gson = new GsonBuilder().disableHtmlEscaping().create();

    }
    private void setListener(){
        //点击事件和长按事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onListItemClick(null,null,position,id);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                String[] items = {"删除"};
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteHistory(courseList.get(position).getCourse_id());
                        findHistory();
                    }
                });
                builder.create().show();
                return true;
            }
        });
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        Bundle bundle=new Bundle();
        bundle.putSerializable(Constant.DataKey.COURSE, courseList.get(position));
        Intent intent=new Intent(getActivity(), CourseActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void findHistory(){
        VolleyUtils.post(Constant.URL.GetHistory, null, new VolleyUtils.NetworkListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    List<Course> courses =gson.fromJson(jsonArray.toString(),new TypeToken<List<Course>>() {
                    }.getType());
                    courseList.clear();
                    courseList.addAll(courses);
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

    private void deleteHistory(int id){
        Map<String ,String> param =new HashMap<>();
        param.put("course_id", id + "");
        VolleyUtils.post(Constant.URL.DeleteHistory, param, new VolleyUtils.NetworkListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String state = jsonObject.optString("state");
                    if(state.equals("success")){
                        Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                        findHistory();
                    } else {
                        throw new JSONException("");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "删除失败，请稍后再试", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFail(String error) {
                Toast.makeText(getActivity(), "网络情况不佳，请稍后再试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        findHistory();
    }










    @Override
    public void leftOff(){
        LogUtil.i("left", "histroy off");
        if(aq==null){
            aq=new AQuery(getActivity());
        }
        aq.id(R.id.btn_history).background(R.color.white);//背景色
        aq.id(R.id.img_history).image(R.drawable.lesson_gray);//图标
        aq.id(R.id.text_history).getTextView().setTextColor(getResources().getColor(R.color.grey));
    }
    @Override
    public void leftOn(){
        LogUtil.i("left", "histroy on");
        aq.id(R.id.btn_history).background(R.color.light_grey);
        aq.id(R.id.img_history).image(R.drawable.lesson_red);
        aq.id(R.id.text_history).getTextView().setTextColor(getResources().getColor(R.color.text_red));
        aq.id(R.id.title_mid_text).text("历史课程");
    }
}
