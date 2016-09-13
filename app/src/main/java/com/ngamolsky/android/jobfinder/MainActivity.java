package com.ngamolsky.android.jobfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchJobs(View view) {
        Intent i = new Intent(this, JobActivity.class);
        startActivity(i);
    }

    public void aboutUs(View view) {
        Intent i = new Intent(this, MapActivity.class);
        startActivity(i);
    }

    public void launchPhoto(View view) {
        Intent i = new Intent(this, PhotoActivity.class);
        startActivity(i);
    }
}
