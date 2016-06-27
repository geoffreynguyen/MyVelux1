package com.myvelux.myvelux;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by geoffrey on 14/06/16.
 */
public class CommandeDataBaseAdapter {

    static final String DATABASE_NAME = "MyVelux.db";
    static final int DATABASE_VERSION = 7;
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
        com.setRoom(cursor.getString(cursor.getColumnIndex(COL_ROOM)));
        com.setAction(cursor.getString(cursor.getColumnIndex(COL_ACTION)));
        com.setActionPrice(cursor.getString(cursor.getColumnIndex(COL_PRICE)));
        com.setRange(cursor.getString(cursor.getColumnIndex(COL_RANGE)));
        com.setType(cursor.getString(cursor.getColumnIndex(COL_TYPE)));
        com.setVersion(cursor.getString(cursor.getColumnIndex(COL_VERSION)));
        com.setSize(cursor.getString(cursor.getColumnIndex(COL_SIZE)));
        com.setFitting(cursor.getString(cursor.getColumnIndex(COL_FITTING)));
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
        //values.put(COL_CLIENT, com.());

        // updating row
        String where = COL_ID_COMMAND+"= ?";
        return db.update(TABLE_COMMANDS, values, where, new String[] { String.valueOf(com.getId()) });
    }

    public Cursor findAll(){
        String mySql = " SELECT * FROM "+TABLE_COMMANDS;
        return db.rawQuery(mySql, null);
    }
    public Cursor findCommandsByClient(int idClient){

        String mySql = " SELECT * FROM "+ TABLE_COMMANDS +
                " WHERE "+ COL_CLIENT +" = "+ idClient +
                " AND "+ COL_DELETED +" = 0";
        return db.rawQuery(mySql, null);
    }
}