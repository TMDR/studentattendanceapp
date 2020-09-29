package com.example.studentattendance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class Student {
    private String FirstName;
    private String LastName;
    private int SchoolId;
    public ArrayList<Boolean> attendance = new ArrayList<>();

    public Student(String firstName, String lastName, int schoolId) {
        this.FirstName = firstName;
        this.LastName = lastName;
        this.SchoolId = schoolId;
    }

    public double getAttendanceRate() {
        if (Class.instance.Sessions.isEmpty()) {
            return 1.0d;
        }
        int presence = 0;
        Iterator<Boolean> it = this.attendance.iterator();
        while (it.hasNext()) {
            if (it.next().booleanValue()) {
                presence++;
            }
        }
        return ((double) presence) / ((double) Class.instance.Sessions.size());
    }

    public void setPresence(boolean presence) {
        this.attendance.add(Boolean.valueOf(presence));
    }

    public ArrayList<Boolean> getAttendance() {
        return this.attendance;
    }

    public String getFirstName() {
        return this.FirstName;
    }

    public String getLastName() {
        return this.LastName;
    }

    public int getSchoolId() {
        return this.SchoolId;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return Objects.equals(Integer.valueOf(getSchoolId()), Integer.valueOf(((Student) o).getSchoolId()));
    }

    public int hashCode() {
        return Objects.hash(new Object[]{Integer.valueOf(getSchoolId())});
    }

    public String toString() {
        return "Student{FirstName='" + this.FirstName + '\'' + ", LastName='" + this.LastName + '\'' + ", SchoolId='" + this.SchoolId + '\'' + '}';
    }
}
