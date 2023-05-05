package com.tahayunus.wordleedit.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tahayunus.wordleedit.R;

import java.util.Locale;


public class Animation {

    private static void rotate(TextView textView ,long delay) {
        ObjectAnimator animation = ObjectAnimator.ofFloat(textView, "rotationX", 0f, 90f);
        animation.setStartDelay(delay);
        animation.setDuration(300);
        animation.start();

    }

    private static void rotateBack(TextView textView , long delay,String wordle,int col) {


        ObjectAnimator animation2 = ObjectAnimator.ofFloat(textView, "rotationX", 90f, 0f);
        animation2.setStartDelay(300 + delay);
        animation2.setDuration(300);
        animation2.start();
        animation2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                String text = textView.getText().toString().trim().toLowerCase(Locale.ROOT);
                System.out.println(text +" : "+ wordle.substring(col,col+1));
                if ( text.equals(wordle.substring(col,col+1))){
                    textView.setBackgroundResource(R.color.green);
                }else if(wordle.contains(text)){
                    textView.setBackgroundResource(R.color.yellow);
                }else{
                    textView.setBackgroundResource(R.color.gray);
                }
            }
        });
    }

    public static void rotate90Degrees(TextView textView,long delay,String wordle,int col) {
        rotate(textView,delay);
        rotateBack(textView,delay,wordle,col);
    }
}