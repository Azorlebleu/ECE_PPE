package com.example.travhelp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PatientsDbHelper extends SQLiteOpenHelper {
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PatientsContract.PatientsEntry.TABLE_NAME + " (" +
                    PatientsContract.PatientsEntry._ID + " INTEGER PRIMARY KEY," +
                    PatientsContract.PatientsEntry.COLUMN_NAME_name + " TEXT," +
                    PatientsContract.PatientsEntry.COLUMN_NAME_surname + " TEXT," +
                    PatientsContract.PatientsEntry.COLUMN_NAME_address + " TEXT," +
                    PatientsContract.PatientsEntry.COLUMN_NAME_email + " TEXT," +
                    PatientsContract.PatientsEntry.COLUMN_NAME_data + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PatientsContract.PatientsEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Patients.db";

    public PatientsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        System.out.println("COnstructor called");

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
