package com.keep.expense.expenesekeep;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 9/24/2015.
 */
public class ViewRecords extends AppCompatActivity {

    ListView v;
    ViewRecords_Adapter adapter;
    ViewRecords_newadap newadap;
    DBHelper db;
    ArrayList<String> a=new ArrayList<>();
    ArrayList<String> a1=new ArrayList<>();
    Context c;
    String s_months[]={"Select month","January","February", "March","April","May","June","July","August","September","October","November","December"};

    Spinner sp_months;
    String mMonths[]={"Select month","January","February", "March","April","May","June","July","August","September","October","November","December"};
    ArrayAdapter<String> spinneradapter;

    Button balancesheet,graphview;
    String strDate;
    SimpleDateFormat sdf;
    Calendar newDate;
    int year,monthOfYear,dayOfMonth;
    ArrayList<HashMap<String, String>> searchresults=new ArrayList<>();
    ArrayList<String> keylist,listvalue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewrecord);
        /*******************Date**************************/
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        // strDate = sdf.format(new Date());
        newDate = Calendar.getInstance();
        year=newDate.get(Calendar.YEAR);
        monthOfYear=newDate.get(Calendar.MONTH);
        dayOfMonth=newDate.get(Calendar.DAY_OF_MONTH);
        newDate.set(year, monthOfYear, dayOfMonth);
        strDate=sdf.format(newDate.getTime());

        c=ViewRecords.this;

        sp_months=(Spinner)findViewById(R.id.spinner_mr);
        spinneradapter=new ArrayAdapter<String>(ViewRecords.this,android.R.layout.simple_spinner_dropdown_item,mMonths);
        sp_months.setAdapter(spinneradapter);


        v=(ListView)findViewById(R.id.records_listView);
        db=new DBHelper(ViewRecords.this);
        db.getTotal(newDate.get(Calendar.MONTH)+1,year);
        int s =new GetterSetter().getDate().size();
        Log.e("e",String.valueOf(s));
        adapter=new ViewRecords_Adapter(DBHelper.array_list,DBHelper.array_list1);
        v.setAdapter(adapter);
      /**  View footerView = ((LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footerview, null, false);
       v.addFooterView(footerView);**/

        balancesheet=(Button)findViewById(R.id.bt_balancesheet);
        graphview=(Button)findViewById(R.id.bt_graphview);
        balancesheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Balancesheet.class);
                startActivity(i);
            }
        });
        graphview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), graphactivity.class);
                startActivity(i);
            }
        });



        sp_months.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {

                    keylist=new ArrayList<String>();
                    listvalue=new ArrayList<String>();
                    if (position > 0) {
                      searchresults= db.getsearch_month(position,year);
                        for(HashMap<String, String> map: searchresults) {
                            //listvalue.add(map.get("date"));
                           // key.add(map.get("total"));
                            for(Map.Entry<String, String> mapEntry: map.entrySet()) {
                                String key = mapEntry.getKey();
                                String value = mapEntry.getValue();
                                Log.e("key+value", key + "" + value);
                                if (key.equals("date"))
                                    keylist.add(value);
                                else
                                    listvalue.add(value);
                            }

                            }
                        newadap = new ViewRecords_newadap(listvalue,keylist);
                        v.setAdapter(newadap );
                        newadap.notifyDataSetChanged();


                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "No record Found Please try Again", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Stuff to do, dependent on requestCode and resultCode
        if(requestCode == 1)  // 1 is an arbitrary number, can be any int
        {
            // This is the return result of your DialogFragment
            if(resultCode == 1) // 1 is an arbitrary number, can be any int
            {
                adapter=new ViewRecords_Adapter(new GetterSetter().getDate_1(),new GetterSetter().getTime_1());
                v.setAdapter(adapter);

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      if(item.getItemId()==R.id.action_search)
      {
          Dialogfordate dm=new Dialogfordate();
          FragmentManager fm=getFragmentManager();

          dm.setTargetFragment(dm, 1);
          dm.show(fm, "Fragment");
      }
        return super.onOptionsItemSelected(item);
    }
}
