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
import com.gz.learnweb.Utils.VolleyUtils;
import com.gz.learnweb.listener.LeftHideShow;
import com.gz.learnweb.Adapter.NoteAdapter;
import com.gz.learnweb.Constant;
import com.gz.learnweb.R;
import com.gz.learnweb.Utils.LogUtil;
import com.gz.learnweb.activity.NoteActivity;
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
 * operation of all note that user take
 * Created by gz on 15/9/11.
 */
public class MyNoteFragment extends Fragment implements LeftHideShow {
    private View mView;
    private ListView listView;
    private NoteAdapter noteAdapter;
    private AQuery aq;
    private List<Note> noteList;
    private Gson gson;
    private DbUtils db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_mynote, container, false);
        initData();
        initView();
        setListener();
        leftOn();
        return mView;
    }

    private void initData() {
        listView = (ListView) mView.findViewById(R.id.note_list);
        db = DbUtils.create(getActivity());
        gson = new GsonBuilder().disableHtmlEscaping().create();
        noteList = new ArrayList<>();
        noteAdapter = new NoteAdapter(getActivity(), noteList);
        listView.setAdapter(noteAdapter);
    }

    private void initView() {

    }

    private void setListener() {
        //查看编辑笔记
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.DataKey.NOTE, noteList.get(position));
                Intent intent = new Intent(getActivity(), NoteActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //删除笔记
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                String[] items = {"删除"};
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

//    private void checkDBForNote() {
//        try {
//            if (noteList == null) {
//                noteList = db.findAll(Selector.from(Note.class).orderBy("id", true));
//                if (noteList == null)//数据库查询为空时，list也为空，所以……
//                    noteList = new ArrayList<>();
//            } else {
//                noteList.clear();
//                List<Note> temp = db.findAll(Selector.from(Note.class).orderBy("id", true));
//                if (temp != null)
//                    noteList.addAll(temp);
//            }
//            if (noteAdapter == null) {//首次查询数据库
//                noteAdapter = new NoteAdapter(getActivity(), noteList);
//                listView.setAdapter(noteAdapter);
//            } else {//查询数据库后更新
//                noteAdapter.notifyDataSetChanged();
//            }
//        } catch (DbException e) {
//            e.printStackTrace();
//        }
//    }


    @Override
    public void onResume() {
        super.onResume();
        checkNote();
    }

    private void checkNote() {
        VolleyUtils.post(Constant.URL.Note + "get", null, new VolleyUtils.NetworkListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    List<Note> notes = gson.fromJson(jsonArray.toString(), new TypeToken<List<Note>>() {
                    }.getType());
                    noteList.clear();
                    noteList.addAll(notes);
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
    public void leftOff() {
        LogUtil.i("left", "my note off");
        if (aq == null) {
            aq = new AQuery(getActivity());
        }
        aq.id(R.id.btn_my_note).background(R.color.white);//背景色
        aq.id(R.id.img_note).image(R.drawable.note_gray);//图标
        aq.id(R.id.text_note).getTextView().setTextColor(getResources().getColor(R.color.grey));
    }

    @Override
    public void leftOn() {
        LogUtil.i("left", "my note on");
        if (aq == null) {
            aq = new AQuery(getActivity());
        }
        aq.id(R.id.btn_my_note).background(R.color.light_grey);
        aq.id(R.id.img_note).image(R.drawable.note_red);
        aq.id(R.id.text_note).getTextView().setTextColor(getResources().getColor(R.color.text_red));
        aq.id(R.id.title_mid_text).text("我的笔记");
    }
}
