package com.myvelux.myvelux;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LoginDataBaseAdapter
{
    static final String DATABASE_NAME = "MyVelux.db";
    static final int DATABASE_VERSION = 3;
    static final String TABLE_LOGIN = "LOGIN";

    private static final String COL_ID_LOGIN = "ID";
    private static final String COL_USERNAME = "USERNAME";
    private static final String COL_PASSWORD = "PASWWORD";

    // SQL Statement to create a new database.
    static final String DATABASE_CREATE = "create table "+ TABLE_LOGIN +
            "( " +COL_ID_LOGIN+" integer primary key autoincrement,"+ COL_USERNAME +"  text,"+ COL_PASSWORD +" text); ";
    // Variable to hold the database instance
    public  SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private DataBaseHelper dbHelper;
    public  LoginDataBaseAdapter(Context _context)
    {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public  LoginDataBaseAdapter open() throws SQLException
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

    public void insertEntry(String userName,String password)
    {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put(COL_USERNAME, userName);
        newValues.put(COL_PASSWORD,password);

        // Insert the row into your table
        db.insert(TABLE_LOGIN, null, newValues);
        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }
    public int deleteEntry(String UserName)
    {
        //String id=String.valueOf(ID);
        String where = COL_USERNAME + "=?";
        int numberOFEntriesDeleted= db.delete(TABLE_LOGIN, where, new String[]{UserName}) ;
        // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }
    public String getSinlgeEntry(String userName)
    {
        Cursor cursor=db.query(TABLE_LOGIN, null, COL_USERNAME + "=?", new String[]{userName}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password= cursor.getString(cursor.getColumnIndex(COL_PASSWORD));
        cursor.close();
        return password;
    }
    public void  updateEntry(String userName,String password)
    {
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        updatedValues.put(COL_USERNAME, userName);
        updatedValues.put(COL_PASSWORD,password);

        String where = COL_USERNAME + "= ?";
        db.update(TABLE_LOGIN,updatedValues, where, new String[]{userName});
    }
}