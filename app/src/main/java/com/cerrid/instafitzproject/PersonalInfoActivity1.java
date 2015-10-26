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
import android.widget.Toast;


public class PersonalInfoActivity1 extends Activity {

    String fName;
    String lName;
    String email;
    String phone;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_personal_info_activity1);
        //Custom font
        Typeface typeFaceBold= Typeface.createFromAsset(getAssets(), "fonts/Oswald-Bold.ttf");
        Typeface typeFaceLight= Typeface.createFromAsset(getAssets(), "fonts/Oswald-Light.ttf");
        final EditText height = (EditText)findViewById(R.id.height);
        final EditText weight = (EditText)findViewById(R.id.weight);
        final EditText address = (EditText)findViewById(R.id.address);
        height.setTypeface(typeFaceLight);
        weight.setTypeface(typeFaceLight);
        address.setTypeface(typeFaceLight);
        height.setRawInputType(Configuration.KEYBOARD_12KEY);
        weight.setRawInputType(Configuration.KEYBOARD_12KEY);
        Button registerContinue = (Button)findViewById(R.id.continueBtn);
        registerContinue.setTypeface(typeFaceLight);
    }

    public void gotoPersonalInfo2(View view){

        if(((EditText) findViewById(R.id.height)).getText().toString().equals(""))
        {
            ((EditText) findViewById(R.id.height)).setError("This field cannot be blank");
        }
        else if(((EditText)findViewById(R.id.weight)).getText().toString().equals(""))
        {
            ((EditText)findViewById(R.id.weight)).setError("This field cannot be blank");
        }
        else if(((EditText)findViewById(R.id.address)).getText().toString().equals(""))
        {
            ((EditText)findViewById(R.id.address)).setError("This field cannot be blank");
        }else{

            FitInfoPojo fitInfoPojo = (FitInfoPojo)getApplication();
            fitInfoPojo.setHeight(((EditText) findViewById(R.id.height)).getText().toString());
            fitInfoPojo.setWeight(((EditText)findViewById(R.id.weight)).getText().toString());
            fitInfoPojo.setAddress(((EditText)findViewById(R.id.address)).getText().toString());

            Intent intentPInfo2=new  Intent(PersonalInfoActivity1.this,PersonalInfoActivity2.class);
            startActivity(intentPInfo2);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_personal_info_activity1, menu);
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
