package com.cerrid.instafitzproject;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class FitInfo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_fit_info);
        TextView fit = (TextView)findViewById(R.id.fitInfo);
        Typeface typeFaceBold= Typeface.createFromAsset(getAssets(), "fonts/Oswald-Bold.ttf");
        Typeface typeFaceLight= Typeface.createFromAsset(getAssets(), "fonts/Oswald-Light.ttf");
        fit.setTypeface(typeFaceBold);
        EditText sleeve = (EditText)findViewById(R.id.sleeve);
        sleeve.setTypeface(typeFaceLight);
        sleeve.setRawInputType(Configuration.KEYBOARD_12KEY);
        EditText neck = (EditText)findViewById(R.id.neck);
        neck.setTypeface(typeFaceLight);
        neck.setRawInputType(Configuration.KEYBOARD_12KEY);
        EditText chest = (EditText)findViewById(R.id.chest);
        chest.setTypeface(typeFaceLight);
        chest.setRawInputType(Configuration.KEYBOARD_12KEY);
        EditText waist = (EditText)findViewById(R.id.waist);
        waist.setTypeface(typeFaceLight);
        waist.setRawInputType(Configuration.KEYBOARD_12KEY);
        Button continueBtn = (Button)findViewById(R.id.continueBtn);
        continueBtn.setTypeface(typeFaceLight);
    }

    public void moveToTopStyle(View view){

       // to get data and to set to the application contxt
        FitInfoPojo fitInfoPojo = (FitInfoPojo) getApplication();

        fitInfoPojo.setSleeve(((EditText)findViewById(R.id.sleeve)).getText().toString());
        fitInfoPojo.setNeck(((EditText)findViewById(R.id.neck)).getText().toString());
        fitInfoPojo.setChest(((EditText)findViewById(R.id.chest)).getText().toString());
        fitInfoPojo.setWaist(((EditText)findViewById(R.id.waist)).getText().toString());

        // to move to new activity
        Intent intent=new  Intent(FitInfo.this,TopStyle.class);
        startActivity(intent);
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fit_info, menu);
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
