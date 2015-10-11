package pl.tkowalcz.examples.basic;

import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

import com.google.common.collect.Maps;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import rx.Observable;
import rx.apache.http.ObservableHttp;
import rx.apache.http.ObservableHttpResponse;

public class ImagesCache implements IObservablesCache<String, byte[]> {

    private final ConcurrentMap<String, Observable<byte[]>> cache;
    private final Function<String, Observable<byte[]>> searcher;

    public ImagesCache(CloseableHttpAsyncClient httpClient) {
        searcher = new Function<String, Observable<byte[]>>() {

            @Override
            public Observable<byte[]> apply(String url) {
                return ObservableHttp
                        .createGet(url, httpClient)
                        .toObservable()
                        .flatMap(ObservableHttpResponse::getContent)
                        .doOnError(throwable -> cache.remove(url))
                        .cache();
            }
        };

        cache = Maps.newConcurrentMap();
    }

    @Override
    public Observable<byte[]> get(String key) {
        return cache.computeIfAbsent(key, searcher);
    }
}
