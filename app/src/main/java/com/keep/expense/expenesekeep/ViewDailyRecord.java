package com.keep.expense.expenesekeep;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bowyer.app.fabtransitionlayout.BottomSheetLayout;
import com.github.clans.fab.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ViewDailyRecord extends AppCompatActivity {

    String strDate;
    SimpleDateFormat sdf;

    int year,monthOfYear,dayOfMonth;

    ListView v;
    ViewRecords_Adapter adapter;
    ViewRecords_newadap newadap;
    DBHelper db;
    Context c;
    EditText search;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter,dateFormatter1;
    Calendar newDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewdailyrecord);
        getActionBar().hide();
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        // strDate = sdf.format(new Date());
        newDate = Calendar.getInstance();
        year=newDate.get(Calendar.YEAR);
        monthOfYear=newDate.get(Calendar.MONTH);
        dayOfMonth=newDate.get(Calendar.DAY_OF_MONTH);
        newDate.set(year, monthOfYear, dayOfMonth);
        strDate=sdf.format(newDate.getTime());
        dateFormatter1 = new SimpleDateFormat("yyyy-MM-dd");

        c=ViewDailyRecord.this;




        v=(ListView)findViewById(R.id.itemsrecords_listView);
        search=(EditText)findViewById(R.id.edt_datef);
        search.setVisibility(View.GONE);
        db=new DBHelper(ViewDailyRecord.this);
        //db.Deleterecord();
       int sum= db.getItemRecord(strDate);

        adapter=new ViewRecords_Adapter(DBHelper.arraylistn1,DBHelper.arraylistn2);
        v.setAdapter(adapter);
        View footerView = ((LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footerview, null, false);
        TextView footersum=(TextView)footerView.findViewById(R.id.txt_footertotal);
        footersum.setText(String.valueOf(sum));
        v.addFooterView(footerView);;


        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                search.setText(dateFormatter1.format(newDate.getTime()));
                db.getItemRecord(dateFormatter1.format(newDate.getTime()));
                if(DBHelper.arraylistn1.size()==0)
                {
                    adapter = new ViewRecords_Adapter(DBHelper.arraylistn1, DBHelper.arraylistn2);
                    v.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(ViewDailyRecord.this);
                    builder1.setTitle("Information");
                    builder1.setMessage("No Record Found");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                else {
                    adapter = new ViewRecords_Adapter(DBHelper.arraylistn1, DBHelper.arraylistn2);
                    v.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

               // Log.e("date",String.valueOf(dateFormatter1.format(newDate.getTime())));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 datePickerDialog.show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_viewdaily_ecord, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
