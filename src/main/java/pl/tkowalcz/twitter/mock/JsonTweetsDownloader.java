package pl.tkowalcz.twitter.mock;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import com.google.common.util.concurrent.Uninterruptibles;
import pl.tkowalcz.twitter.StreamingTwitterClient;

public class JsonTweetsDownloader {

    public static void main(String[] args) throws IOException {
        try (StreamingTwitterClient client = new StreamingTwitterClient()) {
            File firehose = new File("firehose.jsons");
            BufferedWriter writer = new BufferedWriter(new FileWriter(firehose));

            CountDownLatch countDownLatch = new CountDownLatch(1);

            client.tweets()
                    .doOnNext(string -> {
                                if (string.contains("\n")) {
                                    System.out.println("Newline found!");
                                }
                            }
                    )
                    .take(1000)
                    .subscribe(string -> {
                        try {
                            writer.write(string);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }, (throwable) -> {
                        throwable.printStackTrace();
                        countDownLatch.countDown();
                    }, () -> {
                        try {
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        countDownLatch.countDown();
                    });

            Uninterruptibles.awaitUninterruptibly(countDownLatch);
        }
    }
}
