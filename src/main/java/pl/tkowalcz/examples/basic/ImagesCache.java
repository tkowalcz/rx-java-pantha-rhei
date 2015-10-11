package pl.tkowalcz.examples.basic;

import java.util.concurrent.ConcurrentMap;

import com.google.common.collect.Maps;
import rx.Observable;

public class ImagesCache implements IObservablesCache<String, byte[]> {

    private final ConcurrentMap<String, byte[]> cache;

    public ImagesCache() {
        cache = Maps.newConcurrentMap();
    }

    @Override
    public Observable<byte[]> get(String key) {
        byte[] bytes = cache.get(key);

        if (bytes != null) {
            return Observable.just(bytes);
        }

        return Observable.<byte[]>empty();
    }

    public void add(String key, byte[] value) {
        cache.put(key, value);
    }

    @Override
    public long getSize() {
        return cache.size();
    }

    @Override
    public long getSize() {
        return 0;
    }
}
