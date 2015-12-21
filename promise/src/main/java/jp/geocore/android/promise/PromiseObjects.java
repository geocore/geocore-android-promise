package jp.geocore.android.promise;

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

    public static Promise<List<GeocoreObject>, Exception, Void> getByCustomData(String key, String value) {
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

    public static Promise<GeocoreObject, Exception, Void> upvote(String objectId) {
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

    public static Promise<GeocoreObject, Exception, Void> downvote(String objectId) {
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
