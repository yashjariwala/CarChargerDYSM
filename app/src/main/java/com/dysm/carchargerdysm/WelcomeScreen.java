package com.dysm.carchargerdysm;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import static com.dysm.carchargerdysm.R.layout.activity_main;
import static com.dysm.carchargerdysm.R.layout.bottomlayout;
import static com.dysm.carchargerdysm.R.layout.welcomscreen;

public class WelcomeScreen extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(welcomscreen);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Animation animation;
        ImageView lightsoncar = findViewById(R.id.welcomescreencarlights);
        ImageView mat = findViewById(R.id.welcomescreenchargermatoff);
        TextView name = findViewById(R.id.welcomescreename);
        animation = AnimationUtils.loadAnimation(this,R.anim.slideinsmoothstartcharging);
        lightsoncar.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                lightsoncar.setImageResource(R.drawable.carlightsoff);
                mat.setVisibility(View.GONE);
                name.setVisibility(View.VISIBLE);
                 Handler handler = new Handler();
                 handler.postDelayed(new Runnable() {
                     @Override
                     public void run() {
                         startActivity(new Intent(WelcomeScreen.this,MainActivity.class));
                         finish();
                     }
                 },1500);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

    }

}


