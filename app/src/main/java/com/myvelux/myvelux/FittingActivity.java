package com.myvelux.myvelux;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class FittingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitting);
    }

    protected void btnEnd(View view){
        // start new activity
        Intent myIntent = new Intent(view.getContext(), FittingActivity.class);
        startActivity(myIntent);
    }
    protected void btnRoom(View view){
        // start new activity
        Intent myIntent = new Intent(view.getContext(), RoomActivity.class);
        startActivity(myIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
