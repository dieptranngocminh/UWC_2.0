package com.example.logicdesign_project_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class TaskList extends AppCompatActivity {
    Button returnbtn;
    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID;

    Calendar cal = Calendar.getInstance();

    int currentDay = cal.get(Calendar.DAY_OF_MONTH); // current day in the month
    int currentMonth = cal.get(Calendar.MONTH); // month...

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taskList);

        returnbtn = (Button)findViewById(R.id.moveBack);

        returnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int1=new Intent(TaskList.this, MainActivity.class);
                startActivity(int1);
            }
        });

        TextView current = (TextView) findViewById(R.id.currentDate);
        current.setText(currentDay + " " + currentMonth);

        int[] taskLayout = new int[] {R.id.currentTask, R.id.nextTask1, R.id.nextTask2, R.id.nextTask3};

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Tasks");
        userID = user.getUid();

        int[] taskName = new int[] {R.id.currentTaskName, R.id.nextTaskName1, R.id.nextTaskName2, R.id.nextTaskName3};
        int[] taskMCP = new int[] {R.id.currentTaskMCP, R.id.nextTaskMCP1, R.id.nextTaskMCP2, R.id.nextTaskMCP3};
        int[] taskType = new int[] {R.id.currentTaskJob, R.id.nextTaskJob1, R.id.nextTaskJob2, R.id.nextTaskJob3};
        String[] taskId = new String[] {"", "", "", "", ""};

        Query myTopPostsQuery = reference.child("userId").equalTo(userID).limitToFirst(4);
        myTopPostsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0;
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    // TODO: handle the post
                    TaskHelper taskProfile = snapshot.getValue(TaskHelper.class);
                    if(taskProfile != null){
                        String id = taskProfile.id;
                        taskId[count] = taskProfile.id;
                        String mcp = taskProfile.mcp;
                        String type = taskProfile.type;

                        final TextView NameTextview = (TextView)findViewById(taskName[count]);
                        final TextView MCPTextview = (TextView)findViewById(taskMCP[count]);
                        final TextView TypeTextview = (TextView)findViewById(taskType[count]);

                        NameTextview.setText(id);
                        MCPTextview.setText(mcp);
                        TypeTextview.setText(type);

                        count++;
                        if (count>3) break;
                    }
                }
                for (int i = count;i<4;i++)
                {
                    RelativeLayout layout = (RelativeLayout)findViewById(taskLayout[i]);
                    if (layout.getVisibility() == View.VISIBLE) {
                        layout.setVisibility(View.GONE);
                    }
                    else if (layout.getVisibility() == View.GONE) {
                        layout.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TaskList.this,"Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

        for (int i = 0;i<4;i++)
        {
            RelativeLayout layout = (RelativeLayout)findViewById(taskLayout[i]);
            final int pos = i;
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentCurrent=new Intent(TaskList.this, showTask.class);
                    intentCurrent.putExtra("TASK_ID", taskId[pos]);
                    intentCurrent.putExtra("PREVIOUS", "taskList");
                    startActivity(intentCurrent);
                }
            });
        }
    }


}
