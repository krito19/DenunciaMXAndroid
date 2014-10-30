package com.syca.apps.gob.denunciamx.model;

import android.database.Cursor;

import com.syca.apps.gob.denunciamx.data.DenunciaContract;

/**
 * Created by JARP on 10/30/14.
 */
public class EvidenciaTableItem {
    
    public String evidenciaFullPath;
    public int idStatusS3;
    public String idDenuncia;
    public String s3Path;
    public String evidenciaType;
    public String uriEvidencia;
    
    public static EvidenciaTableItem getEvidenciaItem(Cursor cursor)
    {
        EvidenciaTableItem e = new EvidenciaTableItem();

        int idxEvidenciaFullPath = cursor.getColumnIndex(DenunciaContract.DenunciaEvidenciaEntry.COLUMN_FULL_PATH);
        int idxIdStatusS3= cursor.getColumnIndex(DenunciaContract.DenunciaEvidenciaEntry.COLUMN_ID_ESTATUS_S3);
        int idxIdDenuncia= cursor.getColumnIndex(DenunciaContract.DenunciaEvidenciaEntry.COLUMN_ID_INTERNO);
        int idxS3Path= cursor.getColumnIndex(DenunciaContract.DenunciaEvidenciaEntry.COLUMN_PATH_S3);
        int idxEvidenciaType= cursor.getColumnIndex(DenunciaContract.DenunciaEvidenciaEntry.COLUMN_TYPE);
        int idxUriEvidencia= cursor.getColumnIndex(DenunciaContract.DenunciaEvidenciaEntry.COLUMN_URI);

        e.evidenciaFullPath = cursor.getString(idxEvidenciaFullPath);
        e.idStatusS3 = cursor.getInt(idxIdStatusS3);
        e.idDenuncia = cursor.getString(idxIdDenuncia);
        e.s3Path= cursor.getString(idxS3Path);
        e.evidenciaType= cursor.getString(idxEvidenciaType);
        e.uriEvidencia = cursor.getString(idxUriEvidencia);

        return e;
    }
    
}
