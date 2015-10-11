package pl.tkowalcz.twitter.mock;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import rx.Observable;
import rx.schedulers.Schedulers;

public class MockTwitterClient implements AutoCloseable {

    public Observable<String> tweets() {
        return Observable.<String>create(subscriber -> {
            try (BufferedReader reader = new BufferedReader(new FileReader("firehose.jsons"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (subscriber.isUnsubscribed()) {
                        return;
                    }

                    subscriber.onNext(line);
                }

                subscriber.onCompleted();
            } catch (IOException e) {
                subscriber.onError(e);
            }
        }).subscribeOn(Schedulers.newThread());
    }

    @Override
    public void close() {

    }
}
