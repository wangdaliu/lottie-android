package com.airbnb.lottie.samples;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.SeekBar;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = "MainActivity";
  // torso
  LottieAnimationView torsoView;
  // shoes
  LottieAnimationView shoesView;
  // left arm
  LottieAnimationView leftArm;
  // right arm
  LottieAnimationView rightArm;
  // head
  LottieAnimationView underHead;
  LottieAnimationView upperHead;

  FrameLayout headView;

  AppCompatSeekBar seekBar;

  // static String FILE_NAME = "data.json";

  FrameLayout containerView;
  View eyebrow;

  float currentProgress = 0f;


//    static String FILE_NAME = "Hello World.json";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    torsoView = (LottieAnimationView) findViewById(R.id.singer_torso_view);
    shoesView = (LottieAnimationView) findViewById(R.id.singer_shoes_view);
    leftArm = (LottieAnimationView) findViewById(R.id.singer_left_arm_view);
    rightArm = (LottieAnimationView) findViewById(R.id.singer_right_arm_view);
    underHead = (LottieAnimationView) findViewById(R.id.singer_under_head);
    upperHead = (LottieAnimationView) findViewById(R.id.singer_upper_head);

    torsoView.useExperimentalHardwareAcceleration();
    shoesView.useExperimentalHardwareAcceleration();
    leftArm.useExperimentalHardwareAcceleration();
    rightArm.useExperimentalHardwareAcceleration();
    underHead.useExperimentalHardwareAcceleration();
    upperHead.useExperimentalHardwareAcceleration();

    containerView = (FrameLayout) findViewById(R.id.body_container);
    headView = (FrameLayout) findViewById(R.id.head);

    eyebrow = findViewById(R.id.eyebrow);

    seekBar = (AppCompatSeekBar) findViewById(R.id.seek_bar);

    torsoView.setAnimation("singer_torso.json");
    shoesView.setAnimation("singer_shoes.json");
    leftArm.setAnimation("singer_left_arm.json");
    rightArm.setAnimation("singer_right_arm.json");

    underHead.setAnimation("singer_under_head.json");
    upperHead.setAnimation("singer_upper_head.json");

    addIdleAnimations();


    new Handler().postDelayed(new Runnable() {
      @Override public void run() {
        updateEyeBrowPosition();
      }
    }, 2000);

    seekBar.setProgress((int) currentProgress);

    seekBar.setMax(20);
    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (!torsoView.isAnimating()) {
          currentProgress = progress * 5;
          torsoView.setProgress(currentProgress / 100f);
          shoesView.setProgress(currentProgress / 100f);
          leftArm.setProgress(currentProgress / 100f);
          rightArm.setProgress(currentProgress / 100f);
          underHead.setProgress(currentProgress / 100f);
          upperHead.setProgress(currentProgress / 100f);
          updateEyeBrowPosition();
        }
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {
      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {
      }
    });
  }

  private void addTorsoWobbleAnimation() {
    Animation shakeAnim = AnimationUtils.loadAnimation(getApplicationContext(),
        R.anim.shake_troso);
    containerView.startAnimation(shakeAnim);
  }

  private void addHeadWobbleAnimation() {
    Animation shakeAnim = AnimationUtils.loadAnimation(getApplicationContext(),
        R.anim.shake_head);
    headView.startAnimation(shakeAnim);
  }

  private void updateEyeBrowPosition() {

    // FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) eyebrow.getLayoutParams();
    //
    // Log.e(TAG, "updateEyeBrowPosition progress: " + currentProgress);
    //
    // float[] bottomPositions = {
    //     220f, 220f, 220f, 220f, 220f,
    //     220f, 220f, 220f, 226f, 230f,
    //     240f, 245f, 260f, 270f, 285f,
    //     295f, 310f, 315f, 325f, 340f};
    //
    // int position = (int) (currentProgress / 5 - 1);
    //
    // Log.e(TAG, "updateEyeBrowPosition: position" + position);
    // float bottom = bottomPositions[position > 0 ? position : 0] * torsoView.getScale();
    //
    // Log.e(TAG, "updateEyeBrowPosition: bottom" + bottom );
    // params.setMargins(params.leftMargin, params.topMargin, params.rightMargin,
    //     (int) dipToPixels(bottom));
    //
    // eyebrow.setLayoutParams(params);
  }

  private void playSingleBlinkAnimationAndAllowDoubleBlink() {
    final ValueAnimator animator = ValueAnimator.ofInt(0, 100);
    animator.setDuration(4000);
    animator.setInterpolator(new LinearInterpolator());
    animator.setRepeatCount(ValueAnimator.INFINITE);
    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator valueAnimator) {
        Integer progress = (Integer) valueAnimator.getAnimatedValue();
        if (progress > 95) {
          eyebrow.setVisibility(View.VISIBLE);
        } else {
          eyebrow.setVisibility(View.GONE);
        }
      }
    });
    animator.start();
  }

  private void shakeArm(final View view) {
    Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),
        R.anim.shake_arm);
    view.startAnimation(anim);
  }


  private void addArmWobbleAnimation() {
    shakeArm(leftArm);
    shakeArm(rightArm);
  }


  private void addIdleAnimations() {
    // torso
    addTorsoWobbleAnimation();

    // head
    addHeadWobbleAnimation();

    // arm
    addArmWobbleAnimation();

    // playSingleBlinkAnimationAndAllowDoubleBlink();


  }

  @Override
  public void onStop() {
    torsoView.cancelAnimation();
    shoesView.cancelAnimation();
    super.onStop();
  }

  public float dipToPixels(float dipValue) {
    DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
  }


}
