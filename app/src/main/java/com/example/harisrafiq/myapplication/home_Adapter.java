package com.example.harisrafiq.myapplication;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
//import com.example.sgi.recyclerview.MainActivity;
//import com.example.sgi.recyclerview.R;

import java.util.ArrayList;


public class home_Adapter extends RecyclerView.Adapter<home_Adapter.MyViewHolder> {

    private ArrayList<HomeScreen.homeMenuBtn> data;
    Context context ;

    public home_Adapter(Context context, ArrayList<HomeScreen.homeMenuBtn> data) {
        this.context = context;
        this.data = data;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_btn_item, parent, false);
        MyViewHolder holder1 = new MyViewHolder(view1);
        return holder1;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, final int position) {
        myViewHolder.itemView.setTag(position);
        myViewHolder.nameOfInstitute.setText(data.get(position).nameOfhomeMenuBtn);
        myViewHolder.imageOfInstitution.setImageResource(data.get(position).imageOfhomeMenuBtn);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView nameOfInstitute;
        ImageView imageOfInstitution;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            nameOfInstitute = itemView.findViewById(R.id.institutionname);
            imageOfInstitution = itemView.findViewById(R.id.intitution_image);
        }

        public void onClick(View v) {
            int pos = (Integer) v.getTag();

            switch (nameOfInstitute.getText().toString()){

                case "Notifications":
                    Toast.makeText(v.getContext(), "Notifications CLicked" + pos, Toast.LENGTH_SHORT).show();
                    break;
                case "Diary":
                    Toast.makeText(v.getContext(), "Diary CLicked" + pos, Toast.LENGTH_SHORT).show();
                    break;
                case "Attendance":
                    Toast.makeText(v.getContext(), "Attendance CLicked" + pos, Toast.LENGTH_SHORT).show();
                    break;
                case "School":
                    Toast.makeText(v.getContext(), "School CLicked" + pos, Toast.LENGTH_SHORT).show();
                    break;
                case "Periodical":
                    Toast.makeText(v.getContext(), "Periodical CLicked" + pos, Toast.LENGTH_SHORT).show();
                    break;
                case "Fee Chalans":
                    Toast.makeText(v.getContext(), "Fee Chalans CLicked" + pos, Toast.LENGTH_SHORT).show();
                    break;
                case "Assessment":
                    Toast.makeText(v.getContext(), "Assessment CLicked" + pos, Toast.LENGTH_SHORT).show();
                    break;
                case "Time table":
                    Toast.makeText(v.getContext(), "Time table CLicked" + pos, Toast.LENGTH_SHORT).show();
                    break;
                case "Add User":
                    Toast.makeText(v.getContext(), "Ptm Request CLicked" + pos, Toast.LENGTH_SHORT).show();
                    break;
            }

        }

    }


}

