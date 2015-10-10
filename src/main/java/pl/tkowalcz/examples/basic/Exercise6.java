package pl.tkowalcz.examples.basic;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import pl.tkowalcz.asciigen.ASCII;
import pl.tkowalcz.twitter.RetroTwitter;
import rx.Observable;
import rx.apache.http.ObservableHttp;
import rx.apache.http.ObservableHttpResponse;

public class Exercise6 {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void main(String[] args) throws Exception {
        // This exercise contains code similar to Exercise4b. Here
        // we perform a twitter search for 'JDD' and then
        // download profile image of each user returned by twitter.

        // Image download introduces latency and we might be able to save
        // on some calls for repeated images (e.g. famous egg image).

        // TODO: Implement cache of images (e.g. something like Map<String, Observable<byte[]>>).
        // IObservablesCache interface and ImagesCache are good starting points.
        //
        // In case cache is empty we should go and download the image.
        try (CloseableHttpAsyncClient httpClient = HttpAsyncClients.createDefault()) {
            httpClient.start();

            RetroTwitter twitter = new RetroTwitter();

            ASCII ascii = new ASCII();
            Observable.from(Collections.singletonList("JDD"))
                    .flatMap(twitter::searchUsers)
                    .flatMap(Observable::from)
                            // TODO: introduce cache
                    .flatMap(user -> ObservableHttp
                            .createGet(user.getProfileImageUrl(), httpClient)
                            .toObservable())
                            // Hint: look at switchIfEmpty, amb, concatWith().first().
                    .flatMap(ObservableHttpResponse::getContent)
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
