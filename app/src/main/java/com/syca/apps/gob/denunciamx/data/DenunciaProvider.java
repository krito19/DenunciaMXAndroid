package com.syca.apps.gob.denunciamx.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import java.util.Arrays;

/**
 * Created by JARP on 10/15/14.
 */
public class DenunciaProvider extends ContentProvider {

    private static final String TAG = DenunciaProvider.class.getSimpleName();

    private DenunciaDatabase mOpenHelper ;

    private static UriMatcher sUriMatcher = buildUriMatcher();

    private static final int USER = 100;
    private static final int USER_TOKEN = 101;
    private static final int USER_MAIL = 102;
    private static final int USER_ID = 103;

    private static final int DENUNCIA = 200;
    private static final int DENUNCIA_ESTADO = 201;
    private static final int DENUNCIA_FECHA_CREACION = 202;
    private static final int DENUNCIA_FECHA_ACTUALIZACION = 203;
    private static final int DENUNCIA_ID_INTERNO = 204;
    private static final int DENUNCIA_ID_SFP = 205;


    private static final int DENUNCIA_EVIDENCIA = 300;
    private static final int DENUNCIA_EVIDENCIA_ID_INTERNO = 301;
    private static final int DENUNCIA_EVIDENCIA_ID_STATUS_S3 = 302;

    private static final int DENUNCIA_HECHO = 400;
    private static final int DENUNCIA_HECHO_ID_INTERNO = 401;

    private static final int DENUNCIA_HISTORIA = 500;
    private static final int DENUNCIA_HISTORIA_ID_INTERNO = 501;

    private static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        final String AUTHORITY = DenunciaContract.CONTENT_AUTHORITY;

        matcher.addURI(AUTHORITY,"user",USER);
        matcher.addURI(AUTHORITY,"user/#",USER_ID);
        matcher.addURI(AUTHORITY,"user/*/mail",USER_MAIL);
        matcher.addURI(AUTHORITY,"user/*/token",USER_TOKEN);

        matcher.addURI(AUTHORITY,"denunciainfo",DENUNCIA);
        matcher.addURI(AUTHORITY,"denunciainfo/*/idInterno",DENUNCIA_ID_INTERNO);
        matcher.addURI(AUTHORITY,"denunciainfo/*/sfp",DENUNCIA_ID_SFP);
        matcher.addURI(AUTHORITY,"denunciainfo/#/estado",DENUNCIA_ESTADO);

        matcher.addURI(AUTHORITY,"evidencias",DENUNCIA_EVIDENCIA);
        matcher.addURI(AUTHORITY,"evidencias/*/idInterno",DENUNCIA_EVIDENCIA_ID_INTERNO);
        matcher.addURI(AUTHORITY,"evidencias/*/idEstado",DENUNCIA_EVIDENCIA_ID_STATUS_S3);

        matcher.addURI(AUTHORITY,"hechos",DENUNCIA_HECHO);
        matcher.addURI(AUTHORITY,"hechos/*/idInterno",DENUNCIA_HECHO_ID_INTERNO);

        matcher.addURI(AUTHORITY,"historia",DENUNCIA_HISTORIA);
        matcher.addURI(AUTHORITY,"historia/*/idInterno",DENUNCIA_HISTORIA_ID_INTERNO);


        return matcher;
    }


    @Override
    public boolean onCreate() {
        mOpenHelper = new DenunciaDatabase(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int match = sUriMatcher.match(uri);

        Log.d(TAG, "uri=" + uri + " match=" + match + " proj=" + Arrays.toString(projection) +
                " selection=" + selection + " args=" + Arrays.toString(selectionArgs) + ")");
        Cursor retCursor;

        switch (match)
        {
            case USER :
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DenunciaContract.UserEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case USER_ID :
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DenunciaContract.UserEntry.TABLE_NAME,
                        projection,
                        DenunciaContract.UserEntry._ID + " ='" + ContentUris.parseId(uri)+"'",
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case USER_MAIL :
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DenunciaContract.UserEntry.TABLE_NAME,
                        projection,
                        DenunciaContract.UserEntry.COLUMN_EMAIL + " ='" + DenunciaContract.UserEntry.getMailFromUri(uri)+"'",
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
               break;
            case USER_TOKEN :
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DenunciaContract.UserEntry.TABLE_NAME,
                        projection,
                        DenunciaContract.UserEntry.COLUMN_TOKEN + " ='" + DenunciaContract.UserEntry.getTokenFromUri(uri)+"'",
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case DENUNCIA :
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DenunciaContract.DenunciaInfoEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case DENUNCIA_ID_SFP :
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DenunciaContract.DenunciaInfoEntry.TABLE_NAME,
                        projection,
                        DenunciaContract.DenunciaInfoEntry.COLUMN_ID_SPF + " ='" + DenunciaContract.DenunciaInfoEntry.getIdSPFFromUri(uri)+"'",
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case DENUNCIA_ID_INTERNO :
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DenunciaContract.DenunciaInfoEntry.TABLE_NAME,
                        projection,
                        DenunciaContract.DenunciaInfoEntry.COLUMN_ID_INTERNO + " ='" + DenunciaContract.DenunciaInfoEntry.getIdInternoFromUri(uri)+"'",
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case DENUNCIA_ESTADO :
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DenunciaContract.DenunciaInfoEntry.TABLE_NAME,
                        projection,
                        DenunciaContract.DenunciaInfoEntry.COLUMN_ESTATUS + " ='" + DenunciaContract.DenunciaInfoEntry.getIdEstadoFromUri(uri)+"'",
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case DENUNCIA_EVIDENCIA :
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DenunciaContract.DenunciaEvidenciaEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case DENUNCIA_EVIDENCIA_ID_INTERNO :
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DenunciaContract.DenunciaEvidenciaEntry.TABLE_NAME,
                        projection,
                        DenunciaContract.DenunciaEvidenciaEntry.COLUMN_ID_INTERNO + " ='" + DenunciaContract.DenunciaEvidenciaEntry.getIdInternoFromUri(uri)+"'",
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case DENUNCIA_EVIDENCIA_ID_STATUS_S3 :
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DenunciaContract.DenunciaEvidenciaEntry.TABLE_NAME,
                        projection,
                        DenunciaContract.DenunciaEvidenciaEntry.COLUMN_ID_ESTATUS_S3 + " ='" + DenunciaContract.DenunciaEvidenciaEntry.getIdEstadoFromUri(uri)+"'",
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case DENUNCIA_HECHO :
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DenunciaContract.DenunciaHechosEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case DENUNCIA_HECHO_ID_INTERNO :
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DenunciaContract.DenunciaHechosEntry.TABLE_NAME,
                        projection,
                        DenunciaContract.DenunciaHechosEntry.COLUMN_ID_INTERNO + " ='" + DenunciaContract.DenunciaHechosEntry.getIdInternoFromUri(uri)+"'",
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case DENUNCIA_HISTORIA :
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DenunciaContract.DenunciaHistoriaEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case DENUNCIA_HISTORIA_ID_INTERNO :
                retCursor = mOpenHelper.getReadableDatabase().query(
                        DenunciaContract.DenunciaHechosEntry.TABLE_NAME,
                        projection,
                        DenunciaContract.DenunciaHechosEntry.COLUMN_ID_INTERNO + " ='" + DenunciaContract.DenunciaHechosEntry.getIdInternoFromUri(uri)+"'",
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri,null);
        return retCursor;
    }

    @Override
    public String getType(Uri uri) {

        switch (sUriMatcher.match(uri))
        {
            case USER:
                return DenunciaContract.UserEntry.CONTENT_TYPE;
            case USER_ID:
                return DenunciaContract.UserEntry.CONTENT_ITEM_TYPE;
            case USER_MAIL:
                return DenunciaContract.UserEntry.CONTENT_ITEM_TYPE;
            case USER_TOKEN:
                return DenunciaContract.UserEntry.CONTENT_ITEM_TYPE;
            case DENUNCIA:
                return DenunciaContract.DenunciaInfoEntry.CONTENT_TYPE;
            case DENUNCIA_ID_SFP:
                return DenunciaContract.DenunciaInfoEntry.CONTENT_ITEM_TYPE;
            case DENUNCIA_ID_INTERNO:
                return DenunciaContract.DenunciaInfoEntry.CONTENT_ITEM_TYPE;
            case DENUNCIA_ESTADO:
                return DenunciaContract.DenunciaInfoEntry.CONTENT_TYPE;
            case DENUNCIA_EVIDENCIA:
                return DenunciaContract.DenunciaEvidenciaEntry.CONTENT_TYPE;
            case DENUNCIA_EVIDENCIA_ID_INTERNO:
                return DenunciaContract.DenunciaEvidenciaEntry.CONTENT_TYPE;
            case DENUNCIA_EVIDENCIA_ID_STATUS_S3:
                return DenunciaContract.DenunciaEvidenciaEntry.CONTENT_TYPE;
            case DENUNCIA_HECHO:
                return DenunciaContract.DenunciaHechosEntry.CONTENT_TYPE;
            case DENUNCIA_HECHO_ID_INTERNO:
                return DenunciaContract.DenunciaHechosEntry.CONTENT_TYPE;
            case DENUNCIA_HISTORIA:
                return DenunciaContract.DenunciaHistoriaEntry.CONTENT_TYPE;
            case DENUNCIA_HISTORIA_ID_INTERNO:
                return DenunciaContract.DenunciaHistoriaEntry.CONTENT_TYPE;
            default:
                throw  new UnsupportedOperationException("Uknown uri" + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG, "insert(uri=" + uri + ", values=" + values.toString() + ")");
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Uri returnUri = null;
        long id;
        switch (sUriMatcher.match(uri))
        {
            case USER:
                id = db.insertOrThrow(DenunciaContract.UserEntry.TABLE_NAME,null,values);
                returnUri = DenunciaContract.UserEntry.buildUserUri(id);
                break;
            case DENUNCIA:
                id = db.insertOrThrow(DenunciaContract.DenunciaInfoEntry.TABLE_NAME,null,values);
                returnUri = DenunciaContract.DenunciaInfoEntry.buildDenunciaUri(id);
                break;
            case DENUNCIA_EVIDENCIA:
                id = db.insertOrThrow(DenunciaContract.DenunciaEvidenciaEntry.TABLE_NAME,null,values);
                returnUri = DenunciaContract.DenunciaEvidenciaEntry.buildEvidenciaUri(id);
                break;
            case DENUNCIA_HECHO:
                id = db.insertOrThrow(DenunciaContract.DenunciaHechosEntry.TABLE_NAME,null,values);
                returnUri = DenunciaContract.DenunciaHechosEntry.buildHechosUri(id);
                break;
            case DENUNCIA_HISTORIA:
                id = db.insertOrThrow(DenunciaContract.DenunciaHistoriaEntry.TABLE_NAME,null,values);
                returnUri = DenunciaContract.DenunciaHistoriaEntry.buildHistoriaUri(id);
                break;
            default: {
                throw new UnsupportedOperationException("Unknown insert uri: " + uri);
            }
        }
        getContext().getContentResolver().notifyChange(uri,null);

        return returnUri;

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(TAG, "delete(uri=" + uri + ")");
        int rowsDeleted = 0;
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        switch (sUriMatcher.match(uri))
        {
            case USER:
                rowsDeleted = db.delete(DenunciaContract.UserEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case DENUNCIA:
                rowsDeleted = db.delete(DenunciaContract.DenunciaInfoEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case DENUNCIA_ID_INTERNO:
                rowsDeleted = db.delete(DenunciaContract.DenunciaInfoEntry.TABLE_NAME,
                                        DenunciaContract.DenunciaInfoEntry.COLUMN_ID_INTERNO + " = ? " ,//selection
                                        new String[] {DenunciaContract.DenunciaInfoEntry.getIdInternoFromUri(uri)}//selectionArgs
                                        );
                break;
            case DENUNCIA_EVIDENCIA:
                rowsDeleted = db.delete(DenunciaContract.DenunciaEvidenciaEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case DENUNCIA_EVIDENCIA_ID_INTERNO:
                rowsDeleted = db.delete(DenunciaContract.DenunciaEvidenciaEntry.TABLE_NAME,
                                        DenunciaContract.DenunciaEvidenciaEntry.COLUMN_ID_INTERNO + " = ? ",//selection
                                        new String [] {DenunciaContract.DenunciaEvidenciaEntry.getIdInternoFromUri(uri)} //SelectionArgs
                                        );
                break;
            case DENUNCIA_EVIDENCIA_ID_STATUS_S3:
                rowsDeleted = db.delete(DenunciaContract.DenunciaEvidenciaEntry.TABLE_NAME,
                                        DenunciaContract.DenunciaEvidenciaEntry.COLUMN_ID_ESTATUS_S3 + " = ? ",//Selection
                                        new String []{DenunciaContract.DenunciaEvidenciaEntry.getIdEstadoFromUri(uri)} //SelectionArgs
                                        );
                break;
            case DENUNCIA_HECHO:
                rowsDeleted = db.delete(DenunciaContract.DenunciaHechosEntry.TABLE_NAME,selection,selectionArgs);
                break;

            case DENUNCIA_HECHO_ID_INTERNO:
                rowsDeleted = db.delete(DenunciaContract.DenunciaHechosEntry.TABLE_NAME,
                                        DenunciaContract.DenunciaHechosEntry.COLUMN_ID_INTERNO + " = ? ",//Selection
                                        new String [] {DenunciaContract.DenunciaHechosEntry.getIdInternoFromUri(uri)}//selectionArgs
                                        );
                break;

            case DENUNCIA_HISTORIA:
                rowsDeleted = db.delete(DenunciaContract.DenunciaHistoriaEntry.TABLE_NAME, selection, selectionArgs);
                break;

            case DENUNCIA_HISTORIA_ID_INTERNO:
                rowsDeleted = db.delete(DenunciaContract.DenunciaHistoriaEntry.TABLE_NAME,
                                        DenunciaContract.DenunciaHechosEntry.COLUMN_ID_INTERNO + " = ? " , //selection
                                        new String []{ DenunciaContract.DenunciaHechosEntry.getIdInternoFromUri(uri)}//selectionArgs
                                        );
                break;
            default: {
                throw new UnsupportedOperationException("Unknown insert uri: " + uri);
            }
        }
        getContext().getContentResolver().notifyChange(uri,null);

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.d(TAG, "update(uri=" + uri + ", values=" + values.toString());
        int rowsUpdated = 0;
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        switch (sUriMatcher.match(uri)) {
            case USER_ID :
                    rowsUpdated = db.update(DenunciaContract.UserEntry.TABLE_NAME,
                                            values,
                                            DenunciaContract.UserEntry._ID + " = ?",
                                            new String[]{ String.valueOf(ContentUris.parseId(uri))});
                break;
            case DENUNCIA_ID_INTERNO :
                    rowsUpdated = db.update(DenunciaContract.UserEntry.TABLE_NAME,
                                            values,
                                            DenunciaContract.DenunciaInfoEntry.COLUMN_ID_INTERNO+ " = ?",
                                            new String[]{DenunciaContract.DenunciaInfoEntry.getIdInternoFromUri(uri)});
                break;
            default: {
                throw new UnsupportedOperationException("Unknown insert uri: " + uri);
            }

        }
        getContext().getContentResolver().notifyChange(uri,null);
        return rowsUpdated;
    }
}

