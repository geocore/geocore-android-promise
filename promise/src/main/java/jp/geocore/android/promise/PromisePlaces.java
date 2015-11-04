package jp.geocore.android.promise;

import android.content.Context;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

import java.util.List;

import jp.geocore.android.Geocore;
import jp.geocore.android.GeocoreCallback;
import jp.geocore.android.model.GeocorePlace;
import jp.geocore.android.request.GeocoreRequest;

/**
 * Created by watanabejunta on 2015/11/04.
 */
public class PromisePlaces {

    private static PromisePlaces instance;
    private Context context;

    private PromisePlaces(Context context) {
        this.context = context;
    }


    public static synchronized PromisePlaces getInstance(Context context) {
        if (PromisePlaces.instance == null) {
            PromisePlaces.instance = new PromisePlaces(context);
        }
        return PromisePlaces.instance;
    }

    public static synchronized PromisePlaces getInstance() {
        return PromisePlaces.instance;
    }

    public Promise<List<GeocorePlace>, Exception, Void> places() {
        final Deferred<List<GeocorePlace>, Exception, Void> deferred = new DeferredObject<>();
        Geocore.getInstance().places.get(new GeocoreCallback<List<GeocorePlace>>() {
            @Override
            public void onComplete(List<GeocorePlace> geocorePlaces, Exception e) {
                if (e != null)
                    deferred.reject(e);
                else
                    deferred.resolve(geocorePlaces);
            }
        });
        return deferred.promise();
    }

    public Promise<List<GeocorePlace>, Exception, Void> places(GeocoreRequest query) {
        final Deferred<List<GeocorePlace>, Exception, Void> deferred = new DeferredObject<>();
        Geocore.getInstance().places.get(query, new GeocoreCallback<List<GeocorePlace>>() {
            @Override
            public void onComplete(List<GeocorePlace> geocorePlaces, Exception e) {
                if (e != null)
                    deferred.reject(e);
                else
                    deferred.resolve(geocorePlaces);
            }
        });
        return deferred.promise();
    }
}
