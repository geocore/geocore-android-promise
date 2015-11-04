package jp.geocore.android.promise;

import android.content.Context;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.geocore.android.Geocore;
import jp.geocore.android.GeocoreCallback;
import jp.geocore.android.model.GeocoreFeed;
import jp.geocore.android.model.GeocoreUser;
import jp.geocore.android.model.GeocoreUserEngagement;
import jp.geocore.android.model.GeocoreUserEngagementType;
import jp.geocore.android.model.GeocoreUserEvent;
import jp.geocore.android.model.GeocoreUserRelationship;
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

    public Promise<GeocoreUser, Exception, Void> save(GeocoreUser user) {
        final Deferred<GeocoreUser, Exception, Void> deferred = new DeferredObject<>();
        try {
            Geocore.getInstance().users.save(user, new GeocoreCallback<GeocoreUser>() {
                @Override
                public void onComplete(GeocoreUser geocoreUser, Exception e) {
                    if (e != null)
                        deferred.reject(e);
                    else
                        deferred.resolve(geocoreUser);
                }
            });
        } catch (UnsupportedEncodingException e) {
            deferred.reject(e);
        } catch (JSONException e) {
            deferred.reject(e);
        }
        return deferred.promise();
    }

    public Promise<List<GeocoreUserEvent>, Exception, Void> queryEvents(String id) {
        final Deferred<List<GeocoreUserEvent>, Exception, Void> deferred = new DeferredObject<>();
        Geocore.getInstance().users.queryEvents(id, new GeocoreCallback<List<GeocoreUserEvent>>() {
            @Override
            public void onComplete(List<GeocoreUserEvent> geocoreUserEvents, Exception e) {
                if (e != null)
                    deferred.reject(e);
                else
                    deferred.resolve(geocoreUserEvents);
            }
        });
        return deferred.promise();
    }

    public Promise<GeocoreUser, Exception, Void> registerForPushNotification(String token) {
        final Deferred<GeocoreUser, Exception, Void> deferred = new DeferredObject<>();
        Geocore.getInstance().users.registerForPushNotification(token, new GeocoreCallback<GeocoreUser>() {
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

    public Promise<GeocoreUser, Exception, Void> registerForPushNotification(String token, boolean enable) {
        final Deferred<GeocoreUser, Exception, Void> deferred = new DeferredObject<>();
        Geocore.getInstance().users.registerForPushNotification(token, enable, new GeocoreCallback<GeocoreUser>() {
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

    public Promise<GeocoreUser, Exception, Void> enablePushNotification() {
        final Deferred<GeocoreUser, Exception, Void> deferred = new DeferredObject<>();
        Geocore.getInstance().users.enablePushNotification(new GeocoreCallback<GeocoreUser>() {
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

    public Promise<GeocoreUser, Exception, Void> disablePushNotification() {
        final Deferred<GeocoreUser, Exception, Void> deferred = new DeferredObject<>();
        Geocore.getInstance().users.disablePushNotification(new GeocoreCallback<GeocoreUser>() {
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

    public Promise<List<GeocoreUser>, Exception, Void> getConnectedUsers() {
        final Deferred<List<GeocoreUser>, Exception, Void> deferred = new DeferredObject<>();
        Geocore.getInstance().users.getConnectedUsers(new GeocoreCallback<List<GeocoreUser>>() {
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

    public Promise<List<GeocoreUserRelationship>, Exception, Void> getUserRelationships() {
        final Deferred<List<GeocoreUserRelationship>, Exception, Void> deferred = new DeferredObject<>();
        Geocore.getInstance().users.getUserRelationships(new GeocoreCallback<List<GeocoreUserRelationship>>() {
            @Override
            public void onComplete(List<GeocoreUserRelationship> geocoreUserRelationships, Exception e) {
                if (e != null)
                    deferred.reject(e);
                else
                    deferred.resolve(geocoreUserRelationships);
            }
        });
        return deferred.promise();
    }

    public Promise<List<GeocoreUser>, Exception, Void> getWaitingUsers() {
        final Deferred<List<GeocoreUser>, Exception, Void> deferred = new DeferredObject<>();
        Geocore.getInstance().users.getWaitingUsers(new GeocoreCallback<List<GeocoreUser>>() {
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

    public Promise<List<GeocoreUser>, Exception, Void> getPendingUsers() {
        final Deferred<List<GeocoreUser>, Exception, Void> deferred = new DeferredObject<>();
        Geocore.getInstance().users.getPendingUsers(new GeocoreCallback<List<GeocoreUser>>() {
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

    public Promise<GeocoreUserRelationship, Exception, Void> getUserRelationship(String id) {
        final Deferred<GeocoreUserRelationship, Exception, Void> deferred = new DeferredObject<>();
        Geocore.getInstance().users.acceptUserRelationship(id, new GeocoreCallback<GeocoreUserRelationship>() {
            @Override
            public void onComplete(GeocoreUserRelationship geocoreUserRelationship, Exception e) {
                if (e != null)
                    deferred.reject(e);
                else
                    deferred.resolve(geocoreUserRelationship);
            }
        });
        return deferred.promise();
    }

    public Promise<GeocoreUserRelationship, Exception, Void> rejectUserRelationship(String id) {
        final Deferred<GeocoreUserRelationship, Exception, Void> deferred = new DeferredObject<>();
        Geocore.getInstance().users.rejectUserRelationship(id, new GeocoreCallback<GeocoreUserRelationship>() {
            @Override
            public void onComplete(GeocoreUserRelationship geocoreUserRelationship, Exception e) {
                if (e != null)
                    deferred.reject(e);
                else
                    deferred.resolve(geocoreUserRelationship);
            }
        });
        return deferred.promise();
    }

    public Promise<GeocoreUserRelationship, Exception, Void> createUserRelationship(String id) {
        final Deferred<GeocoreUserRelationship, Exception, Void> deferred = new DeferredObject<>();
        Geocore.getInstance().users.createUserRelationship(id, new GeocoreCallback<GeocoreUserRelationship>() {
            @Override
            public void onComplete(GeocoreUserRelationship geocoreUserRelationship, Exception e) {
                if (e != null)
                    deferred.reject(e);
                else
                    deferred.resolve(geocoreUserRelationship);
            }
        });
        return deferred.promise();
    }

    public Promise<GeocoreUserRelationship, Exception, Void> breakUserRelationship(String id) {
        final Deferred<GeocoreUserRelationship, Exception, Void> deferred = new DeferredObject<>();
        Geocore.getInstance().users.breakUserRelationship(id, new GeocoreCallback<GeocoreUserRelationship>() {
            @Override
            public void onComplete(GeocoreUserRelationship geocoreUserRelationship, Exception e) {
                if (e != null)
                    deferred.reject(e);
                else
                    deferred.resolve(geocoreUserRelationship);
            }
        });
        return deferred.promise();
    }

    public Promise<GeocoreUserEngagement, Exception, Void> engageUser(String id, GeocoreUserEngagementType type, String referral) {
        final Deferred<GeocoreUserEngagement, Exception, Void> deferred = new DeferredObject<>();
        Geocore.getInstance().users.engageUser(id, type, referral, new GeocoreCallback<GeocoreUserEngagement>() {
            @Override
            public void onComplete(GeocoreUserEngagement geocoreUserEngagement, Exception e) {
                if (e != null)
                    deferred.reject(e);
                else
                    deferred.resolve(geocoreUserEngagement);
            }
        });
        return deferred.promise();
    }

    public Promise<List<GeocoreFeed>, Exception, Void> getUserRelationshipLatestFeed(String id, int limit) {
        final Deferred<List<GeocoreFeed>, Exception, Void> deferred = new DeferredObject<>();
        Geocore.getInstance().users.getUserRelationshipLatestFeed(id, limit, new GeocoreCallback<List<GeocoreFeed>>() {
            @Override
            public void onComplete(List<GeocoreFeed> geocoreFeeds, Exception e) {
                if (e != null)
                    deferred.reject(e);
                else
                    deferred.resolve(geocoreFeeds);
            }
        });
        return deferred.promise();
    }

    public Promise<List<GeocoreFeed>, Exception, Void> getUserRelationshipLatestFeed(String id, int pageSize, int pageNumber) {
        final Deferred<List<GeocoreFeed>, Exception, Void> deferred = new DeferredObject<>();
        Geocore.getInstance().users.getUserRelationshipLatestFeed(id, pageSize, pageNumber, new GeocoreCallback<List<GeocoreFeed>>() {
            @Override
            public void onComplete(List<GeocoreFeed> geocoreFeeds, Exception e) {
                if (e != null)
                    deferred.reject(e);
                else
                    deferred.resolve(geocoreFeeds);
            }
        });
        return deferred.promise();
    }

    public Promise<List<GeocoreFeed>, Exception, Void> getUserRelationshipFeed(String id, Date toDate, int limit) {
        final Deferred<List<GeocoreFeed>, Exception, Void> deferred = new DeferredObject<>();
        Geocore.getInstance().users.getUserRelationshipFeed(id, toDate, limit, new GeocoreCallback<List<GeocoreFeed>>() {
            @Override
            public void onComplete(List<GeocoreFeed> geocoreFeeds, Exception e) {
                if (e != null)
                    deferred.reject(e);
                else
                    deferred.resolve(geocoreFeeds);
            }
        });
        return deferred.promise();
    }

    public Promise<GeocoreFeed, Exception, Void> postUserRelationshipFeed(String id, Map<String, Object> contentMap) {
        final Deferred<GeocoreFeed, Exception, Void> deferred = new DeferredObject<>();
        try {
            Geocore.getInstance().users.postUserRelationshipFeed(id, contentMap, new GeocoreCallback<GeocoreFeed>() {
                @Override
                public void onComplete(GeocoreFeed geocoreFeed, Exception e) {
                    if (e != null)
                        deferred.reject(e);
                    else
                        deferred.resolve(geocoreFeed);
                }
            });
        } catch (UnsupportedEncodingException e) {
            deferred.reject(e);
        } catch (JSONException e) {
            deferred.reject(e);
        }
        return deferred.promise();
    }
}
