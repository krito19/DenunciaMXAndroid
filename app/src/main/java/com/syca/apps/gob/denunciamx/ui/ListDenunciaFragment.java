package com.syca.apps.gob.denunciamx.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.syca.apps.gob.denunciamx.R;
import com.syca.apps.gob.denunciamx.utils.UtilIntents;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by JARP on 10/2/14.
 */
public class ListDenunciaFragment extends Fragment {

    @InjectView(R.id.listview_denuncia) ListView mListDenuncias;

    public static Fragment createInstance()
    {
        ListDenunciaFragment fragment = new ListDenunciaFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_denuncias,container,false);
        ButterKnife.inject(this, v);
        DenunciaAdapter adapter = new DenunciaAdapter(getActivity());
        mListDenuncias.setAdapter(adapter);
        return v;
    }

    @OnClick(R.id.denunciar_view)
    public void onClickDenunciarView()
    {
        startActivity(UtilIntents.makeIntentDenunciar(getActivity()));
    }

    class DenunciaAdapter extends BaseAdapter
    {

        private Context mContext;

        /*public static class ViewHolder
        {
            public final TextView dateView;
            public final TextView titleDenunciaView;
            public final TextView folioView;
            public ViewHolder(View v)
            {
                dateView = (TextView) v.findViewById(R.id.list_item_date_denuncia);
                titleDenunciaView = (TextView) v.findViewById(R.id.list_item_title_denuncia_textview);
                folioView = (TextView) v.findViewById(R.id.list_item_folio_textview);
            }
        }*/

        public DenunciaAdapter(Context context)
        {
            mContext = context;
        }

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            return LayoutInflater.from(mContext).inflate(R.layout.item_denuncia,parent,false);
        }
    }
}

