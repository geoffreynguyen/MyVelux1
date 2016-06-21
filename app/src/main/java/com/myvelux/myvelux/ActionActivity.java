package com.myvelux.myvelux;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class ActionActivity extends BaseActivity {

    private Commande com;
    private String action = "";
    private EditText actionPrice;
    RadioGroup radioGroup;
    TextInputLayout errorActionPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        setTitle("Action");

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        actionPrice = (EditText) findViewById(R.id.costs);
        errorActionPrice = (TextInputLayout) findViewById(R.id.error_actionPrice);
        com = (Commande) getIntent().getSerializableExtra("com");
        if(!com.getActionPrice().equals("") && !com.getAction().equals("")){
            actionPrice.setText(com.getActionPrice());
            action = com.getAction();
            if(com.getAction().equals("Remplacement")){
                radioGroup.check(R.id.radioButton);
            }else if(com.getAction().equals("Création")){
                radioGroup.check(R.id.radioButton2);
            }
        }

        Button btnAction= (Button) findViewById(R.id.btnNextAction);
        if (btnAction != null) {
            btnAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // start new activity
                    if(!actionPrice.getText().toString().trim().equals("")){
                        errorActionPrice.setErrorEnabled(false);
                        errorActionPrice.setError("");
                        if(action.equals("")){
                            Toast.makeText(getBaseContext(), "Choisissez une action", Toast.LENGTH_SHORT).show();
                        }else{
                            Intent intent = new Intent(v.getContext(), RangeActivity.class);
                            com.setAction(action);
                            com.setActionPrice(actionPrice.getText().toString());
                            intent.putExtra("com", com);
                            startActivity(intent);

                        }

                    }else{
                        errorActionPrice.setErrorEnabled(true);
                        errorActionPrice.setError("Information requise");
                        if(action.equals("")){
                            Toast.makeText(getBaseContext(), "Choisissez une action", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            });
        }

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButton:
                if (checked)
                    action = ((RadioButton) view).getText().toString();
                    break;
            case R.id.radioButton2:
                if (checked)
                    action = ((RadioButton) view).getText().toString();
                break;
        }
    }

}
