package com.syca.apps.gob.denunciamx.ui.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;

import com.syca.apps.gob.denunciamx.ui.DenunciarTabsPager;
import com.syca.apps.gob.denunciamx.ui.ListDenunciaActivity;

import java.io.File;

/**
 * Created by JARP on 10/2/14.
 */
public class UtilIntents {

    public static Intent makeIntentDenunciar(Context context)
    {
        Intent intentDenunicar= new Intent(context,DenunciarTabsPager.class);
        return  intentDenunicar;
    }

    public static Intent makeIntentListDenuncia(Context context)
    {
        Intent intentListDenuncia = new Intent(context,ListDenunciaActivity.class);
        return intentListDenuncia;
    }

    public static Intent makeIntentCaptionVideo( File videoFile)
    {
        // TODO - Create a new intent to launch the MediaStore, Image capture function
        // See: http://developer.android.com/reference/android/provider/MediaStore.html
        Intent mVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        // Intent-extra name, EXTRA_VIDEO_QUALITY, from the MediaStore class
        // set the video image quality to high
        mVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

        mVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT,  Uri.fromFile(videoFile));

        return  mVideoIntent;


    }

    public static Intent makeIntentPhoto(File photoFile)
    {

        Intent mImageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        mImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));

        return mImageIntent;
    }


    public static Intent makeIntentShowEvidenciaFile( File evidenciaFile)
    {
        if(!evidenciaFile.exists())
            return null;

        Intent viewEvidenciaIntent = new Intent();
        viewEvidenciaIntent.setAction(Intent.ACTION_VIEW);
        Uri videoUri = Uri.fromFile(evidenciaFile);
        String fileExtension
                = MimeTypeMap.getFileExtensionFromUrl(videoUri.toString());
        String mimeType
                = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension);

        viewEvidenciaIntent.setDataAndType(videoUri,mimeType);

        return viewEvidenciaIntent;
    }

}

