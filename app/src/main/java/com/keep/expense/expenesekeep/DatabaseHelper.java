package com.keep.expense.expenesekeep;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by admin on 10/11/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyDB.db";

    static ArrayList<String> array_list,arraylistn1,arraylistn2,temp,temp1,categoryname;
          static  ArrayList<Integer>   month_list ;
    static ArrayList<String> array_list1;
    ArrayList<String> datearray_list, totalarray_list;
    static int v;

    public DatabaseHelper(Context context) {
     //   super(context, DATABASE_NAME, null, 2);
        super(context, DATABASE_NAME, null,10);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table category_list " + "(category_id integer primary key autoincrement, categoryname text)");

        db.execSQL(
                "create table categoryrecords " + "(id integer primary key autoincrement,category_id integer,date datetime,month integer,year integer,categoryname text,categoryamount integer,FOREIGN KEY(category_id) REFERENCES category_list(category_id))");

        db.execSQL(
                "CREATE TABLE IF NOT EXISTS totalrecord" + "(id integer primary key autoincrement,date datetime,month integer,year integer,total integer)");


        db.execSQL(
                "CREATE TABLE IF NOT EXISTS income_savings " + "(Income_Id integer primary key autoincrement,month integer,year integer,date datetime,income integer)");
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS balance_record " + "(id integer primary key autoincrement,date datetime,month integer,year integer,balance integer)");

        db.execSQL("INSERT INTO category_list(categoryname) VALUES('Clothes')");
        db.execSQL("INSERT INTO category_list(categoryname) VALUES('Eating Out')");
        db.execSQL("INSERT INTO category_list(categoryname) VALUES('Entertainment')");
        db.execSQL("INSERT INTO category_list(categoryname) VALUES('Fuel')");
        db.execSQL("INSERT INTO category_list(categoryname) VALUES('Food')");
        db.execSQL("INSERT INTO category_list(categoryname) VALUES('General')");
        db.execSQL("INSERT INTO category_list(categoryname) VALUES('Gifts')");
        db.execSQL("INSERT INTO category_list(categoryname) VALUES('Health Care')");
        db.execSQL("INSERT INTO category_list(categoryname) VALUES('Kitchen')");
        db.execSQL("INSERT INTO category_list(categoryname) VALUES('Holidays')");
        db.execSQL("INSERT INTO category_list(categoryname) VALUES('Milk')");
        db.execSQL("INSERT INTO category_list(categoryname) VALUES('Personal')");
        db.execSQL("INSERT INTO category_list(categoryname) VALUES('Shoping')");
        db.execSQL("INSERT INTO category_list(categoryname) VALUES('Travel')");
        db.execSQL("INSERT INTO category_list(categoryname) VALUES('Personal')");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS category_list");
        db.execSQL("DROP TABLE IF EXISTS categoryrecords");

        onCreate(db);

    }

    /********************************Insert Methods*******************************************/
    public boolean insertRecord(String date, int month, int year, String name,int amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date", date);
        contentValues.put("month",month);
        contentValues.put("year", year);
        contentValues.put("categoryname", name);
        contentValues.put("categoryamount", amount);

        db.insert("categoryrecords", null, contentValues);
        Log.e("cont", String.valueOf(contentValues));
        return true;
    }
    public boolean insertCategory(String categoryname) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT categoryname FROM category_list WHERE categoryname='" + categoryname + "' COLLATE NOCASE ", null);
        if(res.moveToFirst()) {
            Log.e("name",res.getString(res.getColumnIndex("categoryname")));
           return false;
        }
        else
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put("categoryname", categoryname);
            db.insert("category_list", null, contentValues);
            Log.e("cont", String.valueOf(contentValues));

        }
        return true;
    }
    public void addtotal(String date, int month,int year, int total) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date", date);
        contentValues.put("month", month);
        contentValues.put("year",year);
        contentValues.put("total", total);
        db.insert("totalrecord", null, contentValues);
        Log.e("cont", String.valueOf(contentValues));
    }

    public boolean insertIncome(int month ,int year,String date,String income) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("month", month);
        contentValues.put("year",year);
        contentValues.put("date", date);
        contentValues.put("income", income);
        db.insert("income_savings", null, contentValues);
        Log.e("cont_s", String.valueOf(contentValues));

        return true;
    }

    /*******************************Delete Methods*********************************************/

    public boolean Deletecategoryrecord()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM category_list WHERE category_id = (SELECT MAX(category_id) FROM category_list");
        db.close();
        return true;
    }
    public boolean Deletetotalsrecord()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM totalrecord ");
        db.close();
        return true;
    }

    /*********************************Sum methods*******************************************/

 /*   public boolean getweek_total(){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT  strftime('%W', date) AS week,categorgyname, sum(categoryamount) FROM categoryrecords order by category GROUP BY week", null);
        if (res.moveToFirst()) {
            amount= res.getInt(0);
        }
        Log.e("sum", String.valueOf(amount));
        res.close();
        return true;
    }*/

    public int getCategory_Amount(String category,String date){
        int amount = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        //SELECT categoryamount FROM categoryrecords WHERE categoryname=Gifts AND date=2015-10-12
        Cursor res = db.rawQuery("SELECT categoryamount FROM categoryrecords WHERE categoryname='" + category + "' AND date='" + date + "'", null);
        if (res.moveToFirst()) {
            amount= res.getInt(0);
        }
        Log.e("sum", String.valueOf(amount));
        res.close();
        return amount;
    }
    public ArrayList<Integer> getTotalofyear(int year) {
        ArrayList<Integer> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        for (int i = 1; i <= 12; i++) {
            Cursor res = db.rawQuery(" SELECT SUM(total) FROM totalrecord  WHERE month=" + i + " AND year="+year+"", null);
            //res.moveToFirst();


            if (res.getCount() == 0) {
                return null;
            }

            while (res.moveToNext()) {
                if (res.getInt(0) == 0) {
                    array_list.add(0);
                } else
                    array_list.add(res.getInt(0));
                //res.moveToNext();
                Log.e("listtotal", String.valueOf(array_list));
            }
        }

        return array_list;

    }
    public ArrayList<HashMap<String,String> > getmonth_sum(String month,int year) {
        int sum=0;
        ArrayList<HashMap<String, String>> rowList = new ArrayList<HashMap<String, String>>();

        String cname=null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT strftime('%Y-%m-%d',date) as valDay,categoryname,SUM(categoryamount) as valTotalDay FROM categoryrecords WHERE strftime('%Y',date)='"+year+"' AND strftime('%m',date) ='"+month+"' GROUP BY valDay,categoryname", null);
        //Log.e("qu","SELECT strftime('%Y-%m-%d',date) as valDay,categoryname,SUM(categoryamount) as valTotalDay FROM categoryrecords WHERE strftime('%Y',date)='"+year+"' AND strftime('%m',date) ='"+month+"' GROUP BY valDay,categoryname");
        if (res.getCount()==0) {
            return rowList;
        }
        while (res.moveToNext())
        {
            sum= res.getInt(res.getColumnIndex("valTotalDay"));
            cname=res.getString(res.getColumnIndex("categoryname"));
            Log.e("sum", sum+" "+cname);

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("date", res.getString(res.getColumnIndex("valDay")));
            map.put("name",res.getString(res.getColumnIndex("categoryname")));
            map.put("daysum", String.valueOf(res.getInt(res.getColumnIndex("valTotalDay"))));
            // Log.e("sum", sum+" "+cname);
            rowList.add(map);
            Log.e("listtotal", String.valueOf(rowList));
        }

        res.close();
        return rowList;
    }


    public ArrayList<HashMap<String,String> > getweek_sum(String month,int year) {
        ArrayList<HashMap<String, String>> rowList = new ArrayList<HashMap<String, String>>();
        int sum = 0;
        String cname=null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT strftime('%W-%m',date) AS week,categoryname,SUM(categoryamount)as weektotal FROM categoryrecords WHERE strftime('%Y',date)='"+year+"' AND strftime('%m',date) ='"+month+"' GROUP BY week,categoryname", null);
        Log.e("query",String.valueOf("SELECT strftime('%W-%m',date) AS week,categoryname,SUM(categoryamount)as weektotal FROM categoryrecords WHERE strftime('%Y',date)='"+year+"' AND strftime('%m',date) ='"+month+"' GROUP BY week,categoryname"));
        if (res.getCount()==0) {

            return rowList;
        }
        while (res.moveToNext())
        {
            sum= res.getInt(res.getColumnIndex("weektotal"));
            cname=res.getString(res.getColumnIndex("categoryname"));
            Log.e("sum", sum+" "+cname);

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("date", res.getString(res.getColumnIndex("week")));
            map.put("name",res.getString(res.getColumnIndex("categoryname")));
            map.put("daysum", String.valueOf(res.getInt(res.getColumnIndex("weektotal"))));
            // Log.e("sum", sum+" "+cname);
            rowList.add(map);
            Log.e("listtotal", String.valueOf(rowList));
        }

        res.close();
        return rowList;
    }


    public ArrayList<HashMap<String,String> >daily_catrecord(int day,int month,int year) {
        ArrayList<HashMap<String, String>> rowList = new ArrayList<HashMap<String, String>>();
        HashMap<String,String > list=new HashMap<>();
        int sum = 0;
        String cname=null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT strftime('%Y-%m-%d', date) as day,categoryname, sum(categoryamount)as daytotal FROM categoryrecords WHERE date = date('now') GROUP BY day,categoryname", null);
        if (res.getCount()==0) {
            Log.e("norecord","no record found");
          //  Log.e("query","SELECT strftime('%Y-%m-%d', date) as day,categoryname, sum(categoryamount)as daytotal FROM categoryrecords WHERE strftime('%Y',date)='"+year+"' AND strftime('%m',date) ='"+month+"' AND strftime('%d',date) ='"+day+"' GROUP BY day,categoryname");
            return rowList;
        }
        while (res.moveToNext()) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("date", res.getString(res.getColumnIndex("day")));
            map.put("name",res.getString(res.getColumnIndex("categoryname")));
            map.put("daysum", String.valueOf(res.getInt(res.getColumnIndex("daytotal"))));
           // Log.e("sum", sum+" "+cname);
            rowList.add(map);
            Log.e("listtotal", String.valueOf(rowList));
        }

        res.close();
        return rowList;
    }
    public int getdailysum(int day,int month,int year)
    {
        int sum=0;
        String day_g=null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT strftime('%Y-%m-%d', date) as day,sum(categoryamount)as daytotal FROM categoryrecords WHERE date = date('now') GROUP BY day", null);
        if (res.getCount()==0) {
            Log.e("norecord","no record found");
            return 0;
        }
        while (res.moveToNext())
        {
            sum=res.getInt(res.getColumnIndex("daytotal"));
           // day_g=res.getString(res.getColumnIndex("day"));
            // Log.e("sum", sum+" "+cname);


        }
        Log.e("total", String.valueOf(sum));
        res.close();
        return sum;

    }


    public int getweekdaysum(String month,int year)
    {
        int sum=0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT strftime('%W', date) AS week,sum(categoryamount)as weektotal FROM categoryrecords WHERE strftime('%Y',date)='"+year+"' AND strftime('%m',date) ='"+month+"'", null);
        if (res.getCount()==0) {
            Log.e("norecord","no record found");
            return 0;
        }
        while (res.moveToNext())
        {
            sum=res.getInt(res.getColumnIndex("weektotal"));
            // Log.e("sum", sum+" "+cname);


        }
        Log.e("total", String.valueOf(sum));
        res.close();
        return sum;

    }

    public int getmonthdaysum(String month,int year)
    {
        int sum=0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT strftime('%m',date) as valDay,SUM(categoryamount) as valTotalDay FROM categoryrecords WHERE strftime('%Y',date)='"+year+"' AND strftime('%m',date) ='"+month+"'", null);
        if (res.getCount()==0) {
            Log.e("norecord","no record found");
            return 0;
        }
        while (res.moveToNext())
        {
            sum=res.getInt(res.getColumnIndex("valTotalDay"));
            // Log.e("sum", sum+" "+cname);


        }
        Log.e("total", String.valueOf(sum));
        res.close();
        return sum;

    }

    /**********************************Select Methods***************************************/

    public ArrayList<String> getcategory()
    {
        ArrayList<String> data=new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query="SELECT * FROM category_list";
        Cursor res = db.rawQuery(query, null);
       if(res.getCount()==0)
       {

       }
        while (res.moveToNext())
        {

            data.add(res.getString(res.getColumnIndex("categoryname")));


        }
        return data;
    }
    public int getincomeof_month(String month,int year) {
        int income = 0;
        SQLiteDatabase db = this.getReadableDatabase();
       /* "SELECT column1, (SELECT max(column1) FROM table1) AS max FROM table1 " +
                "WHERE month = ? OR column1 = ? ORDER BY column1";*/
        String query="SELECT * FROM income_savings WHERE strftime('%Y',date)='"+year+"' AND strftime('%m',date) ='"+month+"'";
        Cursor res = db.rawQuery(query, null);
        Log.e("res",query);
        if(res.getCount()==0)
        {
            return 0;
        }
        while (res.moveToNext())

        {
            String id = res.getString(res.getColumnIndex("income"));
            income = Integer.parseInt(id);
            Log.e("id", id);

        }
        res.close();
        return income;
    }
    //Change table name
    public ArrayList<String> getAllRecords(int month,int year) {
        ArrayList<String> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from totalrecord WHERE month=" + month + " AND year="+year+"", null);
        //res.moveToFirst();


        /*if(res.getCount()==0)
        {
            return array_list;
        }*/

        if (res.moveToFirst()) {

            array_list.add((res.getString(res.getColumnIndex("total"))));
            res.moveToNext();
            Log.e("list", String.valueOf(array_list));
        }
        return array_list;
    }

    public void getTotal(int month,int year) {
        array_list = new ArrayList<String>();
        array_list1 = new ArrayList<String>();
        try {

            //BETWEEN date('now','start of month')AND date('now', 'localtime')"

            //hp = new HashMap();
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor res = db.rawQuery(" SELECT * FROM temptotalrecords_n1 WHERE month ="+month+" AND year="+year+"", null);
            if (res.getCount() == 0) {
                Log.e("null", "no record to display");
                return;
            }

            while (res.moveToNext()) {


                array_list.add(res.getString(res.getColumnIndex("")));
                array_list1.add(res.getString(res.getColumnIndex("")));
                new GetterSetter().addDate(res.getString(res.getColumnIndex("")));


                // get  the  data into array,or class variable
            }
            v = array_list.size();
            Log.e("list", String.valueOf(array_list.size()));
            Log.e("list1", String.valueOf(array_list1));
            res.close();
        } catch (Exception e) {
            Log.e("error", e.getMessage());
        }

    }
 public int  getItemRecord(String date) {
     int sum=0;
     arraylistn1 = new ArrayList<String>();
     arraylistn2 = new ArrayList<String>();
     try {

         //BETWEEN date('now','start of month')AND date('now', 'localtime')"

         //hp = new HashMap();
         SQLiteDatabase db = this.getReadableDatabase();

         Cursor res = db.rawQuery(" SELECT * FROM categoryrecords WHERE date='"+ date+"'", null);
         if (res.getCount() == 0) {
             Log.e("null", "no record to display");
             return 0;
         }

         while (res.moveToNext()) {

           Log.e("rescount",String.valueOf(res.getCount()));
             arraylistn1.add(res.getString(res.getColumnIndex("categoryname")));
             arraylistn2.add(res.getString(res.getColumnIndex("categoryamount")));
             Log.e("check", res.getString(res.getColumnIndex("categoryamount")));


             // get  the  data into array,or class variable
         }



         res.close();
     } catch (Exception e) {
         Log.e("error", e.getMessage());
     }
     return sum;
 }
    public ArrayList<HashMap<String, String>> getsearch_month(int month,int year) {
        ArrayList<HashMap<String, String>> rowList = new ArrayList<HashMap<String, String>>();


        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery(" SELECT * FROM totalrecord WHERE month=" + month + " AND year=" + year + "", null);
        //res.moveToFirst();


        if (res.getCount() == 0) {
            return null;
        }

        while (res.moveToNext()) {
            HashMap<String, String> map = new HashMap<String, String>();

            //Here have the number of columns you want to have according to your database
            map.put("date", res.getString(res.getColumnIndex("")));
            map.put("total", res.getString(res.getColumnIndex("")));
            // array_list.add(res.getInt(0));
            rowList.add(map);
            Log.e("listtotal", String.valueOf(rowList));
        }


        return rowList;

    }


    public int Linegraph(String month,int year)

    {
        int size=0;
        HashMap<String, String> rowList = new HashMap<>();
        temp=new ArrayList<>();
        temp1=new ArrayList<>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor res = db.rawQuery(" SELECT strftime('%d', date) AS day,SUM(categoryamount) as total FROM categoryrecords WHERE strftime('%Y',date)='"+year+"' AND strftime('%m',date) ='"+month+"' GROUP BY day", null);
            //res.moveToFirst();


            while (res.moveToNext()) {


                //Here have the number of columns you want to have according to your database
                // rowList.put(res.getString(res.getColumnIndex("day")),res.getString(res.getColumnIndex("total")));

                temp.add(res.getString(res.getColumnIndex("day")));
                temp1.add(res.getString(res.getColumnIndex("total")));


                Log.e("listtotal", temp + "" + temp1 + "");
            }
            size = temp.size();
        }
        catch (Exception e)
        {
            Log.e("error",e.getMessage());
        }

        return size ;



    }

    public ArrayList<Integer> getTotalof_eachmonth(int year) {
        ArrayList<Integer> array_list = new ArrayList<>();
         month_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();

            Cursor res = db.rawQuery("SELECT strftime('%m',date) as valMonth,SUM(categoryamount) as valTotalMonth FROM categoryrecords WHERE strftime('%Y',date)='" + year + "' GROUP BY valMonth", null);
            //res.moveToFirst();


            if (res.getCount() == 0) {
                return null;
            }

            while (res.moveToNext()) {
                if (res.getInt(0) == 0) {
                    array_list.add(0);
                } else
                month_list.add(res.getInt(res.getColumnIndex("valMonth")));
                    array_list.add(res.getInt(res.getColumnIndex("valTotalMonth")));
                //res.moveToNext();
                Log.e("yeartotal", String.valueOf(array_list)+" "+month_list);

        }

        return array_list;

    }

    public ArrayList<Integer> get_Piechart(String month,int year) {
        ArrayList<Integer> array_list = new ArrayList<>();
        categoryname= new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
         try
         {
        Cursor res = db.rawQuery("SELECT strftime('%d',date) as valDay,categoryname,SUM(categoryamount) as valTotalDay FROM categoryrecords WHERE strftime('%Y',date)='"+year+"' AND strftime('%m',date) ='"+month+"' GROUP BY categoryname", null);
        //res.moveToFirst();


        if (res.getCount() == 0) {
            return null;
        }

        while (res.moveToNext()) {

            categoryname.add(res.getString(res.getColumnIndex("categoryname")));
            array_list.add(res.getInt(res.getColumnIndex("valTotalDay")));
            //res.moveToNext();
            Log.e("listtotal", String.valueOf(array_list)+" "+month_list);

        }}catch (Exception e)
         {
             Log.e("e",e.getMessage());
         }

        return array_list;

    }

    public ArrayList<HashMap<String,String>> getrecordbetween_dates(String minDate, String maxDate) {
        datearray_list = new ArrayList<String>();
        totalarray_list = new ArrayList<String>();
        ArrayList<String> categorgyname=new ArrayList<>();
        ArrayList<HashMap<String, String>> rowList = new ArrayList<HashMap<String, String>>();

        try {

            SQLiteDatabase db = this.getReadableDatabase();
           // String insert = db.rawQuery("select categoryname,SUM(categoryamount) As amount FROM categoryrecords WHERE  date  BETWEEN  " + minDate + " AND '" + maxDate +"' GROUP BY categoryname");

            //Log.e("insert", insert);
            Cursor res = db.rawQuery("select strftime('%Y-%m-%d', date) AS days, categoryname,SUM(categoryamount) As amount FROM categoryrecords WHERE  date  BETWEEN  '"+minDate+"' AND '"+maxDate+"' GROUP BY days,categoryname", null);

            if (res.getCount() == 0) {
                Log.e("null", "no record to display");
                return rowList;
            }
            // res.moveToFirst();
            while (res.moveToNext()) {

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("date", res.getString(res.getColumnIndex("days")));
                map.put("name", res.getString(res.getColumnIndex("categoryname")));
                map.put("daysum", String.valueOf(res.getInt(res.getColumnIndex("amount"))));

                rowList.add(map);
                Log.e("listtotal", String.valueOf(rowList));

            }
            //[400, 100, 250, 700, 0, 400] [10, 11]
            res.close();

        } catch (Exception e) {
            Log.e("error", e.getMessage());

        }
        return rowList;
    }


    /*************************************Update************************************/
    public boolean updatedailycategory(String category, int categoryamount,String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("categoryamount", categoryamount);


        // db.update("temptotalrecords_n1", contentValues, "date"+"="+date, null);
        db.update("categoryrecords", contentValues, "date = ? AND categoryname = ? ", new String[]{date,category});
       // Log.e("cv", String.valueOf(db.update("categoryrecords", contentValues, "date= ? ", new String[]{date,category})));
        return true;
    }

    public boolean updatetotal(String total, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //contentValues.put("date",date);
        contentValues.put("total", total);
        contentValues.put("date", date);

        // db.update("temptotalrecords_n1", contentValues, "date"+"="+date, null);
        db.update("totalrecord", contentValues, "date= ? ", new String[]{date});
        Log.e("cv", String.valueOf(db.update("totalrecord", contentValues, "date= ? ", new String[]{date})));
        return true;
    }

    public boolean updateIncomeRecord(String date, int month,int year, String income) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("date", date);
        contentValues.put("income", income);

        // db.update("tempincome_savings2", contentValues, "month" + "="+month, null);
        db.update("income_savings", contentValues, "month = ? AND year=? ", new String[]{String.valueOf(month),String.valueOf(year)});
        Log.e("cv", String.valueOf(contentValues));
        return true;
    }

    /******************************************Numberofrows***************************/
    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db,"");
        return numRows;
    }
    public int checkincome(int month,int year) {
        String countQuery = "SELECT  income FROM income_savings WHERE strftime('%Y',date)='"+year+"' AND strftime('%m',date) ='"+month+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        Log.e("cnt", String.valueOf(cnt));
        cursor.close();
        return cnt;
    }

    public boolean deleteitem(String date,String categoryname)

    {
       int id=0;
        SQLiteDatabase db=this.getWritableDatabase();


        Cursor res = db.rawQuery("SELECT id FROM categoryrecords WHERE strftime('%Y-%m-%d',date)='" + date + "' AND categoryname = '"+categoryname +"'", null);
        //res.moveToFirst();


        if (res.moveToFirst()) {
            id=res.getInt(res.getColumnIndex("id"));
            Log.e("id",String.valueOf(id));
            db.execSQL("DELETE FROM categoryrecords WHERE id ="+id+" ");
            db.close();
        }

        else
        {
            return false;
        }



        return true;
    }

}

