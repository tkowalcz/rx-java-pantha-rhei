package pl.tkowalcz.examples.subjects;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import rx.Observable;

public class Exercise8b {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void main(String[] args) throws Exception {
        PerformanceMonitor performanceMonitor = new PerformanceMonitorImpl();

        Observable.timer(0, 1, TimeUnit.MILLISECONDS)
                .map(ignore -> Math.abs(ThreadLocalRandom.current().nextLong()) % 1000)
                .subscribe(performanceMonitor::updateResponseTime);

        System.in.read();
    }
}
