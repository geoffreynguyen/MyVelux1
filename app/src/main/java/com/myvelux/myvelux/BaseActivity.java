package com.myvelux.myvelux;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


public class BaseActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_client_detail:
                Intent intent = new Intent(getApplicationContext(), ClientActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_logout:
                SharedPrefManager.DeleteAllEntriesFromPref(); // all values are loaded into corresponding variables of SharedPrefManager class
                Intent intentLogin = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intentLogin);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}