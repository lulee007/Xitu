package com.lulee007.xitu;

import org.junit.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void rxDelayTest() throws  Exception{
        Observable.timer(3, TimeUnit.SECONDS)
                .doOnNext(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        System.out.println("timer: do on next 1"+new Date());
                    }
                })
                .delay(3,TimeUnit.SECONDS)
                .doOnNext(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        System.out.println("timer: do on next 2"+new Date());

                    }
                })
                .delay(3, TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        System.out.println("subscribe"+new Date());
                    }
                });
        Thread.sleep(12000);
    }

    @Test
    public void rxTest() throws Exception {
        Observable<String> s = Observable.just("1", "2", "3", "4", "5")
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println(("string:" + s + Thread.currentThread()));
                    }
                })
//                .doOnCompleted(new Action0() {
//                    @Override
//                    public void call() {
//                        System.out.println("on complete~~~~~~~~~~" );
//
//                    }
//                })
                ;
        s.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("subscribe 1" + s + Thread.currentThread());

            }
        });
        s.
                map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        System.out.println("map on called" + s + Thread.currentThread());
                        return Integer.parseInt(s);
                    }
                })
                .doOnNext(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println(("integer:" + integer + Thread.currentThread()));
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println(("subscribe 2:" + integer + Thread.currentThread()));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                },new Action0() {
                    @Override
                    public void call() {
                        System.out.println("on complete~~~~~~~~~~" );

                    }
                });
//        TagWithUserStatusPresenter  a=new TagWithUserStatusPresenter(null);
//        a.loadNew();
//        Thread.sleep(60000);
    }
}