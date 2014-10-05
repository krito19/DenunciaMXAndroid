package com.syca.apps.gob.denunciamx.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by JARP on 10/5/14.
 */
public class MediaStore {

    
    private final static String TAG = MediaStore.class.getSimpleName();

    public MediaStore(Context context)
    {

    }


    public static  Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    public static File getOutputMediaFile(int type) {

        Log.d(TAG, "getOutputMediaFile() type:" + type);
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        // For future implementation: store videos in a separate directory
        File mediaStorageDir = new File(
                Environment
                        .getExternalStorageDirectory(),
                "DenunciaMX");

        //TODO: Need to check if external storage is the best option

        // PRO : This works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("DenunciaMX", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)
                .format(new Date());
        File mediaFile;
        if (type == EvidenciaFragment.MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == EvidenciaFragment.MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else if (type == EvidenciaFragment.MEDIA_TYPE_AUDIO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "AUD_" + timeStamp + ".3gp");
        } else {
            Log.e(TAG, "typ of media file not supported: type was:" + type);
            return null;
        }

        return mediaFile;
    }

}
