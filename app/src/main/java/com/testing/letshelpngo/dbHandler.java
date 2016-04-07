package com.testing.letshelpngo;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;

public class dbHandler extends SQLiteOpenHelper {

    private static final int databaseVersion = 1;
    private static final String databaseName = "ngoDatabase.db";
    public static final String tableNgo = "Tablengos";
    public static final String column_id = "_id";
    public static final String column_desc = "_description";
    public static final String column_activity = "_activity";
    public static final String column_contact = "_contact";
    public static final String column_name = "_name";

    public dbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE  " + tableNgo + "(" +
                column_id + " INTEGER PRIMARY KEY ," +
                column_name + " TEXT ," +
                column_desc + " TEXT ," +
                column_activity + " TEXT ," +
                column_contact + " TEXT " +

                ");";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //db.execSQL("DROP TABLE IF EXISTS " + tableNgo);
        // onCreate(db);

    }


    public void add_ngo(ngoDatabse tuple) {
        ContentValues values = new ContentValues();
        values.put(column_id, tuple.get_id());
        values.put(column_name, tuple.get_name());
        values.put(column_desc, tuple.get_description());
        values.put(column_activity, tuple.get_activity());
        values.put(column_contact, tuple.get_contact());
        SQLiteDatabase db = getWritableDatabase();


        //Inserting in Database

        db.insert(tableNgo, null, values);

        //checking Entry of Database
        String query = "SELECT * FROM " + tableNgo + " WHERE " + column_id + " = " + tuple.get_id() + " ";
        Cursor c = db.rawQuery(query, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            Log.d(" After Adding Data...", c.getString(1));
        }

        db.close();
    }

    public void update(ngoDatabse tuple) {

        ContentValues values = new ContentValues();
        values.put(column_name, tuple.get_name());
        values.put(column_desc, tuple.get_description());
        values.put(column_activity, tuple.get_activity());
        values.put(column_contact, tuple.get_contact());
        SQLiteDatabase db = getWritableDatabase();
        db.update(tableNgo, values, "_id = " + tuple.get_id(), null);
        db.close();

    }

    public boolean checkUpdated(ngoDatabse tuple) {

        SQLiteDatabase db = getWritableDatabase();
        String s;
       // String query_name = "SELECT " + column_name + " FROM " + tableNgo + " WHERE " + column_id + " = " + tuple.get_id() + " ";
       // String query_desc = "SELECT " + column_desc + " FROM " + tableNgo + " WHERE " + column_id + "=" + tuple.get_id() + " ";
        String query_activity = "SELECT " + column_activity + " FROM " + tableNgo + " WHERE " + column_id + " = " + tuple.get_id() + " ";
        //String query_contact = "SELECT " + column_contact + " FROM " + tableNgo + " WHERE " + column_id + " = " + tuple.get_id() + " ";

        Cursor c;
      /*   if (c.getCount() > 0) {
            c.moveToFirst();
            s = c.getString(0);
            if (s != tuple.get_name())
                return true;
        }

        c = db.rawQuery(query_desc, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            s = c.getString(0);
            if (s != tuple.get_description())
                return true;
        }*/
        c = db.rawQuery(query_activity, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            s = c.getString(0);

            if (!s.equals(tuple.get_activity())) {
                return true;
            }
        }
        /*c = db.rawQuery(query_contact, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            s = c.getString(0);
            if (s != tuple.get_contact())
                return true;
        }*/
        db.close();
        return false;
    }

    public boolean checkNotAvailable(ngoDatabse tuple) {

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + tableNgo + " WHERE " + column_id + " = " + tuple.get_id() + " ";

        Cursor c = db.rawQuery(query, null);
        if (c.getCount() == 0) {
            return true;
        }
        db.close();
        return false;

    }

}



