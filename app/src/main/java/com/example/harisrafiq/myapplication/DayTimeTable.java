package com.example.harisrafiq.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DayTimeTable extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner classSpinner;
    final Calendar myCalendar = Calendar.getInstance();
    Spinner DaySpinner;
    ListView SubjectListView;
    String[] str = {"213123","asdasd","ewrewr","zxczxvxv"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_day_time_table);
        classSpinner = (Spinner) findViewById(R.id.spinnerClassNumber);
        DaySpinner = (Spinner) findViewById(R.id.spinnerDay);
        SubjectListView = (ListView) findViewById(R.id.SubjectListView);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.class_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> dayAdaptor = ArrayAdapter.createFromResource(this,
                R.array.DAYS, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dayAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        classSpinner.setAdapter(adapter);
        classSpinner.setOnItemSelectedListener(this);

        DaySpinner.setAdapter(dayAdaptor);
        DaySpinner.setOnItemSelectedListener(this);

        ListAdapter SubjectListAdaptar = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,str);
        SubjectListView.setAdapter(SubjectListAdaptar);

        SubjectListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                        Calendar mcurrentTime = Calendar.getInstance();
                        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                        int minute = mcurrentTime.get(Calendar.MINUTE);

                        TimePickerDialog mTimePicker;
                        mTimePicker = new TimePickerDialog(DayTimeTable.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                            }
                        }, hour, minute, true);//Yes 24 hour time
                        mTimePicker.setTitle("Select Time");
                        mTimePicker.show();

                    }
                }

        );
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
