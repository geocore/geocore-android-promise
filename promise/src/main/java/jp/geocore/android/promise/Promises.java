package jp.geocore.android.promise;


import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

import java.io.InputStream;
import java.net.URI;

import jp.geocore.android.Geocore;
import jp.geocore.android.GeocoreCallback;
import jp.geocore.android.GeocoreServerError;
import jp.geocore.android.model.GeocoreBinaryDataInfo;
import jp.geocore.android.model.GeocoreUser;
import lombok.Data;

/**
 * Created by kakkar on 2015/08/19.
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
    private Context context;

    private Promises(Context context) {
        this.context = context;
    }


    public static synchronized Promises getInstance(Context context) {
        if (Promises.instance == null) {
            Promises.instance = new Promises(context);
        }
        return Promises.instance;
    }

    public static synchronized Promises getInstance() {
        return Promises.instance;
    }

    // =============================================================================================
    //
    // =============================================================================================
/*
    public String getUserId(String projectId) {
        String userId;
    }
*/
    public Promise<GeocoreUser, Exception, Void> login() {
        ApplicationInfo appInfo = null;
        String userId = null;
        String password = new StringBuffer(Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID)).reverse().toString();
        try {
            appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(),
                    PackageManager.GET_META_DATA);
            String projectId = (String) appInfo.metaData.get("GEOCORE_PROJECT_ID");
            int index = 0;
            if (projectId != null) {
                index = projectId.indexOf("PRO-");
            }
            index += "PRO-".length();
            if (projectId != null) {
                userId = "USE-" + projectId.substring(index) + "-" + Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return login(userId, password);
    }

    public Promise<GeocoreUser, Exception, Void> login(final String userId, final String password) {
        // name <- userId
        // email <- userId@geocore.jp

        // try to get Geocore.getInstance()
        // if geocore is initialized then do login
        // if geocore is NOT initialized, then
        //    try to initialize by getting settings from manifest
        //    now that geocore is initialized, try to login
        //    if login is not successful with Auth.0001 error, register

        final Deferred<GeocoreUser, Exception, Void> deferred = new DeferredObject<>();

        //Initializing geocore
        Geocore geocore = null;
        try {
            geocore = Geocore.getInstance();
        } catch (IllegalStateException ignore) {
        }

        if (geocore == null) {
            // get geocore settings
            String apiServer = null, projectId = null;

            try {
                ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                        context.getPackageName(),
                        PackageManager.GET_META_DATA);
                apiServer = (String) appInfo.metaData.get("GEOCORE_API_SERVER");
                projectId = (String) appInfo.metaData.get("GEOCORE_PROJECT_ID");
                if (apiServer == null || projectId == null) {
                    String errMessage = "Expecting GEOCORE_API_SERVER and GEOCORE_PROJECT_ID to be defined in manifest.";
                    Log.e(TAG, errMessage);
                    throw new Exception(errMessage);
                } else {
                    geocore = Geocore.getInstance(context, apiServer, projectId);
                }
            } catch (Exception e) {
                // shouldn't happen
                Log.e(TAG, e.getMessage(), e);
                deferred.reject(e);
            }
        }

        if (geocore != null && !deferred.isRejected()) {
            //login
            geocore.login(userId, password, new GeocoreCallback<GeocoreUser>() {
                @Override
                public void onComplete(GeocoreUser geocoreUser, Exception e) {
                    if (e != null) {
                        if (e instanceof GeocoreServerError) {
                            GeocoreServerError gse = (GeocoreServerError) e;
                            if ("Auth.0001".equals(gse.getCode())) {
                                Promises.d("registeration is needed");
                                Geocore.getInstance().register(userId, password, userId, userId + "@geocore.com", new GeocoreCallback<GeocoreUser>() {
                                    @Override
                                    public void onComplete(GeocoreUser geocoreUser, Exception e) {
                                        if (e != null) {
                                            deferred.reject(e);
                                        } else {
                                            deferred.resolve(geocoreUser);
                                        }
                                    }
                                });
                            } else {
                                deferred.reject(e);
                            }
                        } else {
                            deferred.reject(e);
                        }
                    } else
                        deferred.resolve(geocoreUser);
                }
            });
        }

        return deferred.promise();
    }

    @Data
    public static class ImageInfo {
        Bitmap bitmap;
        String url;
        String id;
        String key;

        public ImageInfo (Bitmap bitmap, String url, String id, String key) {
            this.bitmap = bitmap;
            this.url = url;
            this.id = id;
            this.key = key;
        }
    }

    public static Promise<ImageInfo, Exception, Void> image(final String objectId, final String key) {
        final Deferred<ImageInfo, Exception, Void> deferred = new DeferredObject<>();
        Geocore.getInstance().getBinaryDataInfo(objectId, key, new GeocoreCallback<GeocoreBinaryDataInfo>() {
            @Override
            public void onComplete(final GeocoreBinaryDataInfo geocoreBinaryDataInfo, Exception e) {
                if (e != null) {
                    deferred.reject(e);
                } else {
                    new AsyncTask<Void, Void, Void>(){
                        Bitmap bmp;

                        @Override
                        protected Void doInBackground(Void... params) {
                            bmp = downloadImage(geocoreBinaryDataInfo.getUrl());
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void result){
                            ImageInfo imageInfo = new ImageInfo(bmp, geocoreBinaryDataInfo.getUrl(), objectId, key);
                            deferred.resolve(imageInfo);
                        }
                    }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                }
            }
        });
        return deferred.promise();
    }

    private static Bitmap downloadImage(String uri) {
        Log.d(TAG, "downloadImage: uri=" + uri);
        HttpGet httpGet = new HttpGet();
        HttpClient httpClient = new DefaultHttpClient();
        try {
            httpGet.setURI(new URI(uri));
            HttpResponse resp = httpClient.execute(httpGet);
            Log.d(TAG,"HttpResponse: "+resp.getStatusLine().getStatusCode());

            if (resp.getStatusLine().getStatusCode() < 400) {
                InputStream is = resp.getEntity().getContent();
                Bitmap bit = BitmapFactory.decodeStream(is);
                is.close();
                return bit;
            }
        } catch (Exception e) {
            Log.d(TAG, "downloadImage error");
            e.printStackTrace();
        }

        return null;
    }


/**
 * <span class="en">Login to Geocore.</span>
 * <span class="ja">Geocoreにログインする。</span>
 *
 * @param userId User's ID.
 * @param userName User's name.
 * @param fbId facebook ID.
 * @param url url for the server. "http://dev1-geocore-jp-5m76jmgs7ocx.runscope.net/api
 * @param serverName server name, ie. "PRO-TEST-1"
 *
 */

    /*
    public Promise<GeocoreUser, Exception, Void> geocoreLogin(
            final Context context,
            final String fbId,
            final String userName,
            final String userId,
            final String url,
            final String serverName) {

        final Deferred<GeocoreUser, Exception, Void> deferred = new DeferredObject<>();
        final String userPassword = (new StringBuilder(fbId)).reverse().toString();
        Promises.d(url);
        Promises.d("fbId = " + fbId + "userName = " + userName + "userId = " + userName);

        Geocore.getInstance(context, url, serverName ).login(userId, userPassword, new GeocoreCallback<GeocoreUser>() {
            @Override
            public void onComplete(GeocoreUser geocoreUser, Exception e) {
                if (e != null) {
                    if (e instanceof GeocoreServerError) {
                        GeocoreServerError gse = (GeocoreServerError) e;
                        if ("Auth.0001".equals(gse.getCode())) {
                            Promises.d("registeration is needed");
                            Geocore.getInstance().register(userId, userPassword, userId, fbId + "@geocore.com", new GeocoreCallback<GeocoreUser>() {
                                @Override
                                public void onComplete(GeocoreUser geocoreUser, Exception e) {
                                    if (e != null) {
                                        deferred.reject(e);
                                    } else
                                        deferred.resolve(geocoreUser);
                                }
                            });
                        }
                    }
                    deferred.reject(e);
                } else
                    deferred.resolve(geocoreUser);
            }
        });
        return deferred.promise();
    }


    public Promise<GeocoreTrackPoint, Exception, Void> LocationLog(Location location) {
        final Deferred<GeocoreTrackPoint, Exception, Void> deferred = new DeferredObject<>();
        Log.d(TAG, "geocoreLocationLog");
        try {
            Geocore.getInstance().tracks.sendLocationLog(location, new GeocoreCallback<GeocoreTrackPoint>() {
                @Override
                public void onComplete(GeocoreTrackPoint geocoreTrackPoint, Exception e) {
                    if (e != null){
                        deferred.reject(e);
                    }
                    else {
                        deferred.resolve(geocoreTrackPoint);
                    }
                }
            });
        } catch (UnsupportedEncodingException e) {
            deferred.reject(e);
        } catch (JSONException e) {
            deferred.reject(e);
        }
        return null;
    }

    public Promise<GeocoreUser, Exception, Void> PushRegistration(String token) {
        Log.d(TAG, "push Registration");
        final Deferred<GeocoreUser, Exception, Void> deferred = new DeferredObject<>();
        Geocore.getInstance().users.registerForPushNotification(token, new GeocoreCallback<GeocoreUser>() {
            @Override
            public void onComplete(GeocoreUser geocoreUser, Exception e) {
                if (e != null) {
                    deferred.reject(e);
                } else {
                    deferred.resolve(geocoreUser);
                }
            }
        });
        return deferred.promise();

    }

*/
}