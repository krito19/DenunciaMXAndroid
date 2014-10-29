package com.syca.apps.gob.denunciamx.ui;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.syca.apps.gob.denunciamx.R;
import com.syca.apps.gob.denunciamx.data.DenunciaContract;
import com.syca.apps.gob.denunciamx.model.EstatusDenuncia;
import com.syca.apps.gob.denunciamx.utils.MonthUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by JARP on 10/28/14.
 */
public class DenunciaListAdapter extends CursorAdapter {

    public DenunciaListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    public static class ViewHolder {

        public final TextView dayDenunciaView;
        public final TextView monthDenunciaView;
        public final TextView yearDenunciaView;
        public final TextView titleDenunciaView;
        public final TextView denunciaFolioInternoView;
        public final TextView denunciaStatusView;

        public ViewHolder(View view)
        {

            dayDenunciaView = (TextView) view.findViewById(R.id.txt_day_denuncia);
            monthDenunciaView = (TextView) view.findViewById(R.id.txt_month_denuncia);
            yearDenunciaView = (TextView) view.findViewById(R.id.txt_year_denuncia);
            titleDenunciaView = (TextView) view.findViewById(R.id.txt_title_denuncia);
            denunciaFolioInternoView = (TextView) view.findViewById(R.id.txt_denuncia_folio_interno);
            denunciaStatusView = (TextView) view.findViewById(R.id.txt_denuncia_status);
        }

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_denuncia_2,parent,false);

        ViewHolder viewHolder = new ViewHolder(v);

        v.setTag(viewHolder);

        return v;

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder viewHolder = (ViewHolder)view.getTag();

        Date date = DenunciaContract.getDateFromDb(cursor.getString(ListDenunciaFragment.COL_DENUNCIA_FECHA_CREACION));

        String day = getFormat("dd", date);
        viewHolder.dayDenunciaView.setText(day);


        String month =getFormat("MM",date);
        month = MonthUtil.getMonth(Integer.parseInt(month));
        viewHolder.monthDenunciaView.setText(month);

        String year = getFormat("yyyy",date);
        viewHolder.yearDenunciaView.setText(year);

        String folioInterno = cursor.getString(ListDenunciaFragment.COL_DENUNCIA_ID_INTERNO);
        viewHolder.denunciaFolioInternoView.setText(folioInterno);

        String title = cursor.getString(ListDenunciaFragment.COL_DENUNCIA_TITULO);
        viewHolder.titleDenunciaView.setText(title);

        int status = cursor.getInt(ListDenunciaFragment.COL_DENUNCIA_ESTATUS);
        String sStatus = EstatusDenuncia.getTextStatus(status);
        viewHolder.denunciaStatusView.setText(sStatus);


    }


    private static String getFormat(String formatPattern, Date dt)
    {
        return new SimpleDateFormat(formatPattern).format(dt);
    }
}
