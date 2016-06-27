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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class RangeActivity extends BaseActivity {

    private Commande com;
    ListView listRange;
    ArrayList<String> ranges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_range);
        setTitle("Gamme");

        com = (Commande) getIntent().getSerializableExtra("com");

        listRange = (ListView)findViewById(R.id.listRange);
        ranges = new ArrayList<>();
        ranges.add("Electrique");
        ranges.add("Solaire");
        ranges.add("Projection");
        ranges.add("Rotation");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, ranges);
        listRange.setAdapter(adapter);

        listRange.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> parent, final View view,
                                    final int position, long id) {
                Intent intent = new Intent(view.getContext(), TypeActivity.class);
                com.setRange((String) listRange.getItemAtPosition(position));
                intent.putExtra("com",com);
                startActivity(intent);
            }
        });
    }
}
