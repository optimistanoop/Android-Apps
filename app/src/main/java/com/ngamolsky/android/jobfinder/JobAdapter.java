package com.ngamolsky.android.jobfinder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ngamolsky on 9/13/16.
 */
public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {

    Context mContext;
    ArrayList<Job> mJobList;

    public JobAdapter(Context context, ArrayList<Job> jobList) {
        mContext = context;
        mJobList = jobList;
    }

    @Override
    public JobViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.job_list_item,parent,false);
        return new JobViewHolder(v);
    }

    @Override
    public void onBindViewHolder(JobViewHolder holder, int position) {
        holder.mJobTitle.setText(mJobList.get(position).getmJobTitle());
        holder.mJobDescription.setText(mJobList.get(position).getmJobField());
        holder.mJobPrice.setText("Rs. " + String.valueOf(mJobList.get(position).getmJobPrice()));
    }

    @Override
    public int getItemCount() {
        return mJobList.size();
    }


    public static class JobViewHolder extends RecyclerView.ViewHolder {
        TextView mJobTitle;
        TextView mJobDescription;
        TextView mJobPrice;

        public JobViewHolder(View itemView) {
            super(itemView);
            mJobTitle = (TextView) itemView.findViewById(R.id.jobTitle);
            mJobDescription = (TextView) itemView.findViewById(R.id.jobDescription);
            mJobPrice = (TextView) itemView.findViewById(R.id.jobPrice);
        }
    }
}
