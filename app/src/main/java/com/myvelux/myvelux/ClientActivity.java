package com.myvelux.myvelux;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;
import static java.lang.Class.forName;

public class ClientActivity extends AppCompatActivity {

    ClientDataBaseAdapter clientDataBaseAdapter;

    private ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        setTitle("Liste des clients");

        clientDataBaseAdapter = new ClientDataBaseAdapter(this);
        clientDataBaseAdapter = clientDataBaseAdapter.open();

        final ArrayList<HashMap<String, String>> todoItems = new ArrayList<>();
        HashMap<String, String> hashmap=new HashMap<>();

        Cursor c = clientDataBaseAdapter.findAll();
        if (c.moveToFirst())
        {
            do{
                hashmap.put("firstName",c.getString(1));
                hashmap.put("id",c.getString(0));
                todoItems.add(hashmap);
                hashmap = new HashMap<>();
            }while (c.moveToNext());
        }

        lv = (ListView) findViewById(R.id.listClient);

        if (todoItems.size() >= 0)
        {
            //Create Simple adapter (make the list)
            final SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), todoItems, R.layout.affichage_client,
                    new String[] {"firstName", "id"}, new int[] {R.id.firstNameListView, R.id.idClientListView});

            lv.setAdapter(mSchedule);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(final AdapterView<?> parent, final View view,
                                        final int position, long id) {


                    final HashMap<String, String> mapItem = (HashMap<String, String>) lv.getItemAtPosition(position);

                    AlertDialog.Builder adb = new AlertDialog.Builder(ClientActivity.this);
                    adb.setTitle("Client n° " + mapItem.get("id"));
                    adb.setMessage("Choix action ?");
                    adb.setNeutralButton("Selectionner", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPrefManager.setIdClient(Integer.parseInt(mapItem.get("id")));
                            SharedPrefManager.StoreToPref();
                            Toast.makeText(getBaseContext(), "Client selectionné : "+mapItem.get("firstName"), Toast.LENGTH_SHORT).show();
                        }
                    });

                    adb.setNegativeButton("Modifier", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(view.getContext(), ManageClient.class);
                            intent.putExtra("idClient", Integer.parseInt(mapItem.get("id")));
                            startActivity(intent);
                            finish();

                        }
                    });
                    adb.setPositiveButton("Supprimer", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            todoItems.remove(position);
                            mSchedule.notifyDataSetChanged();
                            SharedPrefManager.DeleteSingleEntryFromPref("idClient");
                            SharedPrefManager.StoreToPref();
                            clientDataBaseAdapter.deleteClientById(Integer.parseInt(mapItem.get("id")));
                            Toast.makeText(getBaseContext(), "Client supprimé", Toast.LENGTH_SHORT).show();
                        }
                    });
                    adb.show();
                }

            });

        }

        Button btnAddClient = (Button) findViewById(R.id.addClient);

        if(btnAddClient != null) {
            btnAddClient.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ManageClient.class);
                    startActivity(intent);
                }
            });
        }
    }

}
