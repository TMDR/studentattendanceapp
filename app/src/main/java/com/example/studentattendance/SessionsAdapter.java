package com.example.studentattendance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SessionsAdapter extends ArrayAdapter<Session> {
    ArrayList<Session> list;

    public SessionsAdapter(Context context, ArrayList<Session> list2) {
        super(context, 17367043);
        this.list = list2;
    }

    public View getView(int position, View row, ViewGroup parent) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.session_row, parent, false);
        ((TextView) v.findViewById(R.id.SId)).setText("" + (position + 1));
        ((TextView) v.findViewById(R.id.STitle)).setText(this.list.get(position).getTitle());
        ((TextView) v.findViewById(R.id.SDate)).setText(new SimpleDateFormat("yyyy-MM-dd").format(this.list.get(position).getDate()));
        return v;
    }

    public int getCount() {
        return this.list.size();
    }
}
