package com.example.harisrafiq.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Toast;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.Iterator;


public class LoginView extends AppCompatActivity {

    MongoClient mongoClient;
    MongoDatabase mongoDatabase;

    EditText etx_Username;
    EditText etx_Password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_view);

        etx_Username = (EditText) findViewById(R.id.etx_username);
        etx_Password = (EditText) findViewById(R.id.etx_password);

    }
    public void login_pressed(View view){

        String name = etx_Username.getText().toString();
        String Paswd = etx_Password.getText().toString();
        if (name != "" && Paswd != ""){

            Toast toast = Toast.makeText(this, "Loading....", Toast.LENGTH_LONG);
            toast.show();

            mongoClient = new MongoClient(new MongoClientURI("mongodb://192.168.0.106:27017"));
            mongoDatabase = mongoClient.getDatabase("SchoolManagement");

            if (mongoDatabase != null){
                MongoCollection<Document> coll = mongoDatabase.getCollection("UserData");
                FindIterable<Document> iterDoc = coll.find( Filters.eq("name",name));
                Iterator it = iterDoc.iterator();

            }
        }




    }
    class DownloadFilesTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            mongoClient = new MongoClient(new MongoClientURI("mongodb://192.168.0.106:27017"));
            mongoDatabase = mongoClient.getDatabase("SchoolManagement");

            if (mongoDatabase != null){
                MongoCollection<Document> coll = mongoDatabase.getCollection("UserData");
                coll.find();
                //coll.drop();
            }
            return null;
        }

    }
}
