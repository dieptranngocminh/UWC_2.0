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

public class CalendarLayout extends AppCompatActivity {
    Button returnbtn;
    Button leftbtn;
    Button rightbtn;
    Button morebtn;


    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID;

    Calendar cal = Calendar.getInstance();
    int year = cal.get(Calendar.YEAR); // get the current year
    int month = cal.get(Calendar.MONTH); // month...
    int day = cal.get(Calendar.DAY_OF_MONTH); // current day in the month

    int currentDay = day;
    int currentMonth = month;
    int currentYear = year;
    int dateWeek = cal.get(Calendar.DAY_OF_WEEK) - 1; // current day in week


    int firstdateWeek = ((day % 7) - 1);

    int[] dateId = new int[] {R.id.date1, R.id.date2, R.id.date3, R.id.date4, R.id.date5, R.id.date6, R.id.date7
            , R.id.date8, R.id.date9, R.id.date10, R.id.date11, R.id.date12, R.id.date13, R.id.date14, R.id.date15, R.id.date16
            , R.id.date17, R.id.date18, R.id.date19, R.id.date20, R.id.date21, R.id.date22, R.id.date23, R.id.date24, R.id.date25
            , R.id.date26, R.id.date27, R.id.date28, R.id.date29, R.id.date30, R.id.date31, R.id.date32, R.id.date33, R.id.date34
            , R.id.date35, R.id.date36, R.id.date37, R.id.date38, R.id.date39, R.id.date40, R.id.date41, R.id.date42};

    String[] months = {
            "",                               // leave empty so that we start with months[1] = "January"
            "January", "February", "March",
            "April", "May", "June",
            "July", "August", "September",
            "October", "November", "December"
    };

    int[] days = {
            0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
    };

    public void updateCalendar() {
        if  ((((year % 4 == 0) && (year % 100 != 0)) ||  (year % 400 == 0))) days[2] = 29;
        else days[2] = 28;

        for (int i =0;i<firstdateWeek;i++)
        {
            TextView textViewToChange = (TextView) findViewById(dateId[i]);
            textViewToChange.setText(days[month-1]-(firstdateWeek - i + 1));
        }
        for (int i = 0;i<days[month];i++)
        {
            TextView textViewToChange = (TextView) findViewById(dateId[i + firstdateWeek]);
            textViewToChange.setText(i+1);
        }
        for (int i = firstdateWeek + days[month];i<42;i++)
        {
            TextView textViewToChange = (TextView) findViewById(dateId[i]);
            textViewToChange.setText(i-(firstdateWeek + days[month])+1);
        }

        if (currentMonth == month && currentYear == year)
        {
            TextView textViewToChange = (TextView) findViewById(dateId[firstdateWeek+currentDay-1]);
            textViewToChange.setBackgroundColor(Integer.parseInt("f44336", 16));
        }

        TextView textViewToChange = (TextView) findViewById(R.id.month);
        textViewToChange.setText(months[currentMonth] + " " + currentYear);
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        returnbtn = (Button)findViewById(R.id.moveBack);
        leftbtn = (Button)findViewById(R.id.moveLeft);
        rightbtn = (Button)findViewById(R.id.moveRight);

        returnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int1=new Intent(CalendarLayout.this, MainActivity.class);
                startActivity(int1);
            }
        });

        if (firstdateWeek < dateWeek) firstdateWeek = dateWeek - firstdateWeek;
        else if (firstdateWeek > dateWeek) firstdateWeek = (dateWeek+7) - firstdateWeek;
        else firstdateWeek = dateWeek;

        updateCalendar();

        leftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firstdateWeek = (firstdateWeek - days[month]) % 7;
                month = (month-1)%12;

                updateCalendar();
            }
        });

        rightbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firstdateWeek = (firstdateWeek + days[month]) % 7;
                month = (month+1)%12;

                updateCalendar();
            }
        });

        TextView current = (TextView) findViewById(R.id.currentDate);
        current.setText(currentDay + " " + currentMonth);

        int[] taskLayout = new int[] {R.id.currentTask, R.id.nextTask1};

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Tasks");
        userID = user.getUid();
        int[] taskName = new int[] {R.id.currentTaskName, R.id.nextTaskName1};
        int[] taskMCP = new int[] {R.id.currentTaskMCP, R.id.nextTaskMCP1};
        int[] taskType = new int[] {R.id.currentTaskJob, R.id.nextTaskJob1};
        String[] taskId = new String[] {"", ""};

        Query myTopPostsQuery = reference.child("userId").equalTo(userID).limitToFirst(2);
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
                        if (count>1) break;
                    }
                }
                for (int i = count;i<2;i++)
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
                Toast.makeText(CalendarLayout.this,"Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

        for (int i = 0;i<2;i++)
        {
            RelativeLayout layout = (RelativeLayout)findViewById(taskLayout[i]);
            final int pos = i;
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentCurrent=new Intent(CalendarLayout.this, showTask.class);
                    intentCurrent.putExtra("TASK_ID", taskId[pos]);
                    intentCurrent.putExtra("PREVIOUS", "calendar");
                    startActivity(intentCurrent);
                }
            });
        }

        morebtn = (Button)findViewById(R.id.seeTask);
        morebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMore=new Intent(CalendarLayout.this, TaskList.class);
                startActivity(intentMore);
            }
        });
    }
}
