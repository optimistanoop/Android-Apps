package com.cerrid.instafitzproject;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class PersonalInfoActivity2 extends Activity {

    String fName;
    String lName;
    String email;
    String phone;
    String password;
    String height, weight, address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);
        }
        setContentView(R.layout.activity_personal_info_activity2);
        //Custom font
        Typeface typeFaceBold= Typeface.createFromAsset(getAssets(), "fonts/Oswald-Bold.ttf");
        Typeface typeFaceLight= Typeface.createFromAsset(getAssets(), "fonts/Oswald-Light.ttf");
        final EditText profession = (EditText)findViewById(R.id.profession);
        final EditText salary = (EditText)findViewById(R.id.salary);
        final EditText carModel = (EditText)findViewById(R.id.carModel);
        final EditText payment = (EditText)findViewById(R.id.payment);
        profession.setTypeface(typeFaceLight);
        salary.setTypeface(typeFaceLight);
        carModel.setTypeface(typeFaceLight);
        payment.setTypeface(typeFaceLight);

        salary.setRawInputType(Configuration.KEYBOARD_12KEY);

        final Button continueToFit =(Button)findViewById(R.id.continueBtn);
        continueToFit.setTypeface(typeFaceLight);
        continueToFit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FitInfoPojo fitInfoPojo = (FitInfoPojo)getApplication();
                fitInfoPojo.setProfession(((EditText) findViewById(R.id.profession)).getText().toString());
                fitInfoPojo.setSalaryRange(((EditText) findViewById(R.id.salary)).getText().toString());
                fitInfoPojo.setCarModel(((EditText) findViewById(R.id.carModel)).getText().toString());
                fitInfoPojo.setPaymentGateway(((EditText)findViewById(R.id.payment)).getText().toString());

                Intent intent=new  Intent(PersonalInfoActivity2.this,FitInfo.class);
                startActivity(intent);

             //   submitData();
              //  gotoInfo();

            }
        });
    }
    public void gotoInfo(){
        Intent intent=new  Intent(PersonalInfoActivity2.this,InfoActivity.class);
        startActivity(intent);
    }

    public void submitData(){

        String prof = ((EditText)findViewById(R.id.profession)).getText().toString();
        String salary = ((EditText)findViewById(R.id.salary)).getText().toString();
        String carModel = ((EditText)findViewById(R.id.carModel)).getText().toString();
        String payment = ((EditText)findViewById(R.id.payment)).getText().toString();
        InputStream inputStream = null;
        String result = "";
      String url = "http://52.1.81.173:9000/saveuserdetails";
       // String url = "http://192.168.14.143:9000/saveuserdetails";
        try {
            // Add your data
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            String json = "";

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("firstname", fName);
            jsonObject.accumulate("lastname",lName);
            jsonObject.accumulate("password", password);
            jsonObject.accumulate("emailid", email);
            jsonObject.accumulate("phoneno", phone);
            jsonObject.accumulate("height", height);
            jsonObject.accumulate("weight", weight);
            jsonObject.accumulate("address", address);
            jsonObject.accumulate("profession", prof);
            jsonObject.accumulate("salaryrange", salary);
            jsonObject.accumulate("carmodel", carModel);
            jsonObject.accumulate("paymentgateway", payment);

            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
          //  if(inputStream != null)
          //      Toast.makeText(this,"Sent",Toast.LENGTH_SHORT).show();
               // result = convertInputStreamToString(inputStream);
          //  else
          //      result = "Did not work!";
           /* HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://52.1.81.173:9000/saveuserdetails");

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(12);

            nameValuePairs.add(new BasicNameValuePair("firstname", fName));
            nameValuePairs.add(new BasicNameValuePair("lastname",lName));
            nameValuePairs.add(new BasicNameValuePair("password", password));
            nameValuePairs.add(new BasicNameValuePair("emailid", email));
            nameValuePairs.add(new BasicNameValuePair("phoneno", phone));
            nameValuePairs.add(new BasicNameValuePair("height", height));
            nameValuePairs.add(new BasicNameValuePair("weight", weight));
            nameValuePairs.add(new BasicNameValuePair("address", address));
            nameValuePairs.add(new BasicNameValuePair("profession", prof));
            nameValuePairs.add(new BasicNameValuePair("salaryrange", salary));
            nameValuePairs.add(new BasicNameValuePair("carmodel", carModel));
            nameValuePairs.add(new BasicNameValuePair("paymentgateway", payment));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity=response.getEntity();
*/
        } catch (Exception e) {
           //Toast.makeText(this, e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_personal_info_activity2, menu);
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
