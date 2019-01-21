package com.example.harisrafiq.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.net.URL;

public class AddPeroidicalView extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText books;
    EditText firstterm;
    EditText secondterm;
    EditText thirdterm;
    Spinner spin_classNumber;
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_peroidical_view);
        books = (EditText) findViewById(R.id.etx_booksDetails);
        firstterm = (EditText) findViewById(R.id.etx_firsterm);
        secondterm = (EditText) findViewById(R.id.etx_secondterm);
        thirdterm = (EditText) findViewById(R.id.etx_thirdterm);
        spin_classNumber = (Spinner) findViewById(R.id.spin_peroidicalClass);
        ArrayAdapter<CharSequence> classadapter = ArrayAdapter.createFromResource(this,
                R.array.class_array, android.R.layout.simple_spinner_item);
        classadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_classNumber.setAdapter(classadapter);
        spin_classNumber.setOnItemSelectedListener(this);
    }

    public void SavePeroidical(View view){


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
    private ErrorClass SaveTimeTable(){


        MongoCollection<Document> tbl_TimeTable = mongoDatabase.getCollection(Configuration.tbl_Peroidical);

        String class_number = spin_classNumber.getSelectedItem().toString();
        String firstassigment = firstterm.getText().toString();
        String second = secondterm.getText().toString();
        String third = thirdterm.getText().toString();
        String kitab = books.getText().toString();


        Document timetable = new Document();
        timetable.put("class_id",class_number);
        timetable.put("firstterm", firstassigment);
        long unixTime = System.currentTimeMillis() / 1000L;
        timetable.put("unixtime",unixTime);
        timetable.put("secondterm",second);
        timetable.put("thirdterm",third);
        timetable.put("books",kitab);

        tbl_TimeTable.insertOne(timetable);
        ErrorClass err0r = new ErrorClass();
        err0r.result = true;
        err0r.error_message = " Data Save Sucessfully";
        return err0r;
    }


    private class SavePeroidicals extends AsyncTask<URL, Integer, ErrorClass> {

        protected ErrorClass doInBackground(URL... urls) {

            mongoClient = new MongoClient(new MongoClientURI(Configuration.databaseAddress));
            mongoDatabase = mongoClient.getDatabase(Configuration.databaseName);


            if (mongoDatabase != null) {

                return SaveTimeTable();

            } else {

                ErrorClass err0r = new ErrorClass();
                err0r.result = false;
                err0r.error_message = "Database not found";
                return err0r;

            }

        }
    }
}
