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
                .injectFailureWithProbability(0.1);

        // TODO:
        // 1. Log the error to the console
        // 2. Retry the call to main node two times
        // 3. If this fails switch to fallback
        System.in.read();
    }
}
