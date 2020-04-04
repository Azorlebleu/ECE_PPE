package com.example.travhelp;
import android.provider.BaseColumns;

public class SelectionContract {

    private SelectionContract() {}

    //Acts like a declaration of the table.
    public static class SelectionEntry implements BaseColumns {
        public static final String TABLE_NAME = "selection";
        public static final String _ID = "id";
        public static final String COLUMN_NAME_time_min = "time_min";
        public static final String COLUMN_NAME_time_max = "time_max";
        public static final String COLUMN_NAME_duration = "duration";
        public static final String COLUMN_NAME_name = "name";
        public static final String COLUMN_NAME_surname = "surname";
        public static final String COLUMN_NAME_address = "address";


    }
}
