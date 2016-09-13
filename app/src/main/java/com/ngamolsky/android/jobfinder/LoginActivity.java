package com.ngamolsky.android.jobfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;



public class LoginActivity extends AppCompatActivity {

    MyOwnDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new MyOwnDatabase(this);
    }


    public void onLogin(View v){

        boolean mybool = db.getData();

        if(mybool == true){
            Intent i1 = new Intent(this, MainActivity.class);
            startActivity(i1);
        }
        else {
            Toast.makeText(this,"Not Registered yet",Toast.LENGTH_SHORT).show();
        }

    }

    public void onRegister(View v){
        Intent i1 = new Intent(this, RegistrationActivity.class);
        startActivity(i1);
    }
}
