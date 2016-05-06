package com.keep.expense.expenesekeep;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by admin on 9/24/2015.
 */
public class GetterSetter {
    private ArrayList<String> date = new ArrayList<String>();
    private ArrayList<String> time= new ArrayList<String>();
    ArrayList<String> date_1=new ArrayList<>();
    ArrayList<String> time_1=new ArrayList<>();

    public ArrayList<String> getDate_1() {
        return date_1;
    }

    public void setDate_1(ArrayList<String> date_1) {
        this.date_1 = date_1;
    }

    public ArrayList<String> getTime_1() {
        return time_1;
    }

    public void setTime_1(ArrayList<String> time_1) {
        this.time_1 = time_1;
    }

    public GetterSetter() {

    }
    public void addDate(String date1) {
        date.add(date1);
        Log.e("as",date1);
    }

    public void addTime(String time1) {
        time.add(time1);
    }


    public ArrayList<String> getDate() {
        Log.e("d",String.valueOf(date));
        return date;
    }



    public GetterSetter(ArrayList<String> date, ArrayList<String> time) {
        this.date = date;
        this.time = time;
    }

    public ArrayList<String> getTime() {

        return time;
    }


}
