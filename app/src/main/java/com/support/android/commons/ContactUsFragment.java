package com.support.android.commons;

/**
 * Created by anoop on 23/9/15.
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.support.android.user.R;

public class ContactUsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rv = inflater.inflate(
                R.layout.contact_us_fragment, container, false);

        TextView india = (TextView)rv.findViewById(R.id.indiaoffice);
        TextView us = (TextView)rv.findViewById(R.id.usoffice);
        TextView indiaNo = (TextView)rv.findViewById(R.id.indiaofficeNo);
        TextView usNo1 = (TextView)rv.findViewById(R.id.usofficeNo1);
        TextView usNo2 = (TextView)rv.findViewById(R.id.usofficeN02);
        india.setText(Html.fromHtml(getString(R.string.indiaoffice)));
        us.setText(Html.fromHtml(getString(R.string.usoffice)));
        indiaNo.setText(Html.fromHtml(getString(R.string.indiaNo)));
        usNo1.setText(Html.fromHtml(getString(R.string.usNo1)));
        usNo2.setText(Html.fromHtml(getString(R.string.usNo2)));

        indiaNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:8065344202"));
                startActivity(callIntent);
            }
        });

        usNo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:19173009310" ));
                startActivity(callIntent);
            }
        });
        usNo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:19085041977" ));
                startActivity(callIntent);
            }
        });

        return rv;
    }
}