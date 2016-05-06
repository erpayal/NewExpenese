package com.keep.expense.expenesekeep;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by admin on 10/15/2015.
 */
public class Searchclass extends AppCompatActivity {


    EditText dateto,datefrom;
    ImageButton im_bto,img_bfrom;
    String strDate;
    String intMonth;
    Date date;
    SimpleDateFormat sdf;
    Calendar newDate;
    int year,monthOfYear,dayOfMonth;
    DatabaseHelper dbh;
    DatePickerDialog datePickerDialog,datePickerDialog1;
    Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchrecord);
        dateto=(EditText)findViewById(R.id.edt_sdate);
        datefrom=(EditText)findViewById(R.id.edt_sdate1);
        im_bto=(ImageButton)findViewById(R.id.imageButton1);
        img_bfrom=(ImageButton)findViewById(R.id.imageButton2);
        search=(Button)findViewById(R.id.button_search);



    /***********Date*********************/
    sdf = new SimpleDateFormat("yyyy-MM-dd");
    // strDate = sdf.format(new Date());
    newDate = Calendar.getInstance();
    year = newDate.get(Calendar.YEAR);
    monthOfYear = newDate.get(Calendar.MONTH);
    dayOfMonth = newDate.get(Calendar.DAY_OF_MONTH);
    newDate.set(year, monthOfYear, dayOfMonth);
    strDate = sdf.format(newDate.getTime());

    Log.e("strdate", strDate);
    /******************DatePicker************************/
    Calendar newCalendar = Calendar.getInstance();
    dateto.setText(strDate);

    datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            newDate.set(year, monthOfYear, dayOfMonth);
          dateto.setText(sdf.format(newDate.getTime()));




        }

    },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        Calendar newCalendar1 = Calendar.getInstance();
        datefrom.setText(strDate);

        datePickerDialog1 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                newDate.set(year, monthOfYear, dayOfMonth);
                datefrom.setText(sdf.format(newDate.getTime()));




            }

        },newCalendar1.get(Calendar.YEAR), newCalendar1.get(Calendar.MONTH), newCalendar1.get(Calendar.DAY_OF_MONTH));
    im_bto.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            datePickerDialog.show();
            return true;
        }
    });


        img_bfrom.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                datePickerDialog1.show();
                return true;
            }
        });



        /*************************************************/
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("c","clicked !");
                Intent i=new Intent(getApplicationContext(),Viewbysearch.class);
                i.putExtra("dateto",dateto.getText().toString());
                i.putExtra("datefrom",datefrom.getText().toString());
                startActivity(i);
            }
        });

}}
