package com.syca.apps.gob.denunciamx.service;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.syca.apps.gob.denunciamx.data.DenunciaContract;
import com.syca.apps.gob.denunciamx.model.EvidenciaFileModel;
import com.syca.apps.gob.denunciamx.utils.MediaStoreSyca;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DenunciaService extends Service {


    private static final String TAG = "DenunciaService";
    /**
     * Looper associated with the HandlerThread.
     */
    private volatile Looper mServiceLooper;

    /**
     * Processes Messages sent to it from onStartCommnand() that
     * indicate which images to download from a remote server.
     */
    private volatile ServiceHandler mServiceHandler;


    private final static String EXTRA_URI_FILE="Denuncia_Uri_file";
    private final static String EXTRA_FILE_TYPE="Denuncia_Type_file";
    private final static String EXTRA_PARAM_DENUNCIA_ID="Denuncia_ID";

    @Override
    public void onCreate() {

        super.onCreate();

        Log.d(TAG,"onCreate");

        HandlerThread thread = new HandlerThread("UploadService");
        thread.start();

        mServiceLooper = thread.getLooper();

        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Log.d(TAG,"onStart");

        Message message = mServiceHandler.obtainDownloadMessage(intent,startId);

        mServiceHandler.sendMessage(message);



        return START_NOT_STICKY;


    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");

    }

    /**
     * Hook method called back to shutdown the Looper.
     */
    public void onDestroy() {
        mServiceLooper.quit();
    }


    /*
        Factory Method to make the correct Intent
     */
    public static Intent makeServiceIntent(Context context,String idDenuncia,Handler uploadHandler)
    {
        Intent serviceIntent = new Intent(context,DenunciaService.class);

        serviceIntent.putExtra(EXTRA_PARAM_DENUNCIA_ID,idDenuncia);

        //TODO: uploadHandler needs to be set??? right now no 'cause we'll bind to get the progress

        return  serviceIntent;

    }


    private final class ServiceHandler extends Handler {


        private static final String MY_ACCESS_KEY_ID="";
        private static final String MY_SECRET_KEY="";
        private static final String DENUNCIA_BUCKET ="";

        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {

            Log.d(TAG,"onHandle");
            uploadFiles(msg);
        }

        private void uploadFiles(Message msg) {

            Intent intent = (Intent) msg.obj;

            String idDenuncia = intent.getStringExtra(EXTRA_PARAM_DENUNCIA_ID);

            ContentResolver cr = getContentResolver();


            Uri filesUri = DenunciaContract.DenunciaEvidenciaEntry.buildDenunciaWithIdInternoUri(idDenuncia);

            Cursor cursor = cr.query(filesUri, null, null, null, null);


            List<EvidenciaFileModel> evidencias = new ArrayList<EvidenciaFileModel>();

            if(cursor.moveToFirst())
            {

                do {
                    evidencias.add(getFile(cursor));
                }while (cursor.moveToNext());

            }

            cursor.close();


            for (EvidenciaFileModel evidencia : evidencias   ) {
                uploadToAmazon(evidencia, evidencia.uriEvidenciaFile,idDenuncia);
            }

            stopSelf(msg.arg1);

        }

        private Message obtainDownloadMessage(Intent intent,int startId)
        {
            Message message = Message.obtain();
            message.obj = intent;
            message.arg1=startId;
            return  message;
        }

        private void uploadToAmazon(EvidenciaFileModel model, Uri uri,String idDenuncia)
        {

            Log.d(TAG,"onSend");
            AmazonS3Client s3Client = new AmazonS3Client( new BasicAWSCredentials( MY_ACCESS_KEY_ID, MY_SECRET_KEY ) );

            if(!s3Client.doesBucketExist(DENUNCIA_BUCKET))
                s3Client.createBucket(DENUNCIA_BUCKET);

            //(String bucketName, String key, File file)
            PutObjectRequest por = new PutObjectRequest( DENUNCIA_BUCKET,
                                                        idDenuncia + "/" +
                                                        MediaStoreSyca.getAmazonStorageS3Id(model), new File(uri.getPath()));
            por.setMetadata(MediaStoreSyca.getObjectMetadata(model));

            s3Client.putObject( por );

            Log.d(TAG,"onSendFinished");

        }

        public EvidenciaFileModel getFile(Cursor cursor)
        {

            int idxFullPath= cursor.getColumnIndex(DenunciaContract.DenunciaEvidenciaEntry.COLUMN_FULL_PATH);
            int idxType= cursor.getColumnIndex(DenunciaContract.DenunciaEvidenciaEntry.COLUMN_TYPE);
            int idxUri= cursor.getColumnIndex(DenunciaContract.DenunciaEvidenciaEntry.COLUMN_URI);

            String fullPath = cursor.getString(idxFullPath);
            String type = cursor.getString(idxType);

            if(type.equals("1"))
                type="VIDEO";
            if(type.equals("2"))
                type="FOTO";
            if(type.equals("3"))
                type="VIDEO";
            String uriString = cursor.getString(idxUri);

            Uri uriUploadFile = Uri.parse(uriString);

            try {
                return new EvidenciaFileModel(UUID.randomUUID(),new File(uriUploadFile.getPath()),type);
            } catch (Exception e) {
                e.printStackTrace();
                return  null;
            }


        }



    }
}
