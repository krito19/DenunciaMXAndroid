package com.syca.apps.gob.denunciamx.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.util.UUID;

/**
 * Created by JARP on 10/13/14.
 */
public class EvidenciaFileModel implements Parcelable {

    public UUID uuid;
    public String typeEvidencia;
    public String fileName;
    public String fileMimeType;
    public String fileExtension;
    public long   length;
    public Uri    uriEvidenciaFile;


    public EvidenciaFileModel(UUID uuid, File evidenciaFile, String typeEvidencia) throws Exception {

        if(!evidenciaFile.exists())
            throw  new Exception("Error el archivo no existe");

        this.uuid=uuid;

        fileName =  evidenciaFile.getName();

        Uri evidenciaUri = Uri.fromFile(evidenciaFile);

        fileExtension
                = MimeTypeMap.getFileExtensionFromUrl(evidenciaUri.toString());
        fileMimeType
                = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension);

        this.typeEvidencia = typeEvidencia;

        length = evidenciaFile.length();

        this.uriEvidenciaFile = Uri.fromFile(evidenciaFile);
    }

    public EvidenciaFileModel(){}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(uuid);
        dest.writeString(fileName);
        dest.writeString(fileExtension);
        dest.writeString(fileMimeType);
        dest.writeString(typeEvidencia);
        dest.writeLong(length);
        dest.writeParcelable(uriEvidenciaFile,flags);

    }

    public static final Parcelable.Creator<EvidenciaFileModel> CREATOR =  new Parcelable.Creator<EvidenciaFileModel>() {

        @Override
        public EvidenciaFileModel createFromParcel(Parcel parcel) {
            return new EvidenciaFileModel(parcel);
        }

        @Override
        public EvidenciaFileModel[] newArray(int i) {
            return new EvidenciaFileModel[0];
        }
    };

    private EvidenciaFileModel(Parcel in)
    {

        uuid = (UUID) in.readSerializable();
        fileName = in.readString();
        fileExtension = in.readString();
        fileMimeType = in.readString();
        typeEvidencia=in.readString();
        length = in.readLong();
        //TODO:
        //uriEvidenciaFile = in.readParcelable(uriEvidenciaFile);

    }



}
