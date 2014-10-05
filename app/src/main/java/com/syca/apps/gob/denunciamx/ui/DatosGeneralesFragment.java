package com.syca.apps.gob.denunciamx.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.syca.apps.gob.denunciamx.R;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DatosGeneralesFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class DatosGeneralesFragment extends Fragment {


    @InjectView(R.id.denuncia_tipo_radio) RadioGroup denuciaTipoAnonima;
    @InjectView(R.id.que_denuncia_edit_text) EditText queDenunciaEditText;
    @InjectView(R.id.donde_denuncia_edit_text) EditText dondeDenunciaEditText;
    @InjectView(R.id.cuando_denuncia_edit_text) EditText cuandoDenunciaEditText;
    @InjectView(R.id.como_denuncia_edit_text)  EditText comoDenunciaEditText;
    @InjectView(R.id.quienes_denuncia_edit_text) EditText quienesDenunciaEditText;
    @InjectView(R.id.servicio_denuncia_edit_text) EditText servicioDenunciaEditText;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DatosGeneralesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DatosGeneralesFragment newInstance(String param1, String param2) {
        DatosGeneralesFragment fragment = new DatosGeneralesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public DatosGeneralesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_datos_generales, container, false);
        ButterKnife.inject(this, view);
        return view;
    }


}
