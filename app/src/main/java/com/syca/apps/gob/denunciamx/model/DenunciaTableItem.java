package com.syca.apps.gob.denunciamx.model;

import android.database.Cursor;

import com.syca.apps.gob.denunciamx.data.DenunciaContract;

/**
 * Created by JARP on 10/30/14.
 */
public class DenunciaTableItem {


    public String titulo;
    public String id_interno;
    public String id_spf;
    public String token;
    public boolean anonima;
    public String id_dependencia ;
    public String email ;
    public long coord_lat ;
    public long coord_long ;
    public String fecha_creacion ;
    public String fecha_actualizacion ;
    public int estatus;

    public static DenunciaTableItem getDenunciaTable(Cursor cursor)
    {
        DenunciaTableItem d = new DenunciaTableItem();

        int idx_titulo = cursor.getColumnIndex(DenunciaContract.DenunciaInfoEntry.COLUMN_TITULO);
        int idx_id_interno= cursor.getColumnIndex(DenunciaContract.DenunciaInfoEntry.COLUMN_ID_INTERNO);
        int idx_id_spf= cursor.getColumnIndex(DenunciaContract.DenunciaInfoEntry.COLUMN_ID_SPF);
        int idx_token= cursor.getColumnIndex(DenunciaContract.DenunciaInfoEntry.COLUMN_TOKEN);
        int idx_anonima= cursor.getColumnIndex(DenunciaContract.DenunciaInfoEntry.COLUMN_ANONIMA);
        int idx_id_dependencia = cursor.getColumnIndex(DenunciaContract.DenunciaInfoEntry.COLUMN_ID_DEPENDENCIA);
        int idx_email = cursor.getColumnIndex(DenunciaContract.DenunciaInfoEntry.COLUMN_EMAIL);
        int idx_coord_lat = cursor.getColumnIndex(DenunciaContract.DenunciaInfoEntry.COLUMN_COORD_LAT);
        int idx_coord_long = cursor.getColumnIndex(DenunciaContract.DenunciaInfoEntry.COLUMN_COORD_LONG);
        int idx_fecha_creacion = cursor.getColumnIndex(DenunciaContract.DenunciaInfoEntry.COLUMN_FECHA_CREACION);
        int idx_fecha_actualizacion = cursor.getColumnIndex(DenunciaContract.DenunciaInfoEntry.COLUMN_FECHA_ACTUALIZACION);
        int idx_estatus= cursor.getColumnIndex(DenunciaContract.DenunciaInfoEntry.COLUMN_ESTATUS);

        d.anonima=cursor.getInt(idx_anonima)== 0 ? true : false ;
        d.coord_lat = cursor.isNull(idx_coord_lat) ? 0 : cursor.getLong(idx_coord_lat);
        d.coord_long = cursor.isNull(idx_coord_long) ? 0 : cursor.getLong(idx_coord_long);
        d.email = cursor.isNull(idx_email) ? "" : cursor.getString(idx_email);
        d.estatus = cursor.getInt(idx_estatus);
        d.fecha_actualizacion =  cursor.getString(idx_fecha_actualizacion);
        d.fecha_creacion =  cursor.getString(idx_fecha_creacion);
        //TODO : this is wrong has to be an int and not null
        d.id_dependencia = cursor.isNull(idx_id_dependencia) ? "" : cursor.getString(idx_id_dependencia);
        d.id_interno = cursor.getString(idx_id_interno);
        //TODO : this is wrong has to be an int and not null
        d.id_spf = cursor.isNull(idx_id_spf) ? "" : cursor.getString(idx_id_spf);
        d.titulo = cursor.getString(idx_titulo);
        d.token =  cursor.isNull(idx_token) ? "" : cursor.getString(idx_token);

        return d;

    }
    
    
    
}
