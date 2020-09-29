package com.example.studentattendance;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class AttendanceStatistics extends AppCompatActivity {
    public static Context Context;
    private StudentAdapter mAdapter;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_attendance_statistics);
        Context = this;
        this.mAdapter = new StudentAdapter(this, Class.instance.SubscribedStudents);
        ((ListView) findViewById(R.id.list_rate)).setAdapter(this.mAdapter);
    }
}
