package com.keep.expense.expenesekeep;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vstechlab.easyfonts.EasyFonts;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 10/13/2015.
 */
public class Viewbydailyactivity extends AppCompatActivity {

    String strDate;
    SimpleDateFormat sdf;
    Calendar newDate;
    int year,monthOfYear,dayOfMonth;
    private DatePickerDialog datePickerDialog;
    ViewRecords_Adapter adapter;

    ListView v;
    DatabaseHelper dbh;
    ArrayList<String> categoryname,date,daysum;
    TextView descp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_viewbylist);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        descp=(TextView)findViewById(R.id.txt_dr);
        descp.setTypeface(EasyFonts.robotoBold(Viewbydailyactivity.this));
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        newDate = Calendar.getInstance();
        year=newDate.get(Calendar.YEAR);
        monthOfYear=newDate.get(Calendar.MONTH);
        dayOfMonth=newDate.get(Calendar.DAY_OF_MONTH);
        newDate.set(year, monthOfYear, dayOfMonth);
        strDate=sdf.format(newDate.getTime());
        dbh=new DatabaseHelper(Viewbydailyactivity.this);
        /******************************MOnth Name*********************/
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        String month_name = month_date.format(cal.getTime());
        descp.setText(dayOfMonth+" "+month_name+","+year);
        //
        v=(ListView)findViewById(R.id.category_listView);

        /****************************************************************/
        ArrayList<HashMap<String,String>> records=dbh.daily_catrecord(dayOfMonth,monthOfYear+1,year);
             Log.e("records",String.valueOf(records)+"null"+records.size());
        categoryname=new ArrayList<>();
        date=new ArrayList<>();
        daysum=new ArrayList<>();
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
                daysum.add(value);
                Log.e("daysum",String.valueOf(daysum));
            }

        }}
        adapter=new ViewRecords_Adapter(date,categoryname,daysum);
        v.setAdapter(adapter);
        v.setEmptyView(findViewById(R.id.txt_empty));

       int sum= dbh.getdailysum(dayOfMonth,monthOfYear+1,year);
        Log.e("month_yr",String.valueOf(monthOfYear+1));
        TextView t = (TextView) findViewById(R.id.txt_ta);
        t.setText(String.valueOf(sum));
        t.setTypeface(EasyFonts.robotoBold(Viewbydailyactivity.this));


        v.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


                AlertDialog.Builder builder1 = new AlertDialog.Builder(Viewbydailyactivity.this);
                builder1.setTitle("Delete item");
                builder1.setMessage("Are you sure ?You want to Delete");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String cname = categoryname.get(position);
                                String d = date.get(position);
                                dbh.deleteitem(d, cname);
                                Toast.makeText(getApplicationContext(), "Expense is deleted!!", Toast.LENGTH_LONG).show();

                                ArrayList<HashMap<String,String>> records=dbh.daily_catrecord(dayOfMonth,monthOfYear+1,year);
                                Log.e("records",String.valueOf(records)+"null"+records.size());
                                categoryname=new ArrayList<>();
                                date=new ArrayList<>();
                                daysum=new ArrayList<>();
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
                                            daysum.add(value);
                                            Log.e("daysum",String.valueOf(daysum));
                                        }

                                    }}
                                adapter=new ViewRecords_Adapter(date,categoryname,daysum);
                                v.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                                int sum= dbh.getdailysum(dayOfMonth,monthOfYear+1,year);
                                TextView t = (TextView) findViewById(R.id.txt_ta);
                                t.setText(String.valueOf(sum));
                                t.setTypeface(EasyFonts.robotoBold(Viewbydailyactivity.this));

                            }
                        });
                builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

                AlertDialog alert11 = builder1.create();
                alert11.show();



            }
        });

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
