package com.example.harisrafiq.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
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

public class Peroidical extends AppCompatActivity {

    ArrayList<String> peroidical_arrayList = new ArrayList<String>();
    ListView peroidicalListView;
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peroidical);
        peroidicalListView = (ListView) findViewById(R.id.PeroidicalListView);
        peroidicalListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                        Toast.makeText(Peroidical.this, "Diary CLicked" + position, Toast.LENGTH_SHORT).show();

                    }
                }

        );

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.peroidicalFab);

        if (Configuration.user.equals(Configuration.parent)){
            fab.hide();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent inten = new Intent(Peroidical.this,AddPeroidicalView.class);
                startActivity(inten);

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        DownloadAllPeroidical task = new DownloadAllPeroidical();
        try {

            ErrorClass errorClass = task.execute().get();

            if(errorClass.result.equals(true)){

                ListAdapter SubjectListAdaptar = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,peroidical_arrayList);
                peroidicalListView.setAdapter(SubjectListAdaptar);


            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class DownloadAllPeroidical extends AsyncTask<URL, Integer, ErrorClass> {

        protected ErrorClass doInBackground(URL... urls) {

            mongoClient = new MongoClient(new MongoClientURI(Configuration.databaseAddress));
            mongoDatabase = mongoClient.getDatabase(Configuration.databaseName);


            if (mongoDatabase != null) {

                MongoCollection<Document> coll = mongoDatabase.getCollection(Configuration.tbl_Peroidical);
                FindIterable<Document> iterDoc = coll.find();

                if (Configuration.user.equals(Configuration.parent)){

                    Document filter = new Document();
                    filter.put("class_id",Configuration.classNumber);
                    iterDoc = coll.find(filter);

                }
                MongoCursor cursor = iterDoc.iterator();
                peroidical_arrayList.clear();

                try {

                    int count = 0;

                    while(cursor.hasNext()) {

                        Document doc = (Document) cursor.next();
                        String classid = doc.getString("class_id");
                        String firstterm = doc.getString("firstterm");
                        String secondterm = doc.getString("secondterm");
                        String thirdterm = doc.getString("thirdterm");
                        String books = doc.getString("books");
                        String display_str = "Class = "+classid+"\nFirstTerm = "+firstterm+"\n"+"SecondTerm = "+secondterm;
                        display_str += "\nThirdTerm = "+thirdterm+"\nBooks = "+books;
                        peroidical_arrayList.add(display_str);
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
