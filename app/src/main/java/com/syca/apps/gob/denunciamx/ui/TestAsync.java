package com.syca.apps.gob.denunciamx.ui;

import android.os.AsyncTask;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;

/**
 * Created by JARP on 10/8/14.
 */
public class TestAsync extends AsyncTask<File,Boolean,Void>{

    private static final String MY_ACCESS_KEY_ID="";
    private static final String MY_SECRET_KEY="";
    private static final String DENUNCIA_BUCKET ="";

    @Override
    protected Void doInBackground(File... params) {

        File file = params[0];

        AmazonS3Client s3Client = new AmazonS3Client( new BasicAWSCredentials( MY_ACCESS_KEY_ID, MY_SECRET_KEY ) );

        if(!s3Client.doesBucketExist(DENUNCIA_BUCKET))
            s3Client.createBucket(DENUNCIA_BUCKET);

        PutObjectRequest por = new PutObjectRequest( DENUNCIA_BUCKET,file.getName(), file );
        s3Client.putObject( por );


        return null;

    }
}
