package com.syca.apps.gob.denunciamx.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by JARP on 10/14/14.
 */
public class DenunciaModel
        implements Parcelable {


    private List<EvidenciaFileModel> evidenciasList;

    public DenunciaModel()
    {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
