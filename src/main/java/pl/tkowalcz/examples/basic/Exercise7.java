package pl.tkowalcz.examples.basic;

import java.io.IOException;

import org.apache.commons.lang3.tuple.Pair;
import org.testng.annotations.Test;
import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.TestScheduler;
import rx.subjects.TestSubject;

import static java.util.Arrays.asList;
import static org.testng.Assert.assertEquals;

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
        byte[] cachedItem = FOO;
        ImagesCache cache = new ImagesCache();
        cache.add("Key", cachedItem);

        // When
        Observable<byte[]> actual = cache.get("Key");

        // Then
        TestSubscriber<byte[]> subscriber = new TestSubscriber<>();
        actual.subscribe(subscriber);

        subscriber.assertReceivedOnNext(asList(FOO));
    }

    @Test
    public void shouldNotCacheObservableWithError() {
        // Given
        IOException exception = new IOException("Broken pipe");
        Observable<byte[]> cachedItem = Observable.
                <byte[]>error(exception)
                .startWith(FOOBAR);

        ExpiringImagesCache cache = new ExpiringImagesCache(key -> cachedItem);

        // When
        Observable<byte[]> actual = cache.get("Key");

        TestSubscriber<byte[]> subscriber = new TestSubscriber<>();
        actual.subscribe(subscriber);

        // Then
        subscriber.assertReceivedOnNext(asList(FOOBAR));
        subscriber.assertError(exception);
        assertEquals(cache.getSize(), 0);
    }

    // This is not a test of cache. Just another example of testing observables.
    @Test
    public void shouldZipElements() {
        // Given
        TestScheduler scheduler = new TestScheduler();
        TestSubject<String> valuesSubject = TestSubject.create(scheduler);

        valuesSubject.onNext("3.141592");
        valuesSubject.onNext("2.718281");
        valuesSubject.onNext("6.626070");
        valuesSubject.onCompleted();

        TestSubject<String> namesSubject = TestSubject.create(scheduler);
        namesSubject.onNext("Pi");
        namesSubject.onNext("e");
        namesSubject.onNext("Planck constant * 10^34 m2 kg/s");
        namesSubject.onCompleted();

        // When
        Observable<Pair<String, String>> merge = Observable.zip(namesSubject, valuesSubject, Pair::of);

        TestSubscriber<Pair<String, String>> subscriber = new TestSubscriber<>();
        merge.subscribe(subscriber);

        scheduler.triggerActions();

        // Then
        subscriber.assertReceivedOnNext(asList(
                        Pair.of("Pi", "3.141592"),
                        Pair.of("e", "2.718281"),
                        Pair.of("Planck constant * 10^34 m2 kg/s", "6.626070"))
        );
    }
}
