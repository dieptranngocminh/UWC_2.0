package com.example.logicdesign_project_final;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class showTask extends AppCompatActivity {
    Button returnbtn;
    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taskList);

        returnbtn = (Button)findViewById(R.id.moveBack);
        String mytaskId = getIntent().getStringExtra("TASK_ID");
        String previous = getIntent().getStringExtra("PREVIOUS");

        returnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (previous == "calendar")
                {
                    intent = new Intent(showTask.this, CalendarLayout.class);
                }
                else if (previous == "taskList")
                {
                    intent = new Intent(showTask.this, TaskList.class);
                }
                else intent = new Intent(showTask.this, MainActivity.class);
                startActivity(intent);
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Tasks");
        userID = user.getUid();

        Query myTopPostsQuery = reference.child("id").equalTo(mytaskId).limitToFirst(1);
        myTopPostsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    // TODO: handle the post
                    TaskHelper taskProfile = snapshot.getValue(TaskHelper.class);
                    if(taskProfile != null){
                        String taskId = taskProfile.id;
                        String taskCheck = taskProfile.check;
                        String taskType = taskProfile.type;
                        String taskMCP = taskProfile.mcp;
                        String taskDescription = taskProfile.description;
                        String taskRoute = taskProfile.route;

                        final TextView IDTextview = (TextView)findViewById(R.id.taskId);
                        final TextView checkTextview = (TextView)findViewById(R.id.taskCICO);
                        final TextView TypeTextview = (TextView)findViewById(R.id.taskType);
                        final TextView MCPTextview = (TextView)findViewById(R.id.taskMCP);
                        final TextView descriptionTextview = (TextView)findViewById(R.id.taskDescription);
                        final TextView routeTextview = (TextView)findViewById(R.id.taskRoute);

                        IDTextview.setText(taskId);
                        checkTextview.setText(taskCheck);
                        TypeTextview.setText(taskType);
                        MCPTextview.setText(taskMCP);
                        descriptionTextview.setText(taskDescription);
                        routeTextview.setText(taskRoute);

                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(showTask.this,"Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

    }


}
