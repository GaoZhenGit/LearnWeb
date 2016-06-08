package com.gz.learnweb.entire;

import java.io.Serializable;
import java.util.List;

/**
 * Created by host on 2015/8/13.
 */
public class User implements Serializable{
    private int id;
    private String username;
    private String pwd;
    private String user_mail;
    private String user_tel;
    private String avater;
    private String detail;
    private List<Interest> interests;

    public int getId() {
        return id;
    }

    public void setId(int u_id) {
        this.id = u_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getAvater() {
        return avater;
    }

    public void setAvater(String avater) {
        this.avater = avater;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<Interest> getInterests() {
        return interests;
    }

    public void setInterests(List<Interest> interests) {
        this.interests = interests;
    }

    public String getUser_tel() {
        return user_tel;
    }

    public void setUser_tel(String user_tel) {
        this.user_tel = user_tel;
    }

    public String getUser_mail() {
        return user_mail;
    }

    public void setUser_mail(String user_mail) {
        this.user_mail = user_mail;
    }
}
