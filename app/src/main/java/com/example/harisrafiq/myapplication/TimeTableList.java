package com.example.harisrafiq.myapplication;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class TimeTableList extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    TimeTableListAdaptor lviewAdapter;

    private final static String month[] = {"January","February","March","April","May",
            "June","July","August","September","October","November","December"};

    private final static String number[] = {"Month\n1", "Month - 2","Month - 3",
            "Month - 4","Month - 5","Month - 6",
            "Month - 7","Month - 8","Month - 9",
            "Month - 10","Month - 11","Month - 12"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table_list);
        listView = (ListView) findViewById(R.id.timetablelist);
        lviewAdapter = new TimeTableListAdaptor(this, month, number);
        System.out.println("adapter => "+lviewAdapter.getCount());
        listView.setAdapter(lviewAdapter);
        listView.setOnItemClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Add Assignments", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent intent = new Intent(TimeTableList.this,DayTimeTable.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
        // TODO Auto-generated method stub
        Toast.makeText(this,"Title => "+month[position]+"=> n Description"+number[position], Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
