package edu.ynu.entity;

/**
 * Created by Administrator on 2016/7/9.
 */
public class TeacherEntity {
    //用户名
    private String userId;
    //密码
    private String password;
    //姓名
    private String name;
    //学院
    private String academy;

    public String getAcademy() {
        return academy;
    }

    public void setAcademy(String academy) {
        this.academy = academy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
