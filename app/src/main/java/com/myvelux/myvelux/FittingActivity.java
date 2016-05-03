package com.myvelux.myvelux;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class FittingActivity extends AppCompatActivity {

    private Reservation resa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitting);
        setTitle("Raccord");

        resa = (Reservation) getIntent().getSerializableExtra("resa");
        Button btnNextFitting = (Button) findViewById(R.id.btnNextFitting);

        if(btnNextFitting != null) {
            btnNextFitting.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), FinalChoiceActivity.class);
                    resa.getCommande().setFitting("EDW");
                    intent.putExtra("resa",resa);
                    startActivity(intent);
                }
            });
        }

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
