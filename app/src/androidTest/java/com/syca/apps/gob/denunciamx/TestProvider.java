package com.syca.apps.gob.denunciamx;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;

import com.syca.apps.gob.denunciamx.data.DenunciaContract;
import com.syca.apps.gob.denunciamx.model.EstatusDenuncia;
import com.syca.apps.gob.denunciamx.model.Estatus_S3;
import com.syca.apps.gob.denunciamx.model.EvidenciaHechoPreguntas;
import com.syca.apps.gob.denunciamx.model.EvidenciaMediaType;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Created by JARP on 10/16/14.
 */
public class TestProvider extends AndroidTestCase {

    /*

        ################
        To pull database if is device is rooted then

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
        // - Evidencia Info Entry : DONE
        // - All the questions [6]  ref EvidenciaHechoPreguntas : DONE
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
        contentValues = TestDB.createHechoValues(id_denuncia_1, EvidenciaHechoPreguntas.PREGUNTA_DONDE,"DONDE");
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
        contentValues = TestDB.createHechoValues(id_denuncia_1, EvidenciaHechoPreguntas.PREGUNTA_QUE_SERVICIO,"QUE SERVICIO");
        preguntas.add(contentValues);
        preguntaUri= contentResolver.insert(DenunciaContract.DenunciaHechosEntry.CONTENT_URI,contentValues);
        _iPregunta = ContentUris.parseId(preguntaUri);
        assertTrue(-1!=_iPregunta);

        //Pregunta {6}
        contentValues = TestDB.createHechoValues(id_denuncia_1, EvidenciaHechoPreguntas.PREGUNTA_QUIENES,"Quienes");
        preguntas.add(contentValues);
        preguntaUri= contentResolver.insert(DenunciaContract.DenunciaHechosEntry.CONTENT_URI,contentValues);
        _iPregunta = ContentUris.parseId(preguntaUri);
        assertTrue(-1!=_iPregunta);

        //By now we have inserted 6 preguntas , go look and  query for the 6 results
        Uri preguntasWithIdInterno  = DenunciaContract.DenunciaHechosEntry.buildHechoWithIdInternoUri(id_denuncia_1);
        cursor = contentResolver.query(preguntasWithIdInterno,null,null,null,null);

        assertTrue(6==cursor.getCount());

        //Evidencia File [6]

        ArrayList<ContentValues> evidenciaFiles = new ArrayList<ContentValues>();

        // {0} Evidencia Foto{1}
        contentValues = TestDB.createDenunciaEvidenciaMediaValues(id_denuncia_1,"fullPathFoto1", Estatus_S3.SENDING,"BUCKET/FECHA/TIPO/FOTO/1", EvidenciaMediaType.FOTO,"content://file/foto/1");
        evidenciaFiles.add(contentValues);
        Uri evidenciaUri = contentResolver.insert(DenunciaContract.DenunciaEvidenciaEntry.CONTENT_URI,contentValues);
        long idEvidencia = ContentUris.parseId(evidenciaUri);
        assertTrue(-1!=idEvidencia);

        // {1} Evidencia Foto{2}
        contentValues = TestDB.createDenunciaEvidenciaMediaValues(id_denuncia_1,"fullPathFoto2", Estatus_S3.SENDING,"BUCKET/FECHA/TIPO/FOTO/2", EvidenciaMediaType.FOTO,"content://file/foto/2");
        evidenciaFiles.add(contentValues);
        evidenciaUri = contentResolver.insert(DenunciaContract.DenunciaEvidenciaEntry.CONTENT_URI,contentValues);
        idEvidencia = ContentUris.parseId(evidenciaUri);
        assertTrue(-1!=idEvidencia);


        // {2} Evidencia Video{1}
        contentValues = TestDB.createDenunciaEvidenciaMediaValues(id_denuncia_1,"fullPathvideo1", Estatus_S3.SENDING,"BUCKET/FECHA/TIPO/video/1", EvidenciaMediaType.VIDEO,"content://file/video/1");
        evidenciaFiles.add(contentValues);
        evidenciaUri = contentResolver.insert(DenunciaContract.DenunciaEvidenciaEntry.CONTENT_URI,contentValues);
        idEvidencia = ContentUris.parseId(evidenciaUri);
        assertTrue(-1!=idEvidencia);


        // {3} Evidencia Video{2}
         contentValues = TestDB.createDenunciaEvidenciaMediaValues(id_denuncia_1,"fullPathvideo2", Estatus_S3.SENDING,"BUCKET/FECHA/TIPO/video/2", EvidenciaMediaType.VIDEO,"content://file/video/2");
        evidenciaFiles.add(contentValues);
        evidenciaUri = contentResolver.insert(DenunciaContract.DenunciaEvidenciaEntry.CONTENT_URI,contentValues);
        idEvidencia = ContentUris.parseId(evidenciaUri);
        assertTrue(-1!=idEvidencia);



        // {4} Evidencia Audio{1}
        contentValues = TestDB.createDenunciaEvidenciaMediaValues(id_denuncia_1,"fullPathAUDIO1", Estatus_S3.SENDING,"BUCKET/FECHA/TIPO/AUDIO/1", EvidenciaMediaType.AUDIO,"content://file/AUDIO/1");
        evidenciaFiles.add(contentValues);
        evidenciaUri = contentResolver.insert(DenunciaContract.DenunciaEvidenciaEntry.CONTENT_URI,contentValues);
        idEvidencia = ContentUris.parseId(evidenciaUri);
        assertTrue(-1!=idEvidencia);

        // {5} Evidencia Audio{2} --> This is will change the Estatus S3 so we can test another Uri
        contentValues = TestDB.createDenunciaEvidenciaMediaValues(id_denuncia_1,"fullPathAUDIO2", Estatus_S3.PAUSE,"BUCKET/FECHA/TIPO/AUDIO/2", EvidenciaMediaType.AUDIO,"content://file/AUDIO/2");
        evidenciaFiles.add(contentValues);
        evidenciaUri = contentResolver.insert(DenunciaContract.DenunciaEvidenciaEntry.CONTENT_URI,contentValues);
        idEvidencia = ContentUris.parseId(evidenciaUri);
        assertTrue(-1!=idEvidencia);

        Uri listEvidenciaWithIdInternoUri = DenunciaContract.DenunciaEvidenciaEntry.buildDenunciaWithIdInternoUri(id_denuncia_1);

        cursor = contentResolver.query(listEvidenciaWithIdInternoUri,null,null,null,null);

        cursor.moveToFirst();

        assertTrue(6 == cursor.getCount());


        //Get evidencias that s3 status are 'PAUSE' -> Estatus_S3.PAUSE {300}

        Uri evidenciaStatusPause = DenunciaContract.DenunciaEvidenciaEntry.buildDenunciaWithEstadoS3Uri(String.valueOf(Estatus_S3.PAUSE));

        cursor = contentResolver.query(evidenciaStatusPause,null,null,null,null);

        TestDB.validateCursor(cursor,evidenciaFiles.get(5));


        // - Create the first element of the history

        contentValues = TestDB.createHistoriaValues(id_denuncia_1,today,EstatusDenuncia.DENUNCIA_EN_PROCESO,"Se esta enviando su denuncia");

        Uri primerHistoriaDenunciaUri= contentResolver.insert(DenunciaContract.DenunciaHistoriaEntry.CONTENT_URI,contentValues);

        long idHistoriaDenunciaUri = ContentUris.parseId(primerHistoriaDenunciaUri);

        assertTrue(-1!=idHistoriaDenunciaUri);


        //Insert a second Denuncia just for testing another URI but this will have a status with 'Denuncia_Recibida'

        contentValues = TestDB.createDenunciaInfoValues(token,
                EstatusDenuncia.DENUNCIA_RECIBIDA,
                today ,
                today,
                id_denuncia_2,
                "");



        insertedDenunciaUri= contentResolver.insert(DenunciaContract.DenunciaInfoEntry.CONTENT_URI,contentValues);

        long _idDenunciaInfo2 = ContentUris.parseId(insertedDenunciaUri);

        assertTrue(_idDenunciaInfo2!=_idDenunciaInfo);


        Uri denunciaWithStatusDenunciaRecibida = DenunciaContract.DenunciaInfoEntry.buildDenunciaAndEstadoUri(String.valueOf(EstatusDenuncia.DENUNCIA_RECIBIDA));

        cursor = contentResolver.query(denunciaWithStatusDenunciaRecibida,null,null,null,null);

        TestDB.validateCursor(cursor,contentValues);


        Uri dependenciaUri = DenunciaContract.DependenciaEntry.buildDependenciaWithIdDependenciaUri("2");

        cursor = contentResolver.query(dependenciaUri,null,null,null,null);

        contentValues =  TestDB.createDependenciaValues(2,2, "ADMINISTRACIÓN PORTUARIA INTEGRAL DE ALTAMIRA, S.A. DE C.V.");

        TestDB.validateCursor(cursor,contentValues);



    }

    public void testUpdateDenuncia()
    {

    }


    public void testType()
    {

        ContentResolver mContentResolver = mContext.getContentResolver();

        //User
        String type = mContentResolver.getType(DenunciaContract.UserEntry.CONTENT_URI);

        assertEquals(DenunciaContract.UserEntry.CONTENT_TYPE,type);

        //content::/com.syca.apps.gob.denunciamx/user/UUID/token
        String typeTokenUri = mContentResolver.getType(DenunciaContract.UserEntry.buildUserAndTokenUri(UUID.randomUUID().toString()));

        //vnd.android.cursor.item/com.syca.apps.gob.denunciamx/user
        assertEquals(DenunciaContract.UserEntry.CONTENT_ITEM_TYPE,typeTokenUri);

        //DenunciaInfo
        type = mContentResolver.getType(DenunciaContract.DenunciaInfoEntry.CONTENT_URI);

        assertEquals(DenunciaContract.DenunciaInfoEntry.CONTENT_TYPE,type);




        String estado = String.valueOf(EstatusDenuncia.DENUNCIA_RECIBIDA);


        //content://com.syca.apps.gob.denunciamx/denunciainfo/2/estado
        Uri uri  = DenunciaContract.DenunciaInfoEntry.buildDenunciaAndEstadoUri(estado);

        //
        String typeDenunciaInfoIdEstado = mContentResolver.getType(uri);

        //matcher.addURI(AUTHORITY,"denunciainfo/#/estado",DENUNCIA_ESTADO)
        assertEquals(DenunciaContract.DenunciaInfoEntry.CONTENT_TYPE,typeDenunciaInfoIdEstado);




    }




}
