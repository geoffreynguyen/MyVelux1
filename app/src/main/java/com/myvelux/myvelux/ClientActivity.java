package com.myvelux.myvelux;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClientActivity extends AppCompatActivity {

    public ArrayList<ArrayList<String>> finalQuote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        setTitle("Informations client");

    }

    protected void btnNext(View view){
        // start new activity
        Intent intent = new Intent(view.getContext(), RoomActivity.class);
        startActivity(intent);

    }
}
