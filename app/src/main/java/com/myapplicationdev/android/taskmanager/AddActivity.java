package com.myapplicationdev.android.taskmanager;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    TextView tvName, tvDesc, tvRemind;
    Button btnAddTask, btnCancel;
    EditText etName, etDesc, etRemind;
    Task task;
    int reqCode = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        tvName = findViewById(R.id.tvName);
        tvDesc = findViewById(R.id.tvDesc);
        tvRemind = findViewById(R.id.tvRemind);

        etName = findViewById(R.id.etName);
        etDesc = findViewById(R.id.etDesc);
        etRemind = findViewById(R.id.etRemind);

        btnAddTask = findViewById(R.id.btnAddTask);
        btnCancel = findViewById(R.id.btnCancel);



        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String desc = etDesc.getText().toString();
                DBHelper dbh = new DBHelper(AddActivity.this);
                long id = dbh.insertTask(name, desc);
                task = new Task((int)(id), name, desc);
                dbh.close();
                setResult(RESULT_OK);


                if (id != -1){
                    Toast.makeText(AddActivity.this, "Insert successful",
                            Toast.LENGTH_SHORT).show();
                }
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.SECOND, Integer.parseInt(etRemind.getText().toString()));
                Intent intent = new Intent(AddActivity.this, ScheduledNotificationReceiver.class);
                intent.putExtra("name",name);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(AddActivity.this, reqCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);

                AlarmManager am = (AlarmManager)getSystemService(Activity.ALARM_SERVICE);

                am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
                finish();
            }
        });
    }
}
