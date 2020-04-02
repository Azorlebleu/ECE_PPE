package com.example.travhelp;

import android.provider.BaseColumns;

public class PatientsContract {

    private PatientsContract() {}

        //Acts like a declaration of the table.
    public static class PatientsEntry implements BaseColumns {
        public static final String TABLE_NAME = "patients";
        public static final String _ID = "id";
        public static final String COLUMN_NAME_name = "name";
        public static final String COLUMN_NAME_surname = "surname";
        public static final String COLUMN_NAME_address = "address";
        public static final String COLUMN_NAME_email = "email";
        public static final String COLUMN_NAME_data = "data";
        public static final String COLUMN_NAME_phone = "phone";
    }
}
