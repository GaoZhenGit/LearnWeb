package com.gz.learnweb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.gz.learnweb.Adapter.CourseAdapter;
import com.gz.learnweb.Constant;
import com.gz.learnweb.Utils.LogUtil;
import com.gz.learnweb.Utils.VolleyUtils;
import com.gz.learnweb.R;
import com.gz.learnweb.entire.Course;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * the search course activity
 * Created by host on 2015/11/2.
 */
public class SearchActivity extends BasePageActivity {
    private AQuery aQuery;
    private CourseAdapter courseAdapter;
    private ListView listView;
    private List<Course> courseList;
    private EditText searchEditText;
    private ProgressBar progressBar;
    private Gson gson;


    @Override
    protected void initData() {
        gson = new GsonBuilder().disableHtmlEscaping().create();
    }

    @Override
    protected void initLayoutView() {
        setContentView(R.layout.activity_search);
        aQuery = new AQuery(this);
    }

    @Override
    protected void initView() {
        listView = (ListView) findViewById(R.id.listview);
        searchEditText = (EditText) findViewById(R.id.et_search);
        progressBar =(ProgressBar) findViewById(R.id.progressBar1);
    }

    @Override
    protected void setListener() {
        aQuery.id(R.id.btn_search_left).clicked(this, "onBackPressed");
        aQuery.id(R.id.btn_search).clicked(this, "aq_search");
    }

    public void aq_search() {
        String searchkey = searchEditText.getText().toString();
        if (searchkey.length() == 0) {
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        Map<String, String> param = new HashMap<>();
        param.put("type", "search");
        param.put("searchkey", searchkey);
        LogUtil.i("---searchkey---", searchkey);
        //the result from the server,then refresh the list items
        VolleyUtils.post(Constant.URL.SearchCourse, param, new VolleyUtils.NetworkListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    List<Course> courses = gson.fromJson(jsonArray.toString(), new TypeToken<List<Course>>() {
                    }.getType());
                    progressBar.setVisibility(View.GONE);
                    refreshData(courses);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(SearchActivity.this, "数据异常", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFail(String error) {
                Toast.makeText(SearchActivity.this, error, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void refreshData(List<Course> courses) {
        if (this.courseList == null) {
            this.courseList = new ArrayList<>();
        }
        this.courseList.clear();
        this.courseList.addAll(courses);
        if (courseAdapter == null) {
            courseAdapter = new CourseAdapter(this, this.courseList);
            listView.setAdapter(courseAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constant.DataKey.COURSE, courseList.get(position));
                    Intent intent = new Intent(SearchActivity.this, CourseActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        } else {
            courseAdapter.notifyDataSetChanged();
        }
    }
}
