package com.testing.letshelpngo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class print_ngo_details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_ngo_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle extras = getIntent().getExtras();

        TextView name = (TextView) findViewById(R.id.name);
        TextView description = (TextView) findViewById(R.id.description);
        TextView activity = (TextView) findViewById(R.id.activity);
        TextView contact = (TextView) findViewById(R.id.contact);
        Log.d(extras.getString("name"),"PASSED");
        Log.d(extras.getString("desc"),"PASSED");
        Log.d(extras.getString("activity"), "PASSED");

        //TextView errMsg = (TextView) findViewById(R.id.errMsg);
        if(extras!=null){
            name.setText(extras.getString("name"));
            description.setText(extras.getString("desc"));
            activity.setText(extras.getString("activity"));
            contact.setText(extras.getString("contact"));
        }else{
            Log.d("null","bundle");
           // errMsg.setText("Nothing to show.");
        }
    }

}
