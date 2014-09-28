package com.syca.apps.gob.denunciamx.ui;

import android.support.v4.app.Fragment;

/**
 * Created by JARP on 9/27/14.
 */
public class DenunciarActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return DenunciarFragment.newInstance();
    }
}
