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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 10/13/2015.
 */
public class ViewBymonthlyact  extends AppCompatActivity {

    String strDate,month;
    SimpleDateFormat sdf;
    Calendar newDate;
    int year,monthOfYear,dayOfMonth;
    private DatePickerDialog datePickerDialog;
    ViewRecords_Adapter adapter;

    ListView v;
    DatabaseHelper dbh;
    ArrayList<String> categoryname,date,monthsum;
    TextView descp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_viewbylist2);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        descp=(TextView)findViewById(R.id.txt_dr2);
        descp.setTypeface(EasyFonts.robotoBold(ViewBymonthlyact.this));
        sdf = new SimpleDateFormat("yyyy-MM-dd");

        newDate = Calendar.getInstance();
        year=newDate.get(Calendar.YEAR);
        monthOfYear=newDate.get(Calendar.MONTH);

        dayOfMonth=newDate.get(Calendar.DAY_OF_MONTH);
        newDate.set(year, monthOfYear, dayOfMonth);
        strDate=sdf.format(newDate.getTime());

        SimpleDateFormat sdf1 = new SimpleDateFormat("MM");
         month = sdf1.format(newDate.getTime());

        /*********************Month Name**************/
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        String month_name = month_date.format(cal.getTime());
        descp.setText(month_name+","+year);
        dbh=new DatabaseHelper(ViewBymonthlyact.this);

        v=(ListView)findViewById(R.id.category_listView2);

        /****************************************************************/


        ArrayList<HashMap<String,String>> records=dbh.getmonth_sum(month, year);
        Log.e("month",String.valueOf((month)));
        Log.e("records", String.valueOf(records) + "null" + records.size());
        categoryname=new ArrayList<>();
        date=new ArrayList<>();
        monthsum=new ArrayList<>();
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
                    monthsum.add(value);
                    Log.e("daysum",String.valueOf(monthsum));
                }

            }}
        adapter=new ViewRecords_Adapter(date,categoryname,monthsum);
        v.setAdapter(adapter);
        v.setEmptyView(findViewById(R.id.txt_empty_m));
        int sum= dbh.getmonthdaysum(month, year);
        TextView t = (TextView) findViewById(R.id.txt_ta2);
        t.setText(String.valueOf(sum));
        t.setTypeface(EasyFonts.robotoBold(ViewBymonthlyact.this));


        v.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


                AlertDialog.Builder builder1 = new AlertDialog.Builder(ViewBymonthlyact.this);
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

                                ArrayList<HashMap<String,String>> records=dbh.getmonth_sum(month, year);
                                Log.e("records", String.valueOf(records) + "null" + records.size());
                                categoryname=new ArrayList<>();
                                date=new ArrayList<>();
                                monthsum=new ArrayList<>();
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
                                            monthsum.add(value);
                                            Log.e("daysum",String.valueOf(monthsum));
                                        }

                                    }}
                                adapter=new ViewRecords_Adapter(date,categoryname,monthsum);
                                v.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                int sum= dbh.getmonthdaysum(month, year);
                                TextView t = (TextView) findViewById(R.id.txt_ta2);
                                t.setText(String.valueOf(sum));
                                t.setTypeface(EasyFonts.robotoBold(ViewBymonthlyact.this));

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

