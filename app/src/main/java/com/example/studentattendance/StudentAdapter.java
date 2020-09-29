package com.example.studentattendance;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;

class StudentAdapter extends ArrayAdapter<Student> {
    public static ArrayList<Boolean> checked = new ArrayList<>();
    public ArrayList<Student> list_students;

    public StudentAdapter(Context context, ArrayList<Student> list_students2) {
        super(context, R.layout.row_student_rate);
        this.list_students = list_students2;
        checked.clear();
        for (int i = 0; i < this.list_students.size(); i++) {
            checked.add(false);
        }
    }

    public View getView(int position, View row, ViewGroup parent) {
        Student student = this.list_students.get(position);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        if (getContext().equals(AttendanceStatistics.Context)) {
            View v = inflater.inflate(R.layout.row_student_rate, parent, false);
            ((EditText) v.findViewById(R.id.firstname)).setText(student.getFirstName());
            ((EditText) v.findViewById(R.id.lastname)).setText(student.getLastName());
            ((EditText) v.findViewById(R.id.schoolid)).setText(String.valueOf(student.getSchoolId()));
            double rate = student.getAttendanceRate();
            ((TextView) v.findViewById(R.id.rate)).setText("" + rate);
            return v;
        }
        View v2 = inflater.inflate(R.layout.row, parent, false);
        ((EditText) v2.findViewById(R.id.firstname)).setText(student.getFirstName());
        ((EditText) v2.findViewById(R.id.lastname)).setText(student.getLastName());
        ((EditText) v2.findViewById(R.id.schoolid)).setText(String.valueOf(student.getSchoolId()));
        CheckBox Presence = (CheckBox) v2.findViewById(R.id.Present);
        Presence.setTag(Integer.valueOf(position));
        if (checked.get(position) != null) {
            Presence.setChecked(checked.get(position).booleanValue());
        }
        if (getContext().equals(ViewAttendance.context)) {
            Presence.setEnabled(false);
            Session s = new Session();
            s.setId(ViewAttendance.SessionId);
            boolean present = this.list_students.get(position).getAttendance().get(Class.instance.Sessions.indexOf(s)).booleanValue();
            Log.d("Presenttt", "student " + this.list_students.get(position) + "is present " + present);
            Presence.setChecked(present);
        }
        return v2;
    }

    public int getCount() {
        return this.list_students.size();
    }
}
