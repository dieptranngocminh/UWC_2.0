package com.example.logicdesign_project_final;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class about_us extends AppCompatActivity {
    Button returnbtn;
    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        returnbtn = (Button)findViewById(R.id.moveBack);

        returnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int1=new Intent(about_us.this, MainActivity.class);
                startActivity(int1);
            }
        });

    }


}
