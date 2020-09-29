package com.example.studentattendance;

import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Class {
    public static final Class instance = new Class();
    ArrayList<Session> Sessions = new ArrayList<>();
    ArrayList<Student> SubscribedStudents = new ArrayList<>();

    private Class() {
    }

    public void removeData() {
        this.Sessions.clear();
        this.SubscribedStudents.clear();
    }

    public void StudentAttendance(Student s, boolean presence) {
        ArrayList<Student> arrayList = this.SubscribedStudents;
        arrayList.get(arrayList.indexOf(s)).setPresence(presence);
    }

    public void setSubscribedStudents(ArrayList<Student> subscribedStudents) {
        this.SubscribedStudents = subscribedStudents;
    }

    public ArrayList<Student> getAtt(Session s) {
        ArrayList<Student> arrayList = new ArrayList<>();
        int SessionIndex = this.Sessions.indexOf(s);
        Iterator<Student> it = this.SubscribedStudents.iterator();
        while (it.hasNext()) {
            Student student = it.next();
            if (student.getAttendance().get(SessionIndex).booleanValue()) {
                arrayList.add(student);
            }
        }
        return arrayList;
    }

    public void setSessions(ArrayList<Session> sessions) {
        this.Sessions = sessions;
    }

    public void takeAtt(HashMap<Student, Boolean> attendance, Session s) {
        Iterator<Student> it = this.SubscribedStudents.iterator();
        while (it.hasNext()) {
            Student student = it.next();
            if (attendance.containsKey(student)) {
                student.attendance.set(instance.Sessions.indexOf(s), Boolean.valueOf(attendance.get(student).booleanValue()));
            } else {
                student.attendance.set(instance.Sessions.indexOf(s), false);
            }
        }
    }
}
