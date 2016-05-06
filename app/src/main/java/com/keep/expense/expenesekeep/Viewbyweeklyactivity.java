package com.keep.expense.expenesekeep;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
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
public class Viewbyweeklyactivity extends AppCompatActivity {

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
    String month;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_viewbylist1);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        descp=(TextView)findViewById(R.id.txt_dr1);

        sdf = new SimpleDateFormat("yyyy-MM-dd");
        newDate = Calendar.getInstance();
        year=newDate.get(Calendar.YEAR);
        monthOfYear=newDate.get(Calendar.MONTH);
        dayOfMonth=newDate.get(Calendar.DAY_OF_MONTH);
        newDate.set(year, monthOfYear, dayOfMonth);
        strDate=sdf.format(newDate.getTime());
        dbh=new DatabaseHelper(Viewbyweeklyactivity.this);
        //descp.setText((monthOfYear+1)+","+year);
        descp.setTypeface(EasyFonts.robotoBold(Viewbyweeklyactivity.this));
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM");
        month = sdf1.format(newDate.getTime());
        v=(ListView)findViewById(R.id.category_listView1);

        /*******************************Month Name*****************************************/
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        String month_name = month_date.format(cal.getTime());
        descp.setText(month_name+","+year);
        //

        /****************************************************************/
        ArrayList<HashMap<String,String>> records=dbh.getweek_sum(month, year);
        Log.e("records",String.valueOf(records)+"null"+records.size());
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
        v.setEmptyView(findViewById(R.id.txt_empty_w));
        int sum= dbh.getweekdaysum(month, year);
        TextView t = (TextView) findViewById(R.id.txt_ta1);
        t.setText(String.valueOf(sum));
        t.setTypeface(EasyFonts.robotoBold(Viewbyweeklyactivity.this));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
