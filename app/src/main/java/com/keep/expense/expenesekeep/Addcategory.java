package com.keep.expense.expenesekeep;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by admin on 10/11/2015.
 */
public class Addcategory extends AppCompatActivity {

    DatabaseHelper dbh;
    EditText categorgyname;
    Button addcategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcategory);
        AdView mAdView = (AdView) findViewById(R.id.adView_add_c);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        dbh=new DatabaseHelper(Addcategory.this);
        categorgyname=(EditText)findViewById(R.id.textv_category);
        addcategory=(Button)findViewById(R.id.button_addcategory);

        addcategory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(categorgyname.getText().toString().equals(""))
                {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(Addcategory.this);
                    builder1.setTitle("Expense Keep");
                    builder1.setMessage("Please add category");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    categorgyname.setText("");
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                else {
                    dbh.insertCategory(categorgyname.getText().toString());
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(Addcategory.this);
                    builder1.setTitle("Expense Keep");
                    builder1.setMessage("Category is Added");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    categorgyname.setText("");
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }

            }
        });

    }
}
