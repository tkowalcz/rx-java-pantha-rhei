package pl.tkowalcz.twitter.mock;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import com.google.common.collect.ImmutableList;
import pl.tkowalcz.twitter.TwitterUser;
import rx.Observable;
import rx.schedulers.Schedulers;

public class MockRetroTwitter {

    private final List<TwitterUser> users;

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
        return Observable.just(users).skip(1).subscribeOn(Schedulers.newThread());
    }
}
