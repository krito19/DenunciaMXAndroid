package com.syca.apps.gob.denunciamx;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;

import com.syca.apps.gob.denunciamx.data.DenunciaContract;
import com.syca.apps.gob.denunciamx.model.EstatusDenuncia;
import com.syca.apps.gob.denunciamx.model.EvidenciaHechoPreguntas;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Created by JARP on 10/16/14.
 */
public class TestProvider extends AndroidTestCase {

    /*

        ################
        To pull database if is rooted use

        ./adb pull /data/data/com.syca.apps.gob.denunciamx/databases/denunciamx

        ##############

     */

    public static final String TAG = TestDB.class.getSimpleName();

    public static String token = UUID.randomUUID().toString();

    public static String id_denuncia_1 = UUID.randomUUID().toString();

    public static String id_denuncia_2 = UUID.randomUUID().toString();

    public void deleteAllRecords()
    {
        //User
        mContext.getContentResolver().delete(DenunciaContract.UserEntry.CONTENT_URI,
                                                null,
                                                null);

        //Denuncia Historia
        mContext.getContentResolver().delete(DenunciaContract.DenunciaHistoriaEntry.CONTENT_URI,
                null,
                null);
        //Denuncia Hechos
        mContext.getContentResolver().delete(DenunciaContract.DenunciaHechosEntry.CONTENT_URI,
                null,
                null);
        //Denuncia Evidencia
        mContext.getContentResolver().delete(DenunciaContract.DenunciaEvidenciaEntry.CONTENT_URI,
                null,
                null);
        //Denuncia Info
        mContext.getContentResolver().delete(DenunciaContract.DenunciaInfoEntry.CONTENT_URI,
                null,
                null);
    }

    public void setUp(){
        deleteAllRecords();
    }

    public void testInsertReadProvider()
    {
        //In this case first we will create one Denuncia with:
        // - Evidencia Info Entry
        // - All the questions [6]  ref EvidenciaHechoPreguntas
        // - Evidencia [6]  [2] audio [2] photo [2] audio
        // - Create the first element of the history

        //Step one create DenuciaInfo

        ContentResolver contentResolver = mContext.getContentResolver();

        String today = DenunciaContract.getDbDateString(new Date());
        ContentValues contentValues = TestDB.createDenunciaInfoValues(token,
                                                                     EstatusDenuncia.DENUNCIA_EN_PROCESO,
                                                                     today ,
                                                                     today,
                                                                     id_denuncia_1,
                                                                     "");



        Uri insertedDenunciaUri= contentResolver.insert(DenunciaContract.DenunciaInfoEntry.CONTENT_URI,contentValues);

        long _idDenunciaInfo = ContentUris.parseId(insertedDenunciaUri);

        assertTrue(-1!=_idDenunciaInfo);

        Uri uriIdInterno = DenunciaContract.DenunciaInfoEntry.buildDenunciaAndIdInternoUri(id_denuncia_1);

        Cursor cursor = contentResolver.query(uriIdInterno,
                                              null,
                                              null,
                                              null,
                                              null);

        cursor.moveToFirst();

        assertTrue(1 == cursor.getCount());

        TestDB.validateCursor(cursor,contentValues);

        //preguntas

        ArrayList<ContentValues> preguntas = new ArrayList<ContentValues>();

        //Pregunta {1}
        contentValues = TestDB.createHechoValues(id_denuncia_1, EvidenciaHechoPreguntas.PREGUNTA_COMO,"COMO");
        preguntas.add(contentValues);
        Uri preguntaUri= contentResolver.insert(DenunciaContract.DenunciaHechosEntry.CONTENT_URI,contentValues);
        long _iPregunta = ContentUris.parseId(preguntaUri);
        assertTrue(-1!=_iPregunta);

        //Pregunta {2}
        contentValues = TestDB.createHechoValues(id_denuncia_1, EvidenciaHechoPreguntas.PREGUNTA_CUANDO,"Cuando");
        preguntas.add(contentValues);
        preguntaUri= contentResolver.insert(DenunciaContract.DenunciaHechosEntry.CONTENT_URI,contentValues);
        _iPregunta = ContentUris.parseId(preguntaUri);
        assertTrue(-1!=_iPregunta);

        //Pregunta {3}
        contentValues = TestDB.createHechoValues(id_denuncia_1, EvidenciaHechoPreguntas.PREGUNTA_DONDE,"");
        preguntas.add(contentValues);
        preguntaUri= contentResolver.insert(DenunciaContract.DenunciaHechosEntry.CONTENT_URI,contentValues);
        _iPregunta = ContentUris.parseId(preguntaUri);
        assertTrue(-1!=_iPregunta);

        //Pregunta {4}
        contentValues = TestDB.createHechoValues(id_denuncia_1, EvidenciaHechoPreguntas.PREGUNTA_QUE,"Que");
        preguntas.add(contentValues);
        preguntaUri= contentResolver.insert(DenunciaContract.DenunciaHechosEntry.CONTENT_URI,contentValues);
        _iPregunta = ContentUris.parseId(preguntaUri);
        assertTrue(-1!=_iPregunta);

        //Pregunta {5}
        contentValues = TestDB.createHechoValues(id_denuncia_1, EvidenciaHechoPreguntas.PREGUNTA_QUE_SERVICIO,"");
        preguntas.add(contentValues);
        preguntaUri= contentResolver.insert(DenunciaContract.DenunciaHechosEntry.CONTENT_URI,contentValues);
        _iPregunta = ContentUris.parseId(preguntaUri);
        assertTrue(-1!=_iPregunta);

        //Pregunta {6}
        contentValues = TestDB.createHechoValues(id_denuncia_1, EvidenciaHechoPreguntas.PREGUNTA_QUIENES,"Quienes");
        preguntas.add(contentValues);
        _iPregunta = ContentUris.parseId(preguntaUri);
        assertTrue(-1!=_iPregunta);

        //By now we have inserted 6 preguntas , go look and  query for the 6 results

        Uri preguntasWithIdInterno  = DenunciaContract.DenunciaHechosEntry.buildHechoWithIdInternoUri(id_denuncia_1);

        cursor = contentResolver.query(preguntasWithIdInterno,null,null,null,null);

        //Base 0
        assertTrue(5==cursor.getCount());

    }

    public void testUpdateDenuncia()
    {

    }


}
