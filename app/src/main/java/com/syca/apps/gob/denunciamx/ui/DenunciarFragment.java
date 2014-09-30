package com.syca.apps.gob.denunciamx.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.syca.apps.gob.denunciamx.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by JARP on 9/27/14.
 */
public class DenunciarFragment extends Fragment implements TabHost.OnTabChangeListener {


    @InjectView(R.id.tabHost)  android.support.v4.app.FragmentTabHost mFragmentTabHost;

    public static DenunciarFragment newInstance()
    {
        DenunciarFragment fragment = new DenunciarFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.denunciar_fragment,container,false);
        ButterKnife.inject(this, view);
        setupTabs();
        return view;
    }

    @Override
    public void onTabChanged(String tabId) {

    }

    private void setupTabs()
    {
        //mFragmentTabHost.setup(getActivity(),getFragmentManager());
        mFragmentTabHost.setup(getActivity(),getFragmentManager(),android.R.id.tabcontent);

        //DatosGenerales
        mFragmentTabHost.addTab(mFragmentTabHost.newTabSpec("Capturar").setIndicator(getString(R.string.title_tabhost_capturar_fragment),null),
                                DatosGeneralesFragment.class,null);
        //Evidencia
        mFragmentTabHost.addTab(mFragmentTabHost.newTabSpec("Evidencia").setIndicator(getString(R.string.title_tabhost_evidencia_fragment),null),
                                EvidenciaFragment.class,null);
        //Ubicación
        mFragmentTabHost.addTab(mFragmentTabHost.newTabSpec("Ubicacion").setIndicator(getString(R.string.title_tabhost_ubicacion_fragment),null),
                                UbicacionDenunciaFragment.class,null);
        //Enviar
        mFragmentTabHost.addTab(mFragmentTabHost.newTabSpec("Enviar").setIndicator(getString(R.string.title_tabhost_enviar_fragment),null),
                                EnviarDenunciaFragment.class,null);



    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
