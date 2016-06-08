package com.gz.learnweb.fragment.FirstPageChildFgm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.gz.learnweb.activity.CourseActivity;
import com.gz.learnweb.activity.QuestionActivity;
import com.gz.learnweb.entire.Course;
import com.ibm.gz.learnweb.R;

/**
 * show the teacher and course detail,and the entery of questionacitvity
 * Created by host on 2015/8/18.
 */
public class DetailFgm extends Fragment {
    private View converView;
    private AQuery aq;
    private Course course;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        converView = inflater.inflate(R.layout.fragment_firstpage_detail, container, false);
        course = ((CourseActivity) getActivity()).getCourse();
        initView(converView);
        return converView;
    }

    private void initView(View converView) {
        aq = new AQuery(converView);
        aq.id(R.id.detail_course_title).text(course.getCourse_name());
        aq.id(R.id.detail_course_detail).text(course.getDetail());
        if (course.getTeacher() != null) {
            aq.id(R.id.detail_teacher_name).text(course.getTeacher().getUsername());
            aq.id(R.id.detail_teacher_avater).image(course.getTeacher().getAvater());
            aq.id(R.id.detail_teacher_detail).text(course.getTeacher().getDetail());
        }
        aq.id(R.id.btn_img_question).clicked(this, "question");
    }

    public void question() {
        startActivity(new Intent(getActivity(), QuestionActivity.class));
    }
}
