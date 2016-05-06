package com.keep.expense.expenesekeep;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by admin on 10/14/2015.
 */
public class DailyAlram extends AppCompatActivity{

    PendingIntent pendingIntent;
    TimePickerDialog timePickerDialog;
    AlarmManager alarmManager;
    ImageButton img_btime;
    EditText edt_time;
    Spinner rspinner;
    String[ ] s_string={"Select Time For Repeating Alram","12 Hour","24 Hour"};
    ArrayAdapter<String> sadapter;
    Button setalram,stopalram;
    Calendar calSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dailyreminder);
        AdView mAdView = (AdView) findViewById(R.id.adView_dr);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        img_btime=(ImageButton)findViewById(R.id.imgbttime);
        edt_time=(EditText)findViewById(R.id.edt_time);
        setalram=(Button)findViewById(R.id.button_startalram);
        stopalram=(Button)findViewById(R.id.button_stopalram);

       alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(this , AlramRecevier.class);

        pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0);

//get time from database and initialise the variables.
        int minute;
        int hour;

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE,10);
        calendar.set(Calendar.HOUR, 9);
        calendar.set(Calendar.AM_PM, Calendar.PM);    //set accordingly
        calendar.add(Calendar.DAY_OF_YEAR, 0);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String test = sdf.format(cal.getTime());
        edt_time.setText(test);



        final Calendar newCalendar = Calendar.getInstance();
        timePickerDialog =new TimePickerDialog(DailyAlram.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                Calendar calNow = Calendar.getInstance();
                calSet = (Calendar) calNow.clone();

                calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calSet.set(Calendar.MINUTE, minute);
                calSet.set(Calendar.SECOND, 0);
                calSet.set(Calendar.MILLISECOND, 0);

                if(calSet.compareTo(calNow) <= 0){
                    //Today Set time passed, count to tomorrow
                    calSet.add(Calendar.DATE, 1);
                }

                edt_time.setText(hourOfDay +":"+minute);

            }
        },newCalendar.get(Calendar.HOUR_OF_DAY),newCalendar.get(Calendar.MINUTE),false);
        timePickerDialog.setTitle("Set Alarm Time");

        img_btime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show();
            }
        });
        setalram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.e("time", String.valueOf(calSet.getTimeInMillis()));
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                    Toast.makeText(getApplicationContext(),"Alram is set for a day",Toast.LENGTH_LONG).show();
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(),"Set proper timing for alram",Toast.LENGTH_LONG).show();
                }

            }
        });

        stopalram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmManager.cancel(pendingIntent);
                Toast.makeText(getApplicationContext(),"Alram is canceled",Toast.LENGTH_LONG).show();
            }
        });





    }
}
