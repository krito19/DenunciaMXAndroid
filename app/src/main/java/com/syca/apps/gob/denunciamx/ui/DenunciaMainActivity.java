package com.syca.apps.gob.denunciamx.ui;

import android.support.v4.app.Fragment;

/**
 * Created by JARP on 9/26/14.
 */
public class DenunciaMainActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return DenunciaMainFragment.newInstance();
    }
}
