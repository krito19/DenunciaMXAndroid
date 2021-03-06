package com.syca.apps.gob.denunciamx.ui;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.syca.apps.gob.denunciamx.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EnviarDenunciaFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class EnviarDenunciaFragment extends Fragment {


    public interface CallbackEnviarDenuncia {
        public void onFinishDenuncia();
    }

    // @InjectView(R.id.btn_enviar_denuncia) Button bntEnviarDenuncia;

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
     * @return A new instance of fragment EnviarDenunciaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EnviarDenunciaFragment newInstance(String param1, String param2) {
        EnviarDenunciaFragment fragment = new EnviarDenunciaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public EnviarDenunciaFragment() {
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
       View view = inflater.inflate(R.layout.fragment_enviar_denuncia, container, false);
       ButterKnife.inject(this, view);
       return view;
    }

    @OnClick({R.id.btn_enviar_denuncia})
    public void clickFinishDenuncia()
    {
        //Call the activity all the work is done
        //This call does not validate the data in the denuncia context
        ((CallbackEnviarDenuncia)getActivity()).onFinishDenuncia();
    }


}
