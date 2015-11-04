package jp.geocore.android.promise;

import android.content.Context;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

import java.util.List;

import jp.geocore.android.Geocore;
import jp.geocore.android.GeocoreCallback;
import jp.geocore.android.model.GeocoreObject;

/**
 * Created by watanabejunta on 2015/11/04.
 */
public class PromiseObjects {

    private static PromiseObjects instance;
    private Context context;

    private PromiseObjects(Context context) {
        this.context = context;
    }

    public static synchronized PromiseObjects getInstance(Context context) {
        if (PromiseObjects.instance == null) {
            PromiseObjects.instance = new PromiseObjects(context);
        }
        return PromiseObjects.instance;
    }

    public static synchronized PromiseObjects getInstance() {
        return PromiseObjects.instance;
    }

    public Promise<List<GeocoreObject>, Exception, Void> getByCustomData(String key, String value) {
        final Deferred<List<GeocoreObject>, Exception, Void> deferred = new DeferredObject<>();
        Geocore.getInstance().objects.getByCustomData(key, value, new GeocoreCallback<List<GeocoreObject>>() {
            @Override
            public void onComplete(List<GeocoreObject> geocoreObjects, Exception e) {
                if (e != null)
                    deferred.reject(e);
                else
                    deferred.resolve(geocoreObjects);
            }
        });
        return deferred.promise();
    }

    public Promise<GeocoreObject, Exception, Void> upvote(String objectId) {
        final Deferred<GeocoreObject, Exception, Void> deferred = new DeferredObject<>();
        Geocore.getInstance().objects.upvote(objectId, new GeocoreCallback<GeocoreObject>() {
            @Override
            public void onComplete(GeocoreObject geocoreObject, Exception e) {
                if (e != null)
                    deferred.reject(e);
                else
                    deferred.resolve(geocoreObject);
            }
        });
        return deferred.promise();
    }

    public Promise<GeocoreObject, Exception, Void> downvote(String objectId) {
        final Deferred<GeocoreObject, Exception, Void> deferred = new DeferredObject<>();
        Geocore.getInstance().objects.downvote(objectId, new GeocoreCallback<GeocoreObject>() {
            @Override
            public void onComplete(GeocoreObject geocoreObject, Exception e) {
                if (e != null)
                    deferred.reject(e);
                else
                    deferred.resolve(geocoreObject);
            }
        });
        return deferred.promise();
    }
}
