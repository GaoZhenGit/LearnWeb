package com.gz.learnweb.entire;

import com.lidroid.xutils.db.annotation.Id;

import java.io.Serializable;

/**
 * take note for course
 * Created by host on 2015/9/9.
 */
public class Note implements Serializable{
    @Id(column = "id")
    private int note_id;
    private String time;
    private String courseName;
    private String text;
    private int course_id;

    public int getNote_id() {
        return note_id;
    }

    public void setNote_id(int id) {
        this.note_id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }
}
