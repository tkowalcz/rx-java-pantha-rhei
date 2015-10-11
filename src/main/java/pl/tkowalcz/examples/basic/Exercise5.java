package pl.tkowalcz.examples.basic;

import pl.tkowalcz.twitter.RetroTwitter;

public class Exercise5 {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void main(String[] args) throws Exception {
        // This time we will try to handle communication with an
        // unreliable data source (these days they are all like that...)

        // Main server that has a high failure rate
        RetroTwitter mainNode = new RetroTwitter()
                .injectFailureWithProbability(0.9);

        // Secondary server that is more healthy
        RetroTwitter fallbackNode = new RetroTwitter()
                .injectFailureWithProbability(0.9);

        // TODO:
        // 1. Log the error to the console
        mainNode.searchUsers("JDD")
                .doOnError(e -> System.out.println("Exception while accessing twitter using main node: " + e.getMessage()))
                .retry(2) // 2. Retry the call to main node two times
                .onErrorResumeNext( // 3. If this fails switch to fallback
                        fallbackNode.searchUsers("JDD")
                                .doOnError(e -> System.out.println("Exception while accessing twitter using fallback node: " + e.getMessage()))
                                .retry(2)
                )
                .subscribe(System.out::println, System.out::println);

        System.in.read();
    }
}
