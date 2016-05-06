package com.keep.expense.expenesekeep;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.eftimoff.androidplayer.Player;
import com.eftimoff.androidplayer.actions.property.PropertyAction;
import com.vstechlab.easyfonts.EasyFonts;

public class Splashscreen extends Activity {

    TextView e;
    ImageView i;
    private static int SPLASH_TIME_OUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        e=(TextView)findViewById(R.id.txts);
         i=(ImageView)findViewById(R.id.imgw);
        e.setTypeface(EasyFonts.droidSerifBold(Splashscreen.this));

        animate(e, i);
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(Splashscreen.this, Frontpage.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);



}
    private void animate(View layout1, View layout2) {
        final PropertyAction ly1 = PropertyAction.newPropertyAction(layout1).interpolator(new DecelerateInterpolator()).translationY(-200).duration(750).alpha(0.4f).build();

        final PropertyAction ly2 = PropertyAction.newPropertyAction(layout2).scaleX(0).translationY(-200).duration(750).alpha(0f).build();
        //  final PropertyAction spinnern2 = PropertyAction.newPropertyAction(spinner2).scaleX(0).scaleY(0).duration(550).alpha(0f).build();

        Player.init().
                animate(ly1).
                then().
                animate(ly2).

                play();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
