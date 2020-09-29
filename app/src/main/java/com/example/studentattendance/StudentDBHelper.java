package com.example.studentattendance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class StudentDBHelper extends SQLiteOpenHelper {
    public static String dbName = "studentsDB";
    public static int shcemaNB = 3;

    public StudentDBHelper(Context context) {
        super(context, dbName, (SQLiteDatabase.CursorFactory) null, shcemaNB);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE students (Id TEXT PRIMARY KEY,firstname TEXT,lastname  TEXT);");
        db.execSQL("CREATE TABLE sessions (Id INTEGER PRIMARY KEY AUTOINCREMENT,date TEXT,title TEXT);");
        db.execSQL("CREATE TABLE Presence (Id INTEGER PRIMARY KEY AUTOINCREMENT,StudentId INTEGER ,SessionId INTEGER );");
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public void emptyDB() {
        getWritableDatabase().execSQL("delete from students");
        getWritableDatabase().execSQL("delete from Sessions");
        getWritableDatabase().execSQL("delete from Presence");
        Class.instance.removeData();
    }

    public void load_Sessions(ArrayList<Session> list) {
        list.clear();
        Cursor res = getReadableDatabase().rawQuery("select * from sessions", (String[]) null);
        Log.d(NotificationCompat.CATEGORY_CALL, "loadSession");
        res.moveToFirst();
        while (!res.isAfterLast()) {
            Date d = null;
            try {
                d = new SimpleDateFormat("yyyy-MM-dd").parse(res.getString(res.getColumnIndex("date")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Session s = new Session(d, res.getString(res.getColumnIndex("title")));
            s.setId(res.getInt(res.getColumnIndex("Id")));
            list.add(s);
            Log.d("session", "adding " + s);
            res.moveToNext();
        }
        Log.d("Sessions", "" + list);
    }

    public void getAttendance(Session s) {
        HashMap<Student, Boolean> attend = new HashMap<>();
        SQLiteDatabase db = getReadableDatabase();
        Log.d("attendStudent", "getting attendance for session " + s);
        Cursor curse = db.rawQuery("select * from Presence where SessionId = " + s.getId(), (String[]) null);
        curse.moveToFirst();
        while (!curse.isAfterLast()) {
            Cursor curseStudents = db.rawQuery("select * from students where Id = " + curse.getInt(curse.getColumnIndex("StudentId")), (String[]) null);
            Log.d("attendStudent", "adding student " + curse.getInt(curse.getColumnIndex("StudentId")) + " for session " + curse.getInt(curse.getColumnIndex("SessionId")));
            curseStudents.moveToFirst();
            attend.put(new Student(curseStudents.getString(1), curseStudents.getString(2), Integer.parseInt(curseStudents.getString(curseStudents.getColumnIndex("Id")))), true);
            curse.moveToNext();
        }
        Class.instance.takeAtt(attend, s);
        Log.d("attendStudent", attend + "");
    }

    public void addmysession(Session s) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put("date", new SimpleDateFormat("yyyy-MM-dd").format(s.getDate()));
        val.put("title", s.getTitle());
        Log.d("addingSession", "adding session date = " + s.getDate() + " and title = " + s.getTitle());
        s.setId((int) db.insert("sessions", (String) null, val));
    }

    public void addPresenceSession(int SessionId, ArrayList<Integer> StudentsPresent) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues StudentVal = new ContentValues();
        Log.d("AddPresence", "session id = " + SessionId + " have presents = " + StudentsPresent);
        Iterator<Integer> it = StudentsPresent.iterator();
        while (it.hasNext()) {
            int StudentId = it.next().intValue();
            StudentVal.put("StudentId", "" + StudentId);
            StudentVal.put("SessionId", "" + SessionId);
            Log.d("AddPresence", "Session id = " + SessionId + "Student id = " + StudentId);
            Log.d("UltimateAddTest", "StudentId = " + StudentId + " and SessionId = " + SessionId + " added to database");
            db.insert("Presence", (String) null, StudentVal);
        }
        Iterator<Student> it2 = Class.instance.SubscribedStudents.iterator();
        while (it2.hasNext()) {
            Student s = it2.next();
            s.getAttendance().add(Boolean.valueOf(StudentsPresent.contains(Integer.valueOf(s.getSchoolId()))));
        }
    }

    public void addStudent(Student s) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues StudentVal = new ContentValues();
        StudentVal.put("Id", "" + s.getSchoolId());
        StudentVal.put("firstname", s.getFirstName());
        StudentVal.put("lastname", s.getLastName());
        db.insert("students", (String) null, StudentVal);
        Log.d("students", "" + s);
    }

    public void load_Students(ArrayList<Student> list) {
        ArrayList<Student> arrayList = list;
        Cursor res = getReadableDatabase().rawQuery("select * from students", (String[]) null);
        Cursor Sessions = getReadableDatabase().rawQuery("select * from sessions order by Id", (String[]) null);
        arrayList.removeAll(arrayList);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            Sessions.moveToFirst();
            int Id = Integer.parseInt(res.getString(res.getColumnIndex("Id")));
            Student s = new Student(res.getString(1), res.getString(2), Id);
            while (!Sessions.isAfterLast()) {
                SQLiteDatabase readableDatabase = getReadableDatabase();
                Cursor presence = readableDatabase.rawQuery("select count(*) from Presence where StudentId = " + Id + " and SessionId = " + Sessions.getInt(Sessions.getColumnIndex("Id")), (String[]) null);
                presence.moveToFirst();
                boolean z = false;
                s.setPresence(presence.getInt(presence.getColumnIndex("count(*)")) == 1);
                StringBuilder sb = new StringBuilder();
                sb.append("the student ");
                sb.append(s);
                sb.append(" attendanceVerify for session ");
                sb.append(Sessions.getInt(Sessions.getColumnIndex("Id")));
                sb.append(" is ");
                if (presence.getInt(presence.getColumnIndex("count(*)")) == 1) {
                    z = true;
                }
                sb.append(z);
                Log.d("attendanceVerify", sb.toString());
                Sessions.moveToNext();
            }
            arrayList.add(s);
            res.moveToNext();
        }
    }
}
