package com.example.harisrafiq.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.net.URL;
import java.util.concurrent.ExecutionException;


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
        etx_Username.setText("admin");
        etx_Password.setText("admin");


        // alertView("Warning","Please fill all fields",LoginView.this);


    }


    public void startLogin() {

        String name = etx_Username.getText().toString();
        String Paswd = etx_Password.getText().toString();

        if (!name.equals("") && !Paswd.equals("")) {

            mongoClient = new MongoClient(new MongoClientURI("mongodb://192.168.0.109:27017"));

            try {
                mongoClient.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mongoDatabase = mongoClient.getDatabase("SchoolManagement");


            if (mongoDatabase != null) {

                MongoCollection<Document> coll = mongoDatabase.getCollection("UserData");


                Document filter = new Document();
                filter.put("name", name);
                filter.put("password", Paswd);
                FindIterable<Document> iterDoc = coll.find();
                Document rec = iterDoc.first();
                String str_name = rec.get("name").toString();
                String str_password = rec.get("password").toString();

                if (name.equals(str_name) && Paswd.equals(str_password)) {

                    System.out.print(name);
                    Intent intent = new Intent(LoginView.this, HomeScreen.class);
                    startActivity(intent);

                } else {

                    alertView("Warning", "Record not exixts", LoginView.this);
                }


            } else {

                alertView("Warning", "Database error", LoginView.this);
                //  Toast.makeText(LoginView.this, "Database error", Toast.LENGTH_SHORT).show();
            }
        } else {

            alertView("Warning", "Please fill all fields", LoginView.this);
            // Toast.makeText(LoginView.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
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


    public void login_pressed(View view) {

      DownloadFilesTask2 task2 = new DownloadFilesTask2();
        try {
            ErrorClass result = task2.execute().get();
           // Toast.makeText(this,""+result.error_message, Toast.LENGTH_SHORT).show();
            if (result.result.equals(true)){

                Intent intent = new Intent(LoginView.this, HomeScreen.class);
                startActivity(intent);
            }else {

                alertView("Error",result.error_message,LoginView.this);
               // Toast.makeText(this,""+b, Toast.LENGTH_SHORT).show();
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private class DownloadFilesTask2 extends AsyncTask<URL, Integer, ErrorClass> {

        protected ErrorClass doInBackground(URL... urls) {


            String name = etx_Username.getText().toString();
            String Paswd = etx_Password.getText().toString();
            if (!name.equals("") && !Paswd.equals("")) {

                mongoClient = new MongoClient(new MongoClientURI(Configuration.databaseAddress));
                mongoDatabase = mongoClient.getDatabase(Configuration.databaseName);

                if (mongoDatabase != null) {

                    MongoCollection<Document> coll = mongoDatabase.getCollection(Configuration.tbl_userdata);

                    Document filter = new Document();
                    filter.put("name", name);
                    filter.put("password", Paswd);
                    FindIterable<Document> iterDoc = coll.find(filter);
                    Document rec = iterDoc.first();
                    if(rec == null){

                        ErrorClass err0r = new ErrorClass();
                        err0r.result = false;
                        err0r.error_message = "record not exit";
                        return err0r;

                    }


                    String str_name = rec.get("name").toString();
                    String str_password = rec.get("password").toString();

                    if (name.equals(str_name) && Paswd.equals(str_password)) {

                        System.out.print(name);

                        ErrorClass err0r = new ErrorClass();
                        err0r.result = true;
                        err0r.error_message = "success";
                        return err0r;

                    } else {

                        ErrorClass err0r = new ErrorClass();
                        err0r.result = false;
                        err0r.error_message = "Invalid Credentials";
                        return err0r;
                    }

                    } else {
                    ErrorClass err0r = new ErrorClass();
                    err0r.result = false;
                    err0r.error_message = "Database not found";
                    return err0r;

                    }
                } else {

                ErrorClass err0r = new ErrorClass();
                err0r.result = false;
                err0r.error_message = "Please fill all fields";
                return err0r;
                }
            }

    }

}
