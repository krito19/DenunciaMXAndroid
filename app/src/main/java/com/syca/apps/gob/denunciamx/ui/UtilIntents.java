package com.syca.apps.gob.denunciamx.ui;

import android.content.Context;
import android.content.Intent;

/**
 * Created by JARP on 10/2/14.
 */
public class UtilIntents {

    public static Intent makeIntentDenunciar(Context context)
    {
        Intent intentDenunicar= new Intent(context,DenunciarTabsPager.class);
        return  intentDenunicar;
    }

    public static Intent makeIntentListDenuncia(Context context)
    {
        Intent intentListDenuncia = new Intent(context,ListDenunciaActivity.class);
        return intentListDenuncia;
    }
}
