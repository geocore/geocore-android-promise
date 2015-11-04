package jp.geocore.android.promise;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

import java.util.List;

import jp.geocore.android.Geocore;
import jp.geocore.android.GeocoreCallback;
import jp.geocore.android.model.GeocoreTag;

/**
 * Created by watanabejunta on 2015/11/04.
 */
public class PromiseTags {

    public Promise<List<GeocoreTag>, Exception, Void> tags() {
        final Deferred<List<GeocoreTag>, Exception, Void> deferred = new DeferredObject<>();
        Geocore.getInstance().tags.get(new GeocoreCallback<List<GeocoreTag>>() {
            @Override
            public void onComplete(List<GeocoreTag> geocoreTags, Exception e) {
                if (e != null)
                    deferred.reject(e);
                else
                    deferred.resolve(geocoreTags);
            }
        });
        return deferred.promise();
    }
}
