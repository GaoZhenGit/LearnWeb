package com.gz.learnweb.entire;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lidroid.xutils.db.annotation.Id;

/**
 * Created by gz on 15/9/18.
 */
public class HistoryCourse {
    @Id(column = "id")
    private String id;
    private String course;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Course getCourseEntire() {
        Gson gson=new GsonBuilder().disableHtmlEscaping().create();
        return gson.fromJson(course,Course.class);
    }

    public String getCourse(){
        return this.course;
    }
    public void setCourse(String course) {
        this.course = course;
    }

    public void setCourse(Course course){
        Gson gson=new GsonBuilder().disableHtmlEscaping().create();
        this.id=course.getCourse_id()+"";
        this.course=gson.toJson(course);
    }
}
