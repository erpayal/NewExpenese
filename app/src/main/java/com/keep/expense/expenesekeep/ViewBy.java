package com.keep.expense.expenesekeep;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.vstechlab.easyfonts.EasyFonts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class ViewBy extends DialogFragment {

    TextView t1,t2,t3,t4;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.view_by, container, false);


        t1 = (TextView) v.findViewById(R.id.txtv1);
        t1.setTypeface(EasyFonts.robotoThin(getActivity()));
        t2 = (TextView) v.findViewById(R.id.txtv2);
        t2.setTypeface(EasyFonts.robotoThin(getActivity()));
        t3 = (TextView) v.findViewById(R.id.txtv3);
        t3.setTypeface(EasyFonts.robotoThin(getActivity()));
        t4 = (TextView) v.findViewById(R.id.txtv4);
        t4.setTypeface(EasyFonts.robotoThin(getActivity()));

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //exportDB();
                Intent i = new Intent(getActivity(), Viewbydailyactivity.class);
                startActivity(i);
            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // importDB();
                Intent i = new Intent(getActivity(), Viewbyweeklyactivity.class);
                startActivity(i);
            }
        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(getActivity(), ViewBymonthlyact.class);
                startActivity(i2);
            }
        });
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Searchclass.class);
                startActivity(i);
            }
        });
        return v;
    }
        @Override
        public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
            android.app.Dialog dialog = super.onCreateDialog(savedInstanceState);
            // request a window without the title
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            return dialog;
        }
        @Override
        public void onStart() {
            super.onStart();
            Dialog dialog = getDialog();
            if (dialog != null) {

                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        }





}
