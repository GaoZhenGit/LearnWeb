package com.gz.learnweb.activity;

import android.widget.EditText;

import com.androidquery.AQuery;
import com.gz.learnweb.Utils.VolleyUtils;
import com.gz.learnweb.Constant;
import com.gz.learnweb.R;
import com.gz.learnweb.entire.Course;
import com.gz.learnweb.entire.Note;
import com.lidroid.xutils.DbUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NoteActivity extends BasePageActivity {
    private AQuery aq;
    private EditText editText;
    private Note note;
    private Course course;
    private DbUtils db;
    boolean isNewNote = false;

    @Override
    protected void initData() {
        db = DbUtils.create(this);
        if (mBundle != null && mBundle.getSerializable(Constant.DataKey.NOTE) != null) {
            note = (Note) mBundle.getSerializable(Constant.DataKey.NOTE);
            isNewNote = false;
        } else {
            note = new Note();
            course = (Course) mBundle.getSerializable(Constant.DataKey.COURSE);
            isNewNote = true;
        }
    }

    @Override
    protected void initLayoutView() {
        setContentView(R.layout.activity_note);
        aq = new AQuery(this);
        editText = (EditText) findViewById(R.id.note_text);
    }

    @Override
    protected void initView() {
        aq.id(R.id.title_mid_text).text("笔记");
        aq.id(R.id.title_left_img).visible();
        aq.id(R.id.title_left_tv).visible().text("退出");
        aq.id(R.id.title_right_text).visible().text("保存");

        //如果是已有的笔记，则显示出来
        if (!isNewNote) {
            aq.id(R.id.note_date).text(note.getTime());
            aq.id(R.id.note_text).text(note.getText());
        } else {
            aq.id(R.id.note_date).gone();
        }
    }

    @Override
    protected void setListener() {
        aq.id(R.id.title_left_btn).clicked(this, "finish");
        aq.id(R.id.title_right_btn).clicked(this, "aq_save");
    }

    //保存笔记
    public void aq_save() {
        String text = editText.getText().toString();
        if (text.length() == 0) {
            ShowToast("请输入内容");
            return;
        }

        if (isNewNote) {
            //新建笔记add
            note.setText(text);
            note.setCourseName(course.getCourse_name());
            note.setCourse_id(course.getCourse_id());
            Map<String, String> param = new HashMap<>();
            param.put("text", note.getText());
            param.put("course_id", note.getCourse_id() + "");
            VolleyUtils.post(Constant.URL.Note + "add", param, new VolleyUtils.NetworkListener() {
                @Override
                public void onSuccess(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String state = jsonObject.optString("state");
                        if (state.equals("success")) {
                            ShowToast("保存成功");
                            finish();
                        } else {
                            throw new JSONException("");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        ShowToast("保存失败，请稍后再试");
                    }
                }

                @Override
                public void onFail(String error) {
                    ShowToast("保存失败，请稍后再试");
                }
            });
        } else {
            //修改笔记update
            note.setText(text);
            Map<String, String> param = new HashMap<>();
            param.put("note_id", note.getNote_id() + "");
            param.put("text", note.getText());
            VolleyUtils.post(Constant.URL.Note + "update", param, new VolleyUtils.NetworkListener() {
                @Override
                public void onSuccess(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String state = jsonObject.optString("state");
                        if (state.equals("success")) {
                            ShowToast("保存成功");
                            finish();
                        } else {
                            throw new JSONException("");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        ShowToast("修改失败，请稍后再试");
                    }
                }

                @Override
                public void onFail(String error) {
                    ShowToast("修改失败，请稍后再试");
                }
            });
        }
    }
}
