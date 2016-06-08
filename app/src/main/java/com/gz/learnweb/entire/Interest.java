package com.gz.learnweb.entire;


/**
 * Created by host on 2015/10/3.
 */
public class Interest{
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object interest){
        Interest i=(Interest)interest;
        if(this.id==i.getId()&&this.name.equals(i.getName())){
            return true;
        }else {
            return false;
        }
    }
    @Override
    public int hashCode(){
        return name.hashCode()+id;
    }
}
