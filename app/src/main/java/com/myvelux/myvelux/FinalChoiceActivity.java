package com.myvelux.myvelux;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class FinalChoiceActivity extends AppCompatActivity {

    private Reservation resa;
    private Commande commande;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_choice);
        setTitle("Choix final");

        resa = (Reservation) getIntent().getSerializableExtra("resa");
        Button btnEnd = (Button) findViewById(R.id.btnEndChoice);
        Button btnContinue = (Button) findViewById(R.id.btnContinueChoice);
        resa.addArrayCommande(resa.getCommande());
        lv = (ListView) findViewById(R.id.listViewFinal);

        Log.i("commandes", resa.getCommandes().toString());
        ArrayAdapter<Commande> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, resa.getCommandes());

        lv.setAdapter(adapter);

        if(btnEnd != null) {
            btnEnd.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), FinalChoiceActivity.class);
                    intent.putExtra("resa",resa);
                    startActivity(intent);

                }
            });
        }

        if (btnContinue != null) {
            btnContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // start new activity
                    Intent intent = new Intent(v.getContext(), RoomActivity.class);

                    commande = new Commande();
                    resa.setCommande(commande);
                    intent.putExtra("resa",resa);
                    startActivity(intent);
                }
            });
        }
    }
}
