package com.myvelux.myvelux;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClientActivity extends AppCompatActivity {

    public ArrayList<ArrayList<String>> finalQuote;
    private Reservation resa;
    private Client client;
    private Commande commande;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        setTitle("Informations client");
        resa = new Reservation();

        Button button= (Button) findViewById(R.id.btnNextClient);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client = new Client();
                commande = new Commande();
                resa.setCommande(commande);
                client.setLastName("Geoffrey");
                resa.setClient(client);
                Intent intent = new Intent(v.getContext(), RoomActivity.class);
                intent.putExtra("resa",resa);
                startActivity(intent);
            }
        });

    }
}
