package com.myvelux.myvelux;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class TypeActivity extends BaseActivity {

    static private Commande com;
    ListView listTypes;
    ArrayList<String> types;
    ProductDataBaseAdapter productDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        setTitle("Matériaux");

        productDataBaseAdapter = new ProductDataBaseAdapter(this);
        productDataBaseAdapter = productDataBaseAdapter.open();

        com = (Commande) getIntent().getSerializableExtra("com");

        listTypes = (ListView)findViewById(R.id.listType);
        types = productDataBaseAdapter.getProductType(com.getRange());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, types);
        listTypes.setAdapter(adapter);

        listTypes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> parent, final View view,
                                    final int position, long id) {
                Intent intent = new Intent(view.getContext(), VersionActivity.class);
                com.setType((String) listTypes.getItemAtPosition(position));
                intent.putExtra("com",com);
                startActivity(intent);
            }
        });

    }
}
