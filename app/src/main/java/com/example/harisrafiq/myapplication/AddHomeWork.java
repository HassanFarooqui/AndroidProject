package com.example.harisrafiq.myapplication;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class AddHomeWork extends AppCompatActivity implements OnItemSelectedListener {

    final Calendar myCalendar = Calendar.getInstance();
    EditText dateedittext;
    Spinner classSpinner;
    EditText homeWork;
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_home_work);
        dateedittext = (EditText) findViewById(R.id.datePicker);
        homeWork = (EditText) findViewById(R.id.etxHomeWork);
        dateedittext.setKeyListener(null);

        dateedittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddHomeWork.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        classSpinner = (Spinner) findViewById(R.id.classSpinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.class_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        classSpinner.setAdapter(adapter);
        classSpinner.setOnItemSelectedListener(this);

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

//        builder1.setNegativeButton(
//                "No",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
    public void SaveHomeWork(View view){

        InsertHomeWork task = new InsertHomeWork();
        try {
            ErrorClass r = task.execute().get();
            alertView("Message",r.error_message,AddHomeWork.this);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateedittext.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class InsertHomeWork extends AsyncTask<URL, Integer, ErrorClass> {

        protected ErrorClass doInBackground(URL... urls) {

            String datentime = dateedittext.getText().toString();
            String classid = classSpinner.getSelectedItem().toString();
            String homework = homeWork.getText().toString();


            if (!datentime.equals("") && !classid.equals("") && !homework.equals("")) {

                mongoClient = new MongoClient(new MongoClientURI(Configuration.databaseAddress));
                mongoDatabase = mongoClient.getDatabase(Configuration.databaseName);

                if (mongoDatabase != null) {

                    MongoCollection<Document> homeworkdataInsert = mongoDatabase.getCollection(Configuration.tbl_Homework);

                    Document hmwork = new Document();
                    hmwork.put("class_id",classid);
                    hmwork.put("date", datentime);
                    long unixTime = System.currentTimeMillis() / 1000L;
                    hmwork.put("unixtime",unixTime);
                    hmwork.put("homework",homework);

                    homeworkdataInsert.insertOne(hmwork);
                    ErrorClass err0r = new ErrorClass();
                    err0r.result = true;
                    err0r.error_message = "HomeWork Send Successfully";
                    return err0r;

                } else {
                    ErrorClass err0r = new ErrorClass();
                    err0r.result = false;
                    err0r.error_message = "Database not found";
                    return err0r;

                }
            } else {

                ErrorClass err0r = new ErrorClass();
                err0r.result = false;
                err0r.error_message = "Please Fill all fields";
                return err0r;
            }
        }

    }

}
