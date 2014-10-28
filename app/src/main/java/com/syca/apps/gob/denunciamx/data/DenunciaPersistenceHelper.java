package com.syca.apps.gob.denunciamx.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.syca.apps.gob.denunciamx.model.EstatusDenuncia;
import com.syca.apps.gob.denunciamx.model.Estatus_S3;
import com.syca.apps.gob.denunciamx.model.EvidenciaFileModel;
import com.syca.apps.gob.denunciamx.model.EvidenciaHechoPreguntas;
import com.syca.apps.gob.denunciamx.model.EvidenciaMediaType;
import com.syca.apps.gob.denunciamx.ui.DatosGeneralesFragment;
import com.syca.apps.gob.denunciamx.ui.EvidenciaFragment;
import com.syca.apps.gob.denunciamx.utils.MediaStoreSyca;

import java.io.File;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * Created by JARP on 10/27/14.
 */
public class DenunciaPersistenceHelper {

    private Context mContext;

    private final ContentResolver contentResolver;

    private final String TAG = DenunciaPersistenceHelper.class.getSimpleName();

    public DenunciaPersistenceHelper(Context context)
    {
        mContext=context;
        contentResolver = context.getContentResolver();
    }

    public void persistNewDenuncia(DatosGeneralesFragment.DenunciaInfo info,EvidenciaFragment.MediaInfoFile evidenciaFiles)
    {

        Date todayDate = new Date();

        String today= DenunciaContract.getDbDateString(todayDate);

        String idDenuncia = UUID.randomUUID().toString();

        insertDenunciaInfo(info,idDenuncia,today);

        insertEvidencias(idDenuncia,todayDate,evidenciaFiles);

        insertHistoria(idDenuncia,today,EstatusDenuncia.DENUNCIA_POR_ENVIAR,"Su denucia esta por enviar");

    }

    private void insertHistoria(String idDenuncia,String fecha,int idEstatus,String mensaje)
    {
        ContentValues cv = createHistoriaValues(idDenuncia,fecha,idEstatus,mensaje);

        Uri history = DenunciaContract.DenunciaHistoriaEntry.CONTENT_URI;

        contentResolver.insert(history,cv);


    }

    private void insertDenunciaInfo(DatosGeneralesFragment.DenunciaInfo info, String idDenuncia, String today)
    {
        ContentValues cvInfoDenuncia= createDenunciaInfoValues(info.isAnonima?1:0,
                "NoToken",
                EstatusDenuncia.DENUNCIA_POR_ENVIAR,
                today,
                today,
                idDenuncia,
                "EMPTY");

        Uri denunciaUri = DenunciaContract.DenunciaInfoEntry.CONTENT_URI;

        contentResolver.insert(denunciaUri,cvInfoDenuncia);

        insertPregunta(idDenuncia, EvidenciaHechoPreguntas.PREGUNTA_COMO,info.comoDenuncia);
        insertPregunta(idDenuncia, EvidenciaHechoPreguntas.PREGUNTA_QUE,info.queDenuncia);
        insertPregunta(idDenuncia, EvidenciaHechoPreguntas.PREGUNTA_QUIENES,info.quienesDenuncia);
        insertPregunta(idDenuncia, EvidenciaHechoPreguntas.PREGUNTA_CUANDO ,info.cuandoDenuncia);
        insertPregunta(idDenuncia, EvidenciaHechoPreguntas.PREGUNTA_QUE_SERVICIO,info.queDenuncia);
        insertPregunta(idDenuncia, EvidenciaHechoPreguntas.PREGUNTA_DONDE,info.dondeDenuncia);


    }

    private void insertPregunta(String idDenuncia, int idPregunta, String respuesta)
    {
        ContentValues cvPregunta= createHechoValues(idDenuncia,idPregunta,respuesta);
        Uri uriPregunta = DenunciaContract.DenunciaHechosEntry.CONTENT_URI;
        contentResolver.insert(uriPregunta,cvPregunta);
    }

    private void insertEvidencias(String idDenuncia, Date today,EvidenciaFragment.MediaInfoFile evidenciaFiles )
    {
        for(Map.Entry<Integer, Uri> set : evidenciaFiles.audioKeyList.entrySet())
        {
            buildEvidenciaModelToInsert(set,today,EvidenciaMediaType.AUDIO_STRING, EvidenciaMediaType.AUDIO,idDenuncia);
        }

        for(Map.Entry<Integer, Uri> set : evidenciaFiles.videoKeyList.entrySet())
        {
            buildEvidenciaModelToInsert(set,today,EvidenciaMediaType.VIDEO_STRING, EvidenciaMediaType.VIDEO,idDenuncia);
        }

        for(Map.Entry<Integer, Uri> set : evidenciaFiles.photosKeyList.entrySet())
        {
            buildEvidenciaModelToInsert(set,today,EvidenciaMediaType.FOTO_STRING, EvidenciaMediaType.FOTO,idDenuncia);
        }


    }

    private void buildEvidenciaModelToInsert(Map.Entry<Integer, Uri> set , Date today,
                                             String typeFile,int idTypeFile,
                                             String idDenuncia){
        Uri uri;

        EvidenciaFileModel evidencia =null;

        String s3Path ;

        uri = set.getValue();

        String fullPath;

        File file;

        try {

            evidencia = new EvidenciaFileModel(UUID.randomUUID(),new File(uri.getPath()), typeFile);

            fullPath =  uri.getPath();

            s3Path = MediaStoreSyca.getAmazonStorageS3Id(evidencia,today);

        } catch (Exception e) {
            Log.e(TAG, "Error al crear modelo evidencia");
            return;
        }

        insertEvidencia(idDenuncia,fullPath, Estatus_S3.READY_TO_SEND,s3Path,idTypeFile,uri.toString());

    }



    private void insertEvidencia(String idDenuncia, String fullPath,
                                 int idEstatusS3, String s3Path,
                                 int typeFile, String uriFile) {

        ContentValues cvEvidencia = createDenunciaEvidenciaMediaValues(idDenuncia,fullPath,idEstatusS3,s3Path,typeFile,uriFile);

        Uri uriEvidencia = DenunciaContract.DenunciaEvidenciaEntry.CONTENT_URI;

        contentResolver.insert(uriEvidencia,cvEvidencia);

    }


    private static ContentValues createDenunciaInfoValues(int isAnonima,String token,int estatus,
                                                  String fechaCreacion,String fechaActualizacion,
                                                  String idDenuncia,String idSPF)
    {
        ContentValues cv = new ContentValues();
        cv.put(DenunciaContract.DenunciaInfoEntry.COLUMN_ANONIMA,isAnonima);
        cv.put(DenunciaContract.DenunciaInfoEntry.COLUMN_COORD_LAT,1);
        cv.put(DenunciaContract.DenunciaInfoEntry.COLUMN_COORD_LONG,2);
        cv.put(DenunciaContract.DenunciaInfoEntry.COLUMN_FECHA_ACTUALIZACION,fechaActualizacion);
        cv.put(DenunciaContract.DenunciaInfoEntry.COLUMN_FECHA_CREACION,fechaCreacion);
        cv.put(DenunciaContract.DenunciaInfoEntry.COLUMN_EMAIL,"");
        cv.put(DenunciaContract.DenunciaInfoEntry.COLUMN_ESTATUS,estatus);
        cv.put(DenunciaContract.DenunciaInfoEntry.COLUMN_ID_DEPENDENCIA,0);
        cv.put(DenunciaContract.DenunciaInfoEntry.COLUMN_ID_INTERNO,idDenuncia);
        cv.put(DenunciaContract.DenunciaInfoEntry.COLUMN_ID_SPF,idSPF);
        cv.put(DenunciaContract.DenunciaInfoEntry.COLUMN_TITULO,"Some nice title");
        cv.put(DenunciaContract.DenunciaInfoEntry.COLUMN_TOKEN,token);
        return cv;
    }



    private static ContentValues createHechoValues(String idDenuncia,int idPregunta,String respuesta) {

        ContentValues cv = new ContentValues();
        cv.put(DenunciaContract.DenunciaHechosEntry.COLUMN_ID_INTERNO,idDenuncia);
        cv.put(DenunciaContract.DenunciaHechosEntry.COLUMN_ID_PREGUNTA,idPregunta);
        cv.put(DenunciaContract.DenunciaHechosEntry.COLUMN_RESPUESTA,respuesta);

        return cv;

    }

    private static ContentValues createHistoriaValues(String idInterno, String fecha, int idEstatus,String mensaje) {

        ContentValues cv = new ContentValues();
        cv.put(DenunciaContract.DenunciaHistoriaEntry.COLUMN_ID_INTERNO,idInterno);
        cv.put(DenunciaContract.DenunciaHistoriaEntry.COLUMN_FECHA,fecha);
        cv.put(DenunciaContract.DenunciaHistoriaEntry.COLUMN_ID_ESTATUS,idEstatus);
        cv.put(DenunciaContract.DenunciaHistoriaEntry.COLUMN_MENSAJE,mensaje);

        return cv;

    }

    static ContentValues createDenunciaEvidenciaMediaValues(String idDenuncia, String fullPath,
                                                            int idEstatusS3, String s3Path,
                                                            int typeFile, String uriFile)
    {
        ContentValues cv = new ContentValues();
        cv.put(DenunciaContract.DenunciaEvidenciaEntry.COLUMN_FULL_PATH,fullPath);
        cv.put(DenunciaContract.DenunciaEvidenciaEntry.COLUMN_ID_ESTATUS_S3,idEstatusS3);
        cv.put(DenunciaContract.DenunciaEvidenciaEntry.COLUMN_ID_INTERNO,idDenuncia);
        cv.put(DenunciaContract.DenunciaEvidenciaEntry.COLUMN_PATH_S3,s3Path);
        cv.put(DenunciaContract.DenunciaEvidenciaEntry.COLUMN_TYPE,typeFile);
        cv.put(DenunciaContract.DenunciaEvidenciaEntry.COLUMN_URI,uriFile);

        return cv;

    }



}
