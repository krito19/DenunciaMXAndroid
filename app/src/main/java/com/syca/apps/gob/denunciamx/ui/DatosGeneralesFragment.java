package com.syca.apps.gob.denunciamx.ui;


import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.syca.apps.gob.denunciamx.R;
import com.syca.apps.gob.denunciamx.data.DenunciaContract;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DatosGeneralesFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class DatosGeneralesFragment extends Fragment {


   DenunciaInfo info = new DenunciaInfo();


    public class DenunciaInfo
    {
        public Boolean isAnonima;
        public int idDependencia;
        public String queDenuncia;
        public String dondeDenuncia;
        public String cuandoDenuncia;
        public String comoDenuncia;
        public String quienesDenuncia;
        public String servicioDenuncia;
    }


    /*  @InjectView(R.id.video_launch_btn) ImageButton mButtonCreateVideo;
    @InjectView(R.id.video_container_view) LinearLayout  mVideosView;*/

    @InjectView(R.id.denuncia_tipo_radio) RadioGroup denuciaTipoAnonima;
    @InjectView(R.id.que_denuncia_edit_text) EditText queDenunciaEditText;
    @InjectView(R.id.donde_denuncia_edit_text) EditText dondeDenunciaEditText;
    @InjectView(R.id.cuando_denuncia_edit_text) EditText cuandoDenunciaEditText;
    @InjectView(R.id.como_denuncia_edit_text)  EditText comoDenunciaEditText;
    @InjectView(R.id.quienes_denuncia_edit_text) EditText quienesDenunciaEditText;
    @InjectView(R.id.servicio_denuncia_edit_text) EditText servicioDenunciaEditText;
    @InjectView(R.id.spinner_dependencias) Spinner dependeciaSpinner;



    private static final String KEY_QUE="KEY_QUE";
    private static final String KEY_DONDE="KEY_DONDE";
    private static final String KEY_CUANDO="KEY_CUANDO";
    private static final String KEY_COMO="KEY_COMO";
    private static final String KEY_QUIENES="KEY_QUIENES";
    private static final String KEY_SERVICIO="KEY_SERVICIO";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    AsyncTask<Void,Void,Cursor> DependenciasTask = new AsyncTask<Void, Void, Cursor>() {

        private final int DEPENDENCIA_NAME=2;
        @Override
        protected Cursor doInBackground(Void... params) {

            ContentResolver contentResolver = getActivity().getContentResolver();

            Uri dependenciasUri = DenunciaContract.DependenciaEntry.CONTENT_URI;

            String orderBy = DenunciaContract.DependenciaEntry.COLUMN_ID_DEPENDENCIA + " ASC ";

            return contentResolver.query(dependenciasUri,null,null,null,orderBy);


        }

        @Override
        protected void onPostExecute(Cursor cursor) {

            List<String> dependenciaList = new ArrayList<String>();

            if(cursor.moveToFirst()) {
                do {
                    dependenciaList.add(getDependecia(cursor));
                }while (cursor.moveToNext());
                ArrayAdapter<String> dependenciaAdapter =
                        new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, dependenciaList);
                dependeciaSpinner.setAdapter(dependenciaAdapter);
            }
        }

        private String getDependecia(Cursor cursor) {
            return  cursor.getString(DEPENDENCIA_NAME);
        }


    };


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
        setRetainInstance(true);
        //TODO:Set in bundle
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_datos_generales, container, false);
        ButterKnife.inject(this, view);
        DependenciasTask.execute();
        if(null!=savedInstanceState)
            handleInstance(savedInstanceState);

        return view;
    }



    public DenunciaInfo getInfoDenunciaQuestions()
    {
        if(R.id.denuncia_publica_positive== denuciaTipoAnonima.getCheckedRadioButtonId())
            info.isAnonima=true;
        //TODO : get dependencia from spinner
        //dependeciaSpinner.getSelectedItem()
        info.comoDenuncia=comoDenunciaEditText.getText().toString();
        info.cuandoDenuncia=cuandoDenunciaEditText.getText().toString();
        info.dondeDenuncia=dondeDenunciaEditText.getText().toString();
        info.quienesDenuncia=quienesDenunciaEditText.getText().toString();
        info.queDenuncia=queDenunciaEditText.getText().toString();
        info.servicioDenuncia=servicioDenunciaEditText.getText().toString();
        return info;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveInstance(outState);
    }

    private void saveInstance(Bundle outState) {

        outState.putString(KEY_QUE,queDenunciaEditText.getText().toString());

        outState.putString(KEY_DONDE,dondeDenunciaEditText.getText().toString());

        outState.putString(KEY_CUANDO,cuandoDenunciaEditText.getText().toString());

        outState.putString(KEY_COMO,comoDenunciaEditText.getText().toString());

        outState.putString(KEY_QUIENES,quienesDenunciaEditText.getText().toString());

        outState.putString(KEY_SERVICIO,servicioDenunciaEditText.getText().toString());

    }

    private void handleInstance(Bundle savedInstance)
    {

        if(savedInstance.containsKey(KEY_QUE))
        queDenunciaEditText.setText(savedInstance.getString(KEY_QUE));

        if(savedInstance.containsKey(KEY_DONDE))
        dondeDenunciaEditText.setText(savedInstance.getString(KEY_DONDE));

        if(savedInstance.containsKey(KEY_CUANDO))
        cuandoDenunciaEditText.setText(savedInstance.getString(KEY_CUANDO));

        if(savedInstance.containsKey(KEY_COMO))
        comoDenunciaEditText.setText(savedInstance.getString(KEY_COMO));

        if(savedInstance.containsKey(KEY_QUIENES))
        quienesDenunciaEditText.setText(savedInstance.getString(KEY_QUIENES));

        if(savedInstance.containsKey(KEY_SERVICIO))
        servicioDenunciaEditText.setText(savedInstance.getString(KEY_SERVICIO));

    }


}
