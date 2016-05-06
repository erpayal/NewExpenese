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
 * Created by admin on 10/14/2015.
 */
public class Monthly_adapter extends BaseAdapter{



    ArrayList<String> d,cm,dysum;

    @Override
    public int getCount()
    {
        Log.e("size", String.valueOf(d.size()) + "e");
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
        convertView=inflater.inflate(R.layout.viewrecorditem_m,parent,false);
        TextView txt_m=(TextView)convertView.findViewById(R.id.txt_m2);
        TextView txt_d=(TextView)convertView.findViewById(R.id.txt_m2);
        TextView txt_c=(TextView)convertView.findViewById(R.id.txt_m3);
        TextView txt_a=(TextView)convertView.findViewById(R.id.txt_m4);
        // txt_d.setTypeface(EasyFonts.robotoThin(c));
        // txt_d.setText(d.get(position));
       // txt_c.setText(cm.get(position));

        txt_c.setTypeface(EasyFonts.robotoBold(c));
        //txt_a.setText(String.valueOf(dysum.get(position)));
        txt_a.setTypeface(EasyFonts.robotoThin(c));
        return convertView;
    }
}
