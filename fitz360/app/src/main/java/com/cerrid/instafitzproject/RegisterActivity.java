package com.cerrid.instafitzproject;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        //Custom font
        Typeface typeFaceBold= Typeface.createFromAsset(getAssets(), "fonts/Oswald-Bold.ttf");
        Typeface typeFaceLight= Typeface.createFromAsset(getAssets(), "fonts/Oswald-Light.ttf");
        final EditText fName = (EditText)findViewById(R.id.firstName);
        final EditText LName = (EditText)findViewById(R.id.lastName);
        final EditText email = (EditText)findViewById(R.id.emailId);
        final EditText phone = (EditText)findViewById(R.id.phone);
        final EditText pwd = (EditText)findViewById(R.id.password);
        final EditText reenterPwd = (EditText)findViewById(R.id.reenter_password);
        fName.setTypeface(typeFaceLight);
        LName.setTypeface(typeFaceLight);
        email.setTypeface(typeFaceLight);
        phone.setTypeface(typeFaceLight);
        phone.setRawInputType(Configuration.KEYBOARD_12KEY);
        pwd.setTypeface(typeFaceLight);
        reenterPwd.setTypeface(typeFaceLight);
        Button continueBtn = (Button)findViewById(R.id.continueBtn);
        continueBtn.setTypeface(typeFaceLight);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fName.getText().toString().equals(""))
                {
                    fName.setError("This field cannot be blank");
                }
                else if(LName.getText().toString().equals(""))
                {
                    LName.setError("This field cannot be blank");
                }
                else if(email.getText().toString().equals(""))
                {
                    email.setError("This field cannot be blank");
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){

                    email.setError("Please provide valid email address");
                }
                else if(phone.getText().toString().equals(""))
                {
                    phone.setError("This field cannot be blank");
                }
                else if(pwd.getText().toString().equals(""))
                {
                    pwd.setError("This field cannot be blank");
                }
                else if(reenterPwd.getText().toString().equals(""))
                {
                    reenterPwd.setError("This field cannot be blank");
                }

                else if(!isValidPassword(pwd.getText().toString()))
                {
                    pwd.setError("Password must contain one digit , one uppercase & one lowercase charcter" +
                            " one special symbol from list '@#$%' and should 6-20 charecter long  ");
                }
                else if(!pwd.getText().toString().equals(reenterPwd.getText().toString()))
                {
                    pwd.setError("Password does not match");
                }
                else
                {
                    gotoPersonalInfo1();
                }

            }
        });
    }


    public boolean isValidPassword(String password){

           String PASSWORD_PATTERN =
                "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";

        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    };
    public void gotoPersonalInfo1(){

        FitInfoPojo fitInfoPojo = (FitInfoPojo)getApplication();
        fitInfoPojo.setFirstName(((EditText)findViewById(R.id.firstName)).getText().toString());
        fitInfoPojo.setLastName(((EditText) findViewById(R.id.lastName)).getText().toString());
        fitInfoPojo.setEmail(((EditText)findViewById(R.id.emailId)).getText().toString());
        fitInfoPojo.setPhone(((EditText)findViewById(R.id.phone)).getText().toString());
        fitInfoPojo.setPassword(((EditText)findViewById(R.id.password)).getText().toString());
        // start new intent
        Intent intent=new  Intent(RegisterActivity.this,PersonalInfoActivity1.class);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
