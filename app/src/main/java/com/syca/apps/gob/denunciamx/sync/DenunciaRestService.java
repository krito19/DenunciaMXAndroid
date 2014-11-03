package com.syca.apps.gob.denunciamx.sync;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.syca.apps.gob.denunciamx.data.DenunciaContract;
import com.syca.apps.gob.denunciamx.model.DenunciaRest.Audio;
import com.syca.apps.gob.denunciamx.model.DenunciaRest.Denuncia;
import com.syca.apps.gob.denunciamx.model.DenunciaRest.Foto;
import com.syca.apps.gob.denunciamx.model.DenunciaRest.Ubicacion;
import com.syca.apps.gob.denunciamx.model.DenunciaRest.VideoEvidencia;
import com.syca.apps.gob.denunciamx.model.DenunciaTableItem;
import com.syca.apps.gob.denunciamx.model.EvidenciaMediaType;
import com.syca.apps.gob.denunciamx.model.EvidenciaTableItem;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class DenunciaRestService extends IntentService {

    private static final String API_URL = "http://denunciamx-env.elasticbeanstalk.com/";

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_DEVICE = "com.syca.apps.gob.denunciamx.sync.action.POST_DEVICE";
    private static final String ACTION_POST_DENUNCIA = "com.syca.apps.gob.denunciamx.sync.action.POST_DENUNCIA";

    // TODO: Rename parameters
    private static final String EXTRA_GMC_TOKEN = "com.syca.apps.gob.denunciamx.sync.extra.GCM_TOKEN";
    private static final String EXTRA_ID_DENUNCIA = "com.syca.apps.gob.denunciamx.sync.extra.ID_DENUNCIA";

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionRegisterDispositivo(Context context, String gmcToken) {
        Intent intent = new Intent(context, DenunciaRestService.class);
        intent.setAction(ACTION_DEVICE);
        intent.putExtra(EXTRA_GMC_TOKEN, gmcToken);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Post Denuncia with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionPostDenuncia(Context context, String idDenuncia) {
        Intent intent = new Intent(context, DenunciaRestService.class);
        intent.setAction(ACTION_POST_DENUNCIA);
        intent.putExtra(EXTRA_ID_DENUNCIA, idDenuncia);
        context.startService(intent);
    }



    public DenunciaRestService() {
        super("DenunciaRestService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_DEVICE.equals(action)) {
                final String token = intent.getStringExtra(EXTRA_GMC_TOKEN);
                Dispositivo d = new Dispositivo(token,2);
                handleRegisterDispositivo(d);

            }
            else if(ACTION_POST_DENUNCIA.equals(action)) {
                final String idDenuncia = intent.getStringExtra(EXTRA_ID_DENUNCIA);
                handleUploadDenuncia(idDenuncia);
            }
        }
    }


    private interface DenunciaRest
    {
        @POST("/denuncia")
        void newDenuncia(@Body Denuncia denuncia , Callback<Denuncia> cb);

        @POST("/dispositivo")
        void registerDispositivo(@Body Dispositivo dispositivo,Callback<Dispositivo>dispositivoCallback);
    }

    private void handleUploadDenuncia(String idDenuncia)
    {

        RestAdapter restAdapter = new RestAdapter.Builder()
                                                .setEndpoint(API_URL)
                                                .setLogLevel(RestAdapter.LogLevel.FULL)
                                                .setLog(new RestAdapter.Log() {
                                                        public void log(String msg) {
                                                            Log.i("retrofit", msg);
                                                        }
                                                    })
                                                .build();

        //By now we will make sync
        DenunciaRest denunciaApi =restAdapter.create(DenunciaRest.class);

        Denuncia denuncia = getDenunciaFromResolver(idDenuncia);

        denunciaApi.newDenuncia(denuncia, (Callback<Denuncia>) callback);

    }

    private void handleRegisterDispositivo(Dispositivo d)
    {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new RestAdapter.Log() {
                    public void log(String msg) {
                        Log.i("retrofit", msg);
                    }
                })
                .build();

        //Register device
        DenunciaRest denunciaApi =restAdapter.create(DenunciaRest.class);
        denunciaApi.registerDispositivo(d, (Callback<Dispositivo>) callback);
    }

    Callback<?> callback = new Callback<Object>() {
        @Override
        public void success(Object o, Response response) {

            String stop=response.getReason();
        }

        @Override
        public void failure(RetrofitError error) {
            String stop=error.getMessage();

        }
    };


    private Denuncia getDenunciaFromResolver(String idDenuncia)
    {
        ContentResolver contentResolver = getContentResolver();

        Uri denunciaUri = DenunciaContract.DenunciaInfoEntry.buildDenunciaAndIdInternoUri(idDenuncia);

        //Get info denuncia
        Cursor cursor = contentResolver.query(denunciaUri,null,null,null,null);

        cursor.moveToFirst();

        DenunciaTableItem denunciaDBItem = DenunciaTableItem.getDenunciaTable(cursor);

        Denuncia.DenunciaBuilder builderDenuncia =
                new Denuncia.DenunciaBuilder(denunciaDBItem.id_interno, denunciaDBItem.id_spf, denunciaDBItem.token,
                                             denunciaDBItem.anonima,denunciaDBItem.email,denunciaDBItem.titulo,Integer.parseInt(denunciaDBItem.id_dependencia),
                                             denunciaDBItem.estatus,denunciaDBItem.fecha_creacion);

        denunciaUri = DenunciaContract.DenunciaEvidenciaEntry.buildDenunciaWithIdInternoUri(idDenuncia);

        //Get evidencias
        cursor = contentResolver.query(denunciaUri,null,null,null,null);
        cursor.moveToFirst();
        EvidenciaTableItem evidenciaItem ;
        do {

            evidenciaItem=EvidenciaTableItem.getEvidenciaItem(cursor);

            if(evidenciaItem.evidenciaType.equals(String.valueOf(EvidenciaMediaType.AUDIO)))
            {
                builderDenuncia.addAudio(new Audio(evidenciaItem.evidenciaFullPath, evidenciaItem.s3Path));
            }
            else if(evidenciaItem.evidenciaType.equals(String.valueOf(EvidenciaMediaType.VIDEO)))
            {
                builderDenuncia.addVideo(new VideoEvidencia(evidenciaItem.evidenciaFullPath, evidenciaItem.s3Path));
            }
            else if(evidenciaItem.evidenciaType.equals(String.valueOf(EvidenciaMediaType.FOTO)))
            {
                builderDenuncia.addFoto(new Foto(evidenciaItem.evidenciaFullPath, evidenciaItem.s3Path));
            }

        }while (cursor.moveToNext());

        cursor.close();

        builderDenuncia.addUbicacion(new Ubicacion(5.5f,6.6f));

        return builderDenuncia.buildDenuncia();

    }


    class Dispositivo
    {
        private String token;
        private int os;

        public Dispositivo() {
            os=2;
        }

        public Dispositivo(String token, int os) {
            super();
            this.token = token;
            this.os = 2;

        }


        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getOs() {
            return os;
        }

        public void setOs(int os) {
            this.os = os;
        }
    }


}
