package pl.tkowalcz.twitter.mock;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import rx.Observable;
import rx.schedulers.Schedulers;

public class MockImageProvider {

    private final List<byte[]> images;

    public MockImageProvider() {
        try {
            images = Files.list(Paths.get("src/main/resources/"))
                    .filter(path -> {
                        String name = path.toFile().getName();
                        return name.endsWith(".png") || name.endsWith(".jpeg");
                    })
                    .map(Path::toUri)
                    .map(uri -> {
                        try {
                            return IOUtils.toByteArray(uri);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public Observable<byte[]> getImages() {
        int index = ThreadLocalRandom.current().nextInt(images.size());
        return Observable
                .just(images.get(index))
                .subscribeOn(Schedulers.newThread());
    }
}
