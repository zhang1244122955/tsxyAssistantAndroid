package com.example.administrator.JsonBean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/4.
 */

public class AchievementBean {

    private String department;
    private String user_code;
    private String stu_id;
    private String major;
    private List<Score_tables> score_tables;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getStu_id() {
        return stu_id;
    }

    public void setStu_id(String stu_id) {
        this.stu_id = stu_id;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public List<Score_tables> getScore_tables() {
        return score_tables;
    }

    public void setScore_tables(List<Score_tables> score_tables) {
        this.score_tables = score_tables;
    }

    @Override
    public String toString() {
        return "AchievementBean{" +
                "department='" + department + '\'' +
                ", user_code='" + user_code + '\'' +
                ", stu_id='" + stu_id + '\'' +
                ", major='" + major + '\'' +
                ", score_tables=" + score_tables +
                '}';
    }
}
