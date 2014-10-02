package com.syca.apps.gob.denunciamx.ui;

import android.support.v4.app.Fragment;

/**
 * Created by JARP on 10/1/14.
 */
public class ListDenunciaActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return ListDenunciaFragment.createInstance();
    }
}
