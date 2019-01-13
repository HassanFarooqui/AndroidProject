package com.example.harisrafiq.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AssignmentAdapter extends BaseAdapter
{
    Activity context;
    ArrayList<String> date;
    ArrayList<String> class_number;
    ArrayList<String> homeworkDes;

    public AssignmentAdapter(Activity context, ArrayList<String> date, ArrayList<String> classnumber,ArrayList<String> homework) {
        super();
        this.context = context;
        this.date = date;
        this.class_number = classnumber;
        this.homeworkDes = homework;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return date.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    private class ViewHolder {
        TextView txtViewTitle;
        TextView txtViewDescription;
        TextView txtHomeDes;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        // TODO Auto-generated method stub
        ViewHolder holder;
        LayoutInflater inflater =  context.getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.asignmentdatalist, null);
            holder = new ViewHolder();
            holder.txtViewTitle = (TextView) convertView.findViewById(R.id.txtvClass);
            holder.txtViewDescription = (TextView) convertView.findViewById(R.id.txtvDate);
            holder.txtHomeDes = (TextView) convertView.findViewById(R.id.txtvHomeWorkDiscription);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtViewTitle.setText(date.get(position));
        holder.txtViewDescription.setText(class_number.get(position));
        holder.txtHomeDes.setText(homeworkDes.get(position));

        return convertView;
    }

}
