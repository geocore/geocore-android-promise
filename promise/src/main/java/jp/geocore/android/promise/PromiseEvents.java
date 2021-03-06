package jp.geocore.android.promise;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.List;

import jp.geocore.android.Geocore;
import jp.geocore.android.GeocoreCallback;
import jp.geocore.android.model.GeocoreEvent;
import jp.geocore.android.model.GeocorePlace;
import jp.geocore.android.model.GeocorePlaceEvent;
import jp.geocore.android.request.GeocoreRequest;

/**
 * Created by watanabejunta on 2015/11/06.
 */
public class PromiseEvents {

    /*
    public static Promise<GeocoreEvent, Exception, Void> create(GeocoreEvent event, GeocoreRelationship relationship) {
        final Deferred<GeocoreEvent, Exception, Void> deferred = new DeferredObject<>();
        try {
            Geocore.getInstance().events.create(event, relationship, new GeocoreCallback<GeocoreEvent>() {
                @Override
                public void onComplete(GeocoreEvent geocoreEvent, Exception e) {
                    if (e != null)
                        deferred.reject(e);
                    else
                        deferred.resolve(geocoreEvent);
                }
            });
        } catch (UnsupportedEncodingException e) {
            deferred.reject(e);
        } catch (JSONException e) {
            deferred.reject(e);
        }
        return deferred.promise();
    }
    */

    public static Promise<List<GeocoreEvent>, Exception, Void> events(GeocoreRequest query) {
        final Deferred<List<GeocoreEvent>, Exception, Void> deferred = new DeferredObject<>();
        GeocoreEvent.list(query, new GeocoreCallback<List<GeocoreEvent>>() {
            @Override
            public void onComplete(List<GeocoreEvent> geocoreEvents, Exception e) {
                if (e != null)
                    deferred.reject(e);
                else
                    deferred.resolve(geocoreEvents);
            }
        });
        return deferred.promise();
    }

    public static Promise<GeocoreEvent, Exception, Void> save(GeocoreEvent event) {
        final Deferred<jp.geocore.android.model.GeocoreEvent, java.lang.Exception, java.lang.Void> deferred = new DeferredObject<>();
        try {
            Geocore.getInstance().events.save(event, new GeocoreCallback<jp.geocore.android.model.GeocoreEvent>() {
                @Override
                public void onComplete(jp.geocore.android.model.GeocoreEvent geocoreEvent, java.lang.Exception e) {
                    if (e != null)
                        deferred.reject(e);
                    else
                        deferred.resolve(geocoreEvent);
                }
            });
        } catch (UnsupportedEncodingException e) {
            deferred.reject(e);
        } catch (JSONException e) {
            deferred.reject(e);
        }
        return deferred.promise();
    }

    public static Promise<GeocoreEvent, Exception, Void> delete(GeocoreEvent event) {
        final Deferred<jp.geocore.android.model.GeocoreEvent, java.lang.Exception, java.lang.Void> deferred = new DeferredObject<>();
        Geocore.getInstance().events.delete(event, new GeocoreCallback<jp.geocore.android.model.GeocoreEvent>() {
            @Override
            public void onComplete(jp.geocore.android.model.GeocoreEvent geocoreEvent, java.lang.Exception e) {
                if (e != null)
                    deferred.reject(e);
                else
                    deferred.resolve(geocoreEvent);
            }
        });
        return deferred.promise();
    }

    public static Promise<List<GeocorePlaceEvent>, Exception, Void> connectWithPlace(GeocoreEvent event,
                                                                         GeocorePlace place) {
        final Deferred<List<GeocorePlaceEvent>, Exception, Void> deferred = new DeferredObject<>();
        try {
            Geocore.getInstance().events.connectWithPlace(event, place, new GeocoreCallback<List<GeocorePlaceEvent>>() {
                @Override
                public void onComplete(List<GeocorePlaceEvent> geocorePlaceEvents, Exception e) {
                    if (e != null)
                        deferred.reject(e);
                    else
                        deferred.resolve(geocorePlaceEvents);
                }
            });
        } catch (UnsupportedEncodingException e) {
            deferred.reject(e);
        } catch (JSONException e) {
            deferred.reject(e);
        }
        return deferred.promise();
    }

    public static Promise<List<GeocorePlaceEvent>, Exception, Void> getPlaces(GeocoreEvent event) {
        final Deferred<List<GeocorePlaceEvent>, Exception, Void> deferred = new DeferredObject<>();
        Geocore.getInstance().events.getPlaces(event, new GeocoreCallback<List<GeocorePlaceEvent>>() {
            @Override
            public void onComplete(List<GeocorePlaceEvent> geocorePlaceEvents, Exception e) {
                if (e != null)
                    deferred.reject(e);
                else
                    deferred.resolve(geocorePlaceEvents);
            }
        });
        return deferred.promise();
    }

    public static Promise<List<GeocorePlaceEvent>, Exception, Void> getPlaces(String id) {
        final Deferred<List<GeocorePlaceEvent>, Exception, Void> deferred = new DeferredObject<>();
        Geocore.getInstance().events.getPlaces(id, new GeocoreCallback<List<GeocorePlaceEvent>>() {
            @Override
            public void onComplete(List<GeocorePlaceEvent> geocorePlaceEvents, Exception e) {
                if (e != null)
                    deferred.reject(e);
                else
                    deferred.resolve(geocorePlaceEvents);
            }
        });
        return deferred.promise();
    }
}
