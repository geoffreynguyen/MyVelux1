package com.myvelux.myvelux;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class ManageUser extends BaseActivity {

    LoginDataBaseAdapter loginDataBaseAdapter;

    private User user;
    private int updateUser;
    private boolean isAdmin;
    EditText userName, password;
    CheckBox admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_user);
        setTitle("Informations utilisateur");

        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        // -1 is the default value if idClient is not declared
        updateUser = getIntent().getIntExtra("idUser",-1);
        isAdmin = getIntent().getBooleanExtra("isAdmin",false);

        userName   = (EditText)findViewById(R.id.userNameLogin);
        password   = (EditText)findViewById(R.id.passwordLogin);
        admin   = (CheckBox) findViewById(R.id.isAdmin);

        if (isAdmin && updateUser != SharedPrefManager.getIdLogin()) {
            if (admin != null) {
                admin.setVisibility(View.VISIBLE);
            }
        }

        if (updateUser == -1){
            user = new User();
        }else{
            user = loginDataBaseAdapter.getUserById(updateUser);
            user.setId(String.valueOf(updateUser));
            userName.setText(user.getUserName());
            password.setText(user.getPassword());
            admin.setChecked(user.getIsAdmin() == 1);
        }

        Button btnNextClient= (Button) findViewById(R.id.nextAddUser);
        if (btnNextClient != null) {
            btnNextClient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(checkIsEmpty(userName) && checkIsEmpty(password)) {

                        user.setPassword(password.getText().toString());
                        user.setIsAdmin(admin.isChecked() ? 1 : 0);

                        if (updateUser == -1){
                            if(!loginDataBaseAdapter.checkUserByUserName(userName.getText().toString())){
                                user.setUserName(userName.getText().toString());
                                loginDataBaseAdapter.insertEntry(user);
                                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(getBaseContext(), "Un utilisateur possède déjà ce pseudo", Toast.LENGTH_SHORT).show();
                            }


                        }else{
                            if(!user.getUserName().equals(userName.getText().toString())){

                                user.setUserName(userName.getText().toString());
                                user.setId(String.valueOf(updateUser));

                                if(!loginDataBaseAdapter.checkUserByUserName(userName.getText().toString())){
                                    if(String.valueOf(SharedPrefManager.getIdLogin()).equals(user.getId())){
                                        SharedPrefManager.setLogin(user.getUserName());
                                        SharedPrefManager.StoreToPref();
                                    }
                                    loginDataBaseAdapter.updateEntry(user);
                                    Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(getBaseContext(), "Un utilisateur possède déjà ce pseudo", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                user.setUserName(userName.getText().toString());
                                loginDataBaseAdapter.updateEntry(user);
                                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }


                    }
                }
            });
        }
    }

    private boolean checkIsEmpty(EditText input){
        boolean valid = false;

        if(!input.getText().toString().trim().equals("")){
            input.setError("");
            valid=true;
        }else{
            input.setError("Information requise");
        }

        return valid;
    }


    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(getApplicationContext(), UserActivity.class);
        startActivity(intent);
        finish();
    }

}
