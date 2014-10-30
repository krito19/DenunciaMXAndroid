package com.syca.apps.gob.denunciamx.sync;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import com.syca.apps.gob.denunciamx.data.DenunciaContract;
import com.syca.apps.gob.denunciamx.model.DenunciaRest.Audio;
import com.syca.apps.gob.denunciamx.model.DenunciaRest.Denuncia;
import com.syca.apps.gob.denunciamx.model.DenunciaRest.Foto;
import com.syca.apps.gob.denunciamx.model.DenunciaRest.VideoEvidencia;
import com.syca.apps.gob.denunciamx.model.DenunciaTableItem;
import com.syca.apps.gob.denunciamx.model.EvidenciaMediaType;
import com.syca.apps.gob.denunciamx.model.EvidenciaTableItem;

import retrofit.RestAdapter;
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
    private static final String ACTION_FOO = "com.syca.apps.gob.denunciamx.sync.action.FOO";
    private static final String ACTION_BAZ = "com.syca.apps.gob.denunciamx.sync.action.BAZ";
    private static final String ACTION_POST_DENUNCIA = "com.syca.apps.gob.denunciamx.sync.action.POST_DENUNCIA";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.syca.apps.gob.denunciamx.sync.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.syca.apps.gob.denunciamx.sync.extra.PARAM2";
    private static final String EXTRA_ID_DENUNCIA = "com.syca.apps.gob.denunciamx.sync.extra.ID_DENUNCIA";

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, DenunciaRestService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, DenunciaRestService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }


    /**
     * Starts this service to perform action Baz with the given parameters. If
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
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
            else if(ACTION_POST_DENUNCIA.equals(action)) {
                final String idDenuncia = intent.getStringExtra(EXTRA_ID_DENUNCIA);
                handleUploadDenuncia(idDenuncia);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private interface DenunciaRest
    {
        @POST("/denuncia")
        void newDenuncia(@Body Denuncia denuncia);
    }

    private void handleUploadDenuncia(String idDenuncia)
    {

        RestAdapter restAdapter = new RestAdapter.Builder()
                                                .setEndpoint(API_URL)
                                                .build();

        //By now we will make sync
        DenunciaRest denunciaApi =restAdapter.create(DenunciaRest.class);

        Denuncia denuncia = getDenunciaFromResolver(idDenuncia);

        denunciaApi.newDenuncia(denuncia);

    }

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
        int idx = -1;
        EvidenciaTableItem evidenciaItem ;
        do {

            evidenciaItem=EvidenciaTableItem.getEvidenciaItem(cursor);

            if(evidenciaItem.evidenciaType.equals(String.valueOf(EvidenciaMediaType.AUDIO)))
            {
                builderDenuncia.addAudio(new Audio(evidenciaItem.evidenciaFullPath,evidenciaItem.s3Path));
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

        return builderDenuncia.buildDenuncia();

    }



}
