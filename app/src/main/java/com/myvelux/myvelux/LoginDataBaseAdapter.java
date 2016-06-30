package com.myvelux.myvelux;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LoginDataBaseAdapter
{
    static final String DATABASE_NAME = "MyVelux.db";
    static final int DATABASE_VERSION = 9;
    static final String TABLE_LOGIN = "LOGIN";

    private static final String COL_ID_LOGIN = "ID";
    private static final String COL_USERNAME = "USERNAME";
    private static final String COL_PASSWORD = "PASWWORD";
    private static final String COL_DELETED = "DELETED";
    private static final String COL_ADMIN = "ADMIN";

    // SQL Statement to create a new database.
    static final String DATABASE_CREATE = "create table "+ TABLE_LOGIN +
            "( " +COL_ID_LOGIN+" integer primary key autoincrement,"+
            COL_USERNAME +"  text,"+
            COL_ADMIN +"  INT DEFAULT 0,"+
            COL_DELETED +"  INT DEFAULT 0,"+
            COL_PASSWORD +" text); ";
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

    public long insertEntry(User user)
    {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put(COL_USERNAME, user.getUserName());
        newValues.put(COL_PASSWORD,user.getPassword());
        newValues.put(COL_ADMIN,user.getIsAdmin());

        // Inserting Row
        long id = db.insert(TABLE_LOGIN, null, newValues);
        db.close(); // Closing database connection
        return id;
    }

    public int deleteEntry(String UserName)
    {
        //String id=String.valueOf(ID);
        String where = COL_USERNAME + "=?";
        int numberOFEntriesDeleted= db.delete(TABLE_LOGIN, where, new String[]{UserName}) ;
        // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }

    public int deleteUserById(int idUser)
    {
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        updatedValues.put(COL_DELETED, 1);

        //String id=String.valueOf(ID);
        String where = COL_ID_LOGIN + "=?";
        int numberOFEntriesDeleted= db.update(TABLE_LOGIN, updatedValues, where, new String[]{String.valueOf(idUser)}) ;
        // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }

    public int activateUserById(int idUser)
    {
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        updatedValues.put(COL_DELETED, 0);

        //String id=String.valueOf(ID);
        String where = COL_ID_LOGIN + "=?";
        int numberOFEntriesDeleted= db.update(TABLE_LOGIN, updatedValues, where, new String[]{String.valueOf(idUser)}) ;
        // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }

    public User getSinlgeEntry(String userName)
    {
        User user = new User();

        Cursor cursor=db.query(TABLE_LOGIN, null, COL_USERNAME + "=? AND "+COL_DELETED+"=0", new String[]{userName}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return null;
        }
        cursor.moveToFirst();

        user.setPassword(cursor.getString(cursor.getColumnIndex(COL_PASSWORD)));
        user.setUserName(cursor.getString(cursor.getColumnIndex(COL_USERNAME)));
        user.setId(cursor.getString(cursor.getColumnIndex(COL_ID_LOGIN)));
        user.setIsAdmin(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COL_ADMIN))));
        cursor.close();
        return user;
    }

    public User getUserById(int idUser)
    {
        Cursor cursor=db.query(TABLE_LOGIN, null, COL_ID_LOGIN + "=?", new String[]{String.valueOf(idUser)}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return null;
        }
        cursor.moveToFirst();
        User user = new User();
        user.setPassword(cursor.getString(cursor.getColumnIndex(COL_PASSWORD)));
        user.setIsAdmin(cursor.getInt(cursor.getColumnIndex(COL_ADMIN)));
        user.setUserName(cursor.getString(cursor.getColumnIndex(COL_USERNAME)));
        cursor.close();
        return user;
    }

    public void  updateEntry(User user)
    {
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        updatedValues.put(COL_USERNAME, user.getUserName());
        updatedValues.put(COL_ADMIN,user.getIsAdmin());
        updatedValues.put(COL_PASSWORD,user.getPassword());
        updatedValues.put(COL_DELETED,0);

        String where = COL_ID_LOGIN + "= ?";
        db.update(TABLE_LOGIN,updatedValues, where, new String[]{String.valueOf(user.getId())});
    }

    public Cursor findAll(){
        String mySql = " SELECT * FROM "+TABLE_LOGIN + " ORDER BY "+ COL_DELETED +" ASC";
        return db.rawQuery(mySql, null);
    }

    public User findAllValidUser(){
        Cursor cursor=db.query(TABLE_LOGIN, null, COL_DELETED + "=?", new String[]{"0"}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return null;
        }
        cursor.moveToFirst();
        User user = new User();
        user.setPassword(cursor.getString(cursor.getColumnIndex(COL_PASSWORD)));
        user.setIsAdmin(cursor.getInt(cursor.getColumnIndex(COL_ADMIN)));
        user.setUserName(cursor.getString(cursor.getColumnIndex(COL_USERNAME)));
        cursor.close();
        return user;
    }

    public Cursor findUserCursor(int idUser)
    {
        String mySql = " SELECT * FROM "+TABLE_LOGIN+" WHERE "+COL_ID_LOGIN+" = "+ idUser;
        return db.rawQuery(mySql, null);
    }

    public boolean checkUserByUserName(String userName)
    {
        Cursor cursor=db.query(TABLE_LOGIN, null, COL_USERNAME + "=?", new String[]{userName}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return false;
        }
        return true;
    }

    public boolean isAdminById(int idUser)
    {
        Cursor cursor=db.query(TABLE_LOGIN, null, COL_ID_LOGIN + "=?", new String[]{String.valueOf(idUser)}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return false;
        }
        cursor.moveToFirst();
        boolean isAdmin = cursor.getInt(cursor.getColumnIndex(COL_ADMIN)) == 1;
        cursor.close();
        return isAdmin;
    }

}