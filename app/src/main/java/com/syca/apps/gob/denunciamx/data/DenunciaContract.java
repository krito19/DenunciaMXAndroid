package com.syca.apps.gob.denunciamx.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by JARP on 10/14/14.
 */
public class DenunciaContract {

    public static final String CONTENT_AUTHORITY="com.syca.apps.gob.denunciamx";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final String PATH_USER = "user";
    public static final String PATH_DENUNCIA_INFO = "denunciainfo";
    public static final String PATH_DENUNCIA_EVIDENCIA = "evidencias";
    public static final String PATH_DENUNCIA_HECHOS = "hechos";
    public static final String PATH_DENUNCIA_HISTORIA = "historia";

    // Format used for storing dates in the database.  ALso used for converting those strings
    // back into date objects for comparison/processing.
    public static final String DATE_FORMAT = "yyyyMMdd";

    /**
     * Converts Date class to a string representation, used for easy comparison and database lookup.
     * @param date The input date
     * @return a DB-friendly representation of the date, using the format defined in DATE_FORMAT.
     */
    public static String getDbDateString(Date date){
        // Because the API returns a unix timestamp (measured in seconds),
        // it must be converted to milliseconds in order to be converted to valid date.
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(date);
    }

    /**
     * Converts a dateText to a long Unix time representation
     * @param dateText the input date string
     * @return the Date object
     */
    public static Date getDateFromDb(String dateText) {
        SimpleDateFormat dbDateFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
            return dbDateFormat.parse(dateText);
        } catch ( ParseException e ) {
            e.printStackTrace();
            return null;
        }
    }

    public static class UserEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_USER;

        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_USER;

        public static final String TABLE_NAME="user";
        public static final String COLUMN_NAME="name";
        public static final String COLUMN_TOKEN="token";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_EMAIL = "email";

        public static Uri buildUserUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildUserAndTokenUri(String token) {
            return CONTENT_URI.buildUpon().appendPath(token).appendPath("token").build();
        }

        public static String getTokenFromUri(Uri uri)
        {
            return uri.getPathSegments().get(1);
        }

        public static Uri buildUserAndEmailUri(String mail) {
            return CONTENT_URI.buildUpon().appendPath(mail).appendPath("mail").build();
        }

        public static String getMailFromUri(Uri uri)
        {
            return uri.getPathSegments().get(1);
        }


    }

    public static class DenunciaInfoEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_DENUNCIA_INFO).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_DENUNCIA_INFO;

        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_DENUNCIA_INFO;

        public static final String TABLE_NAME="denuncia";
        public static final String COLUMN_TITULO="titulo";
        public static final String COLUMN_ID_INTERNO="id_interno";
        public static final String COLUMN_ID_SPF="id_spf";
        public static final String COLUMN_TOKEN="token";
        public static final String COLUMN_ANONIMA = "anonima";
        public static final String COLUMN_ID_DEPENDENCIA = "dependencia";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_COORD_LAT = "lat";
        public static final String COLUMN_COORD_LONG = "long";
        public static final String COLUMN_FECHA_CREACION = "fecha_creacion";
        public static final String COLUMN_FECHA_ACTUALIZACION = "fecha_actualizacion";
        public static final String COLUMN_ESTATUS = "estatus";

        public static Uri buildDenunciaUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildDenunciaAndIdInternoUri(String idInterno) {
            return CONTENT_URI.buildUpon().appendPath(idInterno).appendPath("idInterno").build();
        }

        public static String getIdInternoFromUri(Uri uri)
        {
            return uri.getPathSegments().get(1);
        }

        public static Uri buildDenunciaAndEstadoUri(String idEstado) {
            return CONTENT_URI.buildUpon().appendPath(idEstado).appendPath("estado").build();
        }

        public static String getIdEstadoFromUri(Uri uri)
        {
            return uri.getPathSegments().get(1);
        }

        public static Uri buildDenunciaAndFechaActualizacionUri(String fechaActualizacion) {
            return CONTENT_URI.buildUpon().appendQueryParameter(COLUMN_FECHA_ACTUALIZACION,fechaActualizacion).build();
        }

        public static String getFechaActualizacionFromUri(Uri uri)
        {
            return uri.getQueryParameter(COLUMN_FECHA_ACTUALIZACION);
        }

        public static Uri buildDenunciaAndFechaCreacionUri(String fechaCreacion) {
            return CONTENT_URI.buildUpon().appendQueryParameter(COLUMN_FECHA_CREACION,fechaCreacion).build();
        }

        public static String getFechaCreacionFromUri(Uri uri)
        {
            return uri.getQueryParameter(COLUMN_FECHA_CREACION);
        }


        public static Uri buildDenunciaAndIdSFPUri(String idSFP) {
            return CONTENT_URI.buildUpon().appendPath(idSFP).appendPath("sfp").build();
        }

        public static String getIdSPFFromUri(Uri uri)
        {
            return uri.getPathSegments().get(1);
        }



    }

    public static class DenunciaEvidenciaEntry implements BaseColumns
    {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_DENUNCIA_EVIDENCIA).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_DENUNCIA_EVIDENCIA;

        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_DENUNCIA_EVIDENCIA;

        public static final String TABLE_NAME="evidencia";
        public static final String COLUMN_TYPE="type";
        public static final String COLUMN_ID_INTERNO="id_interno";
        public static final String COLUMN_ID_ESTATUS_S3="s3_status";
        public static final String COLUMN_FULL_PATH = "path";
        public static final String COLUMN_URI = "uri";
        public static final String COLUMN_PATH_S3 = "s3_path";

        public static Uri buildEvidenciaUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildDenunciaWithIdInternoUri(String idInterno)
        {
            return CONTENT_URI.buildUpon().appendPath(idInterno).appendPath("idInterno").build();
        }

        public static String getIdInternoFromUri(Uri uri)
        {
            return uri.getPathSegments().get(1);
        }


        public static Uri buildDenunciaWithEstadoS3Uri(String idEstado)
        {
            return CONTENT_URI.buildUpon().appendPath(idEstado).appendPath("idEstado").build();
        }

        public static String getIdEstadoFromUri(Uri uri)
        {
            return uri.getPathSegments().get(1);
        }

    }

    public static class DenunciaHechosEntry implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_DENUNCIA_HECHOS).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_DENUNCIA_HECHOS;

        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_DENUNCIA_HECHOS;

        public static final String TABLE_NAME="hechos";
        public static final String COLUMN_ID_INTERNO="id_interno";
        public static final String COLUMN_ID_PREGUNTA="id_pregunta";
        public static final String COLUMN_RESPUESTA= "respuesta";

        public static Uri buildHechosUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildHechoWithIdInternoUri(String idInterno)
        {
            return CONTENT_URI.buildUpon().appendPath(idInterno).appendPath("idInterno").build();
        }

        public static String getIdInternoFromUri(Uri uri)
        {
            return uri.getPathSegments().get(1);
        }
    }

    public static class DenunciaHistoriaEntry implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_DENUNCIA_HISTORIA).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_DENUNCIA_HISTORIA;

        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_DENUNCIA_HISTORIA;

        public static final String TABLE_NAME="historia";
        public static final String COLUMN_ID_INTERNO="id_interno";
        public static final String COLUMN_FECHA="fecha";
        public static final String COLUMN_ID_ESTATUS="estatus";
        public static final String COLUMN_MENSAJE= "mensaje";

        public static Uri buildHistoriaUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildDenunciaWithIdInternoUri(String idInterno)
        {
            return CONTENT_URI.buildUpon().appendPath(idInterno).build();
        }

        public static String getIdInternoFromUri(Uri uri)
        {
            return uri.getPathSegments().get(1);
        }

    }


}
