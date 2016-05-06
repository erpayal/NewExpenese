package com.keep.expense.expenesekeep;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 10/12/2015.
 */
public class Viewbydaily extends Fragment {
    String strDate;
    SimpleDateFormat sdf;
   Calendar newDate;
    int year,monthOfYear,dayOfMonth;
    private DatePickerDialog datePickerDialog;
    ViewRecords_Adapter adapter;

    ListView v;
    DatabaseHelper dbh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View d=inflater.inflate(R.layout.frag_viewbylist,container,false);
        /************************init***************/
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        newDate = Calendar.getInstance();
        year=newDate.get(Calendar.YEAR);
        monthOfYear=newDate.get(Calendar.MONTH);
        dayOfMonth=newDate.get(Calendar.DAY_OF_MONTH);
        newDate.set(year, monthOfYear, dayOfMonth);
        strDate=sdf.format(newDate.getTime());
        dbh=new DatabaseHelper(getActivity());

        v=(ListView)d.findViewById(R.id.category_listView);

       /************
      HashMap<String,String> records=dbh.daily_catrecord(dayOfMonth,monthOfYear+1,year);

        for(Map.Entry<String, String> mapEntry: records.entrySet()) {
            String key = mapEntry.getKey();
            String value = mapEntry.getValue();
            Log.e("key+value", key + "" + value);
        }*/
        //adapter=new ViewRecords_Adapter();
      // v.setAdapter(adapter);
       //View footerView = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footerview, null, false);
       // TextView footersum=(TextView)footerView.findViewById(R.id.txt_footertotal);
      // footersum.setText(String.valueOf(sum));
      // v.addFooterView(footerView);






        return d;
    }
}
