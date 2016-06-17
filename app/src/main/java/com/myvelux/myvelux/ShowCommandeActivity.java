package com.myvelux.myvelux;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class ShowCommandeActivity extends BaseActivity {

    private int idCommande;
    private Commande commande;
    TextView action, actionPrice, room, range, type, version, size, fitting;
    CommandeDataBaseAdapter commandeDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_commande);
        setTitle("Détail de le commande");

        commandeDataBaseAdapter = new CommandeDataBaseAdapter(this);
        commandeDataBaseAdapter = commandeDataBaseAdapter.open();

        action = (TextView) findViewById(R.id.actionShow);
        actionPrice = (TextView) findViewById(R.id.actionPriceShow);
        room = (TextView) findViewById(R.id.roomShow);
        range = (TextView) findViewById(R.id.rangeShow);
        type = (TextView) findViewById(R.id.typeShow);
        version = (TextView) findViewById(R.id.versionShow);
        size = (TextView) findViewById(R.id.sizeShow);
        fitting = (TextView) findViewById(R.id.fittingShow);

        idCommande = getIntent().getIntExtra("idCommande", -1);

        commande = commandeDataBaseAdapter.getSinlgeEntry(idCommande);

        Log.i("idCommande", String.valueOf(idCommande));

        action.setText(commande.getAction());
        actionPrice.setText(commande.getActionPrice());
        room.setText(commande.getRoom());
        range.setText(commande.getRange());
        type.setText(commande.getType());
        version.setText(commande.getVersion());
        size.setText(commande.getSize());
        fitting.setText(commande.getFitting());
    }


}
