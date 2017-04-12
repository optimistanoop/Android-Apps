package com.ngamolsky.android.jobfinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class JobActivity extends AppCompatActivity {

    ArrayList<Job> mJobList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);

        mJobList = new ArrayList<>();

        initializeData();

        JobAdapter adapter = new JobAdapter(this, mJobList);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void initializeData() {
        String[] jobTitles = getResources().getStringArray(R.array.job_titles);
        String[] jobFields = getResources().getStringArray(R.array.job_field);
        int[] jobPrices = getResources().getIntArray(R.array.job_prices);

        for(int i=0; i < jobTitles.length; i++){
            String jobTitle = jobTitles[i];
            String jobField = jobFields[i];
            long jobPrice = (long)jobPrices[i];
            Job job = new Job(jobTitle,jobField,jobPrice);
            mJobList.add(job);
        }
    }
}
