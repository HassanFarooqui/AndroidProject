package com.example.harisrafiq.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class FeeChallan extends AppCompatActivity {


    ArrayList<String> feeChallan = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_challan);
    }
}
