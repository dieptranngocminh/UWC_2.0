package com.example.logicdesign_project_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Changeprofile extends AppCompatActivity {

    TextInputLayout fullname,dob,gender,id,email;

    String NAME, DOB, GENDER, ID, EMAIL;

    DatabaseReference reference;
    FirebaseAuth mAuth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeprofile);

        mAuth= FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");

        fullname = findViewById(R.id.Fullname);
        dob = findViewById(R.id.Dob);
        gender = findViewById(R.id.Gender);
        email = findViewById(R.id.Email);
        id = findViewById(R.id.ID);

        showAlluserdata();
    }

    private void showAlluserdata(){

        Intent intent = getIntent();
        NAME = intent.getStringExtra("fullName");
        DOB = intent.getStringExtra("doB");
        EMAIL = intent.getStringExtra("email");
        ID = intent.getStringExtra("id");
        GENDER = intent.getStringExtra("gender");

//        fullname.getEditText().setText(NAME);
//        dob.getEditText().setText(DOB);
//        studentid.getEditText().setText(STUDENTID);
//        email.getEditText().setText(EMAIL);
//        phonenumber.getEditText().setText(PHONENO);
//        id.getEditText().setText(ID);
//        password.getEditText().setText(PASSWORD);
    }

    public void update(View view){
        if(isNameChanged() || isDobchanged() || isGenderChanged() || isEmailChanged() || isIDChanged()){
            Toast.makeText(this,"Data has been updated", Toast.LENGTH_LONG).show();
        }
        else Toast.makeText(this,"Data is same and can not be updated", Toast.LENGTH_LONG).show();
    }

    private boolean isIDChanged() {
        if(!ID.equals(id.getEditText().getText().toString())){
            reference.child(user.getUid()).child("id").setValue(id.getEditText().getText().toString());
            ID = id.getEditText().getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isEmailChanged() {
        if(!EMAIL.equals(email.getEditText().getText().toString())){
            reference.child(user.getUid()).child("email").setValue(email.getEditText().getText().toString());
            EMAIL = email.getEditText().getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isGenderChanged() {
        if(!GENDER.equals(gender.getEditText().getText().toString())){
            reference.child(user.getUid()).child("studentID").setValue(gender.getEditText().getText().toString());
            GENDER = gender.getEditText().getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isDobchanged() {
        if(!DOB.equals(dob.getEditText().getText().toString())){
            reference.child(user.getUid()).child("doB").setValue(dob.getEditText().getText().toString());
            DOB = dob.getEditText().getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isNameChanged() {
        if(!NAME.equals(fullname.getEditText().getText().toString())){
            reference.child(user.getUid()).child("fullName").setValue(fullname.getEditText().getText().toString());
            NAME = fullname.getEditText().getText().toString();
            return true;
        }
        else{
            return false;
        }
    }
}