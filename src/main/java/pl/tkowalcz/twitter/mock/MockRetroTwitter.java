package pl.tkowalcz.twitter.mock;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.google.common.collect.ImmutableList;
import pl.tkowalcz.twitter.TwitterUser;
import rx.Observable;
import rx.schedulers.Schedulers;

public class MockRetroTwitter {

    private final List<TwitterUser> users;
    private double failureProbability = 0.0;

    public MockRetroTwitter() {
        ImmutableList.Builder<TwitterUser> builder = ImmutableList.builder();
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src/main/resources/users.bin"));
            for (int i = 0; i < 10; i++) {
                builder.add((TwitterUser) ois.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        users = builder.build();
    }

    public Observable<List<TwitterUser>> searchUsers(String prefix) {
        if (ThreadLocalRandom.current().nextDouble() < failureProbability) {
            return Observable.error(new IOException("Broken pipe"));
        }

        return Observable
                .from(users)
                .skip(1)
                .toList()
                .subscribeOn(Schedulers.newThread());
    }

    public MockRetroTwitter injectFailureWithProbability(double failureProbability) {
        this.failureProbability = failureProbability;
        return this;
    }
}
