package com.syca.apps.gob.denunciamx.ui;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.syca.apps.gob.denunciamx.R;
import com.syca.apps.gob.denunciamx.data.DenunciaContract;
import com.syca.apps.gob.denunciamx.utils.UtilIntents;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by JARP on 10/2/14.
 */
public class ListDenunciaFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    @InjectView(R.id.listview_denuncia) ListView mListDenuncias;

    public static final String[] DENUNCIA_COLUMNS=
            {
                    DenunciaContract.DenunciaInfoEntry.TABLE_NAME+"."+ DenunciaContract.DenunciaInfoEntry._ID,
                    DenunciaContract.DenunciaInfoEntry.COLUMN_ID_INTERNO,
                    DenunciaContract.DenunciaInfoEntry.COLUMN_FECHA_CREACION,
                    DenunciaContract.DenunciaInfoEntry.COLUMN_TITULO,
                    DenunciaContract.DenunciaInfoEntry.COLUMN_ESTATUS

            };

    public static final int COL_DENUNCIA_ID = 0;
    public static final int COL_DENUNCIA_ID_INTERNO = 1;
    public static final int COL_DENUNCIA_FECHA_CREACION = 2;
    public static final int COL_DENUNCIA_TITULO = 3;
    public static final int COL_DENUNCIA_ESTATUS = 4;


    public static Fragment createInstance()
    {
        ListDenunciaFragment fragment = new ListDenunciaFragment();
        return fragment;
    }

    private DenunciaListAdapter adapter;
    private static final int LIST_DENUNCIA_LOADER = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        adapter = new DenunciaListAdapter(getActivity(),null,0);
        View v = inflater.inflate(R.layout.fragment_list_denuncias,container,false);
        ButterKnife.inject(this, v);
        mListDenuncias.setAdapter(adapter);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(LIST_DENUNCIA_LOADER,null,this);
        super.onActivityCreated(savedInstanceState);
    }

    @OnClick(R.id.denunciar_view)
    public void onClickDenunciarView()
    {
        startActivity(UtilIntents.makeIntentDenunciar(getActivity()));
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Uri denunciasUri = DenunciaContract.DenunciaInfoEntry.CONTENT_URI;

        String sortOrder = DenunciaContract.DenunciaInfoEntry.COLUMN_FECHA_CREACION + " ASC";
        return new CursorLoader(
                getActivity(),
                denunciasUri,
                DENUNCIA_COLUMNS,
                null,
                null,
                sortOrder
        );

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}

