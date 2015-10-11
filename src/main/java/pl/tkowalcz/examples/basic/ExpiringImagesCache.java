package pl.tkowalcz.examples.basic;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import rx.Observable;
import rx.apache.http.ObservableHttp;
import rx.apache.http.ObservableHttpResponse;

public class ExpiringImagesCache implements IObservablesCache<String, byte[]> {

    private static final int CACHE_EXPIRATION_MINUTES = 10;

    private final LoadingCache<String, Observable<byte[]>> cache;

    public ExpiringImagesCache(CloseableHttpAsyncClient httpClient) {
        Function<String, Observable<byte[]>> searcher = new Function<String, Observable<byte[]>>() {

            @Override
            public Observable<byte[]> apply(String url) {
                return ObservableHttp
                        .createGet(url, httpClient)
                        .toObservable()
                        .flatMap(ObservableHttpResponse::getContent)
                        .doOnError(throwable -> cache.invalidate(url))
                        .cache();
            }
        };

        cache = CacheBuilder
                .newBuilder()
                .expireAfterWrite(CACHE_EXPIRATION_MINUTES, TimeUnit.MINUTES)
                .build(CacheLoader.from(searcher::apply));
    }

    @Override
    public Observable<byte[]> get(String key) {
        return cache.getUnchecked(key);
    }
}
