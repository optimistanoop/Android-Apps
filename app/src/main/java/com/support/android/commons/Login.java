package com.support.android.commons;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.support.android.user.R;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Login extends ActionBarActivity {

    Spinner roleSpinner;
    Employees employee;
    TextView userId;
    TextView password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setCustomView(R.layout.activity_layout_welcome);
        ab.setDisplayOptions(ab.DISPLAY_SHOW_CUSTOM);

        roleSpinner = ((Spinner) findViewById(R.id.user_role));
        userId = (TextView) findViewById(R.id.username);
        password =(TextView) findViewById(R.id.password);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.login_role, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);

        employee = (Employees) getApplication();
        userId.setRawInputType(Configuration.KEYBOARD_12KEY);

      //  startService(new Intent(this, PushNotificationService.class));
    }

    public void validateUser(View view){

        new LoginAuthenticationAsyncTask().execute();
    };
    // async task for validating user credential
    private class LoginAuthenticationAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            return fetchDataFromServer();
        }

        @Override
        protected void onPostExecute(String result) {

            try {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.getString("status").equals("true") ){
                    employee.setFullName(jsonObject.getString("name"));
                    // empId do not comes as response
                    employee.setEmpId(userId.getText().toString());
                    employee.setUserRole(jsonObject.getString("role"));

                    if(roleSpinner.getSelectedItem().toString().equals("User")){
                         employee.setUserRoleForView("User");
                        employee.setEmpIdForPersonalDetail(userId.getText().toString());

                    }else {
                        employee.setUserRoleForView("Admin");
                    }
                    // start activity
                    Intent intent=new Intent(Login.this,MainActivity.class);
                    startActivity(intent);

                }else {
                     Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
                }

            }catch (Exception e){

                // Toast.makeText(getApplicationContext(), "Stream Exception", Toast.LENGTH_SHORT).show();
            }
        };

        protected void onProgressUpdate(Integer... progress) {

        }

        private String fetchDataFromServer() {
            InputStream inputStream = null;
            String result = "";
            String url = Employees.serverUrl+"login";

            try {
                // Add your data
                // 1. create HttpClient

                HttpClient httpclient = new DefaultHttpClient();
                // 2. make POST request to the given URL
                HttpPost httpPost = new HttpPost(url);

                String json = "";
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("employeeId", userId.getText().toString());
                jsonObject.accumulate("password", password.getText().toString());
                jsonObject.accumulate("role", roleSpinner.getSelectedItem().toString());


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
               // inputStream = httpResponse.getEntity().getContent();
                if (httpResponse != null) {
                   inputStream = httpResponse.getEntity().getContent();
                    result = convertStreamToString(inputStream);
                }

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Exception while making hhtp calls", Toast.LENGTH_SHORT).show();
            }
            return result;
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
                 Toast.makeText(getApplicationContext(), "Stream Exception while paesing string result", Toast.LENGTH_SHORT).show();
            }
            return total.toString();
        }
    }; // async task class ends here

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
