package com.myvelux.myvelux;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by geoffrey on 14/06/16.
 */
public class CommandeDataBaseAdapter {

    static final String DATABASE_NAME = "MyVelux.db";
    static final int DATABASE_VERSION = 9;
    static final String TABLE_COMMANDS = "COMMANDE";
    // Client Table Columns names

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
    private static final String COL_REF_ARTICLE = "refArticle";
    private static final String COL_PRICE_HT_VELUX = "prixHTVelux";
    private static final String COL_PRICE_TTC_VELUX = "prixTTCVelux";
    private static final String COL_REF_FITTING = "refFitting";
    private static final String COL_PRICE_HT_FITTING = "prixHTFitting";
    private static final String COL_PRICE_TTC_FITTING = "prixTTCFitting";
    private static final String COL_CLIENT = "idClient";
    private static final String COL_DELETED = "deleted";

    static final String DATABASE_COMMANDS = "CREATE TABLE " + TABLE_COMMANDS + "(" +
            COL_ID_COMMAND + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COL_ROOM    + " TEXT," +
            COL_ACTION  + " TEXT," +
            COL_PRICE   + " LONG," +
            COL_RANGE   + " LONG," +
            COL_TYPE    + " TEXT," +
            COL_VERSION + " TEXT," +
            COL_SIZE    + " LONG," +
            COL_FITTING + " TEXT," +
            COL_REF_ARTICLE + " TEXT," +
            COL_PRICE_HT_VELUX + " TEXT," +
            COL_PRICE_TTC_VELUX + " TEXT," +
            COL_REF_FITTING + " TEXT," +
            COL_PRICE_HT_FITTING + " TEXT," +
            COL_PRICE_TTC_FITTING + " TEXT," +
            COL_DELETED + " INT DEFAULT 0," +
            COL_CLIENT  + " TEXT" + ")";

    // Variable to hold the database instance
    public SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private DataBaseHelper dbHelper;

    public CommandeDataBaseAdapter(Context _context)
    {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public  CommandeDataBaseAdapter open() throws SQLException
    {
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

    public long insertEntry(Commande com)
    {
        ContentValues values = new ContentValues();
        values.put(COL_ROOM, com.getRoom());
        values.put(COL_ACTION, com.getAction());
        values.put(COL_PRICE, com.getActionPrice());
        values.put(COL_RANGE, com.getRange());
        values.put(COL_TYPE, com.getType());
        values.put(COL_VERSION, com.getVersion());
        values.put(COL_SIZE, com.getSize());
        values.put(COL_FITTING, com.getFitting());
        values.put(COL_REF_ARTICLE, com.getRefArticle());
        values.put(COL_PRICE_HT_VELUX, com.getPrixHTVelux());
        values.put(COL_PRICE_TTC_VELUX, com.getPrixTTCVelux());
        values.put(COL_REF_FITTING, com.getRefFitting());
        values.put(COL_PRICE_HT_FITTING, com.getPrixHTFitting());
        values.put(COL_PRICE_TTC_FITTING, com.getPrixTTCFitting());
        values.put(COL_CLIENT, com.getIdClient());

        // Inserting Row
        long id = db.insert(TABLE_COMMANDS, null, values);
        db.close(); // Closing database connection
        return id;
    }

    public int deleteEntry(String UserName)
    {
        //String id=String.valueOf(ID);
        String where = COL_ID_COMMAND+"=?";
        int numberOFEntriesDeleted= db.delete(TABLE_COMMANDS, where, new String[]{UserName}) ;
        // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }


    public int deleteCommandeById(int idCommande)
    {
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();

        updatedValues.put(COL_DELETED, 1);
        //String id=String.valueOf(ID);
        String where = COL_ID_COMMAND+"=?";
        int numberOFEntriesDeleted= db.update(TABLE_COMMANDS,updatedValues, where, new String[]{String.valueOf(idCommande)}) ;
        // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }

    public int activateCommandeById(int idCommande)
    {
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();

        updatedValues.put(COL_DELETED, 0);
        //String id=String.valueOf(ID);
        String where = COL_ID_COMMAND+"=?";
        int numberOFEntriesDeleted= db.update(TABLE_COMMANDS,updatedValues, where, new String[]{String.valueOf(idCommande)}) ;
        // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }


    public Commande getSinlgeEntry(int idCommande)
    {
        Cursor cursor=db.query(TABLE_COMMANDS, null, COL_ID_COMMAND + "=?", new String[]{String.valueOf(idCommande)}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return null;
        }
        cursor.moveToFirst();
        Commande com = new Commande();
        com.setId(cursor.getString(cursor.getColumnIndex(COL_ID_COMMAND)));
        com.setRoom(cursor.getString(cursor.getColumnIndex(COL_ROOM)));
        com.setAction(cursor.getString(cursor.getColumnIndex(COL_ACTION)));
        com.setActionPrice(cursor.getString(cursor.getColumnIndex(COL_PRICE)));
        com.setRange(cursor.getString(cursor.getColumnIndex(COL_RANGE)));
        com.setType(cursor.getString(cursor.getColumnIndex(COL_TYPE)));
        com.setVersion(cursor.getString(cursor.getColumnIndex(COL_VERSION)));
        com.setSize(cursor.getString(cursor.getColumnIndex(COL_SIZE)));
        com.setFitting(cursor.getString(cursor.getColumnIndex(COL_FITTING)));
        com.setRefArticle(cursor.getString(cursor.getColumnIndex(COL_REF_ARTICLE)));
        com.setPrixHTVelux(cursor.getString(cursor.getColumnIndex(COL_PRICE_HT_VELUX)));
        com.setPrixTTCVelux(cursor.getString(cursor.getColumnIndex(COL_PRICE_TTC_VELUX)));
        com.setRefFitting(cursor.getString(cursor.getColumnIndex(COL_REF_FITTING)));
        com.setPrixHTFitting(cursor.getString(cursor.getColumnIndex(COL_PRICE_HT_FITTING)));
        com.setPrixTTCFitting(cursor.getString(cursor.getColumnIndex(COL_PRICE_TTC_FITTING)));
        com.setIdClient(cursor.getString(cursor.getColumnIndex(COL_CLIENT)));
        cursor.close();
        return com;
    }

    public int updateCommande(Commande com)
    {
        ContentValues values = new ContentValues();
        values.put(COL_ROOM, com.getRoom());
        values.put(COL_ACTION, com.getAction());
        values.put(COL_PRICE, com.getActionPrice());
        values.put(COL_RANGE, com.getRange());
        values.put(COL_TYPE, com.getType());
        values.put(COL_VERSION, com.getVersion());
        values.put(COL_SIZE, com.getSize());
        values.put(COL_FITTING, com.getFitting());
        values.put(COL_CLIENT, com.getIdClient());
        values.put(COL_REF_FITTING, com.getRefFitting());
        values.put(COL_PRICE_HT_VELUX, com.getPrixHTVelux());
        values.put(COL_PRICE_TTC_VELUX, com.getPrixTTCVelux());
        values.put(COL_REF_ARTICLE, com.getRefArticle());
        values.put(COL_PRICE_HT_FITTING, com.getPrixHTFitting());
        values.put(COL_PRICE_TTC_FITTING, com.getPrixTTCFitting());

        // updating row
        String where = COL_ID_COMMAND+"= ?";
        return db.update(TABLE_COMMANDS, values, where, new String[] { String.valueOf(com.getId()) });
    }

    public Cursor findCommandsByClient(int idClient){

        String mySql = " SELECT * FROM "+ TABLE_COMMANDS +
                " WHERE "+ COL_CLIENT +" = "+ idClient +
                " ORDER BY " + COL_DELETED + " ASC ";
        return db.rawQuery(mySql, null);
    }

    public boolean findCommandsValidByClient(int idClient) {

        String where = COL_CLIENT + " = ? AND " +
                       COL_DELETED + " = ?";

        Cursor cursor = db.query(TABLE_COMMANDS, null, where, new String[]{String.valueOf(idClient),"0"}, null, null, null);

        if (cursor.getCount() > 0) // UserName Not Exist
        {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    public ArrayList<Commande> findCommandsByClientExport(int idClient){

        Commande com = new Commande();
        final ArrayList<Commande> todoItems = new ArrayList<>();
        String mySql = " SELECT * FROM "+ TABLE_COMMANDS +
                " WHERE "+ COL_CLIENT +" = "+ idClient +
                " AND " + COL_DELETED + " = 0";

        Cursor cursor = db.rawQuery(mySql, null);

        if(cursor.getCount()<1)
        {
            cursor.close();
            return null;

        }else if (cursor.moveToFirst())
        {
            do{
                com.setId(cursor.getString(cursor.getColumnIndex(COL_ID_COMMAND)));
                com.setRoom(cursor.getString(cursor.getColumnIndex(COL_ROOM)));
                com.setAction(cursor.getString(cursor.getColumnIndex(COL_ACTION)));
                com.setActionPrice(cursor.getString(cursor.getColumnIndex(COL_PRICE)));
                com.setRange(cursor.getString(cursor.getColumnIndex(COL_RANGE)));
                com.setType(cursor.getString(cursor.getColumnIndex(COL_TYPE)));
                com.setVersion(cursor.getString(cursor.getColumnIndex(COL_VERSION)));
                com.setSize(cursor.getString(cursor.getColumnIndex(COL_SIZE)));
                com.setFitting(cursor.getString(cursor.getColumnIndex(COL_FITTING)));
                com.setIdClient(cursor.getString(cursor.getColumnIndex(COL_CLIENT)));
                com.setRefArticle(cursor.getString(cursor.getColumnIndex(COL_REF_ARTICLE)));
                com.setPrixHTVelux(cursor.getString(cursor.getColumnIndex(COL_PRICE_HT_VELUX)));
                com.setPrixTTCVelux(cursor.getString(cursor.getColumnIndex(COL_PRICE_TTC_VELUX)));
                com.setRefFitting(cursor.getString(cursor.getColumnIndex(COL_REF_FITTING)));
                com.setPrixHTFitting(cursor.getString(cursor.getColumnIndex(COL_PRICE_HT_FITTING)));
                com.setPrixTTCFitting(cursor.getString(cursor.getColumnIndex(COL_PRICE_TTC_FITTING)));
                todoItems.add(com);
                com = new Commande();
            }while (cursor.moveToNext());
        }

        return todoItems;

    }
}