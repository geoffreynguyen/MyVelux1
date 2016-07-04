package com.myvelux.myvelux;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

import static java.lang.Class.forName;

public class ManageClient extends BaseActivity {

    ClientDataBaseAdapter clientDataBaseAdapter;

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
        setContentView(R.layout.activity_manage_client);
        setTitle("Informations client");

        clientDataBaseAdapter = new ClientDataBaseAdapter(this);
        clientDataBaseAdapter = clientDataBaseAdapter.open();

        // -1 is the default value if idClient is not declared
        updateClient = getIntent().getIntExtra("idClient",-1);

        firstName   = (EditText)findViewById(R.id.firstName);
        lastName   = (EditText)findViewById(R.id.lastName);
        address   = (EditText)findViewById(R.id.address);
        postalCode   = (EditText)findViewById(R.id.postalCode);
        city   = (EditText)findViewById(R.id.city);
        phone1   = (EditText)findViewById(R.id.phone1);
        phone2   = (EditText)findViewById(R.id.phone2);
        email = (EditText)findViewById(R.id.email);

        if (updateClient==-1){
            client = new Client();
        }else{
            client = clientDataBaseAdapter.getSinlgeEntry(updateClient);
            client.setId(String.valueOf(updateClient));
            client.setEmail(client.getEmail());
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

                    if (!isValidPhone(phone1, errorPhone1)) {
                        if (isValidPhone(phone2, errorPhone2) && phone1.getText().toString().equals("")) {
                            errorPhone1.setErrorEnabled(false);
                            errorPhone1.setError("");
                        }
                    } else if (!isValidPhone(phone2, errorPhone2)) {
                        if (isValidPhone(phone1, errorPhone1) && phone2.getText().toString().equals("")) {
                            errorPhone2.setErrorEnabled(false);
                            errorPhone2.setError("");
                        }
                    }

                    okPhone1 = errorPhone1.isErrorEnabled();
                    okPhone2 = errorPhone2.isErrorEnabled();
                    okEmail = isValidEmail(email, errorEmail);

                    if(okFirstName && okLastName && okCity && okAddress &&
                            okPostalCode && !okPhone1 && !okPhone2 && okEmail) {

                        client.setLastName(lastName.getText().toString());
                        client.setFirstName(firstName.getText().toString());
                        client.setAddress(address.getText().toString());
                        client.setCity(city.getText().toString());
                        client.setPostalCode(postalCode.getText().toString());
                        client.setPhone(phone2.getText().toString());
                        client.setMobile(phone1.getText().toString());



                    /*
                    client.setLastName("Geoffrey");
                    client.setFirstName("Nguyen");
                    client.setAddress("rue des marrons chauds");
                    client.setCity("Paris");
                    client.setPostalCode("75018");
                    client.setPhone("0118420022");
                    client.setMobile("0650510137");
                    client.setEmail("geoffrey.nguyen@hotmail.fr");
                    */

                        if (updateClient==-1){
                            if(!clientDataBaseAdapter.checkClientByEmail(email.getText().toString())){
                                client.setEmail(email.getText().toString());
                                SharedPrefManager.setIdClient((int) clientDataBaseAdapter.insertEntry(client));
                                Intent intent = new Intent(getApplicationContext(), ClientActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(getBaseContext(), "Un client possède déjà cet email", Toast.LENGTH_SHORT).show();
                            }


                       }else{
                            if(!client.getEmail().equals(email.getText().toString())){

                                client.setEmail(email.getText().toString());

                                if(!clientDataBaseAdapter.checkClientByEmail(email.getText().toString())){
                                    clientDataBaseAdapter.updateEntry(client);
                                    Intent intent = new Intent(getApplicationContext(), ClientActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(getBaseContext(), "Un client possède déjà cet email", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                client.setEmail(email.getText().toString());
                                clientDataBaseAdapter.updateEntry(client);
                                Intent intent = new Intent(getApplicationContext(), ClientActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }


                    }
                }
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

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(getApplicationContext(), ClientActivity.class);
        startActivity(intent);
        finish();
    }

}
