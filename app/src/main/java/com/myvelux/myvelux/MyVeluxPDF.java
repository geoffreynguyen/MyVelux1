package com.myvelux.myvelux;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MyVeluxPDF extends AppCompatActivity {

    String fname;
    EditText fnameread;
    Button newClient, read;
    TextView filecon;
    private Reservation resa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_velux_pdf);
        this.verifyStoragePermissions(this);

        resa = (Reservation) getIntent().getSerializableExtra("resa");

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());


        fnameread = (EditText) findViewById(R.id.fnameread);
        fname = formattedDate + "-" + resa.getClient().getLastName();
        newClient = (Button) findViewById(R.id.btnNewClient);
        read = (Button) findViewById(R.id.btnread);
        filecon = (TextView) findViewById(R.id.filecon);

        //Generate PDF
        GeneratorPDF fop = new GeneratorPDF();
        fop.write(fname, resa);
        if (fop.write(fname, resa)) {
            Toast.makeText(getApplicationContext(),
                    fname + ".pdf created", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(getApplicationContext(), "I/O error",
                    Toast.LENGTH_SHORT).show();
        }

        newClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ClientActivity.class);
                intent.putExtra("resa", resa);
                startActivity(intent);
            }
        });
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String readfilename = fnameread.getText().toString();
                GeneratorPDF fop = new GeneratorPDF();
                String text = fop.read(readfilename);
                if (text != null) {
                    filecon.setText(text);
                } else {
                    Toast.makeText(getApplicationContext(), "File not Found",
                            Toast.LENGTH_SHORT).show();
                    filecon.setText(null);
                }
            }
        });

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