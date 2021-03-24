package com.dysm.carchargerdysm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import static com.dysm.carchargerdysm.R.layout.activity_main;
import static com.dysm.carchargerdysm.R.layout.welcomscreen;

public class WelcomeScreen extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(welcomscreen);
        int[] imageArray = {
                R.drawable.startup1, R.drawable.startup2, R.drawable.startup3, R.drawable.bg
        };
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int i = 0;

            public void run() {
                ImageView imageView5 = findViewById(R.id.imageView4);
                imageView5.setImageResource(imageArray[i]);
                i++;
                if (i > (imageArray.length) | i == (imageArray.length)){
                    startActivity(new Intent(WelcomeScreen.this, MainActivity.class));
                    System.exit(0);
                }
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(runnable, 1000);
    }


}