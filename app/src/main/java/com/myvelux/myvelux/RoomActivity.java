package com.myvelux.myvelux;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;

public class RoomActivity extends AppCompatActivity {

    private ListView listViewCustom;
    private Reservation resa;
    private Commande commande;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        setTitle("Pièce");

        resa = (Reservation) getIntent().getSerializableExtra("resa");

        listViewCustom = (ListView) findViewById(R.id.listviewperso);
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

        //Create all rooms
        Room room1= new Room("Chambre",String.valueOf(R.mipmap.chambre));
        listItem.add(room1.getHashMap());
        Room room2= new Room("Bureau",String.valueOf(R.mipmap.bureau));
        listItem.add(room2.getHashMap());
        Room room3= new Room("Salle de bain",String.valueOf(R.mipmap.salle_de_bain));
        listItem.add(room3.getHashMap());
        Room room4= new Room("Salle de jeux",String.valueOf(R.mipmap.salle_de_jeux));
        listItem.add(room4.getHashMap());
        //Room room5= new Room("Pallier",String.valueOf(R.mipmap.ic_launcher));
        //listItem.add(room5.getHashMap());
        Room room6= new Room("Dressing",String.valueOf(R.mipmap.dessing));
        listItem.add(room6.getHashMap());
        Room room7= new Room("Couloir",String.valueOf(R.mipmap.couloir));
        listItem.add(room7.getHashMap());

        //Create Simple adapter (make the list)
        SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.affichageitem,
                new String[] {"img", "title"}, new int[] {R.id.img, R.id.title});

        listViewCustom.setAdapter(mSchedule);

        //On item click
        listViewCustom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            @SuppressWarnings("unchecked")
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ActionActivity.class);
                HashMap<String, String> map = (HashMap<String, String>) listViewCustom.getItemAtPosition(position);
                resa.getCommande().setRoom(map.get("title"));
                intent.putExtra("resa",resa);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            case R.id.menu_client_detail:
                MenuUpdate updateClient = new MenuUpdate();
                Intent intent = new Intent(getApplicationContext(), ClientActivity.class);
                startActivity(updateClient.clientMenu(intent, resa, 1, getClass().toString()));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

}
