package com.example.administrator.JsonBean;

/**
 * Created by Administrator on 2017/6/4.
 */

public class Scores {

    private String ps;
    private String score;
    private String exam_method;
    private String name;
    private String quale;
    private String id;
    private String get_method;
    private String worth;

    public String getPs() {
        return ps;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getExam_method() {
        return exam_method;
    }

    public void setExam_method(String exam_method) {
        this.exam_method = exam_method;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuale() {
        return quale;
    }

    public void setQuale(String quale) {
        this.quale = quale;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGet_method() {
        return get_method;
    }

    public void setGet_method(String get_method) {
        this.get_method = get_method;
    }

    public String getWorth() {
        return worth;
    }

    public void setWorth(String worth) {
        this.worth = worth;
    }

    @Override
    public String toString() {
        return "Scores{" +
                "ps='" + ps + '\'' +
                ", score='" + score + '\'' +
                ", exam_method='" + exam_method + '\'' +
                ", name='" + name + '\'' +
                ", quale='" + quale + '\'' +
                ", id='" + id + '\'' +
                ", get_method='" + get_method + '\'' +
                ", worth='" + worth + '\'' +
                '}';
    }
}
