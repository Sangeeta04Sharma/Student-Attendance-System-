package com.example.user.project2;

/**
 * Created by user on 18/07/18.
 */

public class UpdateStudent {
    public  String New_branch ;
    public  String New_number;
    public  String New_yop;

    public UpdateStudent()
    {

    }

    public UpdateStudent(String new_branch, String new_number, String new_yop) {
        New_branch = new_branch;
        New_number = new_number;
        New_yop = new_yop;
    }

    public void setNew_branch(String new_branch) {
        New_branch = new_branch;
    }

    public void setNew_number(String new_number) {
        New_number = new_number;
    }

    public void setNew_yop(String new_yop) {
        New_yop = new_yop;
    }

    public String getNew_branch() {
        return New_branch;
    }

    public String getNew_number() {
        return New_number;
    }

    public String getNew_yop() {
        return New_yop;
    }
}
