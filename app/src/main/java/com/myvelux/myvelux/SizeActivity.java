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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class SizeActivity extends BaseActivity {

    static private Commande com;
    ProductDataBaseAdapter productDataBaseAdapter;
    ListView listSizes;
    TextView currentSize;
    ArrayList<String> sizes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size);
        setTitle("Taille");

        productDataBaseAdapter = new ProductDataBaseAdapter(this);
        productDataBaseAdapter = productDataBaseAdapter.open();

        com = (Commande) getIntent().getSerializableExtra("com");

        currentSize = (TextView) findViewById(R.id.currentSize);
        if(com.getSize()!=null){
            currentSize.setText("Taille : "+com.getSize());
        }

        listSizes = (ListView)findViewById(R.id.listSizes);
        sizes = productDataBaseAdapter.getProductSize(com.getRange(), com.getType(), com.getVersion());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, sizes);
        listSizes.setAdapter(adapter);

        listSizes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> parent, final View view,
                                    final int position, long id) {
                Intent intent = new Intent(view.getContext(), FittingActivity.class);
                com.setSize((String) listSizes.getItemAtPosition(position));
                intent.putExtra("com",com);
                startActivity(intent);
            }
        });
    }

}
