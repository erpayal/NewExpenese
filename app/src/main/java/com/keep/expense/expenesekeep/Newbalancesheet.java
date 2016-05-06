package com.keep.expense.expenesekeep;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by admin on 10/16/2015.
 */
public class Newbalancesheet extends DialogFragment{

    DBHelper db;
    TextView txt_income,txt_expense,txt_balance,txt_cdate;
    String strDate;
    int balance;

    SimpleDateFormat sdf;
    Calendar newDate;
    int year,monthOfYear,dayOfMonth;
    Spinner sp_months,sp_year;
    String mMonths[]={"Select month","January","February", "March","April","May","June","July","August","September","October","November","December"};
    String myear[ ]={"Select months","2015","2016","2017","2018","2019","2020","2021","2022","2023","2024","2025","2026","2027","2028","2029","2030","2031",
    "2032","2033","2034","2035"};
    ArrayAdapter<String> spinneradapter,spinneradapter_y;
    DatabaseHelper dbh;
    DatePickerDialog datePickerDialog;
    EditText date;
    String month_n;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View b=inflater.inflate(R.layout.balancesheet,container,false);
        dbh=new DatabaseHelper(getActivity());
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        // strDate = sdf.format(new Date());
        newDate = Calendar.getInstance();
        year=newDate.get(Calendar.YEAR);
        monthOfYear=newDate.get(Calendar.MONTH);
        dayOfMonth=newDate.get(Calendar.DAY_OF_MONTH);
        newDate.set(year, monthOfYear, dayOfMonth);
        strDate=sdf.format(newDate.getTime());
        date=(EditText)b.findViewById(R.id.edt_baldate);
        txt_income=(TextView)b.findViewById(R.id.txt_income);
        txt_expense=(TextView)b.findViewById(R.id.txt_expense);
        txt_balance=(TextView)b.findViewById(R.id.txt_balance);
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM");
        month_n = sdf1.format(newDate.getTime());
     /* sp_months=(Spinner)b.findViewById(R.id.spinnermonth);
        spinneradapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,mMonths);
        sp_months.setAdapter(spinneradapter);
        sp_year=(Spinner)b.findViewById(R.id.spinneryear);
        spinneradapter_y=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,myear);
        sp_year.setAdapter(spinneradapter_y);

        sp_months.setSelection(monthOfYear+1);
        sp_year.setSelection(1);*/

        int income=dbh.getincomeof_month(month_n,newDate.get(Calendar.YEAR));
        int sum=dbh.getmonthdaysum(month_n,newDate.get(Calendar.YEAR));
        balance=income-sum;

        txt_income.setText(String.valueOf(income));
        txt_expense.setText(String.valueOf(sum));
        txt_balance.setText(String.valueOf(balance));
         /*********************************************************************************/
        final Calendar newCalendar = Calendar.getInstance();
        newDate = Calendar.getInstance();
        newDate.set(newCalendar.get(Calendar.YEAR), (newCalendar.get(Calendar.MONTH)), newCalendar.get(Calendar.DAY_OF_MONTH));
        date.setText(sdf.format(newDate.getTime()));

        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                int income=dbh.getincomeof_month((String.valueOf(newDate.get(Calendar.MONTH))+1),newDate.get(Calendar.YEAR));
                Log.e("20","click"+""+income);
                int sum=dbh.getmonthdaysum((String.valueOf(newDate.get(Calendar.MONTH))+1),newDate.get(Calendar.YEAR));
                balance=income-sum;

                txt_income.setText(String.valueOf(income));
                txt_expense.setText(String.valueOf(sum));
                txt_balance.setText(String.valueOf(balance));
                date.setText(sdf.format(newDate.getTime()));

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));



          date.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                  datePickerDialog.show();
              }
          });
        return b;
    }
    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        android.app.Dialog dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {

            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }
}
