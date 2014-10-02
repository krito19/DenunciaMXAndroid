package com.syca.apps.gob.denunciamx.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.syca.apps.gob.denunciamx.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by JARP on 9/26/14.
 */
public class DenunciaMainFragment extends Fragment {

    @InjectView(R.id.denunciar_view) View mDenunciarView;

    public static Fragment newInstance()
    {
        DenunciaMainFragment fragment = new DenunciaMainFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.denunciar_view)
    public void onClickDenunciarView()
    {
        Toast.makeText(getActivity(),"onClickDenunciar",Toast.LENGTH_SHORT).show();
        startActivity(UtilIntents.makeIntentDenunciar(getActivity()));
    }

    @OnClick(R.id.layout_denuncias_view)
    public void onClickListDenuncia()
    {
        startActivity(UtilIntents.makeIntentListDenuncia(getActivity()));
    }





}
