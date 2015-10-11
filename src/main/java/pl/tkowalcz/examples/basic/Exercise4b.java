package pl.tkowalcz.examples.basic;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import pl.tkowalcz.asciigen.ASCII;
import pl.tkowalcz.twitter.RetroTwitter;
import rx.Observable;
import rx.apache.http.ObservableHttp;
import rx.apache.http.ObservableHttpResponse;

public class Exercise4b {

    @SuppressWarnings({"ResultOfMethodCallIgnored", "ArraysAsListWithZeroOrOneArgument"})
    public static void main(String[] args) throws Exception {
        // This time we will use flatMap to compose asynchronous streams into one.
        // The task is to perform a twitter search for some keywords and
        // download profile image of each user returned by twitter.
        // Then convert it to ASCII and print.
        ASCII ascii = new ASCII();

        try (CloseableHttpAsyncClient httpClient = HttpAsyncClients.createDefault()) {
            httpClient.start();

            RetroTwitter twitter = new RetroTwitter();

            Observable.from(Arrays.asList("JDD"))
                    .flatMap(twitter::searchUsers)
                    .flatMap(Observable::from)
                    .flatMap(user -> ObservableHttp
                            .createGet(user.getProfileImageUrl(), httpClient)
                            .toObservable())
                    .flatMap(ObservableHttpResponse::getContent)
                    // This will slow down emissions so we can see individual images
                    // appearing in the console.
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
