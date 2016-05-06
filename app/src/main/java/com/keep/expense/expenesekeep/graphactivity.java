package com.keep.expense.expenesekeep;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.vstechlab.easyfonts.EasyFonts;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class graphactivity extends AppCompatActivity implements OnChartGestureListener, OnChartValueSelectedListener {



    LineChart chart;
    DatabaseHelper db;
    int numberoftimes;
    MyYAxisValueFormatter custom;
    ArrayList<String> y=new ArrayList<>();
    ArrayList<String> y1=new ArrayList<>();
    TextView descp;
    String month_n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_graphactivity);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Calendar c=Calendar.getInstance();
        int month=c.get(Calendar.MONTH)+1;
        int year=c.get(Calendar.YEAR);
        descp=(TextView)findViewById(R.id.txt_gr2);
        descp.setTypeface(EasyFonts.robotoBold(graphactivity.this));
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        String month_name = month_date.format(cal.getTime());
        descp.setText(month_name+","+year);
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM");
        month_n = sdf1.format(cal.getTime());
        db=new DatabaseHelper(graphactivity.this);
     // db.getdailysum()



        db.Linegraph(month_n, year);

      //y= db.getAllRecords(month);



        chart = (LineChart) findViewById(R.id.chart_m);
        chart.setDrawGridBackground(false);

        // no description text
        chart.setDescription("Contain data About Expenses of Month");
        chart.setNoDataTextDescription("Add expenses to show in chart.");
        chart.setBackgroundColor(Color.WHITE);
        // enable value highlighting
        chart.setHighlightEnabled(true);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        // mChart.setScaleXEnabled(true);
        // mChart.setScaleYEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(true);
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.RED);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);

        LimitLine ll1 = new LimitLine(4000f, "Average Limit");
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);
        ll1.setLineColor(Color.BLUE);
        // ll1.setTypeface(tf);

      /*  LimitLine ll2 = new LimitLine(-30f, "Lower Limit");
        ll2.setLineWidth(4f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(10f);

        //ll2.setTypeface(tf);*/


        MyYAxisValueFormatter custom=new MyYAxisValueFormatter();
       // LineData data = new LineData();
       // data.setValueFormatter(custom);
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.addLimitLine(ll1);
       // leftAxis.addLimitLine(ll2);
        leftAxis.setValueFormatter(custom);
       // leftAxis.resetAxisMaxValue();
       //leftAxis.setAxisMaxValue(2000f);
       leftAxis.setLabelCount(10, true);
       // leftAxis.setAxisMinValue(200f);
        //leftAxis.setAxisMinValue(-50f);
        leftAxis.setStartAtZero(false);
        leftAxis.setSpaceTop(15f);
        //leftAxis.setYOffset(20f);
       // leftAxis.enableGridDashedLine(10f, 10f, 0f);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);

        chart.getAxisRight().setEnabled(false);
        Legend l = chart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
       setData(DatabaseHelper.temp.size());
        chart.notifyDataSetChanged();



    }
    class myValueFormatter implements ValueFormatter{


            private DecimalFormat mFormat;

            public myValueFormatter() {
                mFormat = new DecimalFormat("###,###,##0.0"); // use one decimal
            }

            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                // write your logic here
                return mFormat.format(value) + " Rs"; // e.g. append a dollar-sign
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

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i <count; i++) {
            xVals.add((DatabaseHelper.temp.get(i)) + "");
        }

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {

           //float mult = (range + 1);
           // float val = (float) (Math.random() * mult) + 3;// + (float)
            // ((mult *
            // 0.1) / 10);
            yVals.add(new Entry(Float.parseFloat(DatabaseHelper.temp1.get(i)), i));
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, "Expenses");
        // set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);

        // set the line to be drawn like this "- - - - - -"
       // set1.enableDashedLine(10f, 5f, 0f);
       // set1.enableDashedHighlightLine(10f, 5f, 0f);
        //set1.setDrawFilled(true);
        set1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        set1.setCircleColor(Color.GRAY);
        //set1.isDrawCubicEnabled(true);
        set1.setLineWidth(3f);
        set1.setCircleSize(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setFillAlpha(65);
        set1.setFillColor(Color.BLACK);
//        set1.setDrawFilled(true);
        // set1.setShader(new LinearGradient(0, 0, 0, mChart.getHeight(),
        // Color.BLACK, Color.WHITE, Shader.TileMode.MIRROR));

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);
       //data.setValueFormatter(custom);
        data.setValueFormatter(new myValueFormatter());
        // set data
        try {
            chart.setData(data);
        }
        catch (NullPointerException e)
        {
            Toast.makeText(getApplicationContext(), "Please add expenses to see the report!!", Toast.LENGTH_LONG).show();
        }

        chart.notifyDataSetChanged();
        chart.invalidate();

    }
    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        chart.notifyDataSetChanged();
        chart.invalidate();
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        chart.animateX(3000);
        chart.notifyDataSetChanged();
        chart.invalidate();

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.menu_graphactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
      switch (item.getItemId())
      {
        //noinspection SimplifiableIfStatement
          case R.id.action_settings:
       {
            Intent i=new Intent(getApplicationContext(),Bargraph.class);
            startActivity(i);
           break;
        }
          case android.R.id.home:
              // app icon in action bar clicked; goto parent activity.
              this.finish();
      }
        return super.onOptionsItemSelected(item);
    }
}
