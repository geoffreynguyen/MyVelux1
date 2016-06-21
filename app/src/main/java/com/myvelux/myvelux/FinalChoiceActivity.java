package com.myvelux.myvelux;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class FinalChoiceActivity extends BaseActivity {

    private Commande com;
    private ListView lv;
    static boolean isAdmin;
    CommandeDataBaseAdapter commandeDataBaseAdapter;
    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_choice);

        SharedPrefManager.Init(this);

        //Get all values from SharedPrefference file
        SharedPrefManager.LoadFromPref(); // all values are loaded into corresponding variables of SharedPrefManager class

        if(!SharedPrefManager.getConnected()){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }

        commandeDataBaseAdapter = new CommandeDataBaseAdapter(this);
        commandeDataBaseAdapter = commandeDataBaseAdapter.open();
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        final ArrayList<HashMap<String, String>> todoItems = new ArrayList<>();
        HashMap<String, String> hashmap=new HashMap<>();


        isAdmin = loginDataBaseAdapter.isAdminById(SharedPrefManager.getIdLogin());

        if(SharedPrefManager.getIdClient() != 0 ){
            Cursor c = commandeDataBaseAdapter.findCommandsByClient(SharedPrefManager.getIdClient());

            if (c.moveToFirst())
            {
                do{
                    hashmap.put("room",c.getString(1));
                    hashmap.put("id",c.getString(0));
                    todoItems.add(hashmap);
                    hashmap = new HashMap<>();
                }while (c.moveToNext());
            }

            lv = (ListView) findViewById(R.id.listViewFinal);

            if (todoItems.size() >= 0) {

                //Create Simple adapter (make the list)
                final SimpleAdapter mSchedule = new SimpleAdapter(this.getBaseContext(), todoItems, R.layout.affichage_client,
                        new String[]{"room", "id"}, new int[]{R.id.firstNameListView, R.id.idClientListView});

                lv.setAdapter(mSchedule);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(final AdapterView<?> parent, final View view,
                                            final int position, long id) {

                        final HashMap<String, String> mapItem = (HashMap<String, String>) lv.getItemAtPosition(position);

                        AlertDialog.Builder adb = new AlertDialog.Builder(FinalChoiceActivity.this);
                        adb.setTitle("Commande n° " + mapItem.get("id"));
                        adb.setMessage("Choix action ?");
                        adb.setNeutralButton("Afficher", new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(view.getContext(), ShowCommandeActivity.class);
                                intent.putExtra("idCommande", Integer.parseInt(mapItem.get("id")));
                                startActivity(intent);
                            }
                        });

                        adb.setNegativeButton("Modifier", new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //Intent intent = new Intent(view.getContext(), RoomActivity.class);
                                //resa.setCommande(c1);
                                //arrayCommande.remove(position);
                                //arrayAdapter.notifyDataSetChanged();
                                //intent.putExtra("resa", resa);
                                //startActivity(intent);
                            }
                        });
                        adb.setPositiveButton("Supprimer", new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                /*arrayCommande.remove(position);
                                arrayAdapter.notifyDataSetChanged();
                                Toast.makeText(getBaseContext(), "Commande supprimée", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(view.getContext(), FinalChoiceActivity.class);
                                intent.putExtra("resa",resa);
                                startActivity(intent);
                                finish();*/
                            }
                        });
                        adb.show();
                    }

                });
            }
        }

        Button btnEnd = (Button) findViewById(R.id.btnEndChoice);
        Button btnContinue = (Button) findViewById(R.id.btnContinueChoice);

        if(btnEnd != null){
            if (todoItems.size() > 0) {
                btnEnd.setVisibility(View.VISIBLE);
                btnEnd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), MyVeluxPDF.class);
                        intent.putExtra("com", com);
                        startActivity(intent);
                    }
                });
            } else{
            btnEnd.setVisibility(View.GONE);
                assert btnContinue != null;
                btnContinue.setText("COMMENCER");
            }
        }


        if (btnContinue != null) {
            btnContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(SharedPrefManager.getIdClient()!=0){
                        // start new activity
                        Intent intent = new Intent(v.getContext(), RoomActivity.class);
                        com = new Commande();
                        intent.putExtra("com",com);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getBaseContext(), "Veuillez créer ou selectionner un client", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed()
    {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        if(!isAdmin) {
            menu.findItem(R.id.menu_user_detail).setTitle("Mon compte");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_client_detail:
                Intent intent = new Intent(getApplicationContext(), ClientActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.menu_user_detail:

                if(!isAdmin){
                    item.setTitle("Mon compte");
                    Intent intentUser = new Intent(getApplicationContext(), ManageUser.class);
                    intentUser.putExtra("idUser", (SharedPrefManager.getIdLogin()));
                    intentUser.putExtra("isAdmin", isAdmin);
                    startActivity(intentUser);
                    finish();
                }else{
                    Intent intentUserAdmin = new Intent(getApplicationContext(), UserActivity.class);
                    startActivity(intentUserAdmin);
                    finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
