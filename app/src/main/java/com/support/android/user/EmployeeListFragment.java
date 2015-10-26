

package com.support.android.user;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.support.android.commons.Employees;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

public class EmployeeListFragment extends Fragment {
    Employees employee;
    List<String> empList = new ArrayList<String>();
    LinkedHashMap<String , String> empMap = new LinkedHashMap<String, String>();
    RecyclerView rv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        new EmployeeListAsyncTask().execute();
        employee = (Employees) getActivity().getApplication();
       rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_emp_list, container, false);

        return rv;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        List<String> empIdList = new ArrayList<String>(empMap.keySet());
            recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(getActivity(), empList,empIdList));

    }


    // async task for getting personal detail
    private class EmployeeListAsyncTask extends AsyncTask<String, Integer, String> {

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
                JSONArray jsonArray = new JSONArray(result);

                for(int i = 0 ; i<jsonArray.length();i++){
                    JSONObject eachJsonObj = jsonArray.getJSONObject(i);
                    empList.add(eachJsonObj.getString("name") );
                    empMap.put(eachJsonObj.getString("id") ,eachJsonObj.getString("name"));
                }

            }catch (Exception e){

                 Toast.makeText(getActivity().getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }

            setupRecyclerView(rv);
        };

        protected void onProgressUpdate(Integer... progress) {

        }

        private String fetchDataFromServer() {
            String line = "";
            HttpResponse response = null;
            try {
                HttpClient client = new DefaultHttpClient();
                String apiUrl = Employees.serverUrl+"allEmployee";
                HttpGet request = new HttpGet(apiUrl);
                response = client.execute(request);
                if (response != null) {
                    InputStream inputstream = response.getEntity().getContent();
                    line = convertStreamToString(inputstream);
                }

            } catch (ClientProtocolException e) {
                //   Toast.makeText(getApplicationContext(), "Caught ClientProtocolException", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                //  Toast.makeText(getApplicationContext(), "Caught IOException", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                // Toast.makeText(getApplicationContext(), "Caught Exception", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
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

    public  class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private List<String> mValues;
        private List<String> mKeys;

        public  class ViewHolder extends RecyclerView.ViewHolder {
            public String mBoundString;
            public String mBoundId;

            public final View mView;
            public final ImageView mImageView;
            public final TextView mTextView;


            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (ImageView) view.findViewById(R.id.avatar);
                mTextView = (TextView) view.findViewById(android.R.id.text1);

            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTextView.getText();
            }
        }

        public String getValueAt(int position) {
            return mValues.get(position);
        }

        public SimpleStringRecyclerViewAdapter(Context context, List<String> items, List<String> keys) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mValues = items;
            mKeys = keys;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.mBoundString = mValues.get(position);
            holder.mBoundId = mKeys.get(position);
            holder.mTextView.setText(mValues.get(position));


            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    TextView actionBarName = (TextView) getActivity().findViewById(R.id.textViewActionBarTitle);
                    actionBarName.setText("Employee Detail");
                    //
                    employee.setEmpIdForPersonalDetail(holder.mBoundId);
                    RelativeLayout layout = (RelativeLayout) getActivity().findViewById(R.id.switching);
                    layout.removeAllViewsInLayout();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    PersonalDetailRecyclerFrag personalDetailRecyclerFrag = new PersonalDetailRecyclerFrag();
                    fragmentTransaction.add(R.id.switching, personalDetailRecyclerFrag, "HELLO");
                    fragmentTransaction.commit();
                }
            });


           Glide.with(holder.mImageView.getContext())
                    .load(Employees.getRandomCheeseDrawable())
                    .fitCenter()
                    .into(holder.mImageView);


         /*   Bitmap bitmap;
            URL imageURL = null;
            String imageLocation="http://www.allindiaflorist.com/imgs/arrangemen4.jpg";

            try {
                imageURL = new URL(imageLocation);
            }

            catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                HttpURLConnection connection= (HttpURLConnection)imageURL.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream inputStream = connection.getInputStream();

                bitmap = BitmapFactory.decodeStream(inputStream);//Convert to bitmap
                holder.mImageView.setImageBitmap(bitmap);
            }
            catch (IOException e) {

                e.printStackTrace();
            }*/
        }
        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }
}
