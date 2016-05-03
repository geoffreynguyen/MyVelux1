package com.myvelux.myvelux;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class RangeActivity extends AppCompatActivity {

    private Reservation resa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_range);
        setTitle("Gamme");

        Button button= (Button) findViewById(R.id.btnNextRange);
        resa = (Reservation) getIntent().getSerializableExtra("resa");

        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // start new activity
                    Intent intent = new Intent(v.getContext(), TypeActivity.class);
                    resa.getCommande().setRange("Solaire");
                    intent.putExtra("resa",resa);
                    startActivity(intent);
                }
            });
        }
    }

    protected void btnNext(View view){
        // start new activity
        Intent intent = new Intent(view.getContext(), RoomActivity.class);
        resa = (Reservation) getIntent().getSerializableExtra("resa");
        intent.putExtra("resa",resa);
        startActivity(intent);
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
