package com.myvelux.myvelux;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class ShowCommandeActivity extends BaseActivity {

    private int idCommande;
    private Commande commande;
    TextView action, actionPrice, room, range, type, version, size, priceProduct, refProduct, fitting, priceFitting, refFitting;
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
        priceProduct = (TextView) findViewById(R.id.priceProduct);
        refProduct = (TextView) findViewById(R.id.refProduct);
        fitting = (TextView) findViewById(R.id.fittingShow);
        priceFitting = (TextView) findViewById(R.id.priceFitting);
        refFitting = (TextView) findViewById(R.id.refFitting);

        idCommande = getIntent().getIntExtra("idCommande", -1);

        commande = commandeDataBaseAdapter.getSinlgeEntry(idCommande);

        action.setText(commande.getAction());
        actionPrice.setText(commande.getActionPrice()+" €");
        room.setText(commande.getRoom());
        range.setText(commande.getRange());
        type.setText(commande.getType());
        version.setText(commande.getVersion());
        size.setText(commande.getSize());
        priceProduct.setText(commande.getPrixHTVelux()+" € HT");
        refProduct.setText(commande.getRefArticle());
        fitting.setText(commande.getFitting());
        priceFitting.setText(commande.getPrixHTFitting()+" € HT");
        refFitting.setText(commande.getRefFitting());
    }


}
