package pl.tkowalcz.examples.basic;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import rx.Observable;
import rx.Subscriber;

import static java.util.Arrays.asList;

public class Exercise2a {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void main(String[] args) throws Exception {
        String tweet1 = "{\"created_at\": \"Fri Oct 10 11:26:33 +0000 2014\", \"text\": \"Charging by induction is like proof by induction. It takes ages and you're never quite sure if it worked.\", \"user\": { \"screen_name\": \"tastapod\", \"location\": \"Agilia\" }}";
        String tweet2 = "{\"created_at\": \"Fri Oct 10 11:26:33 +0000 2014\", \"text\": \"TIL the movie Inception was based on node.js callbacks\", \"user\": { \"screen_name\": \"HackerNewsOnion\", \"location\": \"HackerNews\" }}";
        String tweet3 = "{\"created_at\": \"Fri Oct 10 12:26:33 +0000 2014\", \"text\": \"Shark Facts: Statistically, you are a more likely to be killed by a statistician than by a shark\", \"user\": { \"screen_name\": \"TheTechShark\"}}";
        String tweet4 = "{\"delete\":{\"status\":{\"id\":48223552775705600}}}";
        String tweet5 = "{\"created_at\": \"Fri Oct 10 08:26:33 +0000 2014\", \"text\": \"Keep calm and don't block threads\", \"user\": { \"screen_name\": \"tkowalcz\", \"location\": \"JDD\"}}";

        List<String> twitterStorm = asList(tweet1, tweet2, tweet3, tweet4, tweet5);
        Gson gson = new GsonBuilder().create();

        // And now for something completely different: producing events.
        // We will act as a producer of streams and try to implement the
        // 'just' operator ourselves.

        // Note: In Java8 you may nicely collapse the anonymous class definition into lambda.
        Observable<String> tweets = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                // TODO: Emit all the tweets in a loop
                // Don't forget about onComplete!
            }
        });

        System.in.read();
    }
}
