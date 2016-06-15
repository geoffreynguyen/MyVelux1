package com.myvelux.myvelux;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testsBaseDonnees();
    }

    protected void btnNext(View view) {
        Intent intent = new Intent(this, ClientActivity.class);
        startActivity(intent);
    }

    /* A supprimer a la fin du projet */
    protected void testsBaseDonnees() {
        DatabaseHandler db = new DatabaseHandler(this);
        Commande commande = new Commande("1", "chambre", "action", "15", "42", "creation", "version", "12", "1", "1");
        Client client = new Client("1", "Dumou", "Valou", "Chezlui", "666666", "CItédlaMort", "0666666666", "0456642312", "dumou.valou@yahou.nigeria");
        Produit produit = new Produit("COL_ID_PRODUCT", "COL_FAMILLY_PRODUCT", "COL_RANGE_PRODUCT", "COL_TYPE_PRODUCT", "COL_VERSION_PRODUCT", "COL_LIBEL", "COL_CODE", "COL_AREA", "COL_REFERENCE", "COL_COMBINATION_PRODUCT", "COL_COMMENT", "COL_PRICE_HT", "COL_PRICE_TTC", "COL_WEEK_DELAY", "COL_WINDOW_WIDTH", "COL_WINDOW_HEIGHT", "COL_ISOLEMENT_COEFF", "COL_WINDOW_AREA");

        db.createCommand(commande);
        db.viewTable("Commande");


        db.createClient(client);
        db.viewTable("Client");
 /*
        db.createProduit(produit);
        db.viewTable("TABLE_PRODUIT");
*/
    }
}
