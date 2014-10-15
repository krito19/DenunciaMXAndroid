package com.syca.apps.gob.denunciamx;

import android.test.suitebuilder.TestSuiteBuilder;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Created by JARP on 10/15/14.
 */
public class FullTestSuite extends TestSuite {

    public static Test suite()
    {
        return new TestSuiteBuilder(FullTestSuite.class).
                includeAllPackagesUnderHere().build();
    }

    public  FullTestSuite()
    {
        super();
    }
}
