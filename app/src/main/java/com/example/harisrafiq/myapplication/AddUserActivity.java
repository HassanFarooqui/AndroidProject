package com.example.harisrafiq.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.net.URL;
import java.util.concurrent.ExecutionException;


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

        name = (EditText) findViewById(R.id.etx_name);
        password = (EditText) findViewById(R.id.etx_password);
        id = (EditText) findViewById(R.id.etx_id);
        email = (EditText) findViewById(R.id.etx_email);

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
    public void saveUser(View view){

        InsertUserData task = new InsertUserData();
        try {
            ErrorClass r = task.execute().get();
            alertView("Message",r.error_message,AddUserActivity.this);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    private class InsertUserData extends AsyncTask<URL, Integer, ErrorClass> {


        protected Boolean idIsValid(String id){
            Boolean result = false;
            String ad = id.substring(0,2);
            if(ad.equals(Configuration.checkAdmin)){

                result = true;
            }

            String parent = id.substring(0,1);
            String[] arr = Configuration.checkParent;
            for (int i = 0;i < arr.length; i++){

                if (arr[i].equals(parent)){

                    result = true;
                    break;
                }

            }
            return result;
        }
        protected ErrorClass doInBackground(URL... urls) {

             String username = name.getText().toString();
             String Paswd = password.getText().toString();
             String email_user = email.getText().toString();
             String roleid = id.getText().toString();



            if (!username.equals("") && !Paswd.equals("") && !roleid.equals("") && !email_user.equals("")) {


                if (idIsValid(roleid) == false){

                    ErrorClass err0r = new ErrorClass();
                    err0r.result = false;
                    err0r.error_message = "You Enter Invalid ID";
                    return err0r;

                }

                mongoClient = new MongoClient(new MongoClientURI(Configuration.databaseAddress));
                mongoDatabase = mongoClient.getDatabase(Configuration.databaseName);

                if (mongoDatabase != null) {

                    MongoCollection<Document> UserDataInsert = mongoDatabase.getCollection(Configuration.tbl_userdata);

                    Document newUser = new Document();
                    newUser.put("name", username);
                    newUser.put("password", Paswd);
                    newUser.put("id",roleid);
                    newUser.put("email",email_user);

                    UserDataInsert.insertOne(newUser);
                    ErrorClass err0r = new ErrorClass();
                    err0r.result = true;
                    err0r.error_message = "Add User Successfully";
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