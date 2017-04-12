package com.support.android.commons;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import com.support.android.user.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class PushNotificationService extends Service {

    String name ="";
    public PushNotificationService() {
    }

    // constant
    public static final long NOTIFY_INTERVAL = 60 * 1000; // 60 seconds

    // run on another Thread to avoid crash
    private Handler mHandler = new Handler();
    // timer handling
    private Timer mTimer = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        // cancel if already existed
        if (mTimer != null) {
            mTimer.cancel();
        } else {
            // recreate new
            mTimer = new Timer();
        }
        // schedule task
      //  new BackgroundAsyncTask().execute();
        mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 0, NOTIFY_INTERVAL);
    }

    class TimeDisplayTimerTask extends TimerTask {

        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {

                @Override
                public void run() {
                    // display toast
                  //  Toast.makeText(getApplicationContext(), getDateTime(), Toast.LENGTH_SHORT).show();
                   // Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                new BackgroundAsyncTask().execute();
                }

            });
        }

        private String getDateTime() {
            // get date time in custom format
            SimpleDateFormat sdf = new SimpleDateFormat("[yyyy/MM/dd - HH:mm:ss]");
            return sdf.format(new Date());
        }


    }
    // async task for validating user credential
    private class BackgroundAsyncTask extends AsyncTask<String, Integer, String> {

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

                //  name= jsonObject.getString("name");
                // Toast.makeText(getApplicationContext(),jsonObject.getString("role"), Toast.LENGTH_LONG).show();
                NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notify=new Notification(R.drawable.cerrid_logo_new,"Text",System.currentTimeMillis());
                PendingIntent pending= PendingIntent.getActivity(getApplicationContext(), 0, new Intent(), 0);
                notify.setLatestEventInfo(getApplicationContext(),"Subject",jsonObject.getString("notificationText"),pending);
                notif.notify(0, notify);



            }catch (Exception e){

                 Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        protected void onProgressUpdate(Integer... progress) {

        }

        private String fetchDataFromServer() {
            String line = "";
            HttpResponse response = null;
            try {
                HttpClient client = new DefaultHttpClient();
                // String apiUrl = Employees.serverUrl+"getNotification";
                String apiUrl ="http://192.168.14.12:8080/AppServices/rest/getNotification";

                HttpGet request = new HttpGet(apiUrl);
                response = client.execute(request);
                if (response != null) {
                    InputStream inputstream = response.getEntity().getContent();
                    line = convertStreamToString(inputstream);
                }

            } catch (Exception e) {

            }
            return line;
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
    }; // async task class ends here
}
