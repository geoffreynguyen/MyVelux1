package com.myvelux.myvelux;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.ArrayList;

public class ActionActivity extends AppCompatActivity {

    private Reservation resa;
    private String action;
    private EditText actionPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        setTitle("Action");

        actionPrice = (EditText) findViewById(R.id.costs);
        resa = (Reservation) getIntent().getSerializableExtra("resa");

        Button button= (Button) findViewById(R.id.btnNextAction);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start new activity
                Intent intent = new Intent(v.getContext(), RangeActivity.class);
                resa.getCommande().setAction(action);
                resa.getCommande().setActionPrice(actionPrice.getText().toString());
                intent.putExtra("resa",resa);
                startActivity(intent);
            }
        });

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
                    action = ((RadioButton) view).getText().toString();
                    break;
            case R.id.radioButton2:
                if (checked)
                    action = ((RadioButton) view).getText().toString();
                break;
        }
    }


}
