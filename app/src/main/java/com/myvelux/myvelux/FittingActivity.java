package com.myvelux.myvelux;

import android.content.Intent;
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

import java.util.ArrayList;

public class FittingActivity extends BaseActivity {

    static private Commande com;
    ProductDataBaseAdapter productDataBaseAdapter;
    CommandeDataBaseAdapter commandeDataBaseAdapter;
    ListView listFitting;
    ArrayList<String> fittings;

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

        listFitting = (ListView)findViewById(R.id.listFitting);
        fittings = productDataBaseAdapter.getProductFitting(com.getAction(), com.getRange(), com.getType(), com.getVersion(), com.getSize());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, fittings);
        listFitting.setAdapter(adapter);

        listFitting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> parent, final View view,
                                    final int position, long id) {
                Intent intent = new Intent(view.getContext(), FinalChoiceActivity.class);
                com.setFitting((String) listFitting.getItemAtPosition(position));
                com.setIdClient(String.valueOf(SharedPrefManager.getIdClient()));
                commandeDataBaseAdapter.insertEntry(com);
                intent.putExtra("com",com);
                startActivity(intent);
            }
        });
    }

}
