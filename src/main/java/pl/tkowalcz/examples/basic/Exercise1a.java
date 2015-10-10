package pl.tkowalcz.examples.basic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pl.tkowalcz.twitter.Tweet;

public class Exercise1a {

    public static void main(String[] args) throws Exception {
        String tweet1 = "{\"created_at\": \"Fri Oct 10 11:26:33 +0000 2014\", \"text\": \"Charging by induction is like proof by induction. It takes ages and you're never quite sure if it worked.\", \"user\": { \"screen_name\": \"tastapod\", \"location\": \"Agilia\" }}";
        String tweet2 = "{\"created_at\": \"Fri Oct 10 11:26:33 +0000 2014\", \"text\": \"TIL the movie Inception was based on node.js callbacks\", \"user\": { \"screen_name\": \"HackerNewsOnion\", \"location\": \"HackerNews\" }}";
        String tweet3 = "{\"created_at\": \"Fri Oct 10 12:26:33 +0000 2014\", \"text\": \"Shark Facts: Statistically, you are a more likely to be killed by a statistician than by a shark\", \"user\": { \"screen_name\": \"TheTechShark\"}}";
        String tweet4 = "{\"delete\":{\"status\":{\"id\":48223552775705600}}}";
        String tweet5 = "{\"created_at\": \"Fri Oct 10 08:26:33 +0000 2014\", \"text\": \"Keep calm and don't block threads\", \"user\": { \"screen_name\": \"tkowalcz\", \"location\": \"JDD\"}}";

        // TODO: have fun with creating your first Observable.
        // Let's try static methods defined on Observable class
        // that accept arrays or collections.

        // TODO: Apply filter and map (e.g. filter out tweet4 that just informs
        // us about tweet deletion). If you're brave enough try
        // some other operators.

        // Note: you can map json string containing Tweet to Java object this way:
        Gson gson = new GsonBuilder().create();
        Tweet tweetDomainObject = gson.fromJson(tweet1, Tweet.class);

        // Remember that subscription starts the stream flowing!
    }
}
