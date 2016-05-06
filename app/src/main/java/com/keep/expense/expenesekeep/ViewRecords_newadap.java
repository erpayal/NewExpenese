package com.keep.expense.expenesekeep;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by admin on 10/7/2015.
 */
public class ViewRecords_newadap extends BaseAdapter {



    String key,value;

ArrayList<String> values,keys;
    public ViewRecords_newadap(ArrayList<String> key, ArrayList<String> value) {
        keys=key;
        values=value;
    }

    public ViewRecords_newadap(ArrayList<String> listvalue) {
        values=listvalue;
    }

    @Override
    public int getCount()
    {
        return values.size();

    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Context c=parent.getContext();

        LayoutInflater inflater=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.viewrecorditem,parent,false);
        TextView txt_c=(TextView)convertView.findViewById(R.id.txt_date);
        TextView txt_a=(TextView)convertView.findViewById(R.id.txt_tamount);
        txt_c.setText(values.get(position));
        txt_a.setText(keys.get(position));
        return convertView;
    }
}
