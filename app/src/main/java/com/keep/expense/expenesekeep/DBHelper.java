package com.keep.expense.expenesekeep;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String CONTACTS_TABLE_NAME = "temp";
    public static final String CONTACTS_TABLE_NAME1 = "temptotal_new1";
    public static final String CONTACTS_TABLE_NAME2 = "tempsaving";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "date";
    public static final String CONTACTS_COLUMN_EMAIL = "item";
    public static final String CONTACTS_COLUMN_STREET = "itemamount";
    public static final String CONTACTS_COLUMN_TOTAL = "total";
    static ArrayList<String> array_list,arraylistn1,arraylistn2,temp,temp1;
    static ArrayList<String> array_list1;
    ArrayList<String> datearray_list, totalarray_list;
    static int v;


    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 14);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table temp_itemrecords " + "(id integer primary key autoincrement, date datetime,item text,itemamount text, total text)");

        db.execSQL(
                "CREATE TABLE IF NOT EXISTS temptotalrecords_n1" + "(id integer primary key autoincrement,Income_Id integer,month integer,year integer,date datetime,total text,FOREIGN KEY(Income_Id) REFERENCES tempincome_savings2(Income_Id))");


        db.execSQL(
                "CREATE TABLE IF NOT EXISTS tempincome_savings2 " + "(Income_Id integer primary key autoincrement,month integer,year integer,date datetime,income text,savingtype text)");
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS tempbalance_records " + "(id integer primary key autoincrement,Income_Id integer,date datetime,balance text,FOREIGN KEY(Income_Id) REFERENCES tempincome_savings2(Income_Id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("newversion", String.valueOf(newVersion));
        db.execSQL("DROP TABLE IF EXISTS temp_itemrecords");
        db.execSQL("DROP TABLE IF EXISTS temptotalrecords");

        onCreate(db);
    }

    public boolean insertRecord(String date, String item, String itemamount, int total) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date", date);
        contentValues.put("item", item);
        contentValues.put("itemamount", itemamount);
        contentValues.put("total", total);

        db.insert("temp_itemrecords", null, contentValues);
        Log.e("cont", String.valueOf(contentValues));
        return true;
    }

    public boolean insertbalance(String date, String balance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date", date);
        contentValues.put("balance", balance);


        db.insert("tempbalance_records", null, contentValues);
        Log.e("cont", String.valueOf(contentValues));
        return true;
    }

    /**                       **/
    public boolean insertincome(String income,  int month ,int year,String date,String savingtype) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("income", income);
        contentValues.put("month", month);
        contentValues.put("year",year);
        contentValues.put("date", date);



        contentValues.put("savingtype", savingtype);
        db.insert("tempincome_savings2", null, contentValues);
        Log.e("cont_s", String.valueOf(contentValues));

        return true;
    }

    public int getid(String income) {
        int income_id = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from tempincome_savings2 WHERE income='" + income + "'", null);
        res.moveToFirst();
        {
            String id = res.getString(res.getColumnIndex("balance"));
            income_id = Integer.parseInt(id);
            Log.e("id", id);
        }
        return income_id;

    }

    public int getlastrecord() {
        int income = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM tempincome_savings2 ORDER BY Income_Id DESC LIMIT 1", null);
        res.moveToFirst();
        {
            String id = res.getString(res.getColumnIndex("income"));
            income = Integer.parseInt(id);
            Log.e("id", id);
        }
        return income;
    }

    public int getincomeof_month(int month,int year) {
        int income = 0;
        SQLiteDatabase db = this.getReadableDatabase();
       /* "SELECT column1, (SELECT max(column1) FROM table1) AS max FROM table1 " +
                "WHERE month = ? OR column1 = ? ORDER BY column1";*/
        String query="SELECT * FROM tempincome_savings2 WHERE month="+month+" AND year="+year+"";
        Cursor res = db.rawQuery(query, null);
        Log.e("res",query);
        res.moveToFirst();
        {
            String id = res.getString(res.getColumnIndex("income"));
            income = Integer.parseInt(id);
            Log.e("id", id);
        }
        return income;
    }

    public int getBalance() {
        int balance = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM temptotalrecords_n1 ORDER BY id DESC LIMIT 1", null);
        res.moveToFirst();
        if (res.getCount() == 0) {

            return 0;
        }

        String id = res.getString(res.getColumnIndex("balance"));
        balance = Integer.parseInt(id);
        Log.e("id", id);

        return balance;
    }

    public boolean updateSavingtype(Integer id, String income, String savingtype) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("income", income);
        contentValues.put("savingtype", savingtype);

        db.update("tempincome_savings2", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from temp_itemrecords where id=" + id + "", null);
        return res;
    }


    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME1);
        return numRows;
    }

    public boolean updateRecord(String date, int month,int year, String income, String savingtype) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("date", date);
        contentValues.put("income", income);
        contentValues.put("savingtype", savingtype);
       // db.update("tempincome_savings2", contentValues, "month" + "="+month, null);
         db.update("tempincome_savings2", contentValues, "month = ? AND year=? ", new String[]{String.valueOf(month),String.valueOf(year)});
        Log.e("cv", String.valueOf(contentValues));
        return true;
    }


    public ArrayList<String> getAllRecords(int month,int year) {
        ArrayList<String> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from temptotalrecords_n1 WHERE month=" + month + " AND year="+year+"", null);
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

    public void addtotal(String date, int month,int year, int total) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date", date);
        contentValues.put("month", month);
        contentValues.put("year",year);
        contentValues.put("total", total);
        db.insert("temptotalrecords_n1", null, contentValues);
        Log.e("cont", String.valueOf(contentValues));
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


                array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
                array_list1.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_TOTAL)));
                new GetterSetter().addDate(res.getString(res.getColumnIndex(CONTACTS_COLUMN_TOTAL)));


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

            Cursor res = db.rawQuery(" SELECT * FROM temp_itemrecords WHERE date ='"+date+"'", null);
            if (res.getCount() == 0) {
                Log.e("null", "no record to display");
                return 0;
            }

            while (res.moveToNext()) {


                arraylistn1.add(res.getString(res.getColumnIndex("item")));
                arraylistn2.add(res.getString(res.getColumnIndex("itemamount")));
                Log.e("check",res.getString(res.getColumnIndex("item")));


                // get  the  data into array,or class variable
            }
                //sum=res.getInt(0);
            Log.e("list", String.valueOf(arraylistn1.size()));
            Log.e("list1", String.valueOf(arraylistn2));
            res.close();
        } catch (Exception e) {
            Log.e("error", e.getMessage());
        }
        return sum;
    }
    public int getsum() {
        int sum = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT SUM(total) FROM temptotalrecords_n1 WHERE date BETWEEN datetime('now','start of month') AND datetime('now', 'localtime')", null);
        if (res.moveToFirst()) {
            sum = res.getInt(0);
        }
        Log.e("sum", String.valueOf(sum));
        return sum;
    }

    public void getrecordbetween_dates(String datefrom, String dateto) {
        datearray_list = new ArrayList<String>();
        totalarray_list = new ArrayList<String>();
        Date startDate, endDate;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //startDate = (Date) sdf.parse(datefrom);//convert string to date
            // endDate = (Date) sdf.parse(dateto);
            //hp = new HashMap();
            SQLiteDatabase db = this.getReadableDatabase();
            String insert = "select * from temptotalrecords_n1"; // WHERE date  BETWEEN  '"+datefrom+"' AND '" +dateto+ "'";
            // "SELECT * FROM temptotalrecords WHERE date BETWEEN ?  AND ?", new String[]{datefrom,dateto}
            Log.e("insert", insert);
            Cursor res = db.rawQuery(insert, null);

            if (res.getCount() == 0) {
                Log.e("null", "no record to display");
                return;
            }
            // res.moveToFirst();
            while (res.moveToNext()) {


                datearray_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
                totalarray_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_TOTAL)));


                // get  the  data into array,or class variable
            }

            Log.e("datelist", String.valueOf(datearray_list.size()));
            Log.e("totallist1", String.valueOf(totalarray_list));
            new GetterSetter().setDate_1(datearray_list);
            new GetterSetter().setTime_1(totalarray_list);


        } catch (Exception e) {
            Log.e("error", e.getMessage());
        }
    }

    /*************************************************************************************/
    public ArrayList<Integer> getTotalof_eachmonth(int year) {
        ArrayList<Integer> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        for (int i = 1; i <= 12; i++) {
            Cursor res = db.rawQuery(" SELECT SUM(total) FROM temptotalrecords_n1 WHERE month=" + i + " AND year="+year+"", null);
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

    public int getmonth_sum(int month,int year) {
        int sum = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT SUM(total) FROM temptotalrecords_n1 WHERE month=" + month + " AND year="+year+"", null);
        if (res.moveToFirst()) {
            sum = res.getInt(0);
        }
        Log.e("sum", String.valueOf(sum));
        res.close();
        return sum;
    }

    public ArrayList<HashMap<String, String>> getsearch_month(int month,int year) {
        ArrayList<HashMap<String, String>> rowList = new ArrayList<HashMap<String, String>>();


        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery(" SELECT * FROM temptotalrecords_n1 WHERE month=" + month + " AND year="+year+"", null);
        //res.moveToFirst();


        if (res.getCount() == 0) {
            return null;
        }

        while (res.moveToNext()) {
            HashMap<String, String> map = new HashMap<String, String>();

            //Here have the number of columns you want to have according to your database
            map.put("date", res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            map.put("total", res.getString(res.getColumnIndex(CONTACTS_COLUMN_TOTAL)));
            // array_list.add(res.getInt(0));
            rowList.add(map);
            Log.e("listtotal", String.valueOf(rowList));
        }


        return rowList;

    }

    public int checkincome(int month,int year) {
        String countQuery = "SELECT  income FROM tempincome_savings2 where month=" + month +" AND year="+year+"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        Log.e("cnt", String.valueOf(cnt));
        cursor.close();
        return cnt;
    }

    public String checktotal(String date) {
        String total = null;
        String countQuery = "SELECT SUM(total) FROM temptotalrecords_n1 WHERE date='"+ date +"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor.moveToFirst()) {
            total =String.valueOf(cursor.getInt(0));

        } else {
            total="";
        }
        // int cnt = cursor.getCount();
        // Log.e("cnt",String.valueOf(cnt));
        Log.e("total",total+"null");
        cursor.close();
        return total;
    }

    /*************************************
     * Updatetotal
     *******************************/
    public boolean updatetotal(String total, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //contentValues.put("date",date);
        contentValues.put("total", total);
        contentValues.put("date", date);

       // db.update("temptotalrecords_n1", contentValues, "date"+"="+date, null);
        db.update("temptotalrecords_n1", contentValues, "date= ? ", new String[]{date});
        Log.e("cv", String.valueOf(  db.update("temptotalrecords_n1", contentValues, "date= ? ", new String[]{date})));
        return true;
    }

    public int Linegraph(int month,int year)

    {
        int size=0;
        HashMap<String, String> rowList = new HashMap<>();
         temp=new ArrayList<>();
         temp1=new ArrayList<>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor res = db.rawQuery(" SELECT strftime('%d', date) AS day,total FROM temptotalrecords_n1 WHERE month=" + month + " AND year=" + year + "", null);
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
    public boolean Deleterecord()
    {
         SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM temptotalrecords_n1 ");
        db.close();
        return true;
    }
}


