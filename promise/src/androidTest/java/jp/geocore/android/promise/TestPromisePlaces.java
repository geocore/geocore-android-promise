package jp.geocore.android.promise;



import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import jp.geocore.android.model.GeocorePlace;

/**
 * Created by kakkar on 2015/08/19.
 */
public class TestPromisePlaces extends TestPromiseAuthBase {


    public void testPromisesPlaces() throws InterruptedException {


        final CountDownLatch signal = new CountDownLatch(1);
        promise.places()
                .then(new DoneCallback<List<GeocorePlace>>() {
                    @Override
                    public void onDone(List<GeocorePlace> result) {
                        assertTrue((int)result.size() > 0);
                        System.out.println(result.size());
                        signal.countDown();
                    }
                })
                .fail(new FailCallback<Exception>() {
                    @Override
                    public void onFail(Exception result) {
                        result.printStackTrace();
                        fail();
                        signal.countDown();
                    }
                });
        signal.await();
    }

}
