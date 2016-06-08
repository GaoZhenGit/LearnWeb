package com.gz.learnweb.entire;

import java.io.Serializable;

/**
 * Created by gz on 15/9/7.
 */
public class Video implements Serializable{
    private String uri;
    private String name;
    private int order;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setOrder(int order){
        this.order=order;
    }
    public int getOrder(){
        return this.order;
    }
}
