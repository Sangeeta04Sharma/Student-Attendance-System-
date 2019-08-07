package com.example.user.project2;

/**
 * Created by user on 15/07/18.
 */

public class AddTeacher {
    String TechName;
    String Techclgid;
    String TechPhone;
    String TechEmail;
    String TechPassword;
    String TechType;
    String TechDept;

    public AddTeacher(String id, String name, String cldid, String phone, String email, String password, String type, String dept){

    }

    public AddTeacher(String techName, String techclgid,String techPhone, String techEmail, String techPassword, String techType, String techDept){
        TechName = techName;
        Techclgid = techclgid;
         TechPhone = techPhone;
         TechEmail= techEmail;
         TechPassword= techPassword;
         TechType= techType;
         TechDept =techDept;

    }

    public String getTechName() {
        return TechName;
    }

    public String getTechclgid() {
        return Techclgid;
    }

    public String getTechPhone() {
        return TechPhone;
    }

    public String getTechEmail() {
        return TechEmail;
    }

    public String getTechPassword() {
        return TechPassword;
    }

    public String getTechType() {
        return TechType;
    }

    public String getTechDept() {
        return TechDept;
    }
}
