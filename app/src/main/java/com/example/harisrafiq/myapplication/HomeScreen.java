package com.example.harisrafiq.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

//import com.example.sgi.recyclerview.adapters.home_Adapter;

import java.util.ArrayList;


public class HomeScreen extends AppCompatActivity {

    public static  String[] btnName = {
            "Notifications",
            "Diary",
            "Attendance",
            "School",
            "Periodical",
            "Fee Chalans",
            "Assessment",
            "Time table",
            "Ptm Request"};

    public static  int[] btnImage = {
            R.drawable.examicon,
            R.drawable.classicon,
            R.drawable.aricon,
            R.drawable.homeicon,
            R.drawable.profileicon,
            R.drawable.classicon,
            R.drawable.aricon,
            R.drawable.homeicon,
            R.drawable.profileicon};

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        mRecyclerView = findViewById(R.id.homeRV);
        mAdapter = new home_Adapter(this,getData(btnName,btnImage));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    public static ArrayList<homeMenuBtn> getData(String[] btnName , int[] btnImage ) {
        ArrayList<homeMenuBtn> data1 = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            homeMenuBtn current1 = new homeMenuBtn();
            current1.nameOfhomeMenuBtn = btnName[i];
            current1.imageOfhomeMenuBtn = btnImage[i];
            data1.add(current1);
        }
        return data1;
    }

    static public class homeMenuBtn {
        public String nameOfhomeMenuBtn;
        public int imageOfhomeMenuBtn;

    }
}

