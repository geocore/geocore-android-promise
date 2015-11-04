package jp.geocore.android.promise;

import android.content.Context;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

import java.util.List;

import jp.geocore.android.Geocore;
import jp.geocore.android.GeocoreCallback;
import jp.geocore.android.model.GeocoreUser;
import jp.geocore.android.request.GeocoreRequest;

/**
 * Created by watanabejunta on 2015/11/04.
 */
public class PromiseUsers {

    private static PromiseUsers instance;
    private Context context;

    private PromiseUsers(Context context) {
        this.context = context;
    }


    public static synchronized PromiseUsers getInstance(Context context) {
        if (PromiseUsers.instance == null) {
            PromiseUsers.instance = new PromiseUsers(context);
        }
        return PromiseUsers.instance;
    }

    public static synchronized PromiseUsers getInstance() {
        return PromiseUsers.instance;
    }

    public Promise<GeocoreUser, Exception, Void> user(String id) {
        final Deferred<GeocoreUser, Exception, Void> deferred = new DeferredObject<>();
        Geocore.getInstance().users.get(id, new GeocoreCallback<GeocoreUser>() {
            @Override
            public void onComplete(GeocoreUser geocoreUser, Exception e) {
                if (e != null)
                    deferred.reject(e);
                else
                    deferred.resolve(geocoreUser);
            }
        });
        return deferred.promise();
    }

    public Promise<List<GeocoreUser>, Exception, Void> users(GeocoreRequest query) {
        final Deferred<List<GeocoreUser>, Exception, Void> deferred = new DeferredObject<>();
        Geocore.getInstance().users.get(query, new GeocoreCallback<List<GeocoreUser>>() {
            @Override
            public void onComplete(List<GeocoreUser> geocoreUsers, Exception e) {
                if (e != null)
                    deferred.reject(e);
                else
                    deferred.resolve(geocoreUsers);
            }
        });
        return deferred.promise();
    }
}
