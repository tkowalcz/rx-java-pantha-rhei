package pl.tkowalcz.examples.basic;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.tuple.Pair;
import pl.tkowalcz.twitter.StreamingTwitterClient;
import pl.tkowalcz.twitter.Tweet;
import rx.Observable;

public class Exercise3 {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void main(String[] args) throws Exception {
        // Main point of this exercise is to use flatMap to
        // convert stream of tweets into stream of words.

        // Here we will connect to Twitter and receive "firehose" of tweets.
        // This is a sample of all tweets that are produced in the word
        // in real time.
        try (StreamingTwitterClient client = new StreamingTwitterClient()) {
            Gson gson = new GsonBuilder().create();

            Observable<String> twitterObservable = client.tweets();
            twitterObservable
//                     This code is also present in transformer from filterConvertAndLimit().
                    .filter(string -> string.contains("created_at"))
                    .map(string -> gson.fromJson(string, Tweet.class))
                    .map(Tweet::getText)
                    .flatMap(text -> Observable.from(text.split("\\s+")))
                    .take(100)
                    .subscribe(System.out::println);

            twitterObservable
                    .compose(filterConvertAndLimitTo(100))
                    .reduce(Maps.<String, Integer>newHashMap(),
                            (map, string) -> {
                                map.compute(string, (s, oldCount) -> (oldCount == null ? 0 : oldCount) + 1);
                                return map;
                            })
                    .subscribe(System.out::println);

            twitterObservable
                    .compose(filterConvertAndLimitTo(100))
                    .groupBy(word -> word)
                    .flatMap(Observable::count, (byWord, count) -> Pair.of(byWord.getKey(), count))
                    .subscribe(System.out::println);

            System.in.read();
        }
    }

    public static Observable.Transformer<String, String> filterConvertAndLimitTo(int limit) {
        Gson gson = new GsonBuilder().create();

        return observable -> observable
                .filter(string -> string.contains("created_at"))
                .map(string -> gson.fromJson(string, Tweet.class))
                .map(Tweet::getText)
                .flatMap(text -> Observable.from(text.split("\\s+")))
                .filter(word -> word.length() <= 3)
                .take(limit);
    }
}