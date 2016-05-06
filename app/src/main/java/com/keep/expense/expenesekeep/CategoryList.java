package com.keep.expense.expenesekeep;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.vstechlab.easyfonts.EasyFonts;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by admin on 9/18/2015.
 */
public class CategoryList  extends AppCompatActivity {

    public ListView list;
    Category_Adapter adapter;
    Button add, total;
    int count;
    ArrayList<String> c, a;
    DBHelper db;
    int t = 0;
     int balance;
    String strDate;
    String intMonth;
    Date date;
    SimpleDateFormat sdf;
    Calendar newDate;
    int year,monthOfYear,dayOfMonth;
    DatabaseHelper dbh;
    ArrayList<String> Categoryname=new ArrayList<>();
    ArrayAdapter<String> spinneradap;
    Spinner spcategory;
    TextView enterexp;
    EditText ndate;
    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categorylist);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        AdView mAdView = (AdView) findViewById(R.id.adView_add_e);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        db = new DBHelper(CategoryList.this);
        enterexp=(TextView)findViewById(R.id.txt_enterexp);
        ndate=(EditText)findViewById(R.id.edt_ndate);
        enterexp.setTypeface(EasyFonts.robotoBoldItalic(CategoryList.this));

        /***************************Spinner*************/

        dbh = new DatabaseHelper(CategoryList.this);

        Categoryname = dbh.getcategory();
        spcategory = (Spinner) findViewById(R.id.spinner_addcategory);
        spinneradap = new ArrayAdapter<String>(CategoryList.this, android.R.layout.simple_spinner_dropdown_item, Categoryname);
        spcategory.setAdapter(spinneradap);


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
      ndate.setText(strDate);

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                newDate.set(year, monthOfYear, dayOfMonth);
                ndate.setText(sdf.format(newDate.getTime()));




            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        ndate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                datePickerDialog.show();
                return true;
            }
        });



        /*************************************************/

        // list = (ListView) findViewById(R.id.categorylistview);
        add = (Button) findViewById(R.id.button_add);
        total = (Button) findViewById(R.id.button_total);
        // adapter=new Category_Adapter();
        //list.setAdapter(adapter);
        c = new ArrayList<String>();
        a = new ArrayList<>();
        total.setVisibility(View.GONE);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("click", "click");

                EditText amount = (EditText) findViewById(R.id.text_amount);
                //Log.e("s", category.getText().toString());
                try {

                    if (amount.getText().toString().equals("")) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(CategoryList.this);
                        builder1.setMessage("Please Fill valuable data");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    } else {
                        //db.Deleterecord();
                        // c.add(category.getText().toString());
                        //a.add(amount.getText().toString());
                        // adapter = new Category_Adapter(c, a);

                            //InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            //imm.hideSoftInputFromWindow(add.getWindowToken(), 0);
                            dbh.insertRecord(ndate.getText().toString(), (newDate.get(Calendar.MONTH)) + 1, newDate.get(Calendar.YEAR), spcategory.getSelectedItem().toString(), Integer.parseInt(amount.getText().toString()));

                            amount.setText("");

                            AlertDialog.Builder builder1 = new AlertDialog.Builder(CategoryList.this);
                            builder1.setTitle("Expense Keep");
                            builder1.setMessage("Expense is added");
                            builder1.setCancelable(true);
                            builder1.setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            dialog.cancel();
                                        }
                                    });

                            AlertDialog alert11 = builder1.create();
                            alert11.show();
                        } /*else {
                            dbh.updatedailycategory(spcategory.getSelectedItem().toString(),Integer.parseInt(amount.getText().toString())+cat_amount,strDate);
                            category.setText("");
                            amount.setText("");

                            AlertDialog.Builder builder1 = new AlertDialog.Builder(CategoryList.this);
                            builder1.setMessage("Record is added");
                            builder1.setCancelable(true);
                            builder1.setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            dialog.cancel();
                                        }
                                    });

                            AlertDialog alert11 = builder1.create();
                            alert11.show();
                            // total.setVisibility(View.VISIBLE);
                        }*/

                } catch (Exception e) {
               Log.e("error",e.getMessage());
                }
            }

        });

     /*   total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                for (int i = 0; i < a.size(); i++) {

                    t += Integer.parseInt(a.get(i));

                    Log.e("click", String.valueOf(t));


                }
              // add_record();

                //db.getAllRecords();
               // int s = db.numberOfRows();
               // Log.e("s", String.valueOf(s) + "e");

                try {


                    String total = db.checktotal(strDate);

                    if (total.equals("0")) {
                        Log.e("insert", "insert");
                       // db.Deleterecord(strDate);
                       db.addtotal(strDate, (newDate.get(Calendar.MONTH)) + 1,newDate.get(Calendar.YEAR), t);
                        Intent i = new Intent(getApplicationContext(), ViewDailyRecord.class);
                        startActivity(i);
                        finish();
                    } else {
                          int updatetotal=Integer.parseInt(total)+t;
                        Log.e("update", String.valueOf(updatetotal));
                       // db.Deleterecord(strDate);
                       db.updatetotal(String.valueOf(updatetotal), strDate);
                        Intent i = new Intent(getApplicationContext(), ViewDailyRecord.class);
                        startActivity(i);
                        finish();
                    }

                }catch (Exception e)
                {
                    Log.e("exception",e.getMessage());
                    Toast.makeText(getApplicationContext(), "No record Found Please try Again,Please add values", Toast.LENGTH_LONG).show();
                }

              /*  balance=db.getBalance();
                if(balance==0)
                {
                    int l= db.getlastrecord();
                    Log.e("l",String.valueOf(l)+"null");
                    balance=l-t;
                    db.addtotal("28/09/2015", t,balance);

                }
                else
                {
                    int b= db.getBalance();
                    balance=b-t;
                    db.addtotal("28/9/2015",t,balance);
                   Intent i = new Intent(getApplicationContext(), ViewRecords.class);
                    startActivity(i);
                }



            }
        });


    }**/
    }

    public void add_record() {
        for (int i = 0; i < c.size(); i++) {
            db.insertRecord(strDate, c.get(i), a.get(i), t);
        }
    }


   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // getMenuInflater().inflate(R.menu.menu_main,menu);
       return super.onCreateOptionsMenu(menu);
   }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}