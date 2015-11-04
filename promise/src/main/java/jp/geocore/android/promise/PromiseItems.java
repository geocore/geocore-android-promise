package jp.geocore.android.promise;

import android.content.Context;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.List;

import jp.geocore.android.Geocore;
import jp.geocore.android.GeocoreCallback;
import jp.geocore.android.GeocoreVoidCallback;
import jp.geocore.android.model.GeocoreItem;
import jp.geocore.android.request.GeocoreRequest;

/**
 * Created by watanabejunta on 2015/11/04.
 */
public class PromiseItems {

    private static PromiseItems instance;
    private Context context;

    private PromiseItems(Context context) {
        this.context = context;
    }


    public static synchronized PromiseItems getInstance(Context context) {
        if (PromiseItems.instance == null) {
            PromiseItems.instance = new PromiseItems(context);
        }
        return PromiseItems.instance;
    }

    public static synchronized PromiseItems getInstance() {
        return PromiseItems.instance;
    }

    public Promise<GeocoreItem, Exception, Void> item(String id) {
        final Deferred<GeocoreItem, Exception, Void> deferred = new DeferredObject<>();
        Geocore.getInstance().items.get(id, new GeocoreCallback<GeocoreItem>() {
            @Override
            public void onComplete(GeocoreItem geocoreItem, Exception e) {
                if (e != null)
                    deferred.reject(e);
                else
                    deferred.resolve(geocoreItem);
            }
        });
        return deferred.promise();
    }

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

    public Promise<GeocoreItem, Exception, Void> save(GeocoreItem item) {
        final Deferred<GeocoreItem, Exception, Void> deferred = new DeferredObject<>();
        try {
            Geocore.getInstance().items.save(item, new GeocoreCallback<GeocoreItem>() {
                @Override
                public void onComplete(GeocoreItem geocoreItem, Exception e) {
                    if (e != null)
                        deferred.reject(e);
                    else
                        deferred.resolve(geocoreItem);
                }
            });
        } catch (UnsupportedEncodingException e) {
            deferred.reject(e);
        } catch (JSONException e) {
            deferred.reject(e);
        }
        return deferred.promise();
    }

    public Promise<String, Exception, Void> delete(final String id) {
        final Deferred<String, Exception, Void> deferred = new DeferredObject<>();
        Geocore.getInstance().items.delete(id, new GeocoreVoidCallback() {
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
