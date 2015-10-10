package pl.tkowalcz.examples.basic;

import rx.Observable;

public class ImagesCache implements IObservablesCache<String, byte[]> {

    @Override
    public Observable<byte[]> get(String key) {
        return Observable.empty();
    }
}
