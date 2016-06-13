package com.myvelux.myvelux;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;
import java.util.regex.Pattern;
import static java.lang.Class.forName;

public class ClientActivity extends AppCompatActivity {

    private Reservation resa;
    private Client client;
    private Commande commande;
    private int updateClient;
    boolean okFirstName, okLastName, okAddress, okPostalCode,okCity,okPhone1,okPhone2,okEmail;
    EditText firstName, lastName, address, postalCode, city, phone1, phone2, email;
    TextInputLayout errorFirstName, errorLastName,
            errorAddress, errorEmail, errorCity, errorCP, errorPhone1, errorPhone2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        setTitle("Informations client");

        updateClient = getIntent().getIntExtra("updateClient",0);

        firstName   = (EditText)findViewById(R.id.firstName);
        lastName   = (EditText)findViewById(R.id.lastName);
        address   = (EditText)findViewById(R.id.address);
        postalCode   = (EditText)findViewById(R.id.postalCode);
        city   = (EditText)findViewById(R.id.city);
        phone1   = (EditText)findViewById(R.id.phone1);
        phone2   = (EditText)findViewById(R.id.phone2);
        email = (EditText)findViewById(R.id.email);

        if (updateClient==0){
            resa = new Reservation();
            client = new Client();
            commande = new Commande();
            resa.setCommande(commande);
        }else{
            resa = (Reservation) getIntent().getSerializableExtra("resa");
            client = resa.getClient();

            firstName.setText(client.getFirstName());
            lastName.setText(client.getLastName());
            address.setText(client.getAddress());
            postalCode.setText(client.getPostalCode());
            city.setText(client.getCity());
            phone1.setText(client.getMobile());
            phone2.setText(client.getPhone());
            email.setText(client.getEmail());
        }

        errorFirstName = (TextInputLayout) findViewById(R.id.error_firstName);
        errorLastName = (TextInputLayout) findViewById(R.id.error_lastName);
        errorAddress = (TextInputLayout) findViewById(R.id.error_address);
        errorCity = (TextInputLayout) findViewById(R.id.error_city);
        errorCP = (TextInputLayout) findViewById(R.id.error_postalCode);
        errorEmail = (TextInputLayout) findViewById(R.id.error_email);
        errorPhone1 = (TextInputLayout) findViewById(R.id.error_phone1);
        errorPhone2 = (TextInputLayout) findViewById(R.id.error_phone2);

        Button btnNextClient= (Button) findViewById(R.id.btnNextClient);
        if (btnNextClient != null) {
            btnNextClient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    okFirstName = checkIsEmpty(firstName, errorFirstName);
                    okLastName = checkIsEmpty(lastName, errorLastName);
                    okAddress = checkIsEmpty(address, errorAddress);
                    okCity = checkIsEmpty(city, errorCity);
                    okPostalCode = checkIsEmpty(postalCode, errorCP);

                    if(!isValidPhone(phone1, errorPhone1)){
                        if(isValidPhone(phone2, errorPhone2) && phone1.getText().toString().equals("")){
                            errorPhone1.setErrorEnabled(false);
                            errorPhone1.setError("");
                        }
                    }else if (!isValidPhone(phone2, errorPhone2)){
                        if(isValidPhone(phone1, errorPhone1) && phone2.getText().toString().equals("")){
                            errorPhone2.setErrorEnabled(false);
                            errorPhone2.setError("");
                        }
                    }

                    okPhone1 = errorPhone1.isErrorEnabled();
                    okPhone2 = errorPhone2.isErrorEnabled();
                    okEmail = isValidEmail(email, errorEmail);

                    /*if(okFirstName && okLastName && okCity && okAddress &&
                            okPostalCode && !okPhone1 && !okPhone2 && okEmail) {

                        client.setLastName(lastName.getText().toString());
                        client.setFirstName(firstName.getText().toString());
                        client.setAddress(address.getText().toString());
                        client.setCity(city.getText().toString());
                        client.setPostalCode(postalCode.getText().toString());
                        client.setPhone(phone2.getText().toString());
                        client.setMobile(phone1.getText().toString());
                        client.setEmail(email.getText().toString());
*/
                        client.setLastName("Geoffrey");
                        client.setFirstName("Nguyen");
                        client.setAddress("rue des marrons chauds");
                        client.setCity("Paris");
                        client.setPostalCode("75018");
                        client.setPhone("0118420022");
                        client.setMobile("0650510137");
                        client.setEmail("geoffrey.nguyen@hotmail.fr");

                        resa.setClient(client);

                        if(updateClient==0){
                            Intent intent = new Intent(v.getContext(), RoomActivity.class);
                            intent.putExtra("resa", resa);
                            startActivity(intent);
                            finish();
                        }else{
                            String test = getIntent().getStringExtra("backActivity");
                            Intent intent = null;
                            try {
                                intent = new Intent(v.getContext(), forName(test.replace("class","").trim()));
                                intent.putExtra("resa", resa);
                                startActivity(intent);
                                finish();

                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                //}
            });
        }
    }

    private boolean checkIsEmpty(EditText input, TextInputLayout til){
        boolean valid = false;

        if(!input.getText().toString().trim().equals("")){
            til.setErrorEnabled(false);
            til.setError("");
            valid=true;
        }else{
            til.setErrorEnabled(true);
            til.setError("Information requise");
        }

        return valid;
    }

    private boolean isValidPhone(EditText phoneNumber, TextInputLayout til){
        boolean valid = false;
        boolean validPhone = Pattern.compile("^(0|\\+33)[1-9]([-. ]?[0-9]{2}){4}$").matcher(
                phoneNumber.getText().toString().trim()).matches();

        if (validPhone) {
            til.setErrorEnabled(false);
            til.setError("");
            valid = true;
        } else {
            til.setErrorEnabled(true);
            til.setError("Numéro incorrect");
        }
        return valid;
    }

    private boolean isValidEmail(EditText email, TextInputLayout til) {
        boolean valid = false;
        boolean validEmail = Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email.getText().toString().trim()).matches();


        if (validEmail) {
            til.setErrorEnabled(false);
            til.setError("");
            valid = true;
        } else {
            til.setErrorEnabled(true);
            til.setError("Email invalide");
        }
        return valid;
    }

    private boolean clientValid() {
        boolean valid = false;

        if(!lastName.getText().toString().equals(" ") && !firstName.getText().toString().equals(" ")){
            if(!address.getText().toString().equals(" ")){
                valid = true;
            }
        }

        return valid;
    }

}
