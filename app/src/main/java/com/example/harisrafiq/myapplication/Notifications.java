package com.example.harisrafiq.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Notifications extends AppCompatActivity {


    ListView notificationListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_notifications);
        notificationListView = (ListView) findViewById(R.id.Notification_ListView);
        ListAdapter SubjectListAdaptar = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Configuration.notification_message);
        notificationListView.setAdapter(SubjectListAdaptar);
    }
}
