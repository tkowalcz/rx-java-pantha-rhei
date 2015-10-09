package pl.tkowalcz.examples.basic;

import java.util.Arrays;

import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import pl.tkowalcz.asciigen.ASCII;
import pl.tkowalcz.twitter.RetroTwitter;
import rx.Observable;
import rx.apache.http.ObservableHttp;
import rx.apache.http.ObservableHttpResponse;

public class Exercise4b {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void main(String[] args) throws Exception {
        // This time we will use flatMap to compose asynchronous streams into one.
        // The task is perform a twitter search for some keywords and
        // download profile image of each user returned by twitter. Then convert it to ASCII and print.
        ASCII ascii = new ASCII();

        // CloseableHttpAsyncClient is an observable wrapper over asynchronous http client.
        // We will use it to download profile images
        try (CloseableHttpAsyncClient httpClient = HttpAsyncClients.createDefault()) {
            httpClient.start();

            // This client allows us to search twitter for users related to given keywords.
            // See ITwitterSearch for its contract.
            RetroTwitter twitter = new RetroTwitter();

            Observable.from(Arrays.asList("JDD", "GeeCON"));
            // TODO: query 'twitter' object for users
            // TODO: change that stream into stream of image urls

            // TODO: download images using helper class ObservableHttp:
            Observable<ObservableHttpResponse> observable =
                    ObservableHttp.createGet("uri", httpClient).toObservable();

            // TODO: convert response to bytes using ObservableHttpResponse::getContent
            System.in.read();
        }
    }
}
