package com.ngamolsky.android.jobfinder;

/**
 * Created by ngamolsky on 9/13/16.
 */
public class Job {
    String mJobField;
    String mJobTitle;
    long mJobPrice;

    public Job(String mJobField, String mJobTitle, long mJobPrice) {
        this.mJobField = mJobField;
        this.mJobTitle = mJobTitle;
        this.mJobPrice = mJobPrice;
    }

    public String getmJobField() {
        return mJobField;
    }

    public void setmJobField(String mJobField) {
        this.mJobField = mJobField;
    }

    public String getmJobTitle() {
        return mJobTitle;
    }

    public void setmJobTitle(String mJobTitle) {
        this.mJobTitle = mJobTitle;
    }


    public long getmJobPrice() {
        return mJobPrice;
    }

    public void setmJobPrice(long mJobPrice) {
        this.mJobPrice = mJobPrice;
    }
}
