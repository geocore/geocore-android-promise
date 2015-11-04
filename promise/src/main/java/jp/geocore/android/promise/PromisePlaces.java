package jp.geocore.android.promise;

import android.content.Context;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

import java.util.List;

import jp.geocore.android.Geocore;
import jp.geocore.android.GeocoreCallback;
import jp.geocore.android.GeocoreServicePlaces;
import jp.geocore.android.GeocoreVoidCallback;
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

    public Promise<GeocorePlace, Exception, Void> place(String id) {
        final Deferred<GeocorePlace, Exception, Void> deferred = new DeferredObject<>();
        Geocore.getInstance().places.get(id, new GeocoreCallback<GeocorePlace>() {
            @Override
            public void onComplete(GeocorePlace geocorePlace, Exception e) {
                if (e != null)
                    deferred.reject(e);
                else
                    deferred.resolve(geocorePlace);
            }
        });
        return deferred.promise();
    }

    public Promise<GeocorePlace, Exception, Void> save(GeocorePlace place) {
        final Deferred<GeocorePlace, Exception, Void> deferred = new DeferredObject<>();
        Geocore.getInstance().places.save(place, new GeocoreCallback<GeocorePlace>() {
            @Override
            public void onComplete(GeocorePlace geocorePlace, Exception e) {
                if (e != null)
                    deferred.reject(e);
                else
                    deferred.resolve(geocorePlace);
            }
        });
        return deferred.promise();
    }

    public Promise<GeocorePlace, Exception, Void> save(GeocoreServicePlaces.PlaceSaveRequest saveRequest) {
        final Deferred<GeocorePlace, Exception, Void> deferred = new DeferredObject<>();
        Geocore.getInstance().places.save(saveRequest, new GeocoreCallback<GeocorePlace>() {
            @Override
            public void onComplete(GeocorePlace geocorePlace, Exception e) {
                if (e != null)
                    deferred.reject(e);
                else
                    deferred.resolve(geocorePlace);
            }
        });
        return deferred.promise();
    }

    public Promise<GeocorePlace, Exception, Void> create(GeocorePlace place) {
        final Deferred<GeocorePlace, Exception, Void> deferred = new DeferredObject<>();
        Geocore.getInstance().places.create(place, new GeocoreCallback<GeocorePlace>() {
            @Override
            public void onComplete(GeocorePlace geocorePlace, Exception e) {
                if (e != null)
                    deferred.reject(e);
                else
                    deferred.resolve(geocorePlace);
            }
        });
        return deferred.promise();
    }

    public Promise<GeocorePlace, Exception, Void> create(GeocoreServicePlaces.PlaceCreateRequest createRequest) {
        final Deferred<GeocorePlace, Exception, Void> deferred = new DeferredObject<>();
        Geocore.getInstance().places.create(createRequest, new GeocoreCallback<GeocorePlace>() {
            @Override
            public void onComplete(GeocorePlace geocorePlace, Exception e) {
                if (e != null)
                    deferred.reject(e);
                else
                    deferred.resolve(geocorePlace);
            }
        });
        return deferred.promise();
    }

    public Promise<String, Exception, Void> delete(final String id) {
        final Deferred<String, Exception, Void> deferred = new DeferredObject<>();
        Geocore.getInstance().places.delete(id, new GeocoreVoidCallback() {
            @Override
            public void onComplete(Exception e) {
                if (e != null)
                    deferred.reject(e);
                else
                    deferred.resolve(id);
            }
        });
        return deferred.promise();
    }
}
