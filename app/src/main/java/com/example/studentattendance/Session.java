package com.example.studentattendance;

import java.util.Date;
import java.util.Objects;

public class Session {

    /* renamed from: Id */
    private int f2Id;
    private Date date;
    private String title;

    public Session() {
    }

    public Session(Date date2, String title2) {
        this.date = date2;
        this.title = title2;
    }

    public int getId() {
        return this.f2Id;
    }

    public void setId(int id) {
        this.f2Id = id;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date2) {
        this.date = date2;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title2) {
        this.title = title2;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o != null && getClass() == o.getClass() && getId() == ((Session) o).getId()) {
            return true;
        }
        return false;
    }

    public String toString() {
        return "Session{Id=" + this.f2Id + ", date=" + this.date + ", title='" + this.title + '\'' + '}';
    }

    public int hashCode() {
        return Objects.hash(new Object[]{Integer.valueOf(getId())});
    }
}
