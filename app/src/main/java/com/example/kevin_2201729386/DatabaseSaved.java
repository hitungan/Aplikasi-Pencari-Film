package com.example.kevin_2201729386;

import android.provider.BaseColumns;

public class DatabaseSaved {

    private DatabaseSaved(){

    }

    public static final class databaseSaved implements BaseColumns{
        public static final String TABLE_NAME = "SavedMovies";
        public static final String COLOUMN_JUDUL = "JudulMovies";
        public static final String COLOUMN_YEAR = "TahunMovies";
        public static final String COLOUMN_IDMOV = "IDMovies";
        public static final String COLOUMN_LINKGAM = "LinkGambar";
        public static final String COLOUMN_TIMESTAMP = "TIMESTAMP";

    }
}
