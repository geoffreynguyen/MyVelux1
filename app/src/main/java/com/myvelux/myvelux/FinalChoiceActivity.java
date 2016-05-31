package com.myvelux.myvelux;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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
        resa.addArrayCommande(resa.getCommande());
        lv = (ListView) findViewById(R.id.listViewFinal);
        if(resa.getCommandes().size()!=0){
            final ArrayList<Commande> arrayCommande = resa.getCommandes();
            // This is the array adapter, it takes the context of the activity as a
            // first parameter, the type of list view as a second parameter and your
            // array as a third parameter.
            final ArrayAdapter<Commande> arrayAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    arrayCommande);

            lv.setAdapter(arrayAdapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(final AdapterView<?> parent, final View view,
                                        final int position, long id) {

                    final Commande c1 = (Commande) lv.getItemAtPosition(position);

                    AlertDialog.Builder adb = new AlertDialog.Builder(FinalChoiceActivity.this);
                    adb.setTitle("Commande n° "+(position+1));
                    adb.setMessage("Choix action ?");
                    adb.setNeutralButton("Afficher", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(view.getContext(), ShowCommande.class);
                            intent.putExtra("commande", c1);
                            startActivity(intent);
                        }
                    });

                    adb.setNegativeButton("Modifier", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(view.getContext(), RoomActivity.class);
                            resa.setCommande(c1);
                            arrayCommande.remove(position);
                            arrayAdapter.notifyDataSetChanged();
                            intent.putExtra("resa", resa);
                            startActivity(intent);
                        }
                    });
                    adb.setPositiveButton("Supprimer", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            arrayCommande.remove(position);
                            arrayAdapter.notifyDataSetChanged();
                            Toast.makeText(getBaseContext(), "Commande supprimée", Toast.LENGTH_SHORT).show();
                        }
                    });
                    adb.show();
                }

            });

        }

        Button btnEnd = (Button) findViewById(R.id.btnEndChoice);
        Button btnContinue = (Button) findViewById(R.id.btnContinueChoice);

        if(btnEnd != null) {
            btnEnd.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), MyVeluxPDF.class);
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
                    resa.setCommande(new Commande());
                    intent.putExtra("resa",resa);
                    startActivity(intent);
                }
            });
        }
    }
}
