package com.myvelux.myvelux;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Julien on 17/05/2016.
 * Changed by Julien on 20/05/2016
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "MyVeluxDatabase";

    //Tables name
    private static final String TABLE_PRODUCTS = "Produit";
    private static final String TABLE_COMMANDS = "Commande";
    private static final String TABLE_CLIENTS = "Client";

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

    // Commande Table Columns names
    private static final String COL_ID_COMMAND = "id";
    private static final String COL_ROOM = "room";
    private static final String COL_ACTION = "action";
    private static final String COL_PRICE = "actionPrice";
    private static final String COL_RANGE = "Range";
    private static final String COL_TYPE = "type";
    private static final String COL_VERSION = "version";
    private static final String COL_SIZE = "size";
    private static final String COL_FITTING = "fitting";
    private static final String COL_CLIENT = "idClient";

    // Client Table Columns names
    private static final String COL_ID_CLIENT = "id";
    private static final String COL_LASTNAME = "lastname";
    private static final String COL_FIRSTNAME = "firstname";
    private static final String COL_ADDRESS = "address";
    private static final String COL_POSTALCODE = "postalCode";
    private static final String COL_MUNICIPALITY = "municipality";
    private static final String COL_LANDLINE = "landline";
    private static final String COL_MOBILE = "mobile";
    private static final String COL_EMAIL = "email";
    private static final String COL_DATE = "date";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create Table PRODUCTS

        String CREATE_TABLE_PRODUCTS = "CREATE TABLE " + TABLE_PRODUCTS + "(" +
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
        db.execSQL(CREATE_TABLE_PRODUCTS);

        //Create Table COMMANDS
        String CREATE_TABLE_COMMANDS = "CREATE TABLE " + TABLE_COMMANDS + "(" +
                COL_ID_COMMAND + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_ROOM    + " TEXT," +
                COL_ACTION  + " TEXT," +
                COL_PRICE   + " TEXT," +
                COL_RANGE   + " TEXT," +
                COL_TYPE    + " TEXT," +
                COL_VERSION + " TEXT," +
                COL_SIZE    + " TEXT," +
                COL_FITTING + " TEXT," +
                COL_CLIENT  + " TEXT" + ");";
        db.execSQL(CREATE_TABLE_COMMANDS);

        //Create Table CLIENTS
        String CREATE_TABLE_CLIENTS = "CREATE TABLE " + TABLE_CLIENTS + "(" +
                COL_ID_CLIENT   + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_LASTNAME    + " TEXT," +
                COL_FIRSTNAME   + " TEXT," +
                COL_ADDRESS     + " TEXT," +
                COL_POSTALCODE  + " TEXT," +
                COL_MUNICIPALITY + " TEXT," +
                COL_LANDLINE    + " TEXT," +
                COL_MOBILE      + " TEXT," +
                COL_EMAIL       + " TEXT," +
                COL_DATE        + " INT" + ");";
        db.execSQL(CREATE_TABLE_CLIENTS);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMANDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

// CRUD Commandes
    // Create single Command
    public long createCommand(Commande com) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_ROOM, com.getRoom());
        values.put(COL_ACTION, com.getAction());
        values.put(COL_PRICE, com.getActionPrice());
        values.put(COL_RANGE, com.getRange());
        values.put(COL_TYPE, com.getType());
        values.put(COL_VERSION, com.getVersion());
        values.put(COL_SIZE, com.getSize());
        values.put(COL_FITTING, com.getFitting());
        //values.put(COL_CLIENT, com.());

        // Inserting Row
        long id = db.insert(TABLE_COMMANDS, null, values);
        db.close(); // Closing database connection
        return id;
    }

    // Reading single Command
    public Commande readCommand(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor;
        cursor = db.query(TABLE_COMMANDS,
                new String[] {
                        COL_ROOM,
                        COL_ACTION,
                        COL_PRICE,
                        COL_RANGE,
                        COL_TYPE,
                        COL_VERSION,
                        COL_SIZE,
                        COL_FITTING,
                        COL_CLIENT
                },
                COL_ID_COMMAND + "=?", new String[] { String.valueOf(id) },
                null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Commande com = new Commande(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9));

        cursor.close();
        return com;
    }

    // Updating single Command
    public int updateCommand(Commande com) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_ROOM, com.getRoom());
        values.put(COL_ACTION, com.getAction());
        values.put(COL_PRICE, com.getActionPrice());
        values.put(COL_RANGE, com.getRange());
        values.put(COL_TYPE, com.getType());
        values.put(COL_VERSION, com.getVersion());
        values.put(COL_SIZE, com.getSize());
        values.put(COL_FITTING, com.getFitting());
        //values.put(COL_CLIENT, com.());

        // updating row
        return db.update(TABLE_COMMANDS, values, COL_ID_COMMAND + " = ?", new String[] { String.valueOf(com.getId()) });
    }

    // Deleting single Command
    public void deleteCommand(Commande com) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COMMANDS, COL_ID_COMMAND + " = ?",
                new String[] { String.valueOf(com.getId()) });
        db.close();
    }

// CRUD Clients
    // Create single Client
    public long createClient(Client client) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_LASTNAME, client.getLastName() );
        values.put(COL_FIRSTNAME, client.getFirstName() );
        values.put(COL_ADDRESS, client.getAddress() );
        values.put(COL_POSTALCODE, client.getPostalCode() );
        values.put(COL_MUNICIPALITY, client.getCity() );
        values.put(COL_LANDLINE, client.getPhone() );
        values.put(COL_MOBILE, client.getMobile() );
        values.put(COL_EMAIL, client.getEmail() );
        values.put(COL_DATE, System.currentTimeMillis());

        // Inserting Row
        long id = db.insert(TABLE_CLIENTS, null, values);
        db.close(); // Closing database connection
        return id;
    }

    // Reading single Client
    public Commande readClient(Client client) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor;
        cursor = db.query(TABLE_CLIENTS,
                new String[] {
                        COL_ID_CLIENT,
                        COL_LASTNAME,
                        COL_FIRSTNAME,
                        COL_ADDRESS,
                        COL_POSTALCODE,
                        COL_MUNICIPALITY,
                        COL_LANDLINE,
                        COL_MOBILE,
                        COL_EMAIL,
                        COL_DATE
                },
                COL_ID_CLIENT + "=?", new String[] { String.valueOf(client.getId()) },
                null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Commande com = new Commande(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9));
        cursor.close();
        return com;
    }

    // Updating single Client
    public int updateClient(Client client) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_LASTNAME, client.getLastName() );
        values.put(COL_FIRSTNAME, client.getFirstName() );
        values.put(COL_ADDRESS, client.getAddress() );
        values.put(COL_POSTALCODE, client.getPostalCode() );
        values.put(COL_MUNICIPALITY, client.getCity() );
        values.put(COL_LANDLINE, client.getPhone() );
        values.put(COL_MOBILE, client.getMobile() );
        values.put(COL_EMAIL, client.getEmail() );
/*        values.put(COL_DATE, System.currentTimeMillis()); */

        // updating row
        return db.update(TABLE_CLIENTS, values, COL_ID_CLIENT + " = ?", new String[] { String.valueOf(client.getId()) });
    }

    // Deleting single Client
    public void deleteClient(Client client) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CLIENTS, COL_ID_CLIENT + " = ?",
                new String[] { String.valueOf(client.getId()) });
        db.close();
    }

// CRUD Produit
    // Create single Produit
    public long createProduit(Produit produit) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_ID_PRODUCT, produit.getCOL_ID_PRODUCT());
        values.put(COL_FAMILLY_PRODUCT, produit.getCOL_FAMILLY_PRODUCT());
        values.put(COL_RANGE_PRODUCT, produit.getCOL_RANGE_PRODUCT() );
        values.put(COL_TYPE_PRODUCT, produit.getCOL_TYPE_PRODUCT() );
        values.put(COL_VERSION_PRODUCT, produit.getCOL_VERSION_PRODUCT());
        // Inserting Row
        long id = db.insert(TABLE_PRODUCTS, null, values);
        db.close(); // Closing database connection
        return id;
    }

    // Reading single Produit
    public Produit readProduit(Produit produit) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor;
        cursor = db.query(TABLE_PRODUCTS,
                new String[] {
                        COL_ID_PRODUCT,
                        COL_FAMILLY_PRODUCT,
                        COL_RANGE_PRODUCT,
                        COL_TYPE_PRODUCT,
                        COL_VERSION_PRODUCT,
                        COL_LIBEL,
                        COL_CODE,
                        COL_AREA,
                        COL_REFERENCE,
                        COL_COMBINATION_PRODUCT,
                        COL_COMMENT,
                        COL_PRICE_HT,
                        COL_PRICE_TTC,
                        COL_WEEK_DELAY,
                        COL_WINDOW_WIDTH ,
                        COL_WINDOW_HEIGHT,
                        COL_ISOLEMENT_COEFF
                },
                COL_ID_PRODUCT + "=?", new String[] { String.valueOf(produit.getCOL_ID_PRODUCT()) },
                null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        //Produit prod = new Produit(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15), cursor.getString(16), cursor.getString(17) );
        cursor.close();
        return null;
    }

    // Updating single Produit
    public int updateClient(Produit produit) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_ID_PRODUCT, produit.getCOL_ID_PRODUCT());
        values.put(COL_FAMILLY_PRODUCT, produit.getCOL_FAMILLY_PRODUCT());
        values.put(COL_RANGE_PRODUCT, produit.getCOL_RANGE_PRODUCT() );
        values.put(COL_TYPE_PRODUCT, produit.getCOL_TYPE_PRODUCT() );
        values.put(COL_VERSION_PRODUCT, produit.getCOL_VERSION_PRODUCT());

        // updating row
        return db.update(TABLE_PRODUCTS, values, COL_ID_PRODUCT + " = ?", new String[] { String.valueOf(produit.getCOL_ID_PRODUCT()) });
    }

    // Deleting single Command
    public void deleteProduit(Produit produit) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, COL_ID_PRODUCT + " = ?",
                new String[] { String.valueOf(produit.getCOL_ID_PRODUCT()) });
        db.close();
    }

    // Viewing All the table
    public void viewTable(String tablename) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor  cursor = db.rawQuery("select * from "+tablename, null);
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                int i;
                String row = "";
                for(i=0; i<cursor.getColumnCount(); i++) {
                    row += cursor.getColumnName(i) + "  ";
                }
                Log.i(tablename+"["+cursor.getPosition()+"]", row);
                cursor.moveToNext();
            }
        }
        db.close();
    }
}