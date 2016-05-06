package com.keep.expense.expenesekeep;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.vstechlab.easyfonts.EasyFonts;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 10/13/2015.
 */
public class Viewbysearch extends AppCompatActivity {

    String strDate;
    SimpleDateFormat sdf;
    Calendar newDate;
    int year,monthOfYear,dayOfMonth;
    private DatePickerDialog datePickerDialog;
    ViewRecords_Adapter adapter;

    ListView v;
    DatabaseHelper dbh;
    ArrayList<String> categoryname,date,weeksum;
    TextView descp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_searchview);
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        newDate = Calendar.getInstance();
        year=newDate.get(Calendar.YEAR);
        monthOfYear=newDate.get(Calendar.MONTH);
        dayOfMonth=newDate.get(Calendar.DAY_OF_MONTH);
        newDate.set(year, monthOfYear, dayOfMonth);
        strDate=sdf.format(newDate.getTime());
        dbh=new DatabaseHelper(Viewbysearch.this);
        Intent i=getIntent();
        String dateto=i.getStringExtra("dateto");
        String datefrom=i.getStringExtra("datefrom");
        Log.e("dates",dateto+"-"+datefrom);
          descp=(TextView)findViewById(R.id.txt_ds);
       // descp.setText((monthOfYear + 1) + "," + year);
        descp.setTypeface(EasyFonts.robotoBold(Viewbysearch.this));

        /*******************************Month Name*****************************************/
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        String month_name = month_date.format(cal.getTime());
        descp.setText(month_name+","+year);
        //

        /****************************************************************/
        /****************************************************************/
        v=(ListView)findViewById(R.id.search_listView);

        /****************************************************************/
        ArrayList<HashMap<String,String>> records=dbh.getrecordbetween_dates(dateto,datefrom);
        Log.e("records", String.valueOf(records) + "null" + records.size());
        categoryname=new ArrayList<>();
        date=new ArrayList<>();
        weeksum=new ArrayList<>();
        for(HashMap<String, String> map:records)
        {
            for(Map.Entry<String, String> mapEntry: map.entrySet()) {
                String key = mapEntry.getKey();
                String value = mapEntry.getValue();
                Log.e("key+value", key + "" + value);
                if(key.equals("date"))
                {
                    date.add(value);
                }
                else if(key.equals("name"))
                {
                    categoryname.add(value);
                }
                else
                {
                    weeksum.add(value);
                    Log.e("daysum",String.valueOf(weeksum));
                }

            }}
        adapter=new ViewRecords_Adapter(date,categoryname,weeksum);
        v.setAdapter(adapter);
        v.setEmptyView(findViewById(R.id.txt_empty_s));

    }
}
