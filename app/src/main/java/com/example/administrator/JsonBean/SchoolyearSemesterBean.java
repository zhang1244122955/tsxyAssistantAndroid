package com.example.administrator.JsonBean;

/**
 * Created by Administrator on 2017/6/4.
 */

public class SchoolyearSemesterBean {
    private String school_year;
    private String semester;

    public String getSchool_year() {
        return school_year;
    }

    public void setSchool_year(String school_year) {
        this.school_year = school_year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    @Override
    public String toString() {
        return "SchoolyearSemesterBean{" +
                "school_year='" + school_year + '\'' +
                ", semester='" + semester + '\'' +
                '}';
    }
}
