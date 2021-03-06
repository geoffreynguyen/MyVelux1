package com.myvelux.myvelux;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class FittingActivity extends BaseActivity {

    static private Commande com;
    ProductDataBaseAdapter productDataBaseAdapter;
    CommandeDataBaseAdapter commandeDataBaseAdapter;
    ListView listFitting;
    TextView currentFitting;
    ArrayList<HashMap<String, String>> fittings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitting);
        setTitle("Raccord");

        productDataBaseAdapter = new ProductDataBaseAdapter(this);
        productDataBaseAdapter = productDataBaseAdapter.open();
        commandeDataBaseAdapter = new CommandeDataBaseAdapter(this);
        commandeDataBaseAdapter = commandeDataBaseAdapter.open();

        com = (Commande) getIntent().getSerializableExtra("com");

        currentFitting = (TextView) findViewById(R.id.currentFitting);
        if(com.getFitting()!=null){
            currentFitting.setText("Raccord : "+com.getFitting());
        }

        listFitting = (ListView) findViewById(R.id.listFitting);

        String[] velux = productDataBaseAdapter.getProductRefVelux(com.getRange(), com.getType(), com.getVersion(), com.getSize());

        com.setRefArticle(velux[0]);
        com.setPrixHTVelux(velux[1]);
        com.setPrixTTCVelux(velux[2]);

        fittings = productDataBaseAdapter.getProductFitting(com.getAction(), com.getRange(), com.getType(), com.getVersion(), com.getSize());

        listFitting = (ListView) findViewById(R.id.listFitting);

        if (fittings.size() >= 0) {

            //Create Simple adapter (make the list)
            final SimpleAdapter mSchedule = new SimpleAdapter(this.getBaseContext(), fittings, R.layout.affichage_fitting,
                    new String[]{"libel_article", "ref_article"}, new int[]{R.id.libel_article, R.id.ref_article});

            listFitting.setAdapter(mSchedule);

            listFitting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(final AdapterView<?> parent, final View view,
                                        final int position, long id) {
                    Intent intent = new Intent(view.getContext(), FinalChoiceActivity.class);
                    final HashMap<String, String> mapItem = (HashMap<String, String>) listFitting.getItemAtPosition(position);
                    com.setFitting(mapItem.get("libel_article"));
                    com.setRefFitting(mapItem.get("ref_article"));
                    String[] fittingPrice = productDataBaseAdapter.getPriceFitting(com.getRefFitting());
                    com.setPrixHTFitting(fittingPrice[0]);
                    com.setPrixTTCFitting(fittingPrice[1]);
                    com.setIdClient(String.valueOf(SharedPrefManager.getIdClient()));
                    if(com.getUpdate().equals("true")){
                        commandeDataBaseAdapter.updateCommande(com);
                    }else {
                        commandeDataBaseAdapter.insertEntry(com);
                    }
                    intent.putExtra("com", com);
                    startActivity(intent);
                }
            });
        }
    }
}
