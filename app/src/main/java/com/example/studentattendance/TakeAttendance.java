package com.example.studentattendance;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.TimeZone;

public class TakeAttendance extends AppCompatActivity {
    EditText Title;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_take_attendance);
        MainActivity.adapter = new StudentAdapter(this, Class.instance.SubscribedStudents);
        ((ListView) findViewById(R.id.students)).setAdapter(MainActivity.adapter);
        MainActivity.attend.clear();
        this.Title = (EditText) findViewById(R.id.STitle);
    }

    public void checkUnCheck(View v) {
        int position = ((Integer) v.getTag()).intValue();
        CheckBox checkBox = (CheckBox) v;
        ArrayList<Boolean> arrayList = StudentAdapter.checked;
        StudentAdapter.checked.set(position, Boolean.valueOf(checkBox.isChecked()));
        MainActivity.attend.put(Class.instance.SubscribedStudents.get(position), Boolean.valueOf(checkBox.isChecked()));
        Log.d("TakeAttendance", MainActivity.attend + "");
    }

    public void takeAttendance(View v) {
        new SimpleDateFormat("yyyy-MM-dd").setTimeZone(TimeZone.getTimeZone("UTC"));
        Session s = new Session(Calendar.getInstance().getTime(), this.Title.getText().toString());
        MainActivity.dbHelper.addmysession(s);
        Log.d("stupid", "" + s);
        MainActivity.dbHelper.load_Sessions(Class.instance.Sessions);
        ArrayList<Integer> SessionStudents = new ArrayList<>();
        Iterator<Student> it = Class.instance.SubscribedStudents.iterator();
        while (it.hasNext()) {
            Student student = it.next();
            if (MainActivity.attend.containsKey(student) && MainActivity.attend.get(student).booleanValue()) {
                Log.d("AddPresence", student + " must be added ");
                SessionStudents.add(Integer.valueOf(student.getSchoolId()));
            }
        }
        Log.d("AddPresence", "session presents for SessionId " + s.getId() + "= " + SessionStudents);
        MainActivity.dbHelper.addPresenceSession(s.getId(), SessionStudents);
        Class.instance.takeAtt(MainActivity.attend, s);
        StudentAdapter.checked.clear();
        for (int i = 0; i < Class.instance.SubscribedStudents.size(); i++) {
            StudentAdapter.checked.add(false);
        }
        if (MainActivity.adapter2 != null) {
            MainActivity.adapter2.notifyDataSetChanged();
        }
        finish();
    }
}
