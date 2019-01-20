package com.example.harisrafiq.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class DayTimeTable extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner classSpinner;
    final Calendar myCalendar = Calendar.getInstance();
    Spinner DaySpinner;
    ListView SubjectListView;
    ArrayList<String> subjectlist = new ArrayList<String>();
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    Boolean save = false;


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


       DownloadSubjects task = new DownloadSubjects();
        try {
            ErrorClass result = task.execute().get();
            if (result.result.equals(true)){

                ListAdapter SubjectListAdaptar = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,subjectlist);
                SubjectListView.setAdapter(SubjectListAdaptar);

                SubjectListView.setOnItemClickListener(
                        new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


                                Calendar mcurrentTime = Calendar.getInstance();
                                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                                int minute = mcurrentTime.get(Calendar.MINUTE);

                                TimePickerDialog mTimePicker;
                                mTimePicker = new TimePickerDialog(DayTimeTable.this, new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                                        String subjectname = subjectlist.get(position);

                                        String hour = String.valueOf(selectedHour);
                                        String min = String.valueOf(selectedMinute);
                                        String time = hour + ":" + min;
                                        String sub = subjectname +  ":- " + hour + ":" + min;
                                        subjectlist.set(position,sub);
                                        updateView();

                                    }
                                }, hour, minute, true);//Yes 24 hour time
                                mTimePicker.setTitle("Select Time");
                                mTimePicker.show();

                            }
                        }

                );

            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    private void updateView(){
        ListAdapter SubjectListAdaptar = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,subjectlist);
        SubjectListView.setAdapter(SubjectListAdaptar);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private ErrorClass getSubjectList(){

        MongoCollection<Document> coll = mongoDatabase.getCollection(Configuration.tbl_Subject);
        FindIterable<Document> iterDoc = coll.find();

        try {

            Document sub = iterDoc.first();

            subjectlist = (ArrayList<String>) sub.get("Subject");


        } finally {

        }

        ErrorClass err0r = new ErrorClass();
        err0r.result = true;
        err0r.error_message = "OK";
        return err0r;

    }

    private ErrorClass SaveTimeTable(){


        MongoCollection<Document> tbl_TimeTable = mongoDatabase.getCollection(Configuration.tbl_timetable);

        String class_number = classSpinner.getSelectedItem().toString();
        String day = DaySpinner.getSelectedItem().toString();

        Document timetable = new Document();
        timetable.put("class_id",class_number);
        timetable.put("day", day);
        long unixTime = System.currentTimeMillis() / 1000L;
        timetable.put("unixtime",unixTime);

        timetable.put("peroidList",subjectlist);
        tbl_TimeTable.insertOne(timetable);
        ErrorClass err0r = new ErrorClass();
        err0r.result = true;
        err0r.error_message = "Time Table Set Sucessfully";
        return err0r;
    }
    public void SaveTimeTablePressed(View view){

        save = true;
        DownloadSubjects task = new DownloadSubjects();
        try {
            ErrorClass respopnse = task.execute().get();
            if (respopnse.result.equals(true)){

                alertView("Sucess",respopnse.error_message,this);


            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
    public void alertView(String title, String message, Context context) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle(title);
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                        finish();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
    private class DownloadSubjects extends AsyncTask<URL, Integer, ErrorClass> {

        protected ErrorClass doInBackground(URL... urls) {

            mongoClient = new MongoClient(new MongoClientURI(Configuration.databaseAddress));
            mongoDatabase = mongoClient.getDatabase(Configuration.databaseName);


            if (mongoDatabase != null) {


                if (save.equals(false)){

                    return getSubjectList();

                }else {


                   return SaveTimeTable();
                }


            } else {

                ErrorClass err0r = new ErrorClass();
                err0r.result = false;
                err0r.error_message = "Database not found";
                return err0r;

            }

        }
    }

}
