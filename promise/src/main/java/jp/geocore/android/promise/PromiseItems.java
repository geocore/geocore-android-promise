package jp.geocore.android.promise;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

import java.util.List;

import jp.geocore.android.Geocore;
import jp.geocore.android.GeocoreCallback;
import jp.geocore.android.model.GeocoreItem;
import jp.geocore.android.request.GeocoreRequest;

/**
 * Created by watanabejunta on 2015/11/04.
 */
public class PromiseItems {

    public Promise<List<GeocoreItem>, Exception, Void> items(GeocoreRequest query) {
        final Deferred<List<GeocoreItem>, Exception, Void> deferred = new DeferredObject<>();
        Geocore.getInstance().items.get(query, new GeocoreCallback<List<GeocoreItem>>() {
            @Override
            public void onComplete(List<GeocoreItem> geocoreItems, Exception e) {
                if (e != null)
                    deferred.reject(e);
                else
                    deferred.resolve(geocoreItems);
            }
        });
        return deferred.promise();
    }
}
