package com.example.administrator.JsonBean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/4.
 */

public class Score_tables {
    private String semester;
    private List<Scores> scores;

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public List<Scores> getScores() {
        return scores;
    }

    public void setScores(List<Scores> scores) {
        this.scores = scores;
    }

    @Override
    public String toString() {
        return "Score_tables{" +
                "semester='" + semester + '\'' +
                ", scores=" + scores +
                '}';
    }
}
