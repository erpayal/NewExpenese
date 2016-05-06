package com.keep.expense.expenesekeep;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.vstechlab.easyfonts.EasyFonts;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Piechart extends AppCompatActivity implements
        OnChartValueSelectedListener {

    private PieChart mChart;
    String month_n;
    private SeekBar mSeekBarX, mSeekBarY;
    private TextView tvX, tvY;
    DatabaseHelper dbh;
    ArrayList<Integer> categoryamount=new ArrayList<>();
    TextView descp;

    private Typeface tf;
    private String[] mParties = new String[] {
            "Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
            "Party I"

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.piechart);
        dbh=new DatabaseHelper(Piechart.this);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Calendar c=Calendar.getInstance();
        int month=c.get(Calendar.MONTH)+1;
        int year=c.get(Calendar.YEAR);

        descp=(TextView)findViewById(R.id.txt_gr3);
        descp.setTypeface(EasyFonts.robotoBold(Piechart.this));
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        String month_name = month_date.format(cal.getTime());
        descp.setText(month_name+","+year);
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM");
        month_n = sdf1.format(c.getTime());
       // categories=dbh.getcategory();
        categoryamount=dbh.get_Piechart(month_n,year);

        mChart = (PieChart) findViewById(R.id.chart1);
        mChart.setUsePercentValues(true);
        mChart.setDescription(" ");
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

//        tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

     //   mChart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));
        mChart.setCenterText(String.valueOf(generateCenterSpannableText()));

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        mChart.setOnChartValueSelectedListener(this);


        try {
            setData(categoryamount.size());
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "Please add expenses to see the report!!", Toast.LENGTH_LONG).show();
        }



        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
        l.setYOffset(2f);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_graphactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_bargraph: {
                Intent i=new Intent(getApplicationContext(),Bargraph.class);
                startActivity(i);
                break;
            }

            case R.id.action_linegraph: {
                Intent i=new Intent(getApplicationContext(),graphactivity.class);
                startActivity(i);
                break;
            }
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();

        }
        return true;
    }


    private void setData(int count) {

     //   float mult = range;

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        for (int i = 0; i < count ; i++) {
            yVals1.add(new Entry((float)( categoryamount.get(i)), i));
        }

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < count; i++)
            xVals.add(DatabaseHelper.categoryname.get(i));

        PieDataSet dataSet = new PieDataSet(yVals1, "Category");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(tf);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

    private SpannableString generateCenterSpannableText() {

       SpannableString s = new SpannableString("Category");
       /* tempSpannable.setSpan(new RelativeSizeSpan(0.75f), 0, index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tempSpannable.setSpan(new ForegroundColorSpan(Color.parseColor("#999999")),
                0, index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);*/

        // setSpan (8 ... 1) has end before start
      //  java.lang.IndexOutOfBoundsException: setSpan (0 ... 14) ends beyond length 8
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 8, 0);
       // s.setSpan(new StyleSpan(Typeface.NORMAL), 8, s.length() - 7, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 8, 0);
       // s.setSpan(new RelativeSizeSpan(.8f), 8, s.length() - 7, 0);
       // s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 8, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 8, s.length(), 0);
        return s;
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getVal() + ", xIndex: " + e.getXIndex()
                        + ", DataSet index: " + dataSetIndex);
    }



    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }


}
