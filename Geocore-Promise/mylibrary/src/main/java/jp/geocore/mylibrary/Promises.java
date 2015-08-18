package jp.geocore.mylibrary;

import android.content.Context;
import android.util.Log;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

import jp.geocore.android.Geocore;
import jp.geocore.android.GeocoreCallback;
import jp.geocore.android.GeocoreServerError;
import jp.geocore.android.model.GeocoreUser;

/**
 * Created by kakkar on 2015/08/18.
 */
public class Promises {

    // =============================================================================================
    // Debug
    // =============================================================================================

    private static final String TAG = "Promises";
    private static boolean DEBUG = true;

    public static void d(String message) {
        if (DEBUG) {
            Log.d(TAG, message);
        }
    }

    public static void d(String message, Exception e) {
        if (DEBUG) {
            Log.d(TAG, message, e);
        }
    }

    public static void e(String message, Exception e) {
        Log.e(TAG, message, e);
    }

    public static void e(String message) {
        Log.e(TAG, message);
    }

    public static void setDebug(boolean debug) {
        DEBUG = debug;
    }

    private static Promises instance;

    private Promises() {
    }

    public static synchronized Promises getInstance() {
        if (Promises.instance == null) {
            Promises.instance = new Promises();
        }
        return Promises.instance;
    }


    // =============================================================================================
    //
    // =============================================================================================

    /**
     * <span class="en">Login to Geocore.</span>
     * <span class="ja">Geocoreにログインする。</span>
     *
     * @param fbId User's ID or name.
     * @param fbName User's password
     */
    public Promise<GeocoreUser, Exception, Void> geocoreLogin(
            final Context context,
            final String fbId,
            String fbName,
            final String userId) {

        final Deferred<GeocoreUser, Exception, Void> deferred = new DeferredObject<>();
        final String userPassword = (new StringBuilder(fbId)).reverse().toString();
        String url = "http://dev1-geocore-jp-5m76jmgs7ocx.runscope.net/api";
        Promises.d(url);

        Geocore.getInstance(context, url, "PRO-TEST-1").login(userId, userPassword, new GeocoreCallback<GeocoreUser>() {
            @Override
            public void onComplete(GeocoreUser geocoreUser, Exception e) {
                if (e != null) {
                    if (e instanceof GeocoreServerError) {
                        GeocoreServerError gse = (GeocoreServerError) e;
                        if ("Auth.0001".equals(gse.getCode())) {
                            Promises.d("Need to register");
                        }
                        else
                            Promises.d("Not registered Error but could not login");
                    }
                    deferred.reject(e);
                }
                else
                    deferred.resolve(geocoreUser);
            }
        });
        return deferred.promise();
    }



}


