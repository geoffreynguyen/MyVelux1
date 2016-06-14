package com.myvelux.myvelux;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class SizeActivity extends BaseActivity {

    private Reservation resa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size);
        setTitle("Taille");

        resa = (Reservation) getIntent().getSerializableExtra("resa");
        Button btnNextSize= (Button) findViewById(R.id.btnNextSize);
        if (btnNextSize != null) {
            btnNextSize.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // start new activity
                    Intent intent = new Intent(v.getContext(), FittingActivity.class);
                    resa.getCommande().setSize("70 x 60");
                    intent.putExtra("resa",resa);
                    startActivity(intent);
                }
            });
        }
    }

}
