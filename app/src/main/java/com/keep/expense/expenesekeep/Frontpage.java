package com.keep.expense.expenesekeep;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eftimoff.androidplayer.Player;
import com.eftimoff.androidplayer.actions.property.PropertyAction;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.vstechlab.easyfonts.EasyFonts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.Calendar;

import hotchemi.android.rate.AppRate;
import hotchemi.android.rate.OnClickButtonListener;

/**
 * Created by admin on 10/6/2015.
 */
public class Frontpage extends AppCompatActivity implements View.OnClickListener {




    LinearLayout layout1,layout2,layout3,layout4,layout5,layout6,layout7,layout8,layout9;
    DBHelper db;
    Calendar newDate;
    int year,monthOfYear,dayOfMonth;
   TextView t1,t2,t3,t4,t5,t6,t7,t8,t9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frontpage);
        db=new DBHelper(Frontpage.this);
        newDate = Calendar.getInstance();
        year=newDate.get(Calendar.YEAR);
        monthOfYear=newDate.get(Calendar.MONTH);
        layout1=(LinearLayout)findViewById(R.id.layout1);
        //layout1.setAlpha(0.3f);
        layout2=(LinearLayout)findViewById(R.id.layout2);
        layout3=(LinearLayout)findViewById(R.id.layout3);
        layout4=(LinearLayout)findViewById(R.id.layout4);
        layout5=(LinearLayout)findViewById(R.id.layout5);
        layout6=(LinearLayout)findViewById(R.id.layout6);
        layout7=(LinearLayout)findViewById(R.id.layout7);
        layout8=(LinearLayout)findViewById(R.id.layout8);
        layout9=(LinearLayout)findViewById(R.id.layout9);

        layout1.setOnClickListener(this);
        layout2.setOnClickListener(this);
        layout3.setOnClickListener(this);
        layout4.setOnClickListener(this);
        layout5.setOnClickListener(this);
        layout6.setOnClickListener(this);
        layout7.setOnClickListener(this);
        layout8.setOnClickListener(this);
        layout9.setOnClickListener(this);


        t1=(TextView)findViewById(R.id.textView1);
        t2=(TextView)findViewById(R.id.textView2);
        t3=(TextView)findViewById(R.id.textView3);
        t4=(TextView)findViewById(R.id.textView4);
        t5=(TextView)findViewById(R.id.textView5);
        t6=(TextView)findViewById(R.id.textView6);
        t7=(TextView)findViewById(R.id.textView7);
        t8=(TextView)findViewById(R.id.textView8);
        t9=(TextView)findViewById(R.id.textView9);

         t1.setTypeface(EasyFonts.robotoBold(Frontpage.this));
        t2.setTypeface(EasyFonts.robotoBold(Frontpage.this));
        t3.setTypeface(EasyFonts.robotoBold(Frontpage.this));
        t4.setTypeface(EasyFonts.robotoBold(Frontpage.this));
        t5.setTypeface(EasyFonts.robotoBold(Frontpage.this));
        t6.setTypeface(EasyFonts.robotoBold(Frontpage.this));
        t7.setTypeface(EasyFonts.robotoBold(Frontpage.this));
        t8.setTypeface(EasyFonts.robotoBold(Frontpage.this));
        t9.setTypeface(EasyFonts.robotoBold(Frontpage.this));
        animateSampleFour(layout1, layout2, layout3, layout4, layout5, layout6, layout7, layout8, layout9);



        AppRate.with(this)
                .setInstallDays(3) // default 10, 0 means install day.
                .setLaunchTimes(10) // default 10 times.
                .setRemindInterval(2) // default 1 day.
                .setShowLaterButton(true) // default true.
                .setDebug(true) // default false.
                .setCancelable(false) // default false.
                .setOnClickButtonListener(new OnClickButtonListener() { // callback listener.
                    @Override
                    public void onClickButton(int which) {
                        Log.d(MainActivity.class.getName(), Integer.toString(which));
                    }
                })
                .setTitle(R.string.new_rate_dialog_title)
                .setTextLater(R.string.new_rate_dialog_later)
                .setTextNever(R.string.new_rate_dialog_never)
                .setTextRateNow( R.string.new_rate_dialog_ok )
                .monitor();

        AppRate.showRateDialogIfMeetsConditions(this);
        AdView mAdView = (AdView) findViewById(R.id.adView_cl);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.layout1)
        {
            //db.Deleterecord();
            //db.insertincome("20000",10,2015, "2015-10-9", "2000");


            //  Log.e("s", String.valueOf(s));

            Intent i=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
        }
        if(v.getId()==R.id.layout2)
        {
            Intent i=new Intent(getApplicationContext(),CategoryList.class);
            startActivity(i);
        }
        if(v.equals(layout3))

        {
            try {
              {
                  ViewBy dm=new ViewBy();
                  FragmentManager fm=getFragmentManager();


                  dm.show(fm, "Fragment");

              }
            }catch (Exception e)
            {
                Toast.makeText(getApplicationContext(),"No Record Found",Toast.LENGTH_LONG).show();
            }
        }
        if(v.getId()==R.id.layout4)
        {

            // int size=db.Linegraph(monthOfYear + 1, year);

            {
                try {
                    {
                        Newbalancesheet dm=new Newbalancesheet();
                        FragmentManager fm=getFragmentManager();


                        dm.show(fm, "Fragment");
                    }
                }catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(),"No Record Found",Toast.LENGTH_LONG).show();
                }

            }
        }
        if(v.getId()==R.id.layout5)
        {


            Intent i = new Intent(getApplicationContext(),Addcategory.class);
            startActivity(i);

        }
        if(v.getId()==R.id.layout6)
        {


                Intent i = new Intent(getApplicationContext(),DailyAlram.class);
                startActivity(i);

        }
        if(v.getId()==R.id.layout7)
        {

                   try {
                       Intent i = new Intent(getApplicationContext(), Piechart.class);
                       startActivity(i);
                   }
                   catch (NullPointerException e)
                   {
                       Toast.makeText(getApplicationContext(),"Please add expenses to see the report!!",Toast.LENGTH_LONG).show();
                   }

        }
        if(v.getId()==R.id.layout8)
        {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(Frontpage.this);
            builder1.setTitle("Export Database");
            builder1.setMessage("Are you sure ?You want to maintain backup");
            builder1.setCancelable(true);
            builder1.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                             exportDB();

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
        if(v.getId()==R.id.layout9)
        {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(Frontpage.this);
            builder1.setTitle("Restore Database");
            builder1.setMessage("Are you sure ?You want to import backup");
            builder1.setCancelable(true);
            builder1.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            importDB();

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


    }
    public void exportDB() {
        try {
            Context ctx = this; // for Activity, or Service. Otherwise simply get the context.
            String dbname = "MyDB.db";

            //String dbpath = String.valueOf(ctx.getDatabasePath(dbname));
            File dbFile = new File(getFilesDir().getParent()+"/databases/"+dbname);
            Log.e("backup", String.valueOf(dbFile));
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            File myDir = new File(Environment.getExternalStorageDirectory(), "Expense Keep");
            if (!myDir.exists()) {
                myDir.mkdir();
            }

            if (sd.canWrite()) {
                String currentDBPath = "/data/" + "com.keep.expense.expenesekeep"
                        + "/databases/" + "MyDB.db";
                String backupDBPath = "/Expense Keep/MyDB.db";

                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(getApplicationContext(), "Backup Successful!",
                        Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
            Log.e("error",e.getMessage());

            Toast.makeText(getApplicationContext(), "Backup Failed!", Toast.LENGTH_SHORT)
                    .show();

        }
    }
    private void importDB() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            if (sd.canWrite()) {
                String currentDBPath = "/data/" + "com.keep.expense.expenesekeep"
                        + "/databases/" + "MyDB.db";
                String backupDBPath = "/Expense Keep/MyDB.db";

                File backupDB = new File(data, currentDBPath);
                File currentDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(getApplicationContext(), "Import Successful!",
                        Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), "Import Failed!Try Again ", Toast.LENGTH_SHORT)
                    .show();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
       // animateSampleFour(layout1, layout2, layout3, layout4, layout5, layout6, layout7, layout8, layout9);
    }

    private void animateSampleFour(View layout1, View layout2, View layout3, View layou4, View layout5, View layout6, View layout7,View Layout8,View layout9) {
        final PropertyAction ly1 = PropertyAction.newPropertyAction(layout1).interpolator(new DecelerateInterpolator()).translationY(-200).duration(550).alpha(0.4f).build();

        final PropertyAction ly2 = PropertyAction.newPropertyAction(layout2).scaleX(0).translationY(-200).duration(550).alpha(0f).build();
        //  final PropertyAction spinnern2 = PropertyAction.newPropertyAction(spinner2).scaleX(0).scaleY(0).duration(550).alpha(0f).build();
        final PropertyAction  ly3 = PropertyAction.newPropertyAction(layout3).scaleX(0).translationY(-200).duration(550).alpha(0f).build();
        final PropertyAction  ly4 = PropertyAction.newPropertyAction(layout4).scaleX(0).translationY(-200).duration(550).alpha(0f).build();
        final PropertyAction  ly5 = PropertyAction.newPropertyAction(layout5).scaleX(0).translationY(-200).duration(550).alpha(0f).build();
        final PropertyAction  ly6 = PropertyAction.newPropertyAction(layout6).scaleX(0).translationY(-200).duration(550).alpha(0f).build();
        final PropertyAction  ly7 = PropertyAction.newPropertyAction(layout7).scaleX(0).translationY(-200).duration(550).alpha(0f).build();
        final PropertyAction  ly8 = PropertyAction.newPropertyAction(layout8).scaleX(0).translationY(-200).duration(550).alpha(0f).build();
        final PropertyAction  ly9 = PropertyAction.newPropertyAction(layout9).scaleX(0).translationY(-200).duration(550).alpha(0f).build();
        Player.init().
                animate(ly1).
                then().
                animate(ly2).
                then().
                animate(ly3).
                then().
                animate(ly4).
                animate(ly5).
                then().
                animate(ly6).
                then().
                animate(ly7).
                then().
                animate(ly8).
                then().

                animate(ly9).
                play();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.            vckmv+" " +
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_info) {
            Dialog d=new Dialog(Frontpage.this);
            d.setTitle("Expense Keep");
            d.setContentView(R.layout.infodialog);
            TextView t1=(TextView)d.findViewById(R.id.textViewi1);
            TextView t2=(TextView)d.findViewById(R.id.textViewi2);
            TextView t3=(TextView)d.findViewById(R.id.textViewi3);
            TextView t4=(TextView)d.findViewById(R.id.textViewi4);
            TextView t5=(TextView)d.findViewById(R.id.textViewi5);
            TextView t6=(TextView)d.findViewById(R.id.textViewi6);
            TextView t7=(TextView)d.findViewById(R.id.textViewi7);
            TextView t8=(TextView)d.findViewById(R.id.textViewi8);

            t1.setTypeface(EasyFonts.robotoThin(Frontpage.this));
            t2.setTypeface(EasyFonts.robotoThin(Frontpage.this));
            t3.setTypeface(EasyFonts.robotoThin(Frontpage.this));
            t4.setTypeface(EasyFonts.robotoThin(Frontpage.this));
            t5.setTypeface(EasyFonts.robotoThin(Frontpage.this));
            t6.setTypeface(EasyFonts.robotoThin(Frontpage.this));
            t7.setTypeface(EasyFonts.robotoThin(Frontpage.this));
            t8.setTypeface(EasyFonts.robotoThin(Frontpage.this));

            d.show();
           /* AlertDialog.Builder builder1 = new AlertDialog.Builder(Frontpage.this);
            builder1.setTitle("Expense Keep");
            builder1.setMessage("Expense Keep information about your daily expenses."+"\n"+"\n"+ Html.fromHtml("<font color='red'> Expense </font>")+" "+"\n"+
                    " Add your daily expenses click on Expenses icon " + "\n" +" "+ Html.fromHtml("<u>Income</u>")+" "+"\n"+
                            "Add your monthly income on clicking income icon");
            builder1.setCancelable(true);
            builder1.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();*/
        }

        return super.onOptionsItemSelected(item);
    }
}

