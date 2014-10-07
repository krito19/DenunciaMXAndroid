package com.syca.apps.gob.denunciamx.ui.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.widget.ImageView;

/**
 * Created by JARP on 10/5/14.
 */
public class PictureUtils {

    public static void setImageScaled(Context context,ImageView imageView,String photoPath)
    {

        int destHeight = 128;
        int destWidth = 128;

        // read in the dimensions of the image on disk
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, options);

        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;

        int inSampleSize = 1;
        if (srcHeight > destHeight || srcWidth > destWidth) {
            if (srcWidth > srcHeight) {
                inSampleSize = Math.round((float)srcHeight / (float)destHeight);
            } else {
                inSampleSize = Math.round((float)srcWidth / (float)destWidth);
            }
        }

        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;

        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);

        BitmapDrawable drawable =  new BitmapDrawable(context.getResources(), bitmap);

        imageView.setImageDrawable(drawable);

    }

    public static Bitmap createVideoThumbnail(Activity activity, Uri uri) {
        Bitmap bitmap = null;
        android.media.MediaMetadataRetriever retriever = new android.media.MediaMetadataRetriever();
        try {
            retriever.setDataSource(activity,uri);
            bitmap= retriever.getFrameAtTime(10);
        } catch(IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        return bitmap;
    }
}
