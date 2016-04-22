package com.myvelux.myvelux;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import java.util.ArrayList;

public class ActionActivity extends AppCompatActivity {

    public ArrayList<ArrayList<String>> finalQuote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        setTitle("Action");
    }

    protected void btnNext(View view){
        // start new activity
        Intent myIntent = new Intent(view.getContext(), RangeActivity.class);
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
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButton:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.radioButton2:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }


}
