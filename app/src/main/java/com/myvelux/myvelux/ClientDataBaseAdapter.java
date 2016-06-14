package com.myvelux.myvelux;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ClientDataBaseAdapter {

    static final String DATABASE_NAME = "MyVelux.db";
    static final int DATABASE_VERSION = 3;
    static final String TABLE_CLIENTS = "CLIENT";
    // Client Table Columns names

    private static final String COL_ID_CLIENT = "ID";
    private static final String COL_LASTNAME = "LASTNAME";
    private static final String COL_FIRSTNAME = "FIRSTNAME";
    private static final String COL_ADDRESS = "ADDRESS";
    private static final String COL_POSTALCODE = "POSTALCODE";
    private static final String COL_MUNICIPALITY = "MUNICIPALITY";
    private static final String COL_LANDLINE = "LANDLINE";
    private static final String COL_MOBILE = "MOBILE";
    private static final String COL_EMAIL = "EMAIL";
    private static final String COL_DATE_CREATE = "DATE";

    // SQL Statement to create a new database.
    static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_CLIENTS + "(" +
            COL_ID_CLIENT   + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COL_LASTNAME    + " TEXT," +
            COL_FIRSTNAME   + " TEXT," +
            COL_ADDRESS     + " TEXT," +
            COL_POSTALCODE  + " TEXT," +
            COL_MUNICIPALITY + " TEXT," +
            COL_LANDLINE    + " TEXT," +
            COL_MOBILE      + " TEXT," +
            COL_EMAIL       + " TEXT," +
            COL_DATE_CREATE        + " INT);";
    // Variable to hold the database instance
    public  SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private DataBaseHelper dbHelper;

    public  ClientDataBaseAdapter(Context _context)
    {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public  ClientDataBaseAdapter open() throws SQLException
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

    public void insertEntry(Client client)
    {
        ContentValues values = new ContentValues();
        // Assign values for each row.
        values.put(COL_LASTNAME, client.getLastName() );
        values.put(COL_FIRSTNAME, client.getFirstName() );
        values.put(COL_ADDRESS, client.getAddress() );
        values.put(COL_POSTALCODE, client.getPostalCode() );
        values.put(COL_MUNICIPALITY, client.getCity() );
        values.put(COL_LANDLINE, client.getPhone() );
        values.put(COL_MOBILE, client.getMobile() );
        values.put(COL_EMAIL, client.getEmail() );
        values.put(COL_DATE_CREATE, System.currentTimeMillis());

        // Insert the row into your table
        db.insert(TABLE_CLIENTS, null, values);
        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }
    public int deleteEntry(String UserName)
    {
        //String id=String.valueOf(ID);
        String where = COL_LASTNAME+"=?";
        int numberOFEntriesDeleted= db.delete(TABLE_CLIENTS, where, new String[]{UserName}) ;
        // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }


    public int deleteClientById(int idClient)
    {
        //String id=String.valueOf(ID);
        String where = COL_ID_CLIENT+"=?";
        int numberOFEntriesDeleted= db.delete(TABLE_CLIENTS, where, new String[]{String.valueOf(idClient)}) ;
        // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }

    public Client getSinlgeEntry(int idClient)
    {
        Cursor cursor=db.query(TABLE_CLIENTS, null, COL_ID_CLIENT + "=?", new String[]{String.valueOf(idClient)}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return null;
        }
        cursor.moveToFirst();
        Client client= new Client();
        client.setLastName(cursor.getString(cursor.getColumnIndex(COL_LASTNAME)));
        client.setFirstName(cursor.getString(cursor.getColumnIndex(COL_FIRSTNAME)));
        client.setAddress(cursor.getString(cursor.getColumnIndex(COL_ADDRESS)));
        client.setPostalCode(cursor.getString(cursor.getColumnIndex(COL_POSTALCODE)));
        client.setCity(cursor.getString(cursor.getColumnIndex(COL_MUNICIPALITY)));
        client.setPhone(cursor.getString(cursor.getColumnIndex(COL_LANDLINE)));
        client.setMobile(cursor.getString(cursor.getColumnIndex(COL_MOBILE)));
        client.setEmail(cursor.getString(cursor.getColumnIndex(COL_EMAIL)));
        cursor.close();
        return client;
    }

    public boolean checkClientByEmail(String email)
    {
        Cursor cursor=db.query(TABLE_CLIENTS, null, COL_EMAIL + "=?", new String[]{email}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return false;
        }
        return true;
    }

    public void  updateEntry(Client client)
    {
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();

        updatedValues.put(COL_LASTNAME, client.getLastName() );
        updatedValues.put(COL_FIRSTNAME, client.getFirstName() );
        updatedValues.put(COL_ADDRESS, client.getAddress() );
        updatedValues.put(COL_POSTALCODE, client.getPostalCode() );
        updatedValues.put(COL_MUNICIPALITY, client.getCity() );
        updatedValues.put(COL_LANDLINE, client.getPhone() );
        updatedValues.put(COL_MOBILE, client.getMobile() );
        updatedValues.put(COL_EMAIL, client.getEmail() );

        String where = COL_ID_CLIENT+"= ?";
        db.update(TABLE_CLIENTS,updatedValues, where, new String[]{client.getId()});
    }

    public Cursor findAll(){
        String mySql = " SELECT * FROM "+TABLE_CLIENTS;
        return db.rawQuery(mySql, null);
    }
}
