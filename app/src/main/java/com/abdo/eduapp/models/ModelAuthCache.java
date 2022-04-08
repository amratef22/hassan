package com.abdo.eduapp.models;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity

public class ModelAuthCache {

    @PrimaryKey

    private  int  id = 0;

    private String user_name;
    private String user_id;
    private int type_id;


    public ModelAuthCache() {
    }

    public ModelAuthCache(String user_name, String user_id, int type_id) {
        this.user_name = user_name;
        this.user_id = user_id;
        this.type_id = type_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }
}
