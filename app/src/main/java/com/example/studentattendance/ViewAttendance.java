package com.example.studentattendance;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class ViewAttendance extends AppCompatActivity {
    public static int SessionId;
    public static Context context;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_view_attendance);
        SessionId = getIntent().getIntExtra("SessionId", 0);
        MainActivity.adapter2 = new StudentAdapter(this, Class.instance.SubscribedStudents);
        context = this;
        MainActivity.adapter2.notifyDataSetChanged();
        ((ListView) findViewById(R.id.listS)).setAdapter(MainActivity.adapter2);
    }

    public void checkUnCheck(View v) {
    }
}
