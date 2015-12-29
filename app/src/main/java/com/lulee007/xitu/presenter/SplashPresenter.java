package com.lulee007.xitu.presenter;

import com.lulee007.xitu.view.ISplashView;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;


/**
 * User: lulee007@live.com
 * Date: 2015-12-09
 * Time: 12:37
 */
public class SplashPresenter {

    ISplashView splashView;

    public SplashPresenter(ISplashView view){
        splashView=view;
    }

    public void start(){
        splashView.startLoadingAnim();
        Observable.timer(3, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .map(new Func1<Long, Object>() {
                    @Override
                    public Object call(Long aLong) {
                        if(isFirstLoad()){
                            splashView.startLoginOptionsActivity();
                        }else{
                            splashView.startMainActivity();
                        }
                        return null;
                    }
                }).subscribe();
    }

    private boolean isFirstLoad(){

        return true;
    };
}
