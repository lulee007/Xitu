package com.lulee007.xitu;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.lulee007.xitu.Base.XTBaseActivity;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends XTBaseActivity {


    private ImageView logo_icon;
    private ImageView logo_word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.logo_icon = (ImageView) this.findViewById(R.id.logo_icon);
        this.logo_word = (ImageView) this.findViewById(R.id.logo_word);
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        this.getWindow().getDecorView().setSystemUiVisibility(uiOptions);


        //alpha 0-1 scale zoom in
        this.CustomAnimator(this.logo_icon, 0.0F, 1.0F, 0.3F, 1.0F, 0.3F, 1.0F, 0L, 700L);
        // scale zoom out
        this.CustomAnimator(this.logo_icon, 1.0F, 1.0F, 1.0F, 0.9F, 1.0F, 0.9F, 700L, 200L);
        // scale zoom in again
        this.CustomAnimator(this.logo_icon, 1.0F, 1.0F, 0.9F, 1.0F, 0.9F, 1.0F, 900L, 200L);

        //alpha 0-1 scale zoom in
        this.CustomAnimator(this.logo_word, 0.0F, 1.0F, 0.8F, 1.0F, 0.8F, 1.0F, 0L, 700L);
        // scale zoom out
        this.CustomAnimator(this.logo_word, 1.0F, 1.0F, 1.0F, 0.9F, 1.0F, 0.9F, 700L, 200L);
        // scale zoom in again and endWith finish this activity launch main_activity
        AnimatorSet lastAnim = this.CustomAnimator(this.logo_word, 1.0F, 1.0F, 0.9F, 1.0F, 0.9F, 1.0F, 900L, 200L);
        lastAnim.addListener(new Animator.AnimatorListener() {
            public void onAnimationCancel(Animator var1) {
            }

            public void onAnimationEnd(Animator var1) {
                Observable.timer(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                        .map(new Func1<Long, Object>() {
                            @Override
                            public Object call(Long aLong) {
                                startActivity(MainActivity.class);
                                finish();
                                return null;
                            }
                        }).subscribe();

            }

            public void onAnimationRepeat(Animator var1) {
            }

            public void onAnimationStart(Animator var1) {
            }
        });


    }

    /**
     * 自定义动画
     *
     * @param imageView
     * @param alphaFrom
     * @param alphaTo
     * @param scaleXFrom
     * @param scaleXTo
     * @param scaleYFrom
     * @param scaleYTo
     * @param startDelay
     * @param duration
     * @return
     */
    private AnimatorSet CustomAnimator(ImageView imageView, float alphaFrom, float alphaTo, float scaleXFrom, float scaleXTo, float scaleYFrom, float scaleYTo, long startDelay, long duration) {
        ObjectAnimator var12 = ObjectAnimator.ofFloat(imageView, "alpha", new float[]{alphaFrom, alphaTo});
        ObjectAnimator var13 = ObjectAnimator.ofFloat(imageView, "scaleX", new float[]{scaleXFrom, scaleXTo});
        ObjectAnimator var14 = ObjectAnimator.ofFloat(imageView, "scaleY", new float[]{scaleYFrom, scaleYTo});
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(var12).with(var13).with(var14);
        animatorSet.setDuration(duration);
        animatorSet.setStartDelay(startDelay);
        animatorSet.start();
        return animatorSet;
    }

}
