package com.keep.expense.expenesekeep;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by admin on 9/18/2015.
 */
public class Category_Adapter extends BaseAdapter {

    int s;
   static int l;
    ArrayList<String> cat,am;
    public Category_Adapter(int count) {
        s=count;
    }
    public Category_Adapter() {

    }

    public Category_Adapter(ArrayList<String> c, ArrayList<String> a) {
        cat=c;
        am=a;
    }

    @Override
    public int getCount() {

        return cat.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Context c=parent.getContext();

        LayoutInflater inflater=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.category_listitem,parent,false);
        TextView txt_c=(TextView)convertView.findViewById(R.id.txt_category);
        TextView txt_a=(TextView)convertView.findViewById(R.id.txt_amount);
        txt_c.setText(cat.get(position));
        txt_a.setText(am.get(position));
        return convertView;
    }
}
