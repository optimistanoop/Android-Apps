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
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.InputStream;


public class ColorPrefrences extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_color_prefrences);

        //Custom font
        Typeface typeFaceBold= Typeface.createFromAsset(getAssets(), "fonts/Oswald-Bold.ttf");
        Typeface typeFaceLight= Typeface.createFromAsset(getAssets(), "fonts/Oswald-Light.ttf");
        final EditText top = (EditText)findViewById(R.id.top);
        final EditText bottom = (EditText)findViewById(R.id.bottom);
        top.setTypeface(typeFaceLight);
        bottom.setTypeface(typeFaceLight);
        Button continueBtn = (Button)findViewById(R.id.continueBtn);
        continueBtn.setTypeface(typeFaceLight);
        TextView fit = (TextView)findViewById(R.id.colorPrefrence);
        fit.setTypeface(typeFaceBold);
    }

    public void submitFitData(View view){

        // to submit all data with http client
        FitInfoPojo fitInfoPojo = (FitInfoPojo)getApplication();
        fitInfoPojo.setTop(((EditText)findViewById(R.id.top)).getText().toString());
        fitInfoPojo.setBottom(((EditText)findViewById(R.id.bottom)).getText().toString());

        // submit call
        submitData();
        gotoInfo();
    }


    public void gotoInfo(){
        Intent intent=new  Intent(ColorPrefrences.this,InfoActivity.class);
        startActivity(intent);
    }

    public void submitData(){

        FitInfoPojo fitInfoPojo = (FitInfoPojo)getApplication();
        InputStream inputStream = null;
        String result = "";
        String url = "http://52.1.81.173:9000/saveuserdetails";
      //  String url = "http://192.168.14.143:9000/saveuserdetails";
        try {
            // Add your data
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            String json = "";
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("firstname", fitInfoPojo.getFirstName());
            jsonObject.accumulate("lastname",fitInfoPojo.getLastName());
            jsonObject.accumulate("password", fitInfoPojo.getPassword());
            jsonObject.accumulate("emailid", fitInfoPojo.getEmail());
            jsonObject.accumulate("phoneno", fitInfoPojo.getPhone());
            jsonObject.accumulate("height", fitInfoPojo.getHeight());
            jsonObject.accumulate("weight", fitInfoPojo.getWeight());
            jsonObject.accumulate("address", fitInfoPojo.getAddress());
            jsonObject.accumulate("profession", fitInfoPojo.getProfession());
            jsonObject.accumulate("salaryrange", fitInfoPojo.getSalaryRange());
            jsonObject.accumulate("carmodel", fitInfoPojo.getCarModel());
            jsonObject.accumulate("paymentgateway", fitInfoPojo.getPaymentGateway());
            jsonObject.accumulate("sleeve", fitInfoPojo.getSleeve());
            jsonObject.accumulate("neck",fitInfoPojo.getNeck());
            jsonObject.accumulate("chest", fitInfoPojo.getChest());
            jsonObject.accumulate("waist", fitInfoPojo.getWaist());
            jsonObject.accumulate("topstyle", fitInfoPojo.getTopStyle());
            jsonObject.accumulate("bottomstyle", fitInfoPojo.getBottomStyle());
            jsonObject.accumulate("colortop", fitInfoPojo.getTop());
            jsonObject.accumulate("colorbottom", fitInfoPojo.getBottom());
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
            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

        } catch (Exception e) {
            //Toast.makeText(this, e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_color_prefrences, menu);
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
