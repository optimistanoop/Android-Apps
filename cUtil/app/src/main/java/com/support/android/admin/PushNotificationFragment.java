package com.support.android.admin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.support.android.commons.Employees;
import com.support.android.user.R;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Created by anoop on 18/9/15.
 */
public class PushNotificationFragment extends Fragment {
     EditText notification;
    Button save;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(
                R.layout.push_notification_fragment, container, false);

         save = (Button)view.findViewById(R.id.submitButton);
        notification= (EditText)view.findViewById(R.id.pushNotificaton);
        EditText joinCal = (EditText) view.findViewById(R.id.joiningDate);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateUser(view);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    public void validateUser(View view) {

        if (notification.getText().toString().length() == 0) {
            notification.setError("Notification  is required");
            return;
        }

        new PushNotificationAsyncTask().execute();
    }

    // async task for getting personal detail
    private class PushNotificationAsyncTask extends  AsyncTask<String, Integer, String> {

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

                notification.setText("");
            }catch (Exception e){

                // Toast.makeText(getApplicationContext(), "Stream Exception", Toast.LENGTH_SHORT).show();
            }
        };

    protected void onProgressUpdate(Integer... progress) {

    }

    private String fetchDataFromServer() {
        InputStream inputStream = null;
        String result = "";
       // String url = Employees.serverUrl+"addNotification";
        String url ="http://192.168.14.12:8080/AppServices/rest/addNotification";

        try {
            // Add your data
            // 1. create HttpClient

            HttpClient httpclient = new DefaultHttpClient();
            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            String json = "";
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("notificationText", notification.getText().toString());

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
            Toast.makeText(getActivity(), "Exception while making hhtp calls", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getActivity(), "Stream Exception while paesing string result", Toast.LENGTH_SHORT).show();
        }
        return total.toString();
    }
}; // async task class ends here
}
