package com.myapplicationdev.android.taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvTasks;
    Button btnAdd;
    ArrayList<Task> task;
    TaskAdapter aa;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
        task = new ArrayList<Task>();

        DBHelper dbh = new DBHelper(MainActivity.this);
        task.addAll(dbh.getAllTasks());
        aa = new TaskAdapter(this, R.layout.row, task);
        dbh.close();
        lvTasks = (ListView)findViewById(R.id.lvTasks);
        lvTasks.setAdapter(aa);
        aa.notifyDataSetChanged();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 8){
            task.clear();
            DBHelper dbh = new DBHelper(MainActivity.this);
            task.addAll(dbh.getAllTasks());
            dbh.close();
            aa.notifyDataSetChanged();
        }
    }
}
