package pl.tkowalcz.twitter.mock;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

import com.google.common.util.concurrent.Uninterruptibles;
import pl.tkowalcz.twitter.RetroTwitter;
import rx.Observable;

public class TwitterUsersDownloader {

    public static void main(String[] args) throws IOException {
        RetroTwitter twitter = new RetroTwitter();

        File users = new File("src/main/resources/users.bin");
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(users));

        CountDownLatch countDownLatch = new CountDownLatch(1);

        Observable.from(Arrays.asList("JDD"))
                .flatMap(twitter::searchUsers)
                .flatMap(Observable::from)
                .take(10)
                .subscribe(user -> {
                    try {
                        oos.writeObject(user);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }, (throwable) -> {
                    throwable.printStackTrace();
                    countDownLatch.countDown();
                }, () -> {
                    try {
                        oos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    countDownLatch.countDown();
                });

        Uninterruptibles.awaitUninterruptibly(countDownLatch);
    }
}
