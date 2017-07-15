package com.example.administrator.JsonBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/22.
 */

public class Courses {
    private ArrayList week;
    private String week_raw;
    private String nickname;
    private String parity;
    private String teacher;
    private String when_code;
    private String which_room;
    private String where;
    private String worth;
    private String name;

    public String getWeek_raw() {
        return week_raw;
    }

    public void setWeek_raw(String week_raw) {
        this.week_raw = week_raw;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorth() {
        return worth;
    }

    public void setWorth(String worth) {
        this.worth = worth;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public String getWhich_room() {
        return which_room;
    }

    public void setWhich_room(String which_room) {
        this.which_room = which_room;
    }

    public String getWhen_code() {
        return when_code;
    }

    public void setWhen_code(String when_code) {
        this.when_code = when_code;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getParity() {
        return parity;
    }

    public void setParity(String parity) {
        this.parity = parity;
    }

    public ArrayList getWeek() {
        return week;
    }

    public void setWeek(ArrayList week) {
        this.week = week;
    }

    @Override
    public String toString() {
        return "Courses{" +
                "week=" + week +
                ", week_raw='" + week_raw + '\'' +
                ", nickname='" + nickname + '\'' +
                ", parity='" + parity + '\'' +
                ", teacher='" + teacher + '\'' +
                ", when_code='" + when_code + '\'' +
                ", which_room='" + which_room + '\'' +
                ", where='" + where + '\'' +
                ", worth='" + worth + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
