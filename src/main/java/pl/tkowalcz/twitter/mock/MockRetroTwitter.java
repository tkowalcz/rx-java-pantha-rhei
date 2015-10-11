package pl.tkowalcz.twitter.mock;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import com.google.common.collect.ImmutableList;
import pl.tkowalcz.twitter.RetroTwitterApi;
import pl.tkowalcz.twitter.TwitterUser;
import rx.Observable;
import rx.schedulers.Schedulers;

public class MockRetroTwitter implements RetroTwitterApi {

    private final List<TwitterUser> users;

    public MockRetroTwitter() {
        ImmutableList.Builder<TwitterUser> builder = ImmutableList.builder();
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("users.bin"));
            for (int i = 0; i < 10; i++) {
                builder.add((TwitterUser) ois.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        users = builder.build();
    }

    @Override
    public Observable<List<TwitterUser>> searchForUsers(String prefix) {
        return Observable.just(users).subscribeOn(Schedulers.newThread());
    }
}
