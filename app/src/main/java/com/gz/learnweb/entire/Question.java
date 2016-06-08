package com.gz.learnweb.entire;

/**
 * question is asked to student
 * Created by host on 2015/11/16.
 */
public class Question {
    private int id;
    private String content;
    private boolean isFromTeacher;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isFromTeacher() {
        return isFromTeacher;
    }

    public void setIsFromTeacher(boolean isFromTeacher) {
        this.isFromTeacher = isFromTeacher;
    }
}
