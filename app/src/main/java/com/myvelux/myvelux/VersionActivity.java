package com.myvelux.myvelux;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class VersionActivity extends AppCompatActivity {

    private Reservation resa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version);
        setTitle("Version");
        
        resa = (Reservation) getIntent().getSerializableExtra("resa");
        Button btnNextVersion= (Button) findViewById(R.id.btnNextVersion);
        btnNextVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start new activity
                Intent intent = new Intent(v.getContext(), SizeActivity.class);
                resa.getCommande().setVersion("Confort");
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
}
