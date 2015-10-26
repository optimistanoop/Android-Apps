package com.support.android.user;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import com.support.android.admin.EditUserDetailFragment;
import com.support.android.commons.Employees;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by anoop on 1/9/15.
 */
public class PersonalDetailRecyclerFrag extends Fragment {
  //  List<String> cardData = new ArrayList<String>();
  //  List<String> cardHeadings = new ArrayList<String>();
    Map<String, String> map = new LinkedHashMap<String , String>();
    Employees employee;
    String idForPersonalDetail;
    RecyclerView rv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       rv = (RecyclerView) inflater.inflate(
               R.layout.personal_detail_recyclerview, container, false);

        employee = (Employees) getActivity().getApplication();
        idForPersonalDetail = employee.getEmpIdForPersonalDetail();
        new PersonalDetailAsyncTask().execute();
        return rv;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
       // cardHeadings.addAll(Arrays.asList(Employees.cardHeadings));
        // now we have card headings and data, now to make haeading and data available
        // if user is admin and logged in as admin then he should able to edit
        if(employee.getUserRole().equals("Admin") && employee.getUserRoleForView().equals("Admin") ){

            // to do
            // edit button for editing details of employee
            recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(getActivity(), map));
            createEditView();
            createCallView();

        }
        // if user is hr or admin or seeing self deatils , he should be able to see call button with all details (20)
       else if(employee.getUserRole().equals("HR") || employee.getUserRole().equals("Admin") || employee.getEmpId().equals(employee.getEmpIdForPersonalDetail())){
            // either for admin or hr , give full permission
        //    recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(getActivity(), cardData, cardHeadings));

          recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(getActivity(), map));
          createCallView();
        }
        // if user is other then associate
        else if(!employee.getUserRole().equals("Associate")){
            // for not a associate , permissions are n-6
          map.remove("Emergency Contact Person Name");
          map.remove("Emergency Person Relation");
          map.remove("PAN");
          map.remove("Emergency Contact Number");
          map.remove("Present Address");
          map.remove("Permanent Address");
          recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(getActivity(), map));
          createCallView();
        }
        // if user is asociate
        else {
            // for associate .\, permissions are n-6 +-1 (phone no)
          map.remove("Emergency Contact Person Name");
          map.remove("Emergency Person Relation");
          map.remove("PAN");
          map.remove("Emergency Contact Number");
          map.remove("Present Address");
            map.remove("Permanent Address");

          if(!map.get("Role").equals("Associate")){
              map.remove("Contact Number");
          }else {
              createCallView();
          }
            recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(getActivity(), map));
        }

      //  recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(getActivity(), map));

    }
    public void createEditView(){
        ImageView editImage = new ImageView(getActivity());
        editImage.setBackgroundResource(R.drawable.ic_dashboard);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(100, 100);
        lp.setMargins(300, 50, 0, 0);
        editImage.setLayoutParams(lp);

        RelativeLayout switchingLayout = (RelativeLayout) getActivity().findViewById(R.id.switching);
        switchingLayout.addView(editImage);

        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RelativeLayout layout = (RelativeLayout) getActivity().findViewById(R.id.switching);
                layout.removeAllViewsInLayout();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                EditUserDetailFragment editUserDeatilFragment = new EditUserDetailFragment();
                fragmentTransaction.add(R.id.switching, editUserDeatilFragment, "HELLO");
                fragmentTransaction.commit();
            }
        });
    }

    public void createCallView(){
        ImageView image = new ImageView(getActivity());
        image.setBackgroundResource(R.drawable.ap_resize);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(100, 100);
        lp.setMargins(450, 50, 0, 0);
        image.setLayoutParams(lp);

        RelativeLayout switchingLayout = (RelativeLayout) getActivity().findViewById(R.id.switching);
        switchingLayout.addView(image);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                  callIntent.setData(Uri.parse("tel:" + map.get("Contact No")));
                startActivity(callIntent);
            }
        });
    }
    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private List<String> mValues;
        private List<String> mKeys;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public String mBoundString;
            public final View mView;
            public final TextView mTextView;
            public final TextView mTextViewdata;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mTextView = (TextView) view.findViewById(R.id.carddata);
                mTextViewdata = (TextView) view.findViewById(R.id.cardheading);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTextView.getText();
            }
        }

        public String getValueAt(int position) {
            return mValues.get(position);
        }

      //  public SimpleStringRecyclerViewAdapter(Context context, List<String> items, List<String> keys) {
      public SimpleStringRecyclerViewAdapter(Context context,Map<String, String> map) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
           // mValues = items;
           // mKeys = keys;
          mValues =  new ArrayList<String>(map.values());
          mKeys =new ArrayList<String>(map.keySet());
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.personal_detail_two_list, parent, false);
            view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.mBoundString = mValues.get(position);
            holder.mTextView.setText(mValues.get(position));
            holder.mTextViewdata.setText(mKeys.get(position));

        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }

    // async task for getting personal detail
    private class PersonalDetailAsyncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            return fetchDataFromServer();
        }

        protected void onPostExecute(String result) {
            setupRecyclerView(rv);
        }

        private String fetchDataFromServer() {
            String line = "";
            HttpResponse response = null;
            try {
                HttpClient client = new DefaultHttpClient();
                String apiUrl = Employees.serverUrl+"employeeByID/" + idForPersonalDetail;
                HttpGet request = new HttpGet(apiUrl);
                response = client.execute(request);
                if (response != null) {
                    InputStream inputstream = response.getEntity().getContent();
                    line = convertStreamToString(inputstream);
                }
                JSONObject jsonObject = new JSONObject(line);

                //this addition should be sequential to hold suitable card heading and data
            /*    cardData.add(jsonObject.getString("employeeID"));
                cardData.add(jsonObject.getString("firstName") + " " + jsonObject.getString("lastName"));
                cardData.add(jsonObject.getString("designation"));
                cardData.add(jsonObject.getString("mobileNumber"));
                cardData.add(jsonObject.getString("emergencyNumber"));
                cardData.add(jsonObject.getString("joiningDate"));
                cardData.add(jsonObject.getString("presentAddr"));
                cardData.add(jsonObject.getString("permanentAddr"));
                cardData.add(jsonObject.getString("personalEmailId"));
                cardData.add(jsonObject.getString("officialEmailId"));

                cardData.add(jsonObject.getString("emergencyPersonName"));
                cardData.add(jsonObject.getString("emergencyRelation"));
                cardData.add(jsonObject.getString("project"));
                cardData.add(jsonObject.getString("reportingManager"));
                cardData.add(jsonObject.getString("skillSet"));
                cardData.add(jsonObject.getString("bloodGroup"));
                cardData.add(jsonObject.getString("hobbies"));
                cardData.add(jsonObject.getString("pan"));
                cardData.add(jsonObject.getString("birthDate"));
                cardData.add(jsonObject.getString("role"));*/


                map.put("Employee Id",jsonObject.getString("employeeID"));
                map.put("Name",jsonObject.getString("firstName") + " " + jsonObject.getString("lastName"));
                map.put("Designation", jsonObject.getString("designation"));
                map.put("Contact Number", jsonObject.getString("mobileNumber"));
                map.put("Emergency Contact No", jsonObject.getString("emergencyNumber"));
                map.put("Date Of Joining",jsonObject.getString("joiningDate"));
                map.put("Present Address",jsonObject.getString("presentAddr"));
                map.put("Permanent Address", jsonObject.getString("permanentAddr"));
                map.put("Personal Email",jsonObject.getString("personalEmailId"));
                map.put("Professional Email",jsonObject.getString("officialEmailId"));

                map.put("Emergency Contact Person Name",jsonObject.getString("emergencyPersonName"));
                map.put("Emergency Person Relation", jsonObject.getString("emergencyRelation"));
                map.put("Project", jsonObject.getString("project"));
                map.put("Reporting Manager",jsonObject.getString("reportingManager"));
                map.put("Skill Set", jsonObject.getString("skillSet"));
                map.put("Blood Group", jsonObject.getString("bloodGroup"));
                map.put("Hobbies", jsonObject.getString("hobbies"));
                map.put("PAN",jsonObject.getString("pan"));
                map.put("Date Of Birth",jsonObject.getString("birthDate"));
                map.put("Role",jsonObject.getString("role"));

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
    }
}
