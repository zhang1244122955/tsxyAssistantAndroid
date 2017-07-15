package com.example.administrator.JsonBean;



/**
 * Created by Administrator on 2017/5/29.
 */

public class InformationBean {
    private int id;
    private String uesrname;
    private String url;
    private String School_code;
    private String member_since;
    private String last_seen;

    @Override
    public String toString() {
        return "InformationBean{" +
                "id=" + id +
                ", uesrname='" + uesrname + '\'' +
                ", url='" + url + '\'' +
                ", School_code='" + School_code + '\'' +
                ", member_since='" + member_since + '\'' +
                ", last_seen='" + last_seen + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUesrname() {
        return uesrname;
    }

    public void setUesrname(String uesrname) {
        this.uesrname = uesrname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSchool_code() {
        return School_code;
    }

    public void setSchool_code(String school_code) {
        School_code = school_code;
    }

    public String getMember_since() {
        return member_since;
    }

    public void setMember_since(String member_since) {
        this.member_since = member_since;
    }

    public String getLast_seen() {
        return last_seen;
    }

    public void setLast_seen(String last_seen) {
        this.last_seen = last_seen;
    }
}
