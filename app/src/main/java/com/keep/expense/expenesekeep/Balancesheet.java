package com.keep.expense.expenesekeep;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by admin on 9/28/2015.
 */
public class Balancesheet extends AppCompatActivity{

    DBHelper db;
    TextView txt_income,txt_expense,txt_balance,txt_cdate;
    String strDate;
    int balance;
    Date date;
    SimpleDateFormat sdf;
    Calendar newDate;
    int year,monthOfYear,dayOfMonth;
    Spinner sp_months;
    String mMonths[]={"Select month","January","February", "March","April","May","June","July","August","September","October","November","December"};
    ArrayAdapter<String> spinneradapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.balancesheet);
        db=new DBHelper(Balancesheet.this);

        sdf = new SimpleDateFormat("yyyy-MM-dd");
        // strDate = sdf.format(new Date());
        newDate = Calendar.getInstance();
        year=newDate.get(Calendar.YEAR);
        monthOfYear=newDate.get(Calendar.MONTH);
        dayOfMonth=newDate.get(Calendar.DAY_OF_MONTH);
        newDate.set(year, monthOfYear, dayOfMonth);
        strDate=sdf.format(newDate.getTime());


       // txt_cdate=(TextView)findViewById(R.id.txt_cdate);
        txt_income=(TextView)findViewById(R.id.txt_income);
        txt_expense=(TextView)findViewById(R.id.txt_expense);
        txt_balance=(TextView)findViewById(R.id.txt_balance);
       // sp_months=(Spinner)findViewById(R.id.spinner_month);
        spinneradapter=new ArrayAdapter<String>(Balancesheet.this,android.R.layout.simple_spinner_dropdown_item,mMonths);
        sp_months.setAdapter(spinneradapter);

        int income=db.getincomeof_month((newDate.get(Calendar.MONTH))+1,newDate.get(Calendar.YEAR));
        int sum=db.getmonth_sum((newDate.get(Calendar.MONTH))+1,newDate.get(Calendar.YEAR));
        balance=income-sum;
        txt_cdate.setText(strDate);
        txt_income.setText(String.valueOf(income));
        txt_expense.setText(String.valueOf(sum));
        txt_balance.setText(String.valueOf(balance));


         sp_months.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 try {


                     if (position > 0) {
                         int income=0;//db.getincomeof_month(position);
                         int sum = db.getmonth_sum(position,newDate.get(Calendar.YEAR));
                         balance = income - sum;
                         txt_income.setText(String.valueOf(income));
                         txt_expense.setText(String.valueOf(sum));
                         txt_balance.setText(String.valueOf(balance));
                     }
                 }
                 catch (Exception e) {
                     Toast.makeText(getApplicationContext(), "No record Found Please try Again,Please add values", Toast.LENGTH_LONG).show();
                 }
             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });

    }
}
