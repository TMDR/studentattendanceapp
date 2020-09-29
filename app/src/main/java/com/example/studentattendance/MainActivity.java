package com.example.studentattendance;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    public static StudentAdapter adapter;
    public static StudentAdapter adapter2;
    /* access modifiers changed from: private */
    public static ArrayList<Session> als;
    public static HashMap<Student, Boolean> attend = new HashMap<>();
    public static StudentDBHelper dbHelper;
    private SessionsAdapter Sadapter;
    private DatePicker date = null;
    private ListView listSessions;
    private EditText title;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
        Toast.makeText(this, "NOTICE that this application supports putting many sessions in the same name and same date so you can choose one of them when they will be ordered by adding time in the same date", Toast.LENGTH_LONG).show();
        StudentDBHelper studentDBHelper = new StudentDBHelper(this);
        dbHelper = studentDBHelper;
        studentDBHelper.load_Sessions(Class.instance.Sessions);
        dbHelper.load_Students(Class.instance.SubscribedStudents);
        Log.d("sessionslike", "sessions taken in class = " + Class.instance.Sessions);
        this.title = (EditText) findViewById(R.id.SessionTitle);
        this.date = (DatePicker) findViewById(R.id.SessionDate);
        this.listSessions = (ListView) findViewById(R.id.ChooseSession);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.erase_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        dbHelper.emptyDB();
        return true;
    }

    public void CreateStudentsList(View v) {
        startActivity(new Intent(this, CreateStudentsList.class));
    }

    public void TakeAttendance(View v) {
        if (Class.instance.SubscribedStudents.isEmpty()) {
            Toast.makeText(this, "Sorry there is no Students inserted please Create Students list", Toast.LENGTH_LONG).show();
        } else {
            startActivity(new Intent(this, TakeAttendance.class));
        }
    }

    public void ViewSpecificSession(Session s) {
        SessionsAdapter sessionsAdapter = new SessionsAdapter(this, new ArrayList());
        this.Sadapter = sessionsAdapter;
        this.listSessions.setAdapter(sessionsAdapter);
        Intent intent = new Intent(this, ViewAttendance.class);
        new SimpleDateFormat("yyyy-MM-dd");
        intent.putExtra("SessionId", s.getId());
        startActivity(intent);
    }

    public void ChooseSession() {
        Toast.makeText(this, "The results are sorted by adding time so the first is added first", Toast.LENGTH_LONG).show();
        SessionsAdapter sessionsAdapter = new SessionsAdapter(this, als);
        this.Sadapter = sessionsAdapter;
        this.listSessions.setAdapter(sessionsAdapter);
        this.listSessions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MainActivity.this.ViewSpecificSession((Session) MainActivity.als.get(i));
            }
        });
    }

    public void ViewAttendance(View v) {
        StringBuilder sb;
        if (Class.instance.Sessions.isEmpty()) {
            Toast.makeText(this, "Sorry there is no Sessions inserted please take attendance", Toast.LENGTH_LONG).show();
            return;
        }
        Log.d("date", Class.instance.Sessions + "");
        String month = (this.date.getMonth() + 1) + "";
        if (this.date.getMonth() + 1 < 10) {
            month = "0" + month;
        }
        if (this.date.getDayOfMonth() >= 10) {
            sb = new StringBuilder();
            sb.append("");
        } else {
            sb = new StringBuilder();
            sb.append("0");
        }
        sb.append(this.date.getDayOfMonth());
        String datee = this.date.getYear() + "-" + month + "-" + sb.toString();
        ArrayList<Session> results = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Iterator<Session> it = Class.instance.Sessions.iterator();
        while (it.hasNext()) {
            Session session = it.next();
            Log.d("date format", "" + df.format(session.getDate()) + " == " + datee);
            if (session.getTitle().equals(this.title.getText().toString()) && df.format(session.getDate()).equals(datee)) {
                results.add(session);
            }
        }
        if (results.isEmpty()) {
            Toast.makeText(this, "Sorry there is no Sessions like this inserted", Toast.LENGTH_LONG).show();
        } else if (results.size() == 1) {
            ViewSpecificSession(results.get(0));
        } else {
            als = results;
            ChooseSession();
        }
    }

    public void AttendanceStatistics(View v) {
        if (Class.instance.Sessions.isEmpty()) {
            Toast.makeText(this, "Sorry there is no Sessions inserted please take attendance", Toast.LENGTH_LONG).show();
        } else {
            startActivity(new Intent(this, AttendanceStatistics.class));
        }
    }
}
