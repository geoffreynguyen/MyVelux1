package com.myvelux.myvelux;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class UserActivity extends BaseActivity {
    LoginDataBaseAdapter loginDataBaseAdapter;

    private ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        setTitle("Liste des utilisateurs");

        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        final ArrayList<HashMap<String, String>> todoItems = new ArrayList<>();
        HashMap<String, String> hashmap=new HashMap<>();

        final boolean isAdmin = loginDataBaseAdapter.isAdminById(SharedPrefManager.getIdLogin());

        if(isAdmin) {
            Cursor c = loginDataBaseAdapter.findAll();
            if (c.moveToFirst())
            {
                do{
                    hashmap.put("Username",c.getString(1));
                    hashmap.put("id",c.getString(0));
                    if(c.getString(3).equals("1")) {
                        hashmap.put("deleted", "Désactivé");
                    }else {
                        hashmap.put("deleted", "");
                    }
                    todoItems.add(hashmap);
                    hashmap = new HashMap<>();
                }while (c.moveToNext());
            }
        }else{
            Intent intent = new Intent(getApplicationContext(), FinalChoiceActivity.class);
            startActivity(intent);
            finish();
        }


        lv = (ListView) findViewById(R.id.listUser);

        if (todoItems.size() >= 0)
        {
            //Create Simple adapter (make the list)
            final SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), todoItems, R.layout.affichage_client,
                    new String[] {"Username", "deleted", "id"}, new int[] {R.id.firstNameListView, R.id.deleted});

            lv.setAdapter(mSchedule);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(final AdapterView<?> parent, final View view,
                                        final int position, long id) {

                    final HashMap<String, String> mapItem = (HashMap<String, String>) lv.getItemAtPosition(position);

                    AlertDialog.Builder adb = new AlertDialog.Builder(UserActivity.this);
                    adb.setTitle("Utilisateur n° " + mapItem.get("id"));
                    adb.setMessage("Choix action ?");

                    adb.setNegativeButton("Modifier", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(view.getContext(), ManageUser.class);
                            intent.putExtra("idUser", Integer.parseInt(mapItem.get("id")));
                            intent.putExtra("isAdmin", isAdmin);
                            startActivity(intent);
                            finish();

                        }
                    });

                    if(mapItem.get("deleted").length() == 0 && !mapItem.get("id").equals(String.valueOf(SharedPrefManager.getIdLogin()))) {
                        adb.setPositiveButton("Supprimer", new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                todoItems.get(position).put("deleted","Désactivé");
                                mSchedule.notifyDataSetChanged();
                                loginDataBaseAdapter.deleteUserById(Integer.parseInt(mapItem.get("id")));
                                Toast.makeText(getBaseContext(), "Utilisateur supprimé", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else if  (mapItem.get("deleted").length() != 0){
                        adb.setNeutralButton("Activer", new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                todoItems.get(position).put("deleted","");
                                mSchedule.notifyDataSetChanged();
                                loginDataBaseAdapter.activateUserById(Integer.parseInt(mapItem.get("id")));
                                Toast.makeText(getBaseContext(), "Utilisateur activé", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    adb.show();
                }

            });

        }
        if(isAdmin) {
            Button btnAddClient = (Button) findViewById(R.id.addUser);

            if (btnAddClient != null) {
                btnAddClient.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), ManageUser.class);
                        intent.putExtra("isAdmin", isAdmin);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(getApplicationContext(), FinalChoiceActivity.class);
        startActivity(intent);
        finish();
    }

}
