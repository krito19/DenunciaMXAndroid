package com.syca.apps.gob.denunciamx.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by JARP on 10/15/14.
 */
public class DenunciaDatabase extends SQLiteOpenHelper {


    public final static String DATABASE_NAME="denunciamx";

    public final static int DATA_VERSION=1;

    public DenunciaDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATA_VERSION);
    }
    public DenunciaDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Username
        db.execSQL("CREATE TABLE " + DenunciaContract.UserEntry.TABLE_NAME + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DenunciaContract.UserEntry.COLUMN_TOKEN + " TEXT,"
                + DenunciaContract.UserEntry.COLUMN_EMAIL + " TEXT,"
                + DenunciaContract.UserEntry.COLUMN_NAME + " TEXT,"
                + DenunciaContract.UserEntry.COLUMN_ADDRESS + " TEXT,"
                + "UNIQUE ("+DenunciaContract.UserEntry.COLUMN_TOKEN +") ON CONFLICT REPLACE) " );

        //DenunciaInfo
        db.execSQL("CREATE TABLE " + DenunciaContract.DenunciaInfoEntry.TABLE_NAME + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DenunciaContract.DenunciaInfoEntry.COLUMN_ANONIMA + " INTEGER NOT NULL,"
                + DenunciaContract.DenunciaInfoEntry.COLUMN_COORD_LAT + " REAL,"
                + DenunciaContract.DenunciaInfoEntry.COLUMN_COORD_LONG + " REAL,"
                + DenunciaContract.DenunciaInfoEntry.COLUMN_EMAIL + " TEXT,"
                + DenunciaContract.DenunciaInfoEntry.COLUMN_ESTATUS + " INTEGER NOT NULL,"
                + DenunciaContract.DenunciaInfoEntry.COLUMN_FECHA_ACTUALIZACION + " TEXT NOT NULL,"
                + DenunciaContract.DenunciaInfoEntry.COLUMN_FECHA_CREACION + " TEXT NOT NULL,"
                + DenunciaContract.DenunciaInfoEntry.COLUMN_ID_DEPENDENCIA + " TEXT ,"
                + DenunciaContract.DenunciaInfoEntry.COLUMN_ID_INTERNO + " TEXT NOT NULL,"
                + DenunciaContract.DenunciaInfoEntry.COLUMN_ID_SPF + " TEXT ,"
                + DenunciaContract.DenunciaInfoEntry.COLUMN_TITULO + " TEXT NOT NULL,"
                + DenunciaContract.DenunciaInfoEntry.COLUMN_TOKEN + " TEXT ,"
                + "UNIQUE ("+DenunciaContract.DenunciaInfoEntry.COLUMN_ID_INTERNO +") ON CONFLICT REPLACE) " );

        //DenunciaEvidencia
        db.execSQL("CREATE TABLE " + DenunciaContract.DenunciaEvidenciaEntry.TABLE_NAME + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DenunciaContract.DenunciaEvidenciaEntry.COLUMN_FULL_PATH + " TEXT NOT NULL,"
                + DenunciaContract.DenunciaEvidenciaEntry.COLUMN_ID_ESTATUS_S3 + " INTEGER NOT NULL,"
                + DenunciaContract.DenunciaEvidenciaEntry.COLUMN_ID_INTERNO + " TEXT NOT NULL,"
                + DenunciaContract.DenunciaEvidenciaEntry.COLUMN_PATH_S3 + " TEXT NOT NULL,"
                + DenunciaContract.DenunciaEvidenciaEntry.COLUMN_TYPE + " TEXT NOT NULL,"
                + DenunciaContract.DenunciaEvidenciaEntry.COLUMN_URI + " TEXT NOT NULL,"
                + "UNIQUE ("+ DenunciaContract.DenunciaEvidenciaEntry.COLUMN_ID_INTERNO + ","
                            + DenunciaContract.DenunciaEvidenciaEntry.COLUMN_URI  +") ON CONFLICT REPLACE) " );

        //DenunciaHecho
        db.execSQL("CREATE TABLE " + DenunciaContract.DenunciaHechosEntry.TABLE_NAME + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DenunciaContract.DenunciaHechosEntry.COLUMN_ID_INTERNO + " TEXT NOT NULL,"
                + DenunciaContract.DenunciaHechosEntry.COLUMN_ID_PREGUNTA + " INTEGER NOT NULL,"
                + DenunciaContract.DenunciaHechosEntry.COLUMN_RESPUESTA + " TEXT,"
                + "UNIQUE ("+ DenunciaContract.DenunciaHechosEntry.COLUMN_ID_INTERNO + ","
                + DenunciaContract.DenunciaHechosEntry.COLUMN_ID_PREGUNTA  +") ON CONFLICT REPLACE) " );

        //DenunciaHistoria
        db.execSQL("CREATE TABLE " + DenunciaContract.DenunciaHistoriaEntry.TABLE_NAME + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DenunciaContract.DenunciaHistoriaEntry.COLUMN_FECHA + " TEXT NOT NULL,"
                + DenunciaContract.DenunciaHistoriaEntry.COLUMN_ID_ESTATUS + " INTEGER NOT NULL,"
                + DenunciaContract.DenunciaHistoriaEntry.COLUMN_ID_INTERNO + " TEXT NOT NULL,"
                + DenunciaContract.DenunciaHistoriaEntry.COLUMN_MENSAJE + " TEXT "
                + ")" );
        

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}


















