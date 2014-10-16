package com.syca.apps.gob.denunciamx;

import android.test.AndroidTestCase;

import java.util.UUID;

/**
 * Created by JARP on 10/16/14.
 */
public class TestProvider extends AndroidTestCase {

    public static final String TAG = TestDB.class.getSimpleName();

    public static String token = UUID.randomUUID().toString();

    public static String id_denuncia_1 = UUID.randomUUID().toString();

    public static String id_denuncia_2 = UUID.randomUUID().toString();

    public void deleteAllRecords()
    {

    }

    public void setUp(){
        deleteAllRecords();
    }

    public void testInsertReadProvider()
    {

    }

    public void testUpdateDenuncia()
    {

    }


}
