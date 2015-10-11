package pl.tkowalcz.examples.basic;

import java.util.Arrays;

import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.Futures;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pl.tkowalcz.twitter.Tweet;
import rx.Observable;

public class Exercise1a {

    public static void main(String[] args) throws Exception {
        String tweet1 = "{\"created_at\": \"Fri Oct 10 11:26:33 +0000 2014\", \"text\": \"Charging by induction is like proof by induction. It takes ages and you're never quite sure if it worked.\", \"user\": { \"screen_name\": \"tastapod\", \"location\": \"Agilia\" }}";
        String tweet2 = "{\"created_at\": \"Fri Oct 10 11:26:33 +0000 2014\", \"text\": \"TIL the movie Inception was based on node.js callbacks\", \"user\": { \"screen_name\": \"HackerNewsOnion\", \"location\": \"HackerNews\" }}";
        String tweet3 = "{\"created_at\": \"Fri Oct 10 12:26:33 +0000 2014\", \"text\": \"Shark Facts: Statistically, you are a more likely to be killed by a statistician than by a shark\", \"user\": { \"screen_name\": \"TheTechShark\"}}";
        String tweet4 = "{\"delete\":{\"status\":{\"id\":48223552775705600}}}";
        String tweet5 = "{\"created_at\": \"Fri Oct 10 08:26:33 +0000 2014\", \"text\": \"Keep calm and don't block threads\", \"user\": { \"screen_name\": \"tkowalcz\", \"location\": \"JDD\"}}";

        // Observable sequence from array
        Observable<String> observableFromArray = Observable.from(new String[]{tweet1, tweet2, tweet3, tweet4, tweet5});

        // Observable sequence from Iterable
        Iterable<String> iterable = ImmutableList.of(tweet1, tweet2, tweet3, tweet4, tweet5);
        Observable<String> observableFromIterable = Observable.from(iterable);

        // Observable sequence that emits element when future is completed
        Observable<String> observableFromFuture = Observable.from(Futures.immediateFuture(tweet1));

        Observable<String> tweets = Observable.from(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5));

        Gson gson = new GsonBuilder().create();
        tweets
                .filter(string -> !string.contains("{\"delete\":"))
                .map(tweet -> gson.fromJson(tweet, Tweet.class))
                .filter(Tweet::isValidTweet)
                .subscribe(System.out::println);
    }
}
