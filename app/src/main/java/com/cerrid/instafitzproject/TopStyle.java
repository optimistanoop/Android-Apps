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
import android.widget.Toast;


public class TopStyle extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_top_style);
        //Custom font
        Typeface typeFaceBold= Typeface.createFromAsset(getAssets(), "fonts/Oswald-Bold.ttf");
        Typeface typeFaceLight= Typeface.createFromAsset(getAssets(), "fonts/Oswald-Light.ttf");
        final Button slimFit = (Button)findViewById(R.id.slimFit);
        final Button regular = (Button)findViewById(R.id.regular);
        final Button relaxed = (Button)findViewById(R.id.relaxed);
        final Button tall = (Button)findViewById(R.id.tall);

        TextView fit = (TextView)findViewById(R.id.topStyle);
        fit.setTypeface(typeFaceBold);
        slimFit.setTypeface(typeFaceLight);
        regular.setTypeface(typeFaceLight);
        relaxed.setTypeface(typeFaceLight);
        tall.setTypeface(typeFaceLight);
        Button continueBtn = (Button)findViewById(R.id.continueBtn);
        continueBtn.setTypeface(typeFaceLight);

        slimFit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FitInfoPojo fitInfoPojo = (FitInfoPojo) getApplication();
                fitInfoPojo.setTopStyle("Slim Fit");

                slimFit.setBackgroundColor(getResources().getColor(R.color.deepskyeblue));
                regular.setBackgroundColor(getResources().getColor(R.color.gray_backrground));
                relaxed.setBackgroundColor(getResources().getColor(R.color.gray_backrground));
                tall.setBackgroundColor(getResources().getColor(R.color.gray_backrground));


            }

        });

        regular.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FitInfoPojo fitInfoPojo = (FitInfoPojo) getApplication();
                fitInfoPojo.setTopStyle("Regular");

                slimFit.setBackgroundColor(getResources().getColor(R.color.gray_backrground));
                regular.setBackgroundColor(getResources().getColor(R.color.deepskyeblue));
                relaxed.setBackgroundColor(getResources().getColor(R.color.gray_backrground));
                tall.setBackgroundColor(getResources().getColor(R.color.gray_backrground));

            }

        });

        relaxed.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FitInfoPojo fitInfoPojo = (FitInfoPojo) getApplication();
                fitInfoPojo.setTopStyle("Relaxed");

                slimFit.setBackgroundColor(getResources().getColor(R.color.gray_backrground));
                regular.setBackgroundColor(getResources().getColor(R.color.gray_backrground));
                relaxed.setBackgroundColor(getResources().getColor(R.color.deepskyeblue));
                tall.setBackgroundColor(getResources().getColor(R.color.gray_backrground));

            }

        });

        tall.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FitInfoPojo fitInfoPojo = (FitInfoPojo) getApplication();
                fitInfoPojo.setTopStyle("Tall");

                slimFit.setBackgroundColor(getResources().getColor(R.color.gray_backrground));
                regular.setBackgroundColor(getResources().getColor(R.color.gray_backrground));
                relaxed.setBackgroundColor(getResources().getColor(R.color.gray_backrground));
                tall.setBackgroundColor(getResources().getColor(R.color.deepskyeblue));
            }

        });

    }
    public void moveToBottomStyle(View view){
        Intent intent=new  Intent(TopStyle.this,BottomStyle.class);
        startActivity(intent);
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_top_style, menu);
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
