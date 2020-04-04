package com.example.travhelp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//Create the Database from the information stored in the contract
public class JourneyDbHelper extends SQLiteOpenHelper {
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + JourneyContract.JourneyEntry.TABLE_NAME + " (" +
                    JourneyContract.JourneyEntry._ID + " INTEGER PRIMARY KEY," +
                    JourneyContract.JourneyEntry.COLUMN_NAME_name + " TEXT," +
                    JourneyContract.JourneyEntry.COLUMN_NAME_surname + " TEXT," +
                    JourneyContract.JourneyEntry.COLUMN_NAME_address + " TEXT," +
                    JourneyContract.JourneyEntry.COLUMN_NAME_time + " TEXT," +
                    JourneyContract.JourneyEntry.COLUMN_NAME_duration + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + JourneyContract.JourneyEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "journey.db";

    public JourneyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        System.out.println("On create called");
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        System.out.println("On upgrade called");
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
