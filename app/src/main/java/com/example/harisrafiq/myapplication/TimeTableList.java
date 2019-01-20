package com.example.harisrafiq.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class TimeTableList extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    TimeTableListAdaptor lviewAdapter;
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;

//     String month[] = {"January","February","March","April","May",
//            "June","July","August","September","October","November","December"};
//
//    String number[] = {"Month\n1", "Month - 2","Month - 3",
//            "Month - 4","Month - 5","Month - 6",
//            "Month - 7","Month - 8","Month - 9",
//            "Month - 10","Month - 11","Month - 12"};


    ArrayList<String> classandday = new ArrayList<String>();
    ArrayList<String> timetablestr = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table_list);

        listView = (ListView) findViewById(R.id.timetablelist);


        listView.setOnItemClickListener(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (Configuration.user.equals(Configuration.parent)){
            fab.hide();
        }


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Add Assignments", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent intent = new Intent(TimeTableList.this,DayTimeTable.class);
                startActivity(intent);

            }
        });

        DownloadAllTimeTableList task = new DownloadAllTimeTableList();
        try {
            ErrorClass respose = task.execute().get();
            if (respose.result.equals(true)){

                lviewAdapter = new TimeTableListAdaptor(this, classandday,timetablestr);


                listView.setAdapter(lviewAdapter);

            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
     //   Toast.makeText(this,"Title => "+month[position]+"=> n Description"+number[position], Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    private class DownloadAllTimeTableList extends AsyncTask<URL, Integer, ErrorClass> {

        protected ErrorClass doInBackground(URL... urls) {

            mongoClient = new MongoClient(new MongoClientURI(Configuration.databaseAddress));
            mongoDatabase = mongoClient.getDatabase(Configuration.databaseName);


            if (mongoDatabase != null) {

                MongoCollection<Document> coll = mongoDatabase.getCollection(Configuration.tbl_timetable);
                FindIterable<Document> iterDoc = coll.find();

                if (Configuration.user.equals(Configuration.parent)){

                    Document filter = new Document();
                    filter.put("class_id",Configuration.classNumber);
                    iterDoc = coll.find(filter);

                }
                MongoCursor cursor = iterDoc.iterator();

                try {
                    int count = 0;

                    while(cursor.hasNext()) {

                        Document doc = (Document) cursor.next();
                        String classid = doc.getString("class_id");
                        String day = doc.getString("day");
                        ArrayList<String> list = (ArrayList<String>) doc.get("peroidList");
                         String classDay = "Class = " +classid+ " Day = "+day;
                        classandday.add(classDay);
                        String p = "\n";
                        for (int i = 0;i < list.size();i++){

                             p += list.get(i) + "\n";

                        }
                        timetablestr.add(p);
                        count = count + 1;

                    }

                } finally {

                    cursor.close();

                }

                ErrorClass err0r = new ErrorClass();
                err0r.result = true;
                err0r.error_message = "OK";
                return err0r;


            } else {

                ErrorClass err0r = new ErrorClass();
                err0r.result = false;
                err0r.error_message = "Database not found";
                return err0r;

            }

        }
    }

}
