package com.syca.apps.gob.denunciamx;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import com.syca.apps.gob.denunciamx.data.DenunciaContract;
import com.syca.apps.gob.denunciamx.data.DenunciaDatabase;
import com.syca.apps.gob.denunciamx.model.EstatusDenuncia;
import com.syca.apps.gob.denunciamx.model.Estatus_S3;
import com.syca.apps.gob.denunciamx.model.EvidenciaHechoPreguntas;
import com.syca.apps.gob.denunciamx.model.EvidenciaMediaType;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by JARP on 10/15/14.
 */
public class TestDB extends AndroidTestCase {

    public static final String TAG = TestDB.class.getSimpleName();

    public static String token = UUID.randomUUID().toString();

    public static String id_denuncia = UUID.randomUUID().toString();


    public void testCreateDB() throws Throwable
    {
        mContext.deleteDatabase(DenunciaDatabase.DATABASE_NAME);
        SQLiteDatabase database = new DenunciaDatabase(mContext,DenunciaDatabase.DATABASE_NAME,null,DenunciaDatabase.DATA_VERSION)
                                      .getWritableDatabase();

        assertEquals(true,database.isOpen());

        database.close();
    }



    public void testInsertReadDB()
    {

        DenunciaDatabase database = new DenunciaDatabase(mContext);

        SQLiteDatabase db = database.getWritableDatabase();

        //User is inserted and get back

        ContentValues userValues =createAccountUserValues();

        long idUser = db.insert(DenunciaContract.UserEntry.TABLE_NAME,null,userValues);

        assertTrue(idUser != -1);

        Log.d(TAG,"User row id "+ idUser);

        Cursor valueCursor = db.query(DenunciaContract.UserEntry.TABLE_NAME,
                                       null,
                                       null,
                                       null,
                                       null,
                                       null,
                                       null);

        validateCursor(valueCursor,userValues);

        //DenunciaInfo insert and get back

        ContentValues denunciaInfoValues = createDenunciaInfoValues(token,
                                                                    EstatusDenuncia.DENUNCIA_EN_PROCESO,
                                                                    DenunciaContract.getDbDateString(new Date()),
                                                                    DenunciaContract.getDbDateString(new Date()),
                                                                    id_denuncia,
                                                                    UUID.randomUUID().toString());



        long idDenunciaInfo = db.insert(DenunciaContract.DenunciaInfoEntry.TABLE_NAME,null,denunciaInfoValues);

        assertTrue(idDenunciaInfo != -1);

        Log.d(TAG,"Denuncia row id "+ idDenunciaInfo);

        valueCursor =  db.query(DenunciaContract.DenunciaInfoEntry.TABLE_NAME,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null);

        validateCursor(valueCursor,denunciaInfoValues);

        ContentValues fileEvidenciaValues =
                createDenunciaEvidenciaMediaValues(id_denuncia, "fullPathFile", Estatus_S3.DONE,
                        "/some/path/in/s3", EvidenciaMediaType.FOTO,
                        "content://file//storage//emul");

        long idFileEvidencia = db.insert(DenunciaContract.DenunciaEvidenciaEntry.TABLE_NAME,null,fileEvidenciaValues);


        assertTrue(-1!=idFileEvidencia);

        valueCursor = db.query(DenunciaContract.DenunciaEvidenciaEntry.TABLE_NAME,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null);

        validateCursor(valueCursor,fileEvidenciaValues);


        //DenunciaHecho

        ContentValues hechoValues = createHechoValues(id_denuncia, EvidenciaHechoPreguntas.PREGUNTA_COMO,"Fue en por ahi de ahi ");

        long idHecho = db.insert(DenunciaContract.DenunciaHechosEntry.TABLE_NAME,null,hechoValues);

        assertTrue(-1!=idHecho);

        valueCursor = db.query(DenunciaContract.DenunciaHechosEntry.TABLE_NAME,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null);

        validateCursor(valueCursor,hechoValues);


        //DenunciaHistoria

        ContentValues historiaValues = createHistoriaValues(id_denuncia,DenunciaContract.getDbDateString(new Date()),
                                                            EstatusDenuncia.DENUNCIA_ENVIADA,"Denuncia procesada con algun mensaje");

        long idHistoria = db.insert(DenunciaContract.DenunciaHistoriaEntry.TABLE_NAME,null,historiaValues);

        assertTrue(-1!=idHistoria);

        valueCursor = db.query(DenunciaContract.DenunciaHistoriaEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        validateCursor(valueCursor,historiaValues);




        valueCursor= db.query(DenunciaContract.DependenciaEntry.TABLE_NAME,null,null,null,null,null,null);
        assertEquals(DenunciaContract.DependenciaEntry.getDependencias().size(),valueCursor.getCount());
        db.close();
    }


    static ContentValues createDependenciaValues(int _id, int idDependencia, String dependencia) {

        ContentValues cv = new ContentValues();
        cv.put(DenunciaContract.DependenciaEntry._ID,_id);
        cv.put(DenunciaContract.DependenciaEntry.COLUMN_ID_DEPENDENCIA,idDependencia);
        cv.put(DenunciaContract.DependenciaEntry.COLUMN_DEPENDENCIA,dependencia);
        return cv;

    }

    static ContentValues createAccountUserValues()
    {
        ContentValues cv = new ContentValues();
        cv.put(DenunciaContract.UserEntry.COLUMN_ADDRESS,"Address JARP");
        cv.put(DenunciaContract.UserEntry.COLUMN_EMAIL,"jarp@gmail.com");
        cv.put(DenunciaContract.UserEntry.COLUMN_NAME,"JARP");
        cv.put(DenunciaContract.UserEntry.COLUMN_TOKEN, token);
        return cv;
    }

    static ContentValues createDenunciaInfoValues(String token,int estatus,
                                              String fechaCreacion,String fechaActualizacion,
                                              String idDenuncia,String idSPF)
    {
        ContentValues cv = new ContentValues();
        cv.put(DenunciaContract.DenunciaInfoEntry.COLUMN_ANONIMA,0);
        cv.put(DenunciaContract.DenunciaInfoEntry.COLUMN_COORD_LAT,1);
        cv.put(DenunciaContract.DenunciaInfoEntry.COLUMN_COORD_LONG,2);
        cv.put(DenunciaContract.DenunciaInfoEntry.COLUMN_FECHA_ACTUALIZACION,fechaActualizacion);
        cv.put(DenunciaContract.DenunciaInfoEntry.COLUMN_FECHA_CREACION,fechaCreacion);
        cv.put(DenunciaContract.DenunciaInfoEntry.COLUMN_EMAIL,"imjarp@gmail.com");
        cv.put(DenunciaContract.DenunciaInfoEntry.COLUMN_ESTATUS,estatus);
        cv.put(DenunciaContract.DenunciaInfoEntry.COLUMN_ID_DEPENDENCIA,111);
        cv.put(DenunciaContract.DenunciaInfoEntry.COLUMN_ID_INTERNO,idDenuncia);
        cv.put(DenunciaContract.DenunciaInfoEntry.COLUMN_ID_SPF,idSPF);
        cv.put(DenunciaContract.DenunciaInfoEntry.COLUMN_TITULO,"Some nice title");
        cv.put(DenunciaContract.DenunciaInfoEntry.COLUMN_TOKEN,token);
        return cv;
    }

    static ContentValues createDenunciaEvidenciaMediaValues(String idInterno, String fullPath,
                                                            int idEstatusS3, String s3Path,
                                                            int typeFile, String uriFile)
    {
        ContentValues cv = new ContentValues();
        cv.put(DenunciaContract.DenunciaEvidenciaEntry.COLUMN_FULL_PATH,fullPath);
        cv.put(DenunciaContract.DenunciaEvidenciaEntry.COLUMN_ID_ESTATUS_S3,idEstatusS3);
        cv.put(DenunciaContract.DenunciaEvidenciaEntry.COLUMN_ID_INTERNO,idInterno);
        cv.put(DenunciaContract.DenunciaEvidenciaEntry.COLUMN_PATH_S3,s3Path);
        cv.put(DenunciaContract.DenunciaEvidenciaEntry.COLUMN_TYPE,typeFile);
        cv.put(DenunciaContract.DenunciaEvidenciaEntry.COLUMN_URI,uriFile);

        return cv;

    }

    static ContentValues createHechoValues(String idInterno,int idPregunta,String respuesta) {

        ContentValues cv = new ContentValues();
        cv.put(DenunciaContract.DenunciaHechosEntry.COLUMN_ID_INTERNO,idInterno);
        cv.put(DenunciaContract.DenunciaHechosEntry.COLUMN_ID_PREGUNTA,idPregunta);
        cv.put(DenunciaContract.DenunciaHechosEntry.COLUMN_RESPUESTA,respuesta);

        return cv;

    }

    static ContentValues createHistoriaValues(String idInterno, String fecha, int idEstatus,String mensaje) {

        ContentValues cv = new ContentValues();
        cv.put(DenunciaContract.DenunciaHistoriaEntry.COLUMN_ID_INTERNO,idInterno);
        cv.put(DenunciaContract.DenunciaHistoriaEntry.COLUMN_FECHA,fecha);
        cv.put(DenunciaContract.DenunciaHistoriaEntry.COLUMN_ID_ESTATUS,idEstatus);
        cv.put(DenunciaContract.DenunciaHistoriaEntry.COLUMN_MENSAJE,mensaje);

        return cv;

    }





    static void validateCursor(Cursor valueCursor, ContentValues expectedValues) {

        assertTrue(valueCursor.moveToFirst());

        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse(idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals(expectedValue, valueCursor.getString(idx));
        }
        valueCursor.close();
    }


}
