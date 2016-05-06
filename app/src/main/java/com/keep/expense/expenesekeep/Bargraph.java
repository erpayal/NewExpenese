package com.keep.expense.expenesekeep;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.vstechlab.easyfonts.EasyFonts;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Bargraph extends AppCompatActivity {


    protected BarChart mChart;
    DatabaseHelper db;
    Calendar newDate;
    int year,monthOfYear,dayOfMonth;
    String mMonths[]={"January","February", "March","April","May","June","July","August","September","October","November","December"};
    ArrayList<Integer> datalist=new ArrayList<>();
    TextView descp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bargraph);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        newDate = Calendar.getInstance();
        year=newDate.get(Calendar.YEAR);
        db=new DatabaseHelper(Bargraph.this);


        /******************************MOnth Name*********************/
        descp=(TextView)findViewById(R.id.txt_gr1);
        descp.setTypeface(EasyFonts.robotoBold(Bargraph.this));
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        String month_name = month_date.format(cal.getTime());
        descp.setText(month_name+","+year);

       datalist= db.getTotalof_eachmonth(newDate.get(Calendar.YEAR));


        mChart = (BarChart) findViewById(R.id.chart_m);


        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);
        mChart.setDrawHighlightArrow(true);

        mChart.setDescription("");

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        // draw shadows for each bar that show the maximum value
        // mChart.setDrawBarShadow(true);

        // mChart.setDrawXLabels(false);

        mChart.setDrawGridBackground(false);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        // xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(5);

        YAxisValueFormatter custom = new MyYAxisValueFormatter();

        YAxis leftAxis = mChart.getAxisLeft();
        //  leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);

       YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        // rightAxis.setTypeface(mTf);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        try {
            setData(datalist.size());
        }
        catch (NullPointerException e)
        {
            Toast.makeText(getApplicationContext(), "Please add expenses to see the report!!", Toast.LENGTH_LONG).show();
        }

    }
    class MyYAxisValueFormatter implements YAxisValueFormatter {

        private DecimalFormat mFormat;


        public MyYAxisValueFormatter() {

            mFormat = new DecimalFormat("###,###,###,##0.0");
        }

        @Override
        public String getFormattedValue(float value, YAxis yAxis) {
            Log.e("value", String.valueOf(value));
            return mFormat.format(value) + "Rs";
        }

    }
    private void setData(int count) {
       // Float result[] = data.toArray( new Float[data.size()]);
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
          int s=  DatabaseHelper.month_list.get(i);
            xVals.add(mMonths[s-1]);
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < count; i++) {
           // float mult = (range + 1);
            //float val = (float) (Math.random() * mult);
            yVals1.add(new BarEntry((float)(datalist.get(i)), i));
        }

        BarDataSet set1 = new BarDataSet(yVals1, "Expenses");
        set1.setBarSpacePercent(45f);
        set1.setColors(ColorTemplate.VORDIPLOM_COLORS);

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(8f);
        // data.setValueTypeface(mTf);

        mChart.setData(data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_bargraph, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
      switch (item.getItemId()) {
          //noinspection SimplifiableIfStatement
          case R.id.action_settings: {

              return true;

          }
          case android.R.id.home:
              // app icon in action bar clicked; goto parent activity.
              this.finish();
      }
        return super.onOptionsItemSelected(item);
    }
}
