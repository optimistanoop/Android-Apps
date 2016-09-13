package com.ngamolsky.android.jobfinder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class RegistrationActivity extends AppCompatActivity {

    MyOwnDatabase myOwnDb;
    EditText e1,e2,e3,e4,e5,e6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        myOwnDb = new MyOwnDatabase(this);

        e1 = (EditText)findViewById(R.id.edit1);
        e2 = (EditText)findViewById(R.id.edit2);
        e3 = (EditText)findViewById(R.id.edit3);
        e4 = (EditText)findViewById(R.id.edit4);
        e5 = (EditText)findViewById(R.id.edit5);
        e6 = (EditText)findViewById(R.id.edit6);
    }





    public void registerMe(View v){
        myOwnDb.insertDatabase(e1.getText().toString(),e2.getText().toString(),e3.getText().toString(),e4.getText().toString(),e5.getText().toString(),e6.getText().toString());
    }

    public void launchWeb(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("https://www.thanet.gov.uk/your-services/equality-and-diversity/why-do-you-ask-me-questions-about-my-gender,-age,-disability-etc-in-surveys/why-do-you-ask-me-questions-about-my-gender,-age,-disability-etc-in-surveys/"));
        startActivity(i);
    }
}

