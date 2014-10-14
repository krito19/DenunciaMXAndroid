package com.syca.apps.gob.denunciamx.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.syca.apps.gob.denunciamx.model.EvidenciaModel;
import com.syca.apps.gob.denunciamx.utils.MediaStoreSyca;

import java.io.File;
import java.util.UUID;

public class DenunciaService extends Service {

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



    public DenunciaService() {


    }

    @Override
    public void onCreate() {

        super.onCreate();

        HandlerThread thread = new HandlerThread("UploadService");
        thread.start();

        mServiceLooper = thread.getLooper();

        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Message message = mServiceHandler.obtainDownloadMessage(intent,startId);

        mServiceHandler.sendMessage(message);

        return START_NOT_STICKY;


    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");

    }


    /*
        Factory Method to make the correct Intent
     */
    public static Intent makeServiceIntent(Context context, Uri uri,String typeFile,Handler uploadHandler)
    {
        Intent serviceIntent = new Intent(context,DenunciaService.class);

        //This is the uri file that we need to upload
        serviceIntent.setData(uri);

        //Type File
        serviceIntent.putExtra(EXTRA_FILE_TYPE,typeFile);

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
            uploadFile(msg);
        }

        private void uploadFile(Message msg) {

            Intent intent = (Intent) msg.obj;

            Uri uriUploadFile = intent.getData();

            String fileType = intent.getStringExtra(EXTRA_FILE_TYPE);


            EvidenciaModel evidencia =null;
            try {

                evidencia = new EvidenciaModel(UUID.randomUUID(),new File(uriUploadFile.getPath()),fileType);

            } catch (Exception e) {
                e.printStackTrace();
            }


            uploadToAmazon(evidencia,uriUploadFile);

            stopSelf(msg.arg1);

        }

        private Message obtainDownloadMessage(Intent intent,int startId)
        {
            Message message = Message.obtain();
            message.obj = intent;
            message.arg1=startId;
            return  message;
        }

        private void uploadToAmazon(EvidenciaModel model, Uri uri)
        {
            AmazonS3Client s3Client = new AmazonS3Client( new BasicAWSCredentials( MY_ACCESS_KEY_ID, MY_SECRET_KEY ) );

            if(!s3Client.doesBucketExist(DENUNCIA_BUCKET))
                s3Client.createBucket(DENUNCIA_BUCKET);

            //(String bucketName, String key, File file)
            PutObjectRequest por = new PutObjectRequest( DENUNCIA_BUCKET, MediaStoreSyca.getAmazonStorageS3Id(model), new File(uri.getPath()));
            por.setMetadata(MediaStoreSyca.getObjectMetadata(model));

            s3Client.putObject( por );

        }





    }
}
