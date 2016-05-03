package com.myvelux.myvelux;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by geoffrey on 03/05/16.
 */
public class Reservation implements Serializable {
    private Client client;
    private Commande commande;
    private ArrayList<Commande> commandes = new ArrayList<>();


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public void addArrayCommande(Commande completCommande){
        this.commandes.add(completCommande);
    }

    public ArrayList<Commande> getCommandes (){
        return this.commandes;
    }
}
