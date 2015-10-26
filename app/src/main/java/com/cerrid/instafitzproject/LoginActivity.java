package com.cerrid.instafitzproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);
        }
        final EditText uName = (EditText)findViewById(R.id.username);
        final EditText pwd = (EditText)findViewById(R.id.password);
        Button loginBtn = (Button) findViewById(R.id.login_button);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uName.getText().toString().equals("")) {
                    uName.setError("This field cannot be blank");
                } else if (pwd.getText().toString().equals("")) {
                    pwd.setError("This field cannot be blank");
                } else {
                    ValidateUser();
                }
            }
        });
     }

    public void ValidateUser(){
        FitInfoPojo fitInfoPojo = (FitInfoPojo)getApplication();
        InputStream inputStream = null;
        String result = "";
        String url = "http://52.1.81.173:9000/validateuser";
        //  String url = "http://192.168.14.143:9000/saveuserdetails";
        try {
            // Add your data
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            String json = "";
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("username", ((EditText) findViewById(R.id.username)).getText().toString());
            jsonObject.accumulate("password", ((EditText) findViewById(R.id.password)).getText().toString());
            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);
            // 6. set httpPost Entity
            httpPost.setEntity(se);
            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);


            // added by anoop code refactor
            String line ="" ;
            if (httpResponse != null) {
                InputStream inputstream = httpResponse.getEntity().getContent();
                line = convertStreamToString(inputstream);


            }

            JSONObject responseObj = new JSONObject(line);
           /* if(httpResponse.getStatusLine().getStatusCode() == 200) {
                Toast.makeText(this,"success",Toast.LENGTH_SHORT).show();
            }*/
            if(responseObj.getString("Status").equals("Success")){
               // Toast.makeText(this,"Login Success",Toast.LENGTH_SHORT).show();
                Intent intent=new  Intent(LoginActivity.this,OrderStatus.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(this,"Login Failed",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e) {

            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        }
    public void gotoRegisterPage(View view){
        Intent intent=new  Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
    private String convertStreamToString(InputStream is) {
        String line = "";
        StringBuilder total = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = rd.readLine()) != null) {
                total.append(line);
            }
        } catch (Exception e) {
            // Toast.makeText(getApplicationContext(), "Stream Exception", Toast.LENGTH_SHORT).show();
        }
        return total.toString();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
