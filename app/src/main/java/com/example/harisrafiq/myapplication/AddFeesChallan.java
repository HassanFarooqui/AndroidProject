package com.example.harisrafiq.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddFeesChallan extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner classnumberSpinner;
    Spinner MonthSpinner;
    EditText etxFeesValue;

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


        Toast.makeText(AddFeesChallan.this, "BTN_SaveFeesInToDatabaser", Toast.LENGTH_SHORT).show();


    }

}
