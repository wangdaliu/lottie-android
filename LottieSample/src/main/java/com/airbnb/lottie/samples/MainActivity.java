package com.airbnb.lottie.samples;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SeekBar;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = "MainActivity";
  // torso
  LottieAnimationView torsoView;
  // shoes
  LottieAnimationView shoesView;
  // arm
  LottieAnimationView armView;
  // head
  LottieAnimationView underHead;
  LottieAnimationView upperHead;

  FrameLayout headView;

  AppCompatSeekBar seekBar;

  // static String FILE_NAME = "data.json";

  FrameLayout bodyView;
  View eyebrow;

  float currentProgress = 0f;

  private Button fineTuneButton;


//    static String FILE_NAME = "Hello World.json";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    torsoView = (LottieAnimationView) findViewById(R.id.singer_torso_view);
    shoesView = (LottieAnimationView) findViewById(R.id.singer_shoes_view);
    armView = (LottieAnimationView) findViewById(R.id.singer_arm_view);
    underHead = (LottieAnimationView) findViewById(R.id.singer_under_head);
    upperHead = (LottieAnimationView) findViewById(R.id.singer_upper_head);

    torsoView.useExperimentalHardwareAcceleration();
    shoesView.useExperimentalHardwareAcceleration();
    armView.useExperimentalHardwareAcceleration();
    underHead.useExperimentalHardwareAcceleration();
    upperHead.useExperimentalHardwareAcceleration();

    torsoView.enableMergePathsForKitKatAndAbove(true);
    shoesView.enableMergePathsForKitKatAndAbove(true);
    armView.enableMergePathsForKitKatAndAbove(true);
    underHead.enableMergePathsForKitKatAndAbove(true);
    upperHead.enableMergePathsForKitKatAndAbove(true);

    bodyView = (FrameLayout) findViewById(R.id.body_container);
    headView = (FrameLayout) findViewById(R.id.head);

    fineTuneButton = (Button) findViewById(R.id.fine_tune);

    eyebrow = findViewById(R.id.eyebrow);

    seekBar = (AppCompatSeekBar) findViewById(R.id.seek_bar);

    torsoView.setAnimation("singer_torso.json");
    shoesView.setAnimation("singer_shoes.json");
    armView.setAnimation("singer_arms.json");

    underHead.setAnimation("singer_under_head.json");
    upperHead.setAnimation("singer_upper_head.json");

    addIdleAnimations();


    new Handler().postDelayed(new Runnable() {
      @Override public void run() {
        if (eyebrow.getVisibility() == View.GONE) {
          eyebrow.setVisibility(View.VISIBLE);
        }

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
          armView.setProgress(currentProgress / 100f);
          underHead.setProgress(currentProgress / 100f);
          upperHead.setProgress(currentProgress / 100f);
          updateEyeBrowPosition();

          updateAnimDuration();
        }
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {
      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {
      }
    });

    fineTuneButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {

      }
    });
  }

  private void updateEyeBrowPosition() {
    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) eyebrow.getLayoutParams();

    float[] bottomPositions = {
        180f, 180f, 180f, 180f, 180f,
        180f, 180f, 180f, 186f, 190f,
        200f, 210f, 218f, 225f, 235f,
        245f, 258f, 265f, 275f, 285f};

    int position = (int) (currentProgress / 5 - 1);
    float bottom = bottomPositions[position > 0 ? position : 0] * torsoView.getScale();
    params.setMargins(params.leftMargin, params.topMargin, params.rightMargin,
        (int) dipToPixels(bottom));

    eyebrow.setLayoutParams(params);
  }

  private static float WOBBLE_DURATION = 3.0f;

  /**
   * change duration when change progress
   *
   * @return
   */
  private float popAnimationsDuration() {
    return (float) (WOBBLE_DURATION - currentProgress / 100f * WOBBLE_DURATION * 0.6);
  }

  /**
   * update animation duration when change progress
   */
  private void updateAnimDuration() {
    float wobbleDuration = this.popAnimationsDuration();

    bodyView.getAnimation().setDuration((long) (wobbleDuration * 1000));
    headView.getAnimation().setDuration((long) (wobbleDuration * 1000));
    armView.getAnimation().setDuration((long) (wobbleDuration * 1000));
  }


  /**
   * add idle animations
   */
  private void addIdleAnimations() {
    float wobbleDuration = this.popAnimationsDuration();
    Log.e(TAG, "wobbleDuration: " + wobbleDuration);

    // body wobbling
    float wobbleAngle = 3.0f;

    final RotateAnimation torsoAnim =
        new RotateAnimation(-wobbleAngle, wobbleAngle, Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF, 0.7f);
    torsoAnim.setRepeatCount(Animation.INFINITE);
    torsoAnim.setRepeatMode(Animation.REVERSE);
    torsoAnim.setDuration((long) (wobbleDuration * 1000));
    bodyView.startAnimation(torsoAnim);

    // // head rotation
    float headRotationAngle = 2.0f;

    final RotateAnimation headAnim =
        new RotateAnimation(-headRotationAngle, headRotationAngle,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF, 0.4f);
    headAnim.setRepeatCount(Animation.INFINITE);
    headAnim.setRepeatMode(Animation.REVERSE);
    headAnim.setDuration((long) (wobbleDuration * 1000));
    headView.startAnimation(headAnim);

    // arms
    float armsTranslationAmount = 18;

    final TranslateAnimation armAnim = new TranslateAnimation(
        0f, 0f,
        0f,
        armsTranslationAmount
    );
    armAnim.setRepeatCount(Animation.INFINITE);
    armAnim.setRepeatMode(Animation.REVERSE);
    armAnim.setDuration((long) (wobbleDuration * 1000));
    armView.startAnimation(armAnim);

    // tongue translation
    // TODO

    // blink animation

    addBlinkAnimation();
  }

  private void addBlinkAnimation() {
    eyebrow.setPivotY(0);

    ObjectAnimator downAnimation = ObjectAnimator.ofFloat(eyebrow, "scaleY", 0f, 1f);
    downAnimation.setDuration(300);
    ObjectAnimator upAnimation = ObjectAnimator.ofFloat(eyebrow, "scaleY", 1f, 0f);
    upAnimation.setDuration(300);

    AnimatorSet animatorSet = new AnimatorSet();
    animatorSet.setStartDelay(3000);
    animatorSet.playSequentially(downAnimation, upAnimation);
    animatorSet.addListener(new AnimatorListenerAdapter() {
      @Override public void onAnimationEnd(Animator animation) {
        super.onAnimationEnd(animation);
        addBlinkAnimation();
      }
    });
    animatorSet.start();
  }

  @Override
  public void onStop() {
    torsoView.cancelAnimation();
    shoesView.cancelAnimation();
    armView.cancelAnimation();
    underHead.cancelAnimation();
    upperHead.cancelAnimation();

    torsoView.clearAnimation();
    shoesView.clearAnimation();
    armView.clearAnimation();
    underHead.clearAnimation();
    upperHead.clearAnimation();

    super.onStop();
  }

  public float dipToPixels(float dipValue) {
    DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
  }


}
