package com.myvelux.myvelux;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class ShowCommande extends AppCompatActivity {

    private Commande commande;
    TextView action, actionPrice, room, range, type, version, size, fitting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_commande);
        setTitle("Détail de le commande");

        action = (TextView) findViewById(R.id.actionShow);
        actionPrice = (TextView) findViewById(R.id.actionPriceShow);
        room = (TextView) findViewById(R.id.roomShow);
        range = (TextView) findViewById(R.id.rangeShow);
        type = (TextView) findViewById(R.id.typeShow);
        version = (TextView) findViewById(R.id.versionShow);
        size = (TextView) findViewById(R.id.sizeShow);
        fitting = (TextView) findViewById(R.id.fittingShow);

        commande = (Commande) getIntent().getSerializableExtra("commande");

        action.setText(commande.getAction());
        actionPrice.setText(commande.getActionPrice());
        room.setText(commande.getRoom());
        range.setText(commande.getRange());
        type.setText(commande.getType());
        version.setText(commande.getVersion());
        size.setText(commande.getSize());
        fitting.setText(commande.getFitting());
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
