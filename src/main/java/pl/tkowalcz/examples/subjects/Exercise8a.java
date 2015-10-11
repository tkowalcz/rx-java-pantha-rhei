package pl.tkowalcz.examples.subjects;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import rx.observers.TestObserver;
import rx.subjects.*;

public class Exercise8a {

    // This one is about subjects. There are four basic types of
    // subjects. This test uses them all in a scenario when
    // we emit some elements (calling onNext on the subject).
    // Then subscribing two observers between that calls.

    // As a result observers O1 and O2 will receive some of the
    // events we published using the subject.

    // TODO: place onNext calls so you will be able to
    // guess what are the principles behind these subjects.

    PublishSubject<Integer> publishSubject = PublishSubject.create();
    BehaviorSubject<Integer> behaviorSubject = BehaviorSubject.create(10);
    AsyncSubject<Integer> asyncSubject = AsyncSubject.create();
    ReplaySubject<Integer> replaySubject = ReplaySubject.create();

    @DataProvider(name = "testSubjects")
    public Object[][] testSubjects() {
        return new Object[][]{{publishSubject}, {behaviorSubject}, {asyncSubject}, {replaySubject}};
    }

    @Test(dataProvider = "testSubjects")
    public void shouldReceiveNotifications(Subject<Integer, Integer> subject) {
        System.out.println("subject = " + subject);
        TestObserver<Integer> observer1 = new TestObserver<>();
        TestObserver<Integer> observer2 = new TestObserver<>();

        subject.onNext(1);
        subject.onNext(1);
        subject.subscribe(observer1);
        subject.onNext(2);
        subject.onNext(3);
        subject.onNext(5);
        subject.subscribe(observer2);
        subject.onNext(8);

        subject.onCompleted();
        System.out.println("O1: " + observer1.getOnNextEvents());
        System.out.println("O2: " + observer2.getOnNextEvents());
    }
}
