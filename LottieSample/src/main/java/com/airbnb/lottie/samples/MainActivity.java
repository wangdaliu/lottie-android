package com.airbnb.lottie.samples;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
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
  LottieAnimationView headView;

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
    headView = (LottieAnimationView) findViewById(R.id.singer_head_view);

    containerView = (FrameLayout) findViewById(R.id.body_container);

    eyebrow = findViewById(R.id.eyebrow);

    seekBar = (AppCompatSeekBar) findViewById(R.id.seek_bar);

    torsoView.setAnimation("singer_torso.json");
    shoesView.setAnimation("singer_shoes.json");
    leftArm.setAnimation("singer_left_arm.json");
    rightArm.setAnimation("singer_right_arm.json");
    headView.setAnimation("singer_head.json");

    addIdleAnimations();


    updateEyeBrowPosition();


    seekBar.setProgress((int) currentProgress);

    torsoView.addAnimatorUpdateListener(
        new ValueAnimator.AnimatorUpdateListener() {
          @Override
          public void onAnimationUpdate(ValueAnimator animation) {
            currentProgress = animation.getAnimatedFraction() * 100;
            seekBar.setProgress((int) currentProgress);
            updateEyeBrowPosition();
          }
        });

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
          headView.setProgress(currentProgress / 100f);
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
    Animation anim = new RotateAnimation(0f, 2f, Animation.RELATIVE_TO_SELF, 0.5f,
        Animation.RELATIVE_TO_SELF, 0.7f);
    anim.setFillAfter(true);
    anim.setDuration(1000);
    anim.setInterpolator(new AccelerateInterpolator());
    anim.setAnimationListener(new Animation.AnimationListener() {
      @Override public void onAnimationStart(Animation animation) {

      }

      @Override public void onAnimationEnd(Animation animation) {
        rotateLeft();
      }

      @Override public void onAnimationRepeat(Animation animation) {

      }
    });
    containerView.startAnimation(anim);
  }

  private void rotateLeft() {
    Animation anim = new RotateAnimation(2f, -2f, Animation.RELATIVE_TO_SELF, 0.5f,
        Animation.RELATIVE_TO_SELF, 0.7f);
    anim.setFillAfter(true);
    anim.setDuration(2000);
    anim.setInterpolator(new AccelerateInterpolator());
    anim.setAnimationListener(new Animation.AnimationListener() {
      @Override public void onAnimationStart(Animation animation) {

      }

      @Override public void onAnimationEnd(Animation animation) {
        rotateRight();
      }

      @Override public void onAnimationRepeat(Animation animation) {

      }
    });
    containerView.startAnimation(anim);
  }

  private void rotateRight() {
    Animation anim = new RotateAnimation(-2f, 2f, Animation.RELATIVE_TO_SELF, 0.5f,
        Animation.RELATIVE_TO_SELF, 0.7f);
    anim.setFillAfter(true);
    anim.setDuration(2000);
    anim.setInterpolator(new AccelerateInterpolator());
    anim.setAnimationListener(new Animation.AnimationListener() {
      @Override public void onAnimationStart(Animation animation) {

      }

      @Override public void onAnimationEnd(Animation animation) {
        rotateLeft();
      }

      @Override public void onAnimationRepeat(Animation animation) {

      }
    });
    containerView.startAnimation(anim);
  }


  private void addHeadWobbleAnimation() {
    Animation anim = new RotateAnimation(0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f,
        Animation.RELATIVE_TO_SELF, 0.2f);
    anim.setFillAfter(true);
    anim.setDuration(1000);
    anim.setInterpolator(new AccelerateInterpolator());
    anim.setAnimationListener(new Animation.AnimationListener() {
      @Override public void onAnimationStart(Animation animation) {

      }

      @Override public void onAnimationEnd(Animation animation) {
        rotateHeadLeft();
      }

      @Override public void onAnimationRepeat(Animation animation) {

      }
    });
    headView.startAnimation(anim);
  }

  private void rotateHeadLeft() {
    Animation anim = new RotateAnimation(1f, -1f, Animation.RELATIVE_TO_SELF, 0.5f,
        Animation.RELATIVE_TO_SELF, 0.2f);
    anim.setFillAfter(true);
    anim.setDuration(2000);
    anim.setInterpolator(new AccelerateInterpolator());
    anim.setAnimationListener(new Animation.AnimationListener() {
      @Override public void onAnimationStart(Animation animation) {

      }

      @Override public void onAnimationEnd(Animation animation) {
        rotateHeadRight();
      }

      @Override public void onAnimationRepeat(Animation animation) {

      }
    });
    headView.startAnimation(anim);
  }

  private void rotateHeadRight() {
    Animation anim = new RotateAnimation(-1f, 1f, Animation.RELATIVE_TO_SELF, 0.5f,
        Animation.RELATIVE_TO_SELF, 0.2f);
    anim.setFillAfter(true);
    anim.setDuration(2000);
    anim.setInterpolator(new AccelerateInterpolator());
    anim.setAnimationListener(new Animation.AnimationListener() {
      @Override public void onAnimationStart(Animation animation) {

      }

      @Override public void onAnimationEnd(Animation animation) {
        rotateHeadLeft();
      }

      @Override public void onAnimationRepeat(Animation animation) {

      }
    });
    headView.startAnimation(anim);
  }

  private void updateEyeBrowPosition() {

    // FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) eyebrow.getLayoutParams();
    //
    // Log.e(TAG, "updateEyeBrowPosition progress: " + currentProgress);
    //
    // float[] bottomPositions = {
    //     219.0f, 72.2f, 72.2f, 72.5f, 72.2f,
    //     72.8f, 72.8f, 73.2f, 75.2f, 76.8f,
    //     79.5f, 82.5f, 85.8f, 90.2f, 93.5f,
    //     98.5f, 102.5f, 105.5f, 110.5f, 113.5f};
    //
    // int position = (int) (currentProgress / 5 - 1);
    //
    // Log.e(TAG, "updateEyeBrowPosition: position" + position);
    // float bottom = bottomPositions[position > 0 ? position : 0] * torsoView.getScale();
    // params.setMargins(params.leftMargin, params.topMargin, params.rightMargin,
    //     (int) dipToPixels(bottom));
    //
    // eyebrow.setLayoutParams(params);
  }

  private void playSingleBlinkAnimationAndAllowDoubleBlink() {
    final ValueAnimator animator = ValueAnimator.ofInt(0, 100);
    animator.setDuration(4000);
    animator.setRepeatCount(ValueAnimator.INFINITE);
    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator valueAnimator) {
        float progress = valueAnimator.getAnimatedFraction();
        if (progress > 0.95) {
          eyebrow.setVisibility(View.VISIBLE);
        } else {
          eyebrow.setVisibility(View.GONE);
        }
      }
    });
    animator.start();
  }

  private void shakeTop(final View view) {
    Animation shakeAnim = AnimationUtils.loadAnimation(getApplicationContext(),
        R.anim.shake_top);

    shakeAnim.setAnimationListener(new Animation.AnimationListener() {
      @Override public void onAnimationStart(Animation animation) {

      }

      @Override public void onAnimationEnd(Animation animation) {
        shakeBottom(view);
      }

      @Override public void onAnimationRepeat(Animation animation) {

      }
    });
    view.startAnimation(shakeAnim);
  }

  private void shakeBottom(final View view) {
    Animation shakeAnim = AnimationUtils.loadAnimation(getApplicationContext(),
        R.anim.shake_bottom);

    shakeAnim.setAnimationListener(new Animation.AnimationListener() {
      @Override public void onAnimationStart(Animation animation) {

      }

      @Override public void onAnimationEnd(Animation animation) {
        shakeTop(view);
      }

      @Override public void onAnimationRepeat(Animation animation) {

      }
    });
    view.startAnimation(shakeAnim);
  }

  private void addArmAnimation() {
    shakeTop(leftArm);
    shakeTop(rightArm);
  }


  private void addIdleAnimations() {
    addTorsoWobbleAnimation();
    // playSingleBlinkAnimationAndAllowDoubleBlink();

    addArmAnimation();
    addHeadWobbleAnimation();

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
