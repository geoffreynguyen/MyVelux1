package com.myvelux.myvelux;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.pdf.PdfReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MyVeluxPDF extends AppCompatActivity {

    String fname = "";
    EditText fnameread;
    Button read;
    TextView filecon;
    ArrayList<Commande> commandes;
    private Client client;
    ClientDataBaseAdapter clientDataBaseAdapter;
    CommandeDataBaseAdapter commandeDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_velux_pdf);
        this.verifyStoragePermissions(this);

        commandeDataBaseAdapter = new CommandeDataBaseAdapter(this);
        commandeDataBaseAdapter = commandeDataBaseAdapter.open();
        clientDataBaseAdapter = new ClientDataBaseAdapter(this);
        clientDataBaseAdapter = clientDataBaseAdapter.open();


        commandes = commandeDataBaseAdapter.findCommandsByClientExport(SharedPrefManager.getIdClient());
        client = clientDataBaseAdapter.getSinlgeEntry(SharedPrefManager.getIdClient());

        fnameread = (EditText) findViewById(R.id.fnameread);

        Calendar cal = Calendar.getInstance();
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("dd-MM-yyyy");
        String localTime = date.format(currentLocalTime);

        filecon = (TextView) findViewById(R.id.filecon);
        fname = "Devis_" + client.getLastName() + "_" + localTime;
        //Generate PDF
        GeneratorPDF fop = new GeneratorPDF();
        fop.write(fname, commandes, client);
        if (fop.write(fname, commandes, client)) {
            Toast.makeText(getApplicationContext(),
                    fname + ".pdf created", Toast.LENGTH_SHORT)
                    .show();

            File filelocation = new File(Environment.getExternalStorageDirectory().getPath(), fname +".pdf");
            Uri path = Uri.fromFile(filelocation);
            Intent emailIntent = new Intent(Intent.ACTION_SEND);

            emailIntent.setType("application/pdf");
            String to[] = {client.getEmail()};
            emailIntent.putExtra(Intent.EXTRA_EMAIL, to);

            emailIntent.putExtra(Intent.EXTRA_STREAM, path);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, fname);
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Ci-joint, le devis AVR.");

            startActivity(Intent.createChooser(emailIntent , "Send email..."));
            finish();

        } else {
            Toast.makeText(getApplicationContext(), "I/O error",
                    Toast.LENGTH_SHORT).show();
            finish();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}