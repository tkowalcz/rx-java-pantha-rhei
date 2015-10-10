package pl.tkowalcz.examples.basic;

import java.io.IOException;

import org.testng.annotations.Test;
import rx.Observable;
import rx.observers.TestSubscriber;

import static java.util.Arrays.asList;
import static org.testng.Assert.assertNull;

/**
 * Let's test the cache implementation from Exercise6.
 */
public class Exercise7 {

    public static final byte[] FOO = "Foo".getBytes();
    public static final byte[] BAR = "Bar".getBytes();
    public static final byte[] FOOBAR = "FooBar".getBytes();

    @Test
    public void shouldCacheAndReplay() {
        // Given
        IObservablesCache<String, byte[]> cache = new ImagesCache();

        Observable<byte[]> cachedItem = Observable.just(FOO, BAR, FOOBAR);
        // TODO: Add to cache

        // When
//        Observable<byte[]> actual = cache.get("Key");
        Observable<byte[]> actual = cachedItem;

        // Then
        TestSubscriber<byte[]> subscriber = new TestSubscriber<>();
        actual.subscribe(subscriber);

        subscriber.assertReceivedOnNext(asList(FOO, BAR, FOOBAR));
    }

    @Test
    public void shouldNotCacheObservableWithError() {
        // Given
        IObservablesCache<String, String> cache = null;

        Observable<byte[]> cachedItem = Observable.
                <byte[]>error(new IOException("Broken pipe"))
                .startWith(FOOBAR);

        // TODO: Add to cache

        // When
//        Observable<String> key = cache.get("Key");
        Observable<byte[]> actual = cachedItem;

        // Then
        assertNull(actual);
    }
}
