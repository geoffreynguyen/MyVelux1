package com.myvelux.myvelux;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by geoffrey on 17/06/16.
 */
public class ProductDataBaseAdapter {

    static final String DATABASE_NAME = "MyVelux.db";
    static final int DATABASE_VERSION = 7;
    static final String TABLE_PRODUCT = "PRODUCT";

    // Product Table Columns names
    private static final String COL_ID_PRODUCT = "id";
    private static final String COL_FAMILLY_PRODUCT = "FAMILLE";
    private static final String COL_RANGE_PRODUCT = "GAMME";
    private static final String COL_TYPE_PRODUCT = "TYPE";
    private static final String COL_VERSION_PRODUCT = "VERSION";
    private static final String COL_LIBEL_ARTICLE = "LIBELLE";
    private static final String COL_REF_DIMENSION = "REF_DIMENSION";
    private static final String COL_DIMENSION = "DIMENSION";
    private static final String COL_REF_ARTICLE = "REF_ARTICLE";
    private static final String COL_PRICE_HT = "PRIX_HT";
    private static final String COL_PRICE_TTC = "PRIX_TTC";

    static final String DATABASE_PRODUCTS = "CREATE TABLE " + TABLE_PRODUCT + "(" +
            COL_ID_PRODUCT + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COL_FAMILLY_PRODUCT + " TEXT," +
            COL_RANGE_PRODUCT   + " TEXT," +
            COL_TYPE_PRODUCT    + " TEXT," +
            COL_VERSION_PRODUCT + " TEXT," +
            COL_LIBEL_ARTICLE   + " TEXT," +
            COL_REF_DIMENSION   + " TEXT," +
            COL_DIMENSION       + " TEXT," +
            COL_REF_ARTICLE     + " TEXT," +
            COL_PRICE_HT        + " TEXT," +
            COL_PRICE_TTC       + " TEXT );";

    // Variable to hold the database instance
    public SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private DataBaseHelper dbHelper;

    public  ProductDataBaseAdapter(Context _context)
    {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public  ProductDataBaseAdapter open() throws SQLException
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

    public long insertEntry(Produit product)
    {
        ContentValues values = new ContentValues();
        values.put(COL_FAMILLY_PRODUCT, product.getCOL_FAMILLY_PRODUCT());
        values.put(COL_RANGE_PRODUCT, product.getCOL_RANGE_PRODUCT() );
        values.put(COL_TYPE_PRODUCT, product.getCOL_TYPE_PRODUCT() );
        values.put(COL_VERSION_PRODUCT, product.getCOL_VERSION_PRODUCT());
        values.put(COL_LIBEL_ARTICLE, product.getCOL_LIBEL_ARTICLE());
        values.put(COL_REF_DIMENSION, product.getCOL_REF_DIMENSION());
        values.put(COL_DIMENSION, product.getCOL_DIMENSION());
        values.put(COL_REF_ARTICLE, product.getCOL_REF_ARTCILE());
        values.put(COL_PRICE_HT, product.getCOL_PRICE_HT());
        values.put(COL_PRICE_TTC, product.getCOL_PRICE_TTC());

        // Inserting Row
        long id = db.insert(TABLE_PRODUCT, null, values);
        db.close(); // Closing database connection
        return id;
    }

    public long insertRowCSV(ArrayList<String> product)
    {
        String where = COL_FAMILLY_PRODUCT+"= ? AND "+
                COL_RANGE_PRODUCT+"= ? AND "+
                COL_TYPE_PRODUCT+"= ? AND "+
                COL_VERSION_PRODUCT+"= ? AND "+
                COL_LIBEL_ARTICLE+"= ? AND "+
                COL_REF_DIMENSION+"= ? AND "+
                COL_DIMENSION+"= ? AND "+
                COL_REF_ARTICLE+"= ? AND "+
                COL_PRICE_HT+"= ? AND "+
                COL_PRICE_TTC+"= ?";

        String[] mStringArray = new String[product.size()];
        mStringArray = product.toArray(mStringArray);

        Cursor cursor=db.query(TABLE_PRODUCT, null, where, mStringArray, null, null, null);

        if(cursor.getCount()>0) // UserName Not Exist
        {
            cursor.close();
            return 0;

        }else{

            ContentValues values = new ContentValues();
            values.put(COL_FAMILLY_PRODUCT, product.get(0));
            values.put(COL_RANGE_PRODUCT, product.get(1) );
            values.put(COL_TYPE_PRODUCT, product.get(2) );
            values.put(COL_VERSION_PRODUCT, product.get(3));
            values.put(COL_LIBEL_ARTICLE, product.get(4));
            values.put(COL_REF_DIMENSION, product.get(5));
            values.put(COL_DIMENSION, product.get(6));
            values.put(COL_REF_ARTICLE, product.get(7));
            values.put(COL_PRICE_HT, product.get(8));
            values.put(COL_PRICE_TTC, product.get(9));

            // Inserting Row
            long id = db.insert(TABLE_PRODUCT, null, values);
            // Closing database connection
            cursor.close();
            return id;
        }

    }

    public int deleteEntry(int idProduct)
    {
        //String id=String.valueOf(ID);
        String where = COL_ID_PRODUCT + "=?";
        int numberOFEntriesDeleted= db.delete(TABLE_PRODUCT, where, new String[]{String.valueOf(idProduct)}) ;
        // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }

    public int deleteProducts()
    {
        db.delete(TABLE_PRODUCT, null, null) ;
        return 1;
    }

    public Produit getSinlgeEntry(int idProduct)
    {
        Cursor cursor=db.query(TABLE_PRODUCT, null, COL_ID_PRODUCT + "=?", new String[]{String.valueOf(idProduct)}, null, null, null);

        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return null;
        }

        Produit prod = new Produit();
        prod.setCOL_FAMILLY_PRODUCT(cursor.getString(cursor.getColumnIndex(COL_FAMILLY_PRODUCT)));
        prod.setCOL_RANGE_PRODUCT(cursor.getString(cursor.getColumnIndex(COL_RANGE_PRODUCT)));
        prod.setCOL_TYPE_PRODUCT(cursor.getString(cursor.getColumnIndex(COL_TYPE_PRODUCT)));
        prod.setCOL_VERSION_PRODUCT(cursor.getString(cursor.getColumnIndex(COL_VERSION_PRODUCT)));
        prod.setCOL_LIBEL_ARTICLE(cursor.getString(cursor.getColumnIndex(COL_LIBEL_ARTICLE)));
        prod.setCOL_REF_DIMENSION(cursor.getString(cursor.getColumnIndex(COL_REF_DIMENSION)));
        prod.setCOL_DIMENSION(cursor.getString(cursor.getColumnIndex(COL_DIMENSION)));
        prod.setCOL_REF_ARTCILE(cursor.getString(cursor.getColumnIndex(COL_REF_ARTICLE)));
        prod.setCOL_PRICE_HT(cursor.getString(cursor.getColumnIndex(COL_PRICE_HT)));
        prod.setCOL_PRICE_TTC(cursor.getString(cursor.getColumnIndex(COL_PRICE_TTC)));
        cursor.close();
        return prod;
    }


    public ArrayList<String> getProductType(String range)
    {
        ArrayList<String> types = new ArrayList<>();
        String mySql = " SELECT * FROM "+ TABLE_PRODUCT +
                " WHERE "+ COL_FAMILLY_PRODUCT + " LIKE '%FENETRES%' AND " +
                COL_RANGE_PRODUCT+" LIKE '%" + range + "%'";

        Cursor cursor = db.rawQuery(mySql, null);

        if(cursor.getCount()<1)
        {
            cursor.close();
            return null;
        }else if (cursor.moveToFirst())
        {
            do{
                String type = cursor.getString(cursor.getColumnIndex(COL_TYPE_PRODUCT));
                type = type.replace("\"","");
                if(!types.contains(type)){
                    types.add(type);
                }

            }while (cursor.moveToNext());
        }

        cursor.close();
        return types;
    }
    
    public ArrayList<String> getProductVersion(String range, String type)
    {
        ArrayList<String> versions = new ArrayList<>();
        String mySql = " SELECT * FROM "+ TABLE_PRODUCT +
                " WHERE "+ COL_RANGE_PRODUCT+" LIKE '%" + range + "%'AND " +
                COL_TYPE_PRODUCT+" LIKE '%" + type + "%'";

        Cursor cursor = db.rawQuery(mySql, null);
        
        if(cursor.getCount()<1)
        {
            cursor.close();
            return null;
        }else if (cursor.moveToFirst())
        {
            do{
                String version = cursor.getString(cursor.getColumnIndex(COL_VERSION_PRODUCT));
                version = version.replace("\"","");
                if(!versions.contains(version)){
                    versions.add(version);
                }

            }while (cursor.moveToNext());
        }
        cursor.close();
        return versions;
    }

    public ArrayList<String> getProductSize(String range, String type, String version)
    {
        ArrayList<String> sizes = new ArrayList<>();
        String mySql = " SELECT * FROM "+ TABLE_PRODUCT +
                " WHERE "+ COL_RANGE_PRODUCT+" LIKE '%" + range + "%'AND " +
                COL_TYPE_PRODUCT+" LIKE '%" + type + "%' AND " +
                COL_VERSION_PRODUCT+" LIKE '%" + version + "%'";

        Cursor cursor = db.rawQuery(mySql, null);

        if(cursor.getCount()<1)
        {
            cursor.close();
            return null;

        }else if (cursor.moveToFirst())
        {
            do{
                String size = cursor.getString(cursor.getColumnIndex(COL_DIMENSION));
                sizes.add(size.replace("\"",""));
            }while (cursor.moveToNext());
        }

        cursor.close();
        return sizes;
    }

    public String getProductRefVelux(String action, String range, String type, String version, String size)
    {
        ArrayList<String> sizes = new ArrayList<>();
        String mySql = " SELECT * FROM "+ TABLE_PRODUCT +
                " WHERE "+ COL_RANGE_PRODUCT+" LIKE '%" + range + "%'AND " +
                COL_TYPE_PRODUCT+" LIKE '%" + type + "%' AND " +
                COL_VERSION_PRODUCT+" LIKE '%" + version + "%' AND " +
                COL_DIMENSION+" LIKE '%" + size + "%'";

        Cursor cursor = db.rawQuery(mySql, null);

        if(cursor.getCount()<1)
        {
            cursor.close();
            return null;

        }
        cursor.moveToFirst();
        String refArticle = cursor.getString(cursor.getColumnIndex(COL_REF_ARTICLE));

        cursor.close();
        return refArticle;
    }

    public ArrayList<HashMap<String, String>> getProductFitting(String action, String range, String type, String version, String size)
    {
        HashMap<String, String> fittings = new HashMap<>();
        final ArrayList<HashMap<String, String>> todoItems = new ArrayList<>();

        String mySql = " SELECT * FROM "+ TABLE_PRODUCT +
                " WHERE "+ COL_RANGE_PRODUCT+" LIKE '%" + range + "%'AND " +
                COL_TYPE_PRODUCT+" LIKE '%" + type + "%' AND " +
                COL_VERSION_PRODUCT+" LIKE '%" + version + "%' AND " +
                COL_DIMENSION+" LIKE '%" + size + "%'";

        Cursor cursor = db.rawQuery(mySql, null);

        if(cursor.getCount()<1)
        {
            cursor.close();
            return null;

        }
        cursor.moveToFirst();
        String refSize = cursor.getString(cursor.getColumnIndex(COL_REF_DIMENSION));

        String getFitting = " SELECT * FROM "+ TABLE_PRODUCT +
                " WHERE "+ COL_FAMILLY_PRODUCT + " LIKE '%RACCORDS%' AND " +
                COL_REF_DIMENSION+" LIKE '%" + refSize.replace("\"","") + "%'";
        if(action.equals("Création")){
            getFitting += " AND " + COL_TYPE_PRODUCT+" IN ('\"EDL\"', '\"EDN\"', '\"EDW\"', '\"EDJ\"', '\"EDP\"')";
        }else if(action.equals("Remplacement")){
            getFitting += " AND " + COL_TYPE_PRODUCT+" IN ('\"EL\"', '\"EW\"')";
        }

        Cursor cursor2 = db.rawQuery(getFitting, null);

        if(cursor2.getCount()<1)
        {
            cursor2.close();
            return null;

        }else if (cursor2.moveToFirst())
        {
            do{
                String fittingLibel = cursor2.getString(cursor.getColumnIndex(COL_LIBEL_ARTICLE));
                String fittingRef = cursor2.getString(cursor.getColumnIndex(COL_REF_ARTICLE));
                fittings.put("libel_article",fittingLibel.replace("\"",""));
                fittings.put("ref_article", fittingRef.replace("\"",""));
                todoItems.add(fittings);
                fittings = new HashMap<>();
            }while (cursor2.moveToNext());
        }

        return todoItems;
    }

    public int  updateEntry(Produit product)
    {
        ContentValues values = new ContentValues();
        values.put(COL_FAMILLY_PRODUCT, product.getCOL_FAMILLY_PRODUCT());
        values.put(COL_RANGE_PRODUCT, product.getCOL_RANGE_PRODUCT() );
        values.put(COL_TYPE_PRODUCT, product.getCOL_TYPE_PRODUCT() );
        values.put(COL_VERSION_PRODUCT, product.getCOL_VERSION_PRODUCT());
        values.put(COL_LIBEL_ARTICLE, product.getCOL_LIBEL_ARTICLE());
        values.put(COL_REF_DIMENSION, product.getCOL_REF_DIMENSION());
        values.put(COL_DIMENSION, product.getCOL_DIMENSION());
        values.put(COL_REF_ARTICLE, product.getCOL_REF_DIMENSION());
        values.put(COL_PRICE_HT, product.getCOL_PRICE_HT());
        values.put(COL_PRICE_TTC, product.getCOL_PRICE_TTC());

        // updating row
        return db.update(TABLE_PRODUCT, values, COL_ID_PRODUCT + " = ?", new String[] {product.getCOL_ID_PRODUCT()});

    }

    public Cursor findAll(){
        String mySql = " SELECT * FROM "+TABLE_PRODUCT;
        return db.rawQuery(mySql, null);
    }
}
