package com.support.android.admin;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;
import com.support.android.commons.Employees;
import com.support.android.user.R;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;

/**
 * Created by anoop on 8/9/15.
 */
public class UserResistration  extends Fragment  {

    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    Calendar cal;
    int year, month, day;
    EditText employeeId, firstName, lastName, presentAddress, permanentAddress, designation, personalEmail, professionalEmail, contactNumber, emrgencyNumber, joiningDate,
            emergencyPersonName,emergencyRelation, project, reportingManager, skillSet, bloodGroup,hobbies,pan, birthDate, password;
    Spinner role;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rv = (View) inflater.inflate(
                R.layout.user_resistration, container, false);
        Button save = (Button)rv.findViewById(R.id.submitButton);
        EditText joinCal = (EditText) rv.findViewById(R.id.joiningDate);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateUser(view);
            }
        });
        return rv;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //calender
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);

        employeeId=(EditText)getActivity().findViewById(R.id.empId);
        firstName=(EditText)getActivity().findViewById(R.id.firstName);
        lastName=(EditText)getActivity().findViewById(R.id.lastName);
        presentAddress=(EditText)getActivity().findViewById(R.id.presentAddress);
        permanentAddress=(EditText)getActivity().findViewById(R.id.permanentAddress);
        designation=(EditText) getActivity().findViewById(R.id.designation);
        personalEmail=(EditText)getActivity().findViewById(R.id.personalEmail);
        professionalEmail=(EditText)getActivity().findViewById(R.id.professionalEmail);
        contactNumber=(EditText)getActivity().findViewById(R.id.contactNumber);
        emrgencyNumber=(EditText)getActivity().findViewById(R.id.emergencyNumber);
        joiningDate=(EditText)getActivity().findViewById(R.id.joiningDate);
        contactNumber.setRawInputType(Configuration.KEYBOARD_12KEY);
        emrgencyNumber.setRawInputType(Configuration.KEYBOARD_12KEY);
        employeeId.setRawInputType(Configuration.KEYBOARD_12KEY);

        emergencyPersonName=(EditText)getActivity().findViewById(R.id.EmergencyPersonName);
        emergencyRelation=(EditText)getActivity().findViewById(R.id.EmergencyPersonRelation);
        project=(EditText)getActivity().findViewById(R.id.Project);
        reportingManager=(EditText)getActivity().findViewById(R.id.ReportingManager);
        skillSet=(EditText)getActivity().findViewById(R.id.SkillSet);
        bloodGroup=(EditText) getActivity().findViewById(R.id.BloodGroup);
        hobbies=(EditText)getActivity().findViewById(R.id.Hobbies);
        pan=(EditText)getActivity().findViewById(R.id.Pan);
        birthDate=(EditText)getActivity().findViewById(R.id.DOB);
        role = ((Spinner) getActivity().findViewById(R.id.Role));
       // password=(EditText)getActivity().findViewById(R.id.Password);

        joiningDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getFragmentManager(), "JoiningDatePicker");

            }
        });

        birthDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getFragmentManager(), "DOBDatePicker");

            }
        });

      //  Spinner spinner = (Spinner) getActivity().findViewById(R.id.Role);
      // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.resister_role, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
// Apply the adapter to the spinner
        role.setAdapter(adapter);
        Button browse = (Button)getActivity().findViewById(R.id.BrowseButton);
        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, 1);
            }
        });

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == -1
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ImageView imgView = (ImageView) getActivity().findViewById(R.id.pic);
                // Set the Image in ImageView after decoding the String
                imgView.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));

            } else {
                // you havnt pic the image
            }
        } catch (Exception e) {
            // sometjimng went wrong
        }

    }
    public void validateUser(View view){


        if(employeeId.getText().toString().length()==0)
        {
            employeeId.setError("Employee id is required");
            return;
        }
        if(firstName.getText().toString().length()==0)
        {
            firstName.setError("First name is required");
            return;
        }
        if(lastName.getText().toString().length()==0)
        {
            lastName.setError("Last name is required");
            return;
        }
        if(presentAddress.getText().toString().length()==0)
        {
            presentAddress.setError("Present address is required");
            return;
        }
        if(permanentAddress.getText().toString().length()==0)
        {
            permanentAddress.setError("Permanent address is required");
            return;
        }
        if(designation.getText().toString().length()==0)
        {
            designation.setError("Designation is required");
            return;
        }

        if(personalEmail.getText().toString().length()==0)
        {
            personalEmail.setError("Personal email is required");
            return;
        }
        if(professionalEmail.getText().toString().length()==0)
        {
            professionalEmail.setError("Professional email is required");
            return;
        }
        if(contactNumber.getText().toString().length()==0)
        {
            contactNumber.setError("Contact number is required");
            return;
        }
        if(emrgencyNumber.getText().toString().length()==0)
        {
            emrgencyNumber.setError("Emrgency contact number is required");
            return;
        }


        if(emergencyPersonName.getText().toString().length()==0)
        {
            emergencyPersonName.setError("Emrgency contact person name  is required");
            return;
        }
        if(emergencyRelation.getText().toString().length()==0)
        {
            emergencyRelation.setError("Emrgency contact person relation  is required");
            return;
        }

        if(project.getText().toString().length()==0)
        {
            project.setError("Project is required");
            return;
        }

        if(reportingManager.getText().toString().length()==0)
        {
            reportingManager.setError("Reporting manager is required");
            return;
        }

        if(skillSet.getText().toString().length()==0)
        {
            skillSet.setError("Skill set is required");
            return;
        }

        if(bloodGroup.getText().toString().length()==0)
        {
            bloodGroup.setError("Blood group is required");
            return;
        }

        if(hobbies.getText().toString().length()==0)
        {
            hobbies.setError("Hobbies  required");
            return;
        }
        if(pan.getText().toString().length()==0)
        {
            pan.setError("PAN no  is required");
            return;
        }
        if(birthDate.getText().toString().length()==0)
        {
            birthDate.setError("DOB is required");
            return;
        }

     /*   if(role.getSelectedItem().toString().length()==0)
        {
            role.setError("Role  is required");
            return;
        }*/
        if(password.getText().toString().length()==0)
        {
            password.setError("Password is required");
            return;
        }

        if(joiningDate.getText().toString().length()==0)
        {
            joiningDate.setError("Joining date is required");
            return;
        }
        new UserResistrationAsyncTask().execute();


    }
    // async task for validating user credential
    private class UserResistrationAsyncTask extends AsyncTask<String, Integer, String> {

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
               String name ="";

            try {
                JSONObject jsonObject = new JSONObject(result);
         name= jsonObject.getString("firstName");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(name.length() >0){
                Toast.makeText(getActivity(), "Data Saved", Toast.LENGTH_SHORT).show();
                employeeId.setText(""); firstName.setText(""); lastName.setText(""); presentAddress.setText(""); permanentAddress.setText(""); designation.setText("");
                personalEmail.setText(""); professionalEmail.setText(""); contactNumber.setText(""); emrgencyNumber.setText(""); joiningDate.setText("");
                        emergencyPersonName.setText("");emergencyRelation.setText(""); project.setText(""); reportingManager.setText(""); skillSet.setText("");
                bloodGroup.setText("");
                hobbies.setText("");pan.setText(""); birthDate.setText(""); password.setText("");
                role.setSelection(Adapter.NO_SELECTION);

            }

        };

        protected void onProgressUpdate(Integer... progress) {

        }

        private String fetchDataFromServer() {

            InputStream inputStream = null;

            String result = "";
            String url = Employees.serverUrl+"addEmployee";
            try {
                // Add your data
                // 1. create HttpClient

                HttpClient httpclient = new DefaultHttpClient();
                // 2. make POST request to the given URL
                HttpPost httpPost = new HttpPost(url);

                String json = "";
                JSONObject jsonObject = new JSONObject();

                jsonObject.accumulate("employeeID", employeeId.getText().toString());
                jsonObject.accumulate("firstName", firstName.getText().toString());
                jsonObject.accumulate("lastName", lastName.getText().toString());
                jsonObject.accumulate("designation", designation.getText().toString());
                jsonObject.accumulate("mobileNumber", contactNumber.getText().toString());
                jsonObject.accumulate("emergencyNumber", emrgencyNumber.getText().toString());
                jsonObject.accumulate("permanentAddr", permanentAddress.getText().toString());
                jsonObject.accumulate("presentAddr",presentAddress.getText().toString());
                jsonObject.accumulate("personalEmailId",personalEmail.getText().toString());
                jsonObject.accumulate("officialEmailId", professionalEmail.getText().toString());

                jsonObject.accumulate("emergencyPersonName", emergencyPersonName.getText().toString());
                jsonObject.accumulate("emergencyRelation", emergencyRelation.getText().toString());
                jsonObject.accumulate("project", project.getText().toString());
                jsonObject.accumulate("reportingManager", reportingManager.getText().toString());
                jsonObject.accumulate("skillSet", skillSet.getText().toString());
                jsonObject.accumulate("bloodGroup", bloodGroup.getText().toString());
                jsonObject.accumulate("hobbies", hobbies.getText().toString());
                jsonObject.accumulate("pan",pan.getText().toString());
                jsonObject.accumulate("birthDate",birthDate.getText().toString());
                jsonObject.accumulate("role", role.getSelectedItem().toString());
                jsonObject.accumulate("password", password.getText().toString());

                jsonObject.accumulate("joiningDate", joiningDate.getText().toString());
                //  jsonObject.accumulate("role",roleSpinner.getSelectedItem().toString());

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
                // Toast.makeText(getApplicationContext(), "Stream Exception", Toast.LENGTH_SHORT).show();
            }
            return total.toString();
        }
    }; // async task class ends here


    public  void setDataForDatePicker(){

    }

    public  class  SelectDateFragment  extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            populateSetDate(yy, mm+1, dd);
        }
        public void populateSetDate(int year, int month, int day) {

            if(getTag().equals("DOBDatePicker")){
                birthDate.setText(day + "-" + month + "-" + year);
            }else {
                joiningDate.setText(day + "-" + month + "-" + year);
            }
        }

    }
}