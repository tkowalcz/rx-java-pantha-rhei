package pl.tkowalcz.examples.basic;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import pl.tkowalcz.asciigen.ASCII;
import pl.tkowalcz.twitter.RetroTwitter;
import pl.tkowalcz.twitter.TwitterUser;
import rx.Observable;
import rx.apache.http.ObservableHttp;
import rx.apache.http.ObservableHttpResponse;

public class Exercise6b {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void main(String[] args) throws Exception {
        // This solution uses Guava's loading cache...

        try (CloseableHttpAsyncClient httpClient = HttpAsyncClients.createDefault()) {
            httpClient.start();

            RetroTwitter twitter = new RetroTwitter();
            ExpiringImagesCache cache = new ExpiringImagesCache(url -> ObservableHttp
                    .createGet(url, httpClient)
                    .toObservable()
                    .flatMap(ObservableHttpResponse::getContent));

            ASCII ascii = new ASCII();
            Observable.from(Collections.singletonList("JDD"))
                    .flatMap(twitter::searchUsers)
                    .flatMap(Observable::from)
                    .map(TwitterUser::getProfileImageUrl)
                    // ... here
                    .flatMap(cache::get)
                            // This will delay the emissions so that we can watch images appear in the console
                    .zipWith(Observable.timer(5, 5, TimeUnit.SECONDS), (image, ignore) -> image)
                    .flatMap(ascii::convert)
                    .subscribe((x) -> {
                        System.out.println(x);
                        System.out.println();
                    });

            System.in.read();
        }
    }
}
