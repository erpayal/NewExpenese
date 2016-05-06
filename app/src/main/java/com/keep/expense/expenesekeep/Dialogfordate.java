package com.keep.expense.expenesekeep;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by admin on 10/6/2015.
 */
public class Dialogfordate extends DialogFragment {
    private DatePickerDialog datePickerDialog,datePickerDialogto;
    private SimpleDateFormat dateFormatter,dateFormatter1;
    EditText dateto,datefrom;
    TextView search;
    DBHelper dbh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View v= inflater.inflate(R.layout.customdialog, container, false);
        getDialog().setTitle("Choose Date");
        dbh=new DBHelper(getActivity());
        dateto=(EditText)v.findViewById(R.id.edt_datefrom);
        datefrom=(EditText)v.findViewById(R.id.edt_dateto);
        search=(TextView)v.findViewById(R.id.txt_search);
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        dateFormatter1 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                datefrom.setText(dateFormatter1.format(newDate.getTime()));
                Log.e("date", String.valueOf(dateFormatter1.format(newDate.getTime())));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialogto = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateto.setText(dateFormatter1.format(newDate.getTime()));
                Log.e("dateto", String.valueOf(dateFormatter1.format(newDate.getTime())));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        dateto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialogto.show();
            }
        });
        datefrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
              dbh.getrecordbetween_dates(datefrom.getText().toString(),dateto.getText().toString());
                getTargetFragment().onActivityResult(getTargetRequestCode(),1, getActivity().getIntent());
                dismiss();
               // Bundle b=new Bundle();
               // b.putStringArrayList("dateaaraylist",new GetterSetter().getDate_1());
               // b.putStringArrayList("totalarraylist",new GetterSetter().getTime_1());
            }
        });
        return v;
    }



}
