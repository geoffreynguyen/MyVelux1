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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class VersionActivity extends BaseActivity {

    static private Commande com;
    ListView listVersions;
    ArrayList<String> versions;

    ProductDataBaseAdapter productDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version);
        setTitle("Version");

        productDataBaseAdapter = new ProductDataBaseAdapter(this);
        productDataBaseAdapter = productDataBaseAdapter.open();

        com = (Commande) getIntent().getSerializableExtra("com");

        listVersions = (ListView)findViewById(R.id.listVersion);
        versions = productDataBaseAdapter.getProductVersion(com.getRange(), com.getType());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, versions);
        listVersions.setAdapter(adapter);

        listVersions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> parent, final View view,
                                    final int position, long id) {
                Intent intent = new Intent(view.getContext(), SizeActivity.class);
                com.setVersion((String) listVersions.getItemAtPosition(position));
                intent.putExtra("com",com);
                startActivity(intent);
            }
        });
    }
}
