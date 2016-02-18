package jp.geocore.android.promise;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

import java.util.List;

import jp.geocore.android.Geocore;
import jp.geocore.android.GeocoreCallback;
import jp.geocore.android.GeocoreServicePlaces;
import jp.geocore.android.GeocoreVoidCallback;
import jp.geocore.android.model.GeocorePlace;
import jp.geocore.android.request.GeocorePlaceQueryRequest;
import jp.geocore.android.request.GeocoreRequest;

/**
 * Created by watanabejunta on 2015/11/04.
 */
public class PromisePlaces {

    public static Promise<List<GeocorePlace>, Exception, Void> places() {
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

    public static Promise<List<GeocorePlace>, Exception, Void> places(GeocoreRequest query) {
        final Deferred<List<GeocorePlace>, Exception, Void> deferred = new DeferredObject<>();
        GeocorePlace.list(query, new GeocoreCallback<List<GeocorePlace>>() {
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

    public static Promise<GeocorePlace, Exception, Void> place(String id) {
        final Deferred<GeocorePlace, Exception, Void> deferred = new DeferredObject<>();
        GeocorePlace.get(new GeocorePlaceQueryRequest().withId(id), new GeocoreCallback<GeocorePlace>() {
            @Override
            public void onComplete(GeocorePlace geocorePlace, Exception e) {
                if (e != null)
                    deferred.reject(e);
                else
                    deferred.resolve(geocorePlace);
            }
        });
        /*
        Geocore.getInstance().places.get(id, new GeocoreCallback<GeocorePlace>() {
            @Override
            public void onComplete(GeocorePlace geocorePlace, Exception e) {
                if (e != null)
                    deferred.reject(e);
                else
                    deferred.resolve(geocorePlace);
            }
        });
        */
        return deferred.promise();
    }

    public static Promise<GeocorePlace, Exception, Void> save(GeocorePlace place) {
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

    public static Promise<GeocorePlace, Exception, Void> save(GeocoreServicePlaces.PlaceSaveRequest saveRequest) {
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

    public static Promise<GeocorePlace, Exception, Void> create(GeocorePlace place) {
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

    public static Promise<GeocorePlace, Exception, Void> create(GeocoreServicePlaces.PlaceCreateRequest createRequest) {
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

    public static Promise<String, Exception, Void> delete(final String id) {
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
