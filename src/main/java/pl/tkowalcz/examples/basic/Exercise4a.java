package pl.tkowalcz.examples.basic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import javax.imageio.ImageIO;

import pl.tkowalcz.asciigen.ASCII;
import rx.Observable;

public class Exercise4a {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void main(String[] args) throws Exception {
        // This is just an intro into glorious word of ASCII art!

        // Image to ASCII art converter
        ASCII ascii = new ASCII();

        // Try to load Rx logo
        Observable<BufferedImage> usingMainPath = Observable.just(new File("src/main/resources/rx.png"))
                .map(Exercise4a::loadImage);

        // Alternative path
        Observable<BufferedImage> usingSecondaryPath = Observable.just(new File("rx.png"))
                .map(Exercise4a::loadImage);

        usingMainPath
                // Can you guess what does this one do?
                .onErrorResumeNext(usingSecondaryPath)
                .map(ascii::convert)
                .subscribe(System.out::println, Throwable::printStackTrace);
    }

    private static BufferedImage loadImage(File file) {
        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
