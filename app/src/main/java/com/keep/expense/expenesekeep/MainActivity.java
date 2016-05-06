package com.keep.expense.expenesekeep;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView next;
    EditText income,date;
    DatePicker picker;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter,dateFormatter1;
    Calendar newDate,cal;
DatabaseHelper dbh;
    private Tracker mTracker;
    int year,monthOfYear,dayOfMonth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        dbh=new DatabaseHelper(MainActivity.this);
        next=(TextView)findViewById(R.id.txt_savingtype);
        income=(EditText)findViewById(R.id.edt_income1);
        date=(EditText)findViewById(R.id.edt_date);
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

        dateFormatter1 = new SimpleDateFormat("yyyy-MM-dd");
        cal = Calendar.getInstance();
       year=cal.get(Calendar.YEAR);
        monthOfYear=cal.get(Calendar.MONTH);
        dayOfMonth=cal.get(Calendar.DAY_OF_MONTH);



        Calendar newCalendar = Calendar.getInstance();
        newDate = Calendar.getInstance();
        newDate.set(newCalendar.get(Calendar.YEAR),(newCalendar.get(Calendar.MONTH)),newCalendar.get(Calendar.DAY_OF_MONTH));
        date.setText(dateFormatter1.format(newDate.getTime()));

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                 newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                date.setText(dateFormatter1.format(newDate.getTime()));
                Log.e("date",String.valueOf(dateFormatter1.format(newDate.getTime())));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              datePickerDialog.show();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(income.getText().toString().equals(""))
                {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                    builder1.setMessage("Please add some informatiom");
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
                    int count=  dbh.checkincome(monthOfYear + 1, year);
                    Log.e("count", String.valueOf(count));
                    if(count>=1)
                    {
                        dbh.updateIncomeRecord(dateFormatter1.format(newDate.getTime()),monthOfYear+1,year,income.getText().toString());
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                        builder1.setTitle("Information");
                        builder1.setMessage("Income of month is updated. Do You want to add expense for today?");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        Intent i = new Intent(getApplicationContext(), CategoryList.class);
                                        startActivity(i);
                                    }
                                });
                        builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                finish();
                            }
                        });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }
                    else {
                        dbh.insertIncome(monthOfYear + 1, year, date.getText().toString(), income.getText().toString());
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                        builder1.setTitle("Information");
                        builder1.setMessage("Income for month is added.Do You want to add expense for today?");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        Intent i=new Intent(getApplicationContext(),CategoryList.class);
                                        startActivity(i);
                                        finish();
                                    }
                                });
                        builder1.setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                finish();
                            }
                        });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }
                }
            }
        });
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();

    }

    @Override
    protected void onResume() {
        super.onResume();

        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.menu_main, menu);
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
