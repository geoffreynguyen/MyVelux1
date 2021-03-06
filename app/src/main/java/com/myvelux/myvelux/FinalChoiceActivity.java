package com.myvelux.myvelux;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class FinalChoiceActivity extends BaseActivity {

    private Commande com;
    private ListView lv;
    static boolean isAdmin;

    CommandeDataBaseAdapter commandeDataBaseAdapter;
    LoginDataBaseAdapter loginDataBaseAdapter;
    ProductDataBaseAdapter productDataBaseAdapter;

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
        productDataBaseAdapter = new ProductDataBaseAdapter(this);
        productDataBaseAdapter = productDataBaseAdapter.open();

        final ArrayList<HashMap<String, String>> todoItems = new ArrayList<>();
        HashMap<String, String> hashmap=new HashMap<>();

        isAdmin = loginDataBaseAdapter.isAdminById(SharedPrefManager.getIdLogin());

        if(SharedPrefManager.getIdClient() != 0 ){
            Cursor c = commandeDataBaseAdapter.findCommandsByClient(SharedPrefManager.getIdClient());
            if (c.moveToFirst()) {
                do {
                    hashmap.put("room", c.getString(1));
                    hashmap.put("id", c.getString(0));
                    if(c.getString(15).equals("1")) {
                        hashmap.put("deleted", "Désactivé");
                    }else {
                        hashmap.put("deleted", "");
                    }
                    todoItems.add(hashmap);
                    hashmap = new HashMap<>();
                } while (c.moveToNext());
            }

            lv = (ListView) findViewById(R.id.listViewFinal);

            if (todoItems.size() >= 0) {

                //Create Simple adapter (make the list)
                final SimpleAdapter mSchedule = new SimpleAdapter(this.getBaseContext(), todoItems, R.layout.affichage_client,
                        new String[]{"room", "deleted", "id"}, new int[]{R.id.firstNameListView, R.id.deleted});

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
                                Intent intent = new Intent(view.getContext(), RoomActivity.class);
                                com = new Commande();
                                com = commandeDataBaseAdapter.getSinlgeEntry(Integer.parseInt(mapItem.get("id")));
                                com.setUpdate("true");
                                todoItems.remove(position);
                                mSchedule.notifyDataSetChanged();
                                intent.putExtra("com",com);
                                startActivity(intent);
                            }
                        });
                        if(mapItem.get("deleted").length() == 0) {
                            adb.setPositiveButton("Supprimer", new AlertDialog.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    todoItems.get(position).put("deleted","Désactivé");
                                    mSchedule.notifyDataSetChanged();
                                    commandeDataBaseAdapter.deleteCommandeById(Integer.parseInt(mapItem.get("id")));
                                    Toast.makeText(getBaseContext(), "Commande supprimée", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else{
                            adb.setPositiveButton("Activer", new AlertDialog.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    todoItems.get(position).put("deleted","");
                                    mSchedule.notifyDataSetChanged();
                                    commandeDataBaseAdapter.activateCommandeById(Integer.parseInt(mapItem.get("id")));
                                    Toast.makeText(getBaseContext(), "Commande activée", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
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
                        boolean valid = commandeDataBaseAdapter.findCommandsValidByClient(SharedPrefManager.getIdClient());
                        if(valid){
                            Intent intent = new Intent(v.getContext(), MyVeluxPDF.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getBaseContext(), "Aucune commande valide", Toast.LENGTH_SHORT).show();
                        }
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
                    if(SharedPrefManager.getIdClient()!=0 && productDataBaseAdapter.findAll()){
                        // start new activity
                        Intent intent = new Intent(v.getContext(), RoomActivity.class);
                        com = new Commande();
                        intent.putExtra("com",com);
                        startActivity(intent);
                    }else if(!productDataBaseAdapter.findAll()) {
                        Toast.makeText(getBaseContext(), "Veuillez importer les produits", Toast.LENGTH_SHORT).show();
                    }else {
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
            menu.findItem(R.id.menu_import_product).setVisible(false);
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

            case R.id.menu_import_product:
                importProduct();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void importProduct(){
        this.verifyStoragePermissions(this);
        productDataBaseAdapter.deleteProducts();
        FileReader file = null;
        try {
            file = new FileReader(Environment.getExternalStorageDirectory().getPath()+"/ProduitsCSV.csv");
            BufferedReader buffer = new BufferedReader(file);
            String line = null;
            String value = null;
            ArrayList<String> dataRow = null;
            StringTokenizer token;
            while((line=buffer.readLine()) != null) {
                dataRow = new ArrayList<String>();
                token = new StringTokenizer(line, ";");
                while (token.hasMoreElements()) {
                    value = (String) token.nextElement();
                    dataRow.add(value);
                }
                productDataBaseAdapter.insertRowCSV(dataRow);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(AppCompatActivity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

}
