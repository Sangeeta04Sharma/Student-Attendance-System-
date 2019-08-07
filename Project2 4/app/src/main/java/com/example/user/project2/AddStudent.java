package com.example.user.project2;

/**
 * Created by user on 15/07/18.
 */

public class AddStudent {

    String StuName;
    String Stuclgid;
    String StuPhone;
    String StuEmail;
    String StuPassword;
    String StuBranch;
    String StuYop;


public AddStudent()
{

}


    public AddStudent( String stuName, String stuclgid, String stuPhone, String stuEmail, String stuPassword, String stuBranch, String stuYop) {
        StuName = stuName;
        Stuclgid = stuclgid;
        StuPhone = stuPhone;
        StuEmail = stuEmail;
        StuPassword = stuPassword;
        StuBranch = stuBranch;
        StuYop = stuYop;
    }

    public void setStuName(String stuName) {
        StuName = stuName;
    }

    public void setStuclgid(String stuclgid) {
        Stuclgid = stuclgid;
    }

    public void setStuPhone(String stuPhone) {
        StuPhone = stuPhone;
    }

    public void setStuEmail(String stuEmail) {
        StuEmail = stuEmail;
    }

    public void setStuPassword(String stuPassword) {
        StuPassword = stuPassword;
    }

    public void setStuBranch(String stuBranch) {
        StuBranch = stuBranch;
    }

    public void setStuYop(String stuYop) {
        StuYop = stuYop;
    }

    public String getStuName() {
        return StuName;
    }


    public String getStuclgid() {
        return Stuclgid;
    }

    public String getStuPhone() {
        return StuPhone;
    }

    public String getStuEmail() {
        return StuEmail;
    }

    public String getStuPassword() {
        return StuPassword;
    }

    public String getStuBranch() {
        return StuBranch;
    }

    public String getStuYop() {
        return StuYop;
    }
}
