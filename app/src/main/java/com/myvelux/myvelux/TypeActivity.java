package com.myvelux.myvelux;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class TypeActivity extends BaseActivity {

    private Commande com;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        setTitle("Matériaux");

        Button btnNextType= (Button) findViewById(R.id.btnNextType);

        com = (Commande) getIntent().getSerializableExtra("com");

        if (btnNextType != null) {
            btnNextType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // start new activity
                    Intent intent = new Intent(v.getContext(), VersionActivity.class);
                    com.setType("Bois");
                    intent.putExtra("com",com);
                    startActivity(intent);
                }
            });
        }
    }

}
