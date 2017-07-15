package com.example.administrator.JsonBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/22.
 */

public class CurriculumBean {
    private String grade;
    private String majar;
    private String school_year;
    private String class_code;
    private String department;
    private String semester;
    private String class_name;
    private List<Courses> courses;

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public List<Courses> getCourses() {
        return courses;
    }

    public void setCourses(List<Courses> courses) {
        this.courses = courses;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getClass_code() {
        return class_code;
    }

    public void setClass_code(String class_code) {
        this.class_code = class_code;
    }

    public String getSchool_year() {
        return school_year;
    }

    public void setSchool_year(String school_year) {
        this.school_year = school_year;
    }

    public String getMajar() {
        return majar;
    }

    public void setMajar(String majar) {
        this.majar = majar;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "CurriculumBean{" +
                "grade='" + grade + '\'' +
                ", majar='" + majar + '\'' +
                ", school_year='" + school_year + '\'' +
                ", class_code='" + class_code + '\'' +
                ", department='" + department + '\'' +
                ", semester='" + semester + '\'' +
                ", class_name='" + class_name + '\'' +
                ", courses=" + courses +
                '}';
    }
}
