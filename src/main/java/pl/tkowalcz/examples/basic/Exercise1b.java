package pl.tkowalcz.examples.basic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Exercise1b {

    public static void main(String[] args) throws Exception {
        String tweet1 = "{\"created_at\": \"Fri Oct 10 11:26:33 +0000 2014\", \"text\": \"Charging by induction is like proof by induction. It takes ages and you're never quite sure if it worked.\", \"user\": { \"screen_name\": \"tastapod\", \"location\": \"Agilia\" }}";
        String tweet2 = "{\"created_at\": \"Fri Oct 10 11:26:33 +0000 2014\", \"text\": \"TIL the movie Inception was based on node.js callbacks\", \"user\": { \"screen_name\": \"HackerNewsOnion\", \"location\": \"HackerNews\" }";
        String tweet3 = "{\"created_at\": \"Fri Oct 10 12:26:33 +0000 2014\", \"text\": \"Shark Facts: Statistically, you are a more likely to be killed by a statistician than by a shark\", \"user\": { \"screen_name\": \"TheTechShark\"}}";
        String tweet4 = "{\"delete\":{\"status\":{\"id\":48223552775705600}}}";
        String tweet5 = "{\"created_at\": \"Fri Oct 10 08:26:33 +0000 2014\", \"text\": \"Keep calm and don't block threads\", \"user\": { \"screen_name\": \"tkowalcz\", \"location\": \"JDD\"}}";

        Gson gson = new GsonBuilder().create();

        // Welcome again. Feel free to copy your observable
        // with filter and map from previous exercise.

        // This time we have a surprise: tweet with invalid json
        // What can we do about that?

        // TODO:
        // 1. Let's print the error to the console (look at
        //      different flavors of subscribe())
        // 2. Let's catch the exception and emit empty tweet instead (inside map)
        // 3. Let's take() just first tweet so we will not have to
        //      deal with invalid one that comes next.
    }
}
