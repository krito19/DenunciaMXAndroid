package com.syca.apps.gob.denunciamx.model;

/**
 * Created by JARP on 10/15/14.
 */
public class EstatusDenuncia {

    public static final int DENUNCIA_POR_ENVIAR=0;
    public static final int DENUNCIA_ENVIADA=1;
    public static final int DENUNCIA_RECIBIDA=2;
    public static final int DENUNCIA_EN_PROCESO=3;
    public static final int DENUNCIA_RESULTADO=4;


    
    public static String getTextStatus(int estatus)
    {
        switch (estatus)
        {
            case  DENUNCIA_POR_ENVIAR:
                return "Denuncia en proceso de envío";
            case  DENUNCIA_ENVIADA:
                return "Denuncia en enviada";
            case  DENUNCIA_RECIBIDA:
                return "Denuncia recibida por la SFP";
            case  DENUNCIA_EN_PROCESO:
                return "Denuncia en proceso";
            case  DENUNCIA_RESULTADO:
                return "Denuncia finalizada";
            default:
                return "";
        }
    }
}
