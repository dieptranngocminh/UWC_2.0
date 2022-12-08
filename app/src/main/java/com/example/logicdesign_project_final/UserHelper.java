package com.example.logicdesign_project_final;

import android.view.View;

public class UserHelper {
    String fullname, DoB, gender, email, ID;

    public UserHelper() {
    }
    public UserHelper(String fullname, String Dob,String gender, String email, String ID) {
        this.fullname = fullname;
        this.DoB = Dob;
        this.email = email;
        this.gender= gender;
        this.ID = ID;
    }


    public String getFullName() {
        return fullname;
    }

    public void setFullName(String name) {
        this.fullname = name;
    }

    public String getDoB() {
        return DoB;
    }

    public void setDoB(String DoB) {
        this.DoB = DoB;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void update(View view){

    }
}