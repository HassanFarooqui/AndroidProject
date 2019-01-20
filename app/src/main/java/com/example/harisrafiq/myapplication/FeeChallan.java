package com.example.harisrafiq.myapplication;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;


public class FeeChallan extends AppCompatActivity {


    ArrayList<String> feeChallan = new ArrayList<String>();
    ListView feesChallanListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_challan);
        feesChallanListView = (ListView) findViewById(R.id.feesChallanListView);

        for (int i = 0;i<20;i++){

            feeChallan.add("feesChallan");

        }
        ListAdapter SubjectListAdaptar = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,feeChallan);
        feesChallanListView.setAdapter(SubjectListAdaptar);
        feesChallanListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                        Toast.makeText(FeeChallan.this, "Diary CLicked" + position, Toast.LENGTH_SHORT).show();

                    }
                }

        );

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.feesFab);

        if (Configuration.user.equals(Configuration.parent)){
            fab.hide();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Snackbar.make(view, "Add Assignments", Snackbar.LENGTH_LONG)
                //       .setAction("Action", null).show();

                Intent inten = new Intent(FeeChallan.this,AddFeesChallan.class);
                startActivity(inten);


            }
        });




    }
}
