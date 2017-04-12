package com.zeladder.zeladder;

/**
 * Created by anoop on 16/8/15.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class YourLadder extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rv = (View) inflater.inflate(
                R.layout.your_ladder, container, false);

        // setupRecyclerView(rv);
        return rv;
    }
}