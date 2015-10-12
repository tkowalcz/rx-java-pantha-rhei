package pl.tkowalcz.examples.basic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pl.tkowalcz.twitter.StreamingTwitterClient;
import rx.Observable;

public class Exercise3 {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void main(String[] args) throws Exception {
        // Main point of this exercise is to use flatMap to
        // convert stream of tweets into stream of words.

        // Here we will connect to Twitter and receive "firehose" of tweets.
        // This is a sample of all tweets that are produced in the word
        // in real time.

        // In case of network connectivity problems use MockTwitterClient
        // instead of StreamingTwitterClient
        try (StreamingTwitterClient client = new StreamingTwitterClient()) {
            Gson gson = new GsonBuilder().create();

            // TODO: subscribe to the stream, filter, map and print it like in Exercise 1a.
            Observable<String> twitterObservable = client.tweets();

            // TODO: Split Tweet.getText() into words and
            // flatMap that shit (© www.flatmapthatshit.com)

            // Use something like take(1000) to limit number of tweets we process.

            // Bonus points: how to calculate which word is the most common?
            // (hint: reduce, scan or groupBy are your friends).

            System.in.read();
        }
    }
}
