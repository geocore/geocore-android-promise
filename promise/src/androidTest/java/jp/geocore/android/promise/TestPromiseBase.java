package jp.geocore.android.promise;


import android.content.Context;
import android.test.AndroidTestCase;
import android.test.ServiceTestCase;

import java.lang.reflect.Method;

import jp.geocore.android.Geocore;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class TestPromiseBase extends AndroidTestCase {

    public static final String GEOCORE_URL = "http://dev1-geocore-jp-5m76jmgs7ocx.runscope.net/api";
    //"http://dev1.geocore.jp/api";

    public static final String GEOCORE_PROJECT_ID = "PRO-TEST-1";

    public static final String GEOCORE_USERID = "USE-TEST-1-ADMIN-1";
    public static final String GEOCORE_USERPWD = "nimda";

    protected Geocore geocore;
    protected Promises promise;
    protected PromisePlaces places;

    protected static String TAG = "geocore promise (test)";


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        geocore.getInstance(getTestContext(), GEOCORE_URL, GEOCORE_PROJECT_ID);
        promise = Promises.getInstance(getTestContext());
        places = PromisePlaces.getInstance(getTestContext());
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * @return The {@link Context} of the test project.
     */
    private Context getTestContext()
    {
        try
        {
            Method getTestContext = ServiceTestCase.class.getMethod("getTestContext");
            return (Context) getTestContext.invoke(this);
        }
        catch (final Exception exception)
        {
            exception.printStackTrace();
            return null;
        }
    }
}