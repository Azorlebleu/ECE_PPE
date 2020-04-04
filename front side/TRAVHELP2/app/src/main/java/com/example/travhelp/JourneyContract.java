package com.example.travhelp;
import android.provider.BaseColumns;

public class JourneyContract {

    private JourneyContract() {}

    //Acts like a declaration of the table.
    public static class JourneyEntry implements BaseColumns {
        public static final String TABLE_NAME = "journey";
        public static final String _ID = "id";
        public static final String COLUMN_NAME_name = "name";
        public static final String COLUMN_NAME_surname = "surname";
        public static final String COLUMN_NAME_address = "address";
        public static final String COLUMN_NAME_time = "time";
        public static final String COLUMN_NAME_duration = "duration";
        public static final String COLUMN_NAME_driving_time = "driving_time";


    }
}
