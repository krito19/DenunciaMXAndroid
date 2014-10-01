package com.syca.apps.gob.denunciamx.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.syca.apps.gob.denunciamx.R;

/**
 * Created by JARP on 9/29/14.
 */
public class DenunciaFragmentPager  extends FragmentActivity{

    public FragmentManager fragmentManager ;

    ViewPager mPager ;

    DenunciaPageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        setContentView(R.layout.fragment_denuciar_pager);
        mPager = (ViewPager) findViewById(R.id.pager);
        adapter = new DenunciaPageAdapter(fragmentManager);
        mPager.setAdapter(adapter);
        mPager.setOffscreenPageLimit(3);

    }

    class DenunciaPageAdapter extends FragmentPagerAdapter {


        Fragment datosGeneralesFragment = DatosGeneralesFragment.newInstance("","");
        Fragment evidenciaFragment = EvidenciaFragment.newInstance("","");
        Fragment ubicacionFragment= UbicacionDenunciaFragment.newInstance("","");
        Fragment enviarDenunciaFragment = EnviarDenunciaFragment.newInstance("","");

        public DenunciaPageAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {

            switch (position)
            {
                case 0:
                    return datosGeneralesFragment;
                case 1:
                    return evidenciaFragment;
                case 2:
                    return ubicacionFragment;
                case 3:
                    return enviarDenunciaFragment;
                default:
                    return null;

            }
        }

        @Override
        public int getCount() {
            //1.Datos Generales
            //2.Evidencia
            //3.Ubicación
            //4.Enviar??
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position)
            {
                case 0:return "Capturar";
                case 1:return "Evidencia";
                case 2:return "Ubicación";
                case 3: return "Enviar";
                default:return "";
            }
        }

    }
}

