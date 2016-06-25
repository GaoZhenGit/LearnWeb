package com.gz.learnweb.fragment.FirstPageChildFgm;

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
import com.gz.learnweb.Utils.VolleyUtils;
import com.gz.learnweb.activity.CourseActivity;
import com.gz.learnweb.Adapter.NoteAdapter;
import com.gz.learnweb.Constant;
import com.gz.learnweb.R;
import com.gz.learnweb.activity.NoteActivity;
import com.gz.learnweb.entire.Course;
import com.gz.learnweb.entire.Note;
import com.lidroid.xutils.DbUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * operation of this course
 * Created by host on 2015/8/18.
 */
public class NoteFgm extends Fragment{
    private View contextView;
    private AQuery aq;
    private Course course;
    private ListView listView;
    private NoteAdapter noteAdapter;
    private List<Note> noteList;
    private DbUtils db;
    private Gson gson;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson = new GsonBuilder().disableHtmlEscaping().create();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contextView =inflater.inflate(R.layout.fragment_note, container, false);
        initData();
        initView();
        setListener();
        return contextView;
    }

    private void initData() {
        course=((CourseActivity)getActivity()).getCourse();
        db=DbUtils.create(getActivity());
        listView=(ListView)contextView.findViewById(R.id.note_list);
        noteList =new ArrayList<>();
        noteAdapter =new NoteAdapter(getActivity(),noteList);
        listView.setAdapter(noteAdapter);
    }

    private void initView() {
    }
    private void setListener(){
        //查看编辑笔记
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle=new Bundle();
                bundle.putSerializable(Constant.DataKey.NOTE,noteList.get(position));
                Intent intent=new Intent(getActivity(), NoteActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //删除笔记
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //设置长按删除
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                String[] items={"删除"};
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Map<String, String> param = new HashMap<>();
                        param.put("note_id", noteList.get(position).getNote_id() + "");
                        VolleyUtils.post(Constant.URL.Note + "delete", param, new VolleyUtils.NetworkListener() {
                            @Override
                            public void onSuccess(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String state = jsonObject.optString("state");
                                    if (state.equals("success")) {
                                        checkNote();
                                    } else {
                                        throw new JSONException("");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getActivity(),"删除失败，请稍后再试",Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFail(String error) {
                                Toast.makeText(getActivity(),"删除失败，请稍后再试",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                builder.create().show();
                return true;
            }
        });
    }
//    private void checkDBForNote(){
//        try {
//            if(noteList==null) {
//                noteList= db.findAll(Selector.from(Note.class)
//                        .where("courseName", "=", course.getCourse_name()).orderBy("id",true));
//                if(noteList==null)//数据库查询为空时，list也为空，所以……
//                    noteList=new ArrayList<>();
//            }else {
//                noteList.clear();
//                List<Note> temp= db.findAll(Selector.from(Note.class)
//                        .where("courseName", "=", course.getCourse_name()).orderBy("id",true));
//                if(temp!=null)
//                    noteList.addAll(temp);
//            }
//            if(noteAdapter==null) {//首次查询数据库
//                noteAdapter = new NoteAdapter(getActivity(), noteList);
//                listView.setAdapter(noteAdapter);
//            }else {//查询数据库后更新
//                noteAdapter.notifyDataSetChanged();
//            }
//        } catch (DbException e) {
//            e.printStackTrace();
//        }
//    }

    private void checkNote() {
        VolleyUtils.post(Constant.URL.Note + "get", null, new VolleyUtils.NetworkListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    //先请求所有笔记
                    List<Note> notes = gson.fromJson(jsonArray.toString(), new TypeToken<List<Note>>() {
                    }.getType());
                    noteList.clear();
                    for(Note note:notes){
                        //挑选与该课程相关的笔记显示
                        if(note.getCourse_id()==course.getCourse_id()){
                            noteList.add(note);
                        }
                    }
                    noteAdapter.notifyDataSetChanged();
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFail(String error) {

            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        checkNote();
    }
}
