package com.example.harisrafiq.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.net.URL;



public class AddUserActivity extends AppCompatActivity {

    EditText name;
    EditText email;
    EditText password;
    EditText id;

    MongoClient mongoClient;
    MongoDatabase mongoDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        name = (EditText) findViewById(R.id.etx_username);
        password = (EditText) findViewById(R.id.etx_password);
        id = (EditText) findViewById(R.id.etx_id);
        email = (EditText) findViewById(R.id.etx_email);


    }

    private class InsertUserData extends AsyncTask<URL, Integer, Boolean> {

        protected Boolean doInBackground(URL... urls) {

            boolean totalSize = false;
              String username = name.getText().toString();
             String Paswd = password.getText().toString();
             String email_user = email.getText().toString();
             String roleid = id.getText().toString();

            if (!username.equals("") && !Paswd.equals("") && !roleid.equals("") && email_user.equals("")) {

                mongoClient = new MongoClient(new MongoClientURI("mongodb://192.168.0.109:27017"));
                mongoDatabase = mongoClient.getDatabase("SchoolManagement");

                if (mongoDatabase != null) {

                    MongoCollection<Document> coll = mongoDatabase.getCollection("UserData");

                    Document filter = new Document();
                    filter.put("name", name);
                    filter.put("password", Paswd);
                    FindIterable<Document> iterDoc = coll.find(filter);
                    Document rec = iterDoc.first();
                    if (rec == null) {

                        return false;

                    }


                    String str_name = rec.get("name").toString();
                    String str_password = rec.get("password").toString();

                    if (name.equals(str_name) && Paswd.equals(str_password)) {

                        System.out.print(name);

                        return true;

                    } else {

                        return false;
                    }

                } else {
                    return false;

                }
            } else {

                return false;
            }
        }

    }
}