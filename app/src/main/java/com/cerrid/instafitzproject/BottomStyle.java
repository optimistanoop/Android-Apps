package com.cerrid.instafitzproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class BottomStyle extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_bottom_style);

        //Custom font
        Typeface typeFaceBold= Typeface.createFromAsset(getAssets(), "fonts/Oswald-Bold.ttf");
        Typeface typeFaceLight= Typeface.createFromAsset(getAssets(), "fonts/Oswald-Light.ttf");
        final Button bottomRelaxed = (Button)findViewById(R.id.bottomRelaxed);
        final Button teperedFit = (Button)findViewById(R.id.teparedFit);
        final Button lowWaist = (Button)findViewById(R.id.lowWaist);
        bottomRelaxed.setTypeface(typeFaceLight);
        teperedFit.setTypeface(typeFaceLight);
        lowWaist.setTypeface(typeFaceLight);
        Button continueBtn = (Button)findViewById(R.id.continueBtn);
        continueBtn.setTypeface(typeFaceLight);
        TextView fit = (TextView)findViewById(R.id.bottomStyle);
        fit.setTypeface(typeFaceBold);

        bottomRelaxed.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FitInfoPojo fitInfoPojo = (FitInfoPojo) getApplication();
                fitInfoPojo.setBottomStyle("Relaxed");

                teperedFit.setBackgroundColor(getResources().getColor(R.color.gray_backrground));
                lowWaist.setBackgroundColor(getResources().getColor(R.color.gray_backrground));
                bottomRelaxed.setBackgroundColor(getResources().getColor(R.color.deepskyeblue));

            }

        });

        teperedFit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FitInfoPojo fitInfoPojo = (FitInfoPojo) getApplication();
                fitInfoPojo.setBottomStyle("Tapered Fit");
                fitInfoPojo.setBottomStyle("Tapered Fit");

                teperedFit.setBackgroundColor(getResources().getColor(R.color.deepskyeblue));
                lowWaist.setBackgroundColor(getResources().getColor(R.color.gray_backrground));
                bottomRelaxed.setBackgroundColor(getResources().getColor(R.color.gray_backrground));


            }

        });

        lowWaist.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FitInfoPojo fitInfoPojo = (FitInfoPojo) getApplication();
                fitInfoPojo.setBottomStyle("Low Waist");

                teperedFit.setBackgroundColor(getResources().getColor(R.color.gray_backrground));
                lowWaist.setBackgroundColor(getResources().getColor(R.color.deepskyeblue));
                bottomRelaxed.setBackgroundColor(getResources().getColor(R.color.gray_backrground));

            }

        });

    }

    public void moveToColorPrefrences(View view){
        Intent intent=new  Intent(BottomStyle.this,ColorPrefrences.class);
        startActivity(intent);
    };



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bottom_style, menu);
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
