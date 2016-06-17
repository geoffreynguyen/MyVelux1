package com.myvelux.myvelux;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class VersionActivity extends BaseActivity {

    private Commande com;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version);
        setTitle("Version");
        
        com = (Commande) getIntent().getSerializableExtra("com");

        Button btnNextVersion= (Button) findViewById(R.id.btnNextVersion);
        if (btnNextVersion != null) {
            btnNextVersion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // start new activity
                    Intent intent = new Intent(v.getContext(), SizeActivity.class);
                    com.setVersion("Confort");
                    intent.putExtra("com",com);
                    startActivity(intent);
                }
            });
        }
    }

}
