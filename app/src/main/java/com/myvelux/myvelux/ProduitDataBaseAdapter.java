package com.myvelux.myvelux;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ProduitDataBaseAdapter
{
    static final String DATABASE_NAME = "MyVelux.db";
    static final int DATABASE_VERSION = 3;
    static final String TABLE_PRODUCTS = "PRODUIT";

    // Product Table Columns names
    private static final String COL_ID_PRODUCT = "id";
    private static final String COL_FAMILLY_PRODUCT = "FAMILLES DE PRODUITS";
    private static final String COL_RANGE_PRODUCT = "GAMMES DE PRODUITS";
    private static final String COL_TYPE_PRODUCT = "TYPES";
    private static final String COL_VERSION_PRODUCT = "VERSIONS";
    private static final String COL_LIBEL = "LIBELLES ARTICLES";
    private static final String COL_CODE = "codes dimensionnels";
    private static final String COL_AREA = "Cotes Hors tout";
    private static final String COL_REFERENCE = "TES";
    /* private static final String COL_REFERENCE = "REFERENCES ARTICLES ";*/
    private static final String COL_COMBINATION_PRODUCT = "COMBINAISONS DE PRODUITS ";
    private static final String COL_COMMENT = "Commentaires";
    private static final String COL_PRICE_HT = "Prix de vente HT unitaire";
    private static final String COL_PRICE_TTC = "Prix TTC";
    private static final String COL_WEEK_DELAY = "Delais en semaines";
    private static final String COL_WINDOW_WIDTH = "LARGEUR Cote Hors tout de la fenetre";
    private static final String COL_WINDOW_HEIGHT = "HAUTEUR Cote Hors tout de la fenetre ";
    private static final String COL_ISOLEMENT_COEFF = "Coef isolation thermique hiver";
    private static final String COL_WINDOW_AREA = "surface de baie";

    static final String CREATE_TABLE_PRODUCTS = "CREATE TABLE " + TABLE_PRODUCTS + "(" +
            COL_ID_PRODUCT + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COL_FAMILLY_PRODUCT + " TEXT," +
            COL_RANGE_PRODUCT   + " TEXT," +
            COL_TYPE_PRODUCT    + " TEXT," +
            COL_VERSION_PRODUCT + " TEXT," +
            COL_LIBEL           + " TEXT," +
            COL_CODE            + " TEXT," +
            COL_AREA            + " TEXT," +
            COL_REFERENCE       + " TEXT," +
            COL_COMBINATION_PRODUCT + " TEXT," +
            COL_COMMENT         + " TEXT," +
            COL_PRICE_HT        + " TEXT," +
            COL_PRICE_TTC       + " TEXT," +
            COL_WEEK_DELAY      + " TEXT," +
            COL_WINDOW_WIDTH    + " TEXT," +
            COL_WINDOW_HEIGHT   + " TEXT," +
            COL_ISOLEMENT_COEFF + " TEXT," +
            COL_WINDOW_AREA     + " TEXT" + ");";

    // Variable to hold the database instance
    public SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private DataBaseHelper dbHelper;

    public ProduitDataBaseAdapter(Context _context) {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public ProduitDataBaseAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        db.close();
    }

    public  SQLiteDatabase getDatabaseInstance()
    {
        return db;
    }

    public long insertEntry(Produit produit) {

        ContentValues values = new ContentValues();
        values.put(COL_ID_PRODUCT, produit.getCOL_ID_PRODUCT());
        values.put(COL_FAMILLY_PRODUCT, produit.getCOL_FAMILLY_PRODUCT());
        values.put(COL_RANGE_PRODUCT, produit.getCOL_RANGE_PRODUCT() );
        values.put(COL_TYPE_PRODUCT, produit.getCOL_TYPE_PRODUCT() );
        values.put(COL_VERSION_PRODUCT, produit.getCOL_VERSION_PRODUCT());
        values.put(COL_LIBEL, produit.getCOL_LIBEL());
        values.put(COL_CODE, produit.getCOL_CODE());
        values.put(COL_AREA, produit.getCOL_AREA());
        values.put(COL_REFERENCE, produit.getCOL_REFERENCE());
        values.put(COL_COMBINATION_PRODUCT, produit.getCOL_COMBINATION_PRODUCT());
        values.put(COL_COMMENT, produit.getCOL_COMMENT());
        values.put(COL_PRICE_HT, produit.getCOL_PRICE_HT());
        values.put(COL_PRICE_TTC, produit.getCOL_PRICE_TTC());
        values.put(COL_WEEK_DELAY, produit.getCOL_WEEK_DELAY());
        values.put(COL_WINDOW_WIDTH , produit.getCOL_WINDOW_WIDTH());
        values.put(COL_WINDOW_HEIGHT, produit.getCOL_WINDOW_HEIGHT());
        values.put(COL_ISOLEMENT_COEFF, produit.getCOL_ISOLEMENT_COEFF());
        values.put(COL_WINDOW_AREA, produit.getCOL_AREA());

        // Inserting Row
        long id = db.insert(TABLE_PRODUCTS, null, values);
        return id;
    }

    public int deleteEntry(String produit) {
        String where = COL_ID_PRODUCT + "=?";
        int numberOFEntriesDeleted= db.delete(TABLE_PRODUCTS, where, new String[]{produit}) ;
        return numberOFEntriesDeleted;
    }

    public int deleteProduitById(int idProduit)
    {
        String where = COL_ID_PRODUCT + "=?";
        int numberOFEntriesDeleted= db.delete(TABLE_PRODUCTS, where, new String[]{String.valueOf(idProduit)}) ;
        return numberOFEntriesDeleted;
    }

    public Produit getSinlgeEntry(int idProduit) {
        Cursor cursor=db.query(TABLE_PRODUCTS, null, COL_ID_PRODUCT + "=?", new String[]{String.valueOf(idProduit)}, null, null, null);
        if(cursor.getCount()<1) {
            cursor.close();
            return null;
        }

        cursor.moveToFirst();
        Produit prod = new Produit();
        prod.setCOL_FAMILLY_PRODUCT(cursor.getString(cursor.getColumnIndex(COL_FAMILLY_PRODUCT)));
        prod.setCOL_RANGE_PRODUCT(cursor.getString(cursor.getColumnIndex(COL_RANGE_PRODUCT)));
        prod.setCOL_TYPE_PRODUCT(cursor.getString(cursor.getColumnIndex(COL_TYPE_PRODUCT)));
        prod.setCOL_VERSION_PRODUCT(cursor.getString(cursor.getColumnIndex(COL_VERSION_PRODUCT)));
        prod.setCOL_LIBEL(cursor.getString(cursor.getColumnIndex(COL_LIBEL)));
        prod.setCOL_CODE(cursor.getString(cursor.getColumnIndex(COL_CODE)));
        prod.setCOL_AREA(cursor.getString(cursor.getColumnIndex(COL_AREA)));
        prod.setCOL_REFERENCE(cursor.getString(cursor.getColumnIndex(COL_REFERENCE)));
        prod.setCOL_COMBINATION_PRODUCT(cursor.getString(cursor.getColumnIndex(COL_COMBINATION_PRODUCT)));
        prod.setCOL_COMMENT(cursor.getString(cursor.getColumnIndex(COL_COMMENT)));
        prod.setCOL_PRICE_HT(cursor.getString(cursor.getColumnIndex(COL_PRICE_HT)));
        prod.setCOL_PRICE_TTC(cursor.getString(cursor.getColumnIndex(COL_PRICE_TTC)));
        prod.setCOL_WEEK_DELAY(cursor.getString(cursor.getColumnIndex(COL_WEEK_DELAY)));
        prod.setCOL_WINDOW_WIDTH(cursor.getString(cursor.getColumnIndex(COL_WINDOW_WIDTH )));
        prod.setCOL_WINDOW_HEIGHT(cursor.getString(cursor.getColumnIndex(COL_WINDOW_HEIGHT)));
        prod.setCOL_ISOLEMENT_COEFF(cursor.getString(cursor.getColumnIndex(COL_ISOLEMENT_COEFF)));
        cursor.close();
        return prod;
    }

    public int updateCommande(Produit prod) {
        ContentValues values = new ContentValues();
        values.put(COL_FAMILLY_PRODUCT, prod.getCOL_FAMILLY_PRODUCT());
        values.put(COL_RANGE_PRODUCT, prod.getCOL_RANGE_PRODUCT());
        values.put(COL_TYPE_PRODUCT, prod.getCOL_TYPE_PRODUCT());
        values.put(COL_VERSION_PRODUCT, prod.getCOL_VERSION_PRODUCT());
        values.put(COL_LIBEL, prod.getCOL_LIBEL());
        values.put(COL_CODE, prod.getCOL_CODE());
        values.put(COL_AREA, prod.getCOL_AREA());
        values.put(COL_REFERENCE, prod.getCOL_REFERENCE());
        values.put(COL_COMBINATION_PRODUCT, prod.getCOL_COMBINATION_PRODUCT());
        values.put(COL_COMMENT, prod.getCOL_COMMENT());
        values.put(COL_PRICE_HT, prod.getCOL_PRICE_HT());
        values.put(COL_PRICE_TTC, prod.getCOL_PRICE_TTC());
        values.put(COL_WEEK_DELAY, prod.getCOL_WEEK_DELAY());
        values.put(COL_WINDOW_WIDTH, prod.getCOL_WINDOW_WIDTH());
        values.put(COL_WINDOW_HEIGHT, prod.getCOL_WINDOW_HEIGHT());
        values.put(COL_ISOLEMENT_COEFF, prod.getCOL_ISOLEMENT_COEFF());
        String where = COL_ID_PRODUCT+"= ?";
        return db.update(TABLE_PRODUCTS, values, where, new String[] { String.valueOf(prod.getCOL_ID_PRODUCT()) });
    }

    public Cursor findAll(){
        String mySql = " SELECT * FROM "+TABLE_PRODUCTS;
        return db.rawQuery(mySql, null);
    }
}


