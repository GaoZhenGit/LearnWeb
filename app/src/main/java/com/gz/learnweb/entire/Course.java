package com.gz.learnweb.entire;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by host on 2015/8/9.
 */
public class Course implements Serializable{
    private int course_id;
    private String course_name;
    private List<Video> course_videos;
    private String course_img;
    private String detail;
    private User teacher;

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_img() {
        return course_img;
    }

    public void setCourse_img(String course_img) {
        this.course_img = course_img;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public List<Video> getCourse_videos() {
        return course_videos;
    }

    public void setCourse_videos(List<Video> course_videos) {
        this.course_videos = course_videos;
    }
    public void addVideo(Video video){
        if(course_videos==null){
            course_videos=new ArrayList<>();
        }
        course_videos.add(video);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Course) {
            Course c = (Course) o;
            return course_id == c.course_id;
        } else {
            return false;
        }
    }
}
