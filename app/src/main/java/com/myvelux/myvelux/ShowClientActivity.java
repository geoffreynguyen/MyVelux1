package com.myvelux.myvelux;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class ShowClientActivity extends AppCompatActivity {

    private Client client;
    TextView lastName, firstName, address, postalCode, city, phone1, phone2, email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_client);
        setTitle("Détail du client");
        client = (Client) getIntent().getSerializableExtra("client");

        firstName   = (EditText)findViewById(R.id.firstName);
        lastName   = (EditText)findViewById(R.id.lastName);
        address   = (EditText)findViewById(R.id.address);
        postalCode   = (EditText)findViewById(R.id.postalCode);
        city   = (EditText)findViewById(R.id.city);
        phone1   = (EditText)findViewById(R.id.phone1);
        phone2   = (EditText)findViewById(R.id.phone2);
        email = (EditText)findViewById(R.id.email);

        lastName.setText(client.getLastName());
        firstName.setText(client.getFirstName());
        address.setText(client.getAddress());
        postalCode.setText(client.getPostalCode());
        city.setText(client.getCity());
        phone1.setText(client.getMobile());
        phone2.setText(client.getPhone());
        email.setText(client.getEmail());

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
