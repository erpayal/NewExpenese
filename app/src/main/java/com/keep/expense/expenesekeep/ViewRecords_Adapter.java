package com.keep.expense.expenesekeep;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vstechlab.easyfonts.EasyFonts;

import java.util.ArrayList;

/**
 * Created by admin on 9/24/2015.
 */
public class ViewRecords_Adapter extends BaseAdapter{



    ArrayList<String> d,cm,dysum;
    public ViewRecords_Adapter(ArrayList<String> date, ArrayList<String> time) {
        d=date;
        cm=time;
    }

    public ViewRecords_Adapter(String key, String value) {

    }

    public ViewRecords_Adapter() {

    }

    public ViewRecords_Adapter(ArrayList<String> date, ArrayList<String> categoryname, ArrayList<String> daysum) {

        d=date;
        cm=categoryname;
        dysum=daysum;
    }

    @Override
    public int getCount()
    {
        Log.e("size",String.valueOf(d.size())+"e");
        return d.size();

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
        TextView txt_d=(TextView)convertView.findViewById(R.id.txt_date1);
        TextView txt_c=(TextView)convertView.findViewById(R.id.txt_date);
        TextView txt_a=(TextView)convertView.findViewById(R.id.txt_tamount);
        txt_d.setTypeface(EasyFonts.robotoBold(c));
        txt_d.setText(d.get(position));
          txt_c.setText(cm.get(position));

        txt_c.setTypeface(EasyFonts.robotoBold(c));
        txt_a.setText(String.valueOf(dysum.get(position)));
        txt_a.setTypeface(EasyFonts.robotoBold(c));
        return convertView;
    }
}
