package com.example.studentattendance;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CreateStudentsList extends AppCompatActivity {
    private EditText firstName;
    private EditText lastName;
    private EditText schoolId;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_create_students_list);
        this.firstName = (EditText) findViewById(R.id.firstName);
        this.lastName = (EditText) findViewById(R.id.lastName);
        this.schoolId = (EditText) findViewById(R.id.SchoolId);
    }

    public void addStudent(View v) {
        String firstNameVar = this.firstName.getText().toString();
        String lastNameVar = this.lastName.getText().toString();
        if (!"".equals(this.schoolId.getText().toString())) {
            Student s = new Student(firstNameVar, lastNameVar, Integer.parseInt(this.schoolId.getText().toString()));
            if (Class.instance.SubscribedStudents.contains(s)) {
                Toast.makeText(this, "school id = " + s.getSchoolId() + " already added", 1).show();
                return;
            }
            MainActivity.dbHelper.addStudent(s);
            MainActivity.dbHelper.load_Students(Class.instance.SubscribedStudents);
            Toast.makeText(this, s + " added", 1).show();
            Log.d("StudentAdd", "students list :  " + Class.instance.SubscribedStudents);
            this.firstName.setText("");
            this.lastName.setText("");
            this.schoolId.setText("");
            Toast.makeText(this, "Info : Student added", 1).show();
            return;
        }
        Toast.makeText(this, "set schoolId please", 1).show();
    }
}
