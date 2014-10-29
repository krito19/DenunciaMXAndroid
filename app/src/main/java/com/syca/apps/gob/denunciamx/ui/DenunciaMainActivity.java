package com.syca.apps.gob.denunciamx.ui;

import android.support.v4.app.Fragment;

import com.syca.apps.gob.denunciamx.model.Dependencias;

/**
 * Created by JARP on 9/26/14.
 */
public class DenunciaMainActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {

        Dependencias.getDependencias(this);
        return DenunciaMainFragment.newInstance();
    }
}
