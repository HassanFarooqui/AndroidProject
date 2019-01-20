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
import android.widget.Toast;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.net.URL;
import java.util.concurrent.ExecutionException;

public class AddFeesChallan extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner classnumberSpinner;
    Spinner MonthSpinner;
    EditText etxFeesValue;
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fees_challan);
        classnumberSpinner = (Spinner) findViewById(R.id.spinner_feesChallanClass);
        MonthSpinner = (Spinner) findViewById(R.id.spinner_MonthFeesChallan);
        etxFeesValue = (EditText) findViewById(R.id.editText_for_feesValue);
        ArrayAdapter<CharSequence> classadapter = ArrayAdapter.createFromResource(this,
                R.array.class_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> monthAdaptor = ArrayAdapter.createFromResource(this,
                R.array.MonthName, android.R.layout.simple_spinner_item);

        classadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        classnumberSpinner.setAdapter(classadapter);
        classnumberSpinner.setOnItemSelectedListener(this);

        MonthSpinner.setAdapter(monthAdaptor);
        MonthSpinner.setOnItemSelectedListener(this);


    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void BTN_SaveFeesInToDatabaser(View view){


       // Toast.makeText(AddFeesChallan.this, "BTN_SaveFeesInToDatabaser", Toast.LENGTH_SHORT).show();
        SaveFeesChallanAysnc task = new SaveFeesChallanAysnc();
        try {
            ErrorClass response = task.execute().get();
            if (response.result.equals(true)){
                alertView("Success",response.error_message,this);
            }
            else {

                alertView("Failed",response.error_message,this);
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
    private ErrorClass SaveTimeTable(){


        MongoCollection<Document> tbl_TimeTable = mongoDatabase.getCollection(Configuration.tbl_fessChallan);

        String class_number = classnumberSpinner.getSelectedItem().toString();
        String month = MonthSpinner.getSelectedItem().toString();
        String fees = etxFeesValue.getText().toString();

        if(class_number.equals("") || month.equals("") || fees.equals("")){

            ErrorClass err0r = new ErrorClass();
            err0r.result = false;
            err0r.error_message = "Enter All Fields";
            return err0r;

        }
        Document timetable = new Document();
        timetable.put("class_id",class_number);
        timetable.put("month", month);
        long unixTime = System.currentTimeMillis() / 1000L;
        timetable.put("unixtime",unixTime);

        timetable.put("fees",fees);
        tbl_TimeTable.insertOne(timetable);
        ErrorClass err0r = new ErrorClass();
        err0r.result = true;
        err0r.error_message = " Data Save Sucessfully";
        return err0r;
    }

    private class SaveFeesChallanAysnc extends AsyncTask<URL, Integer, ErrorClass> {

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
