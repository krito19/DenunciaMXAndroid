package com.syca.apps.gob.denunciamx.sync;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.syca.apps.gob.denunciamx.R;

import java.io.IOException;

/**
 * Created by JARP on 11/3/14.
 */
public class GCMRegisterer {



    private Context mContext;

    private GoogleCloudMessaging gcm;

    private String projectNumber ;

    private String token;

    private String TAG =GCMRegisterer.class.getSimpleName();

    public GCMRegisterer(Context context)
    {
        this.mContext=context;
        projectNumber=mContext.getResources().getString(R.string.gcm_project_number);
    }

    public void register(){
        gcm = GoogleCloudMessaging.getInstance(mContext);
        AsyncRegistration asyncRegistration = new AsyncRegistration();
        asyncRegistration.execute(null);

    }


    private class AsyncRegistration extends AsyncTask
    {
        @Override
        protected Object doInBackground(Object[] params) {

            try
            {
                token= gcm.register(projectNumber);
                registerToken(token);
            }
            catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "Error in cloud registration ", e);
                return null ;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            DenunciaRestService.startActionRegisterDispositivo(mContext,token);
        }

        private void registerToken(String token)
        {
            RegistrationFile register = new RegistrationFile(mContext);
            register.writeToken(token);
        }
    }


}
