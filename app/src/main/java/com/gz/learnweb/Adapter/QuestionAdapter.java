package com.gz.learnweb.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gz.learnweb.entire.Question;
import com.gz.learnweb.R;

import java.util.List;

/**
 * adapter for question dialog
 * Created by host on 2015/11/15.
 */
public class QuestionAdapter extends BaseAdapter {
    private static final int TEACHER = 0;
    private static final int STUDENT = 1;

    List<Question> questionList;


    public QuestionAdapter(List<Question> questions) {
        this.questionList = questions;
    }

    @Override
    public int getCount() {
        return questionList == null ? 0 : questionList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return questionList.get(position).isFromTeacher() ? TEACHER : STUDENT;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
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
        if (convertView == null) {
            int type = getItemViewType(position);
            if (type == TEACHER) {
                convertView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapter_question_left, parent, false);
            } else {
                convertView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adapter_question_right, parent, false);
            }
        }
        TextView textView = (TextView) convertView.findViewById(R.id.tv_question);
        textView.setText(questionList.get(position).getContent());
        return convertView;
    }
}
