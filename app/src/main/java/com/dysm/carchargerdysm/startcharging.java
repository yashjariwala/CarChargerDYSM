package com.dysm.carchargerdysm;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class startcharging extends MainActivity{
    @Override
    protected void onStart() {
        super.onStart();
        Animation animation;
        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slideinsmoothstartcharging);
        ImageView car = findViewById(R.id.caronstartcharging);
        car.startAnimation(animation);
            Animation animation1;
            animation1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
            ImageView charger = findViewById(R.id.startchargingcharger);
            TextView startcharging =findViewById(R.id.startcharging);
            charger.startAnimation(animation1);
            startcharging.startAnimation(animation1);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startcharging);

        Button startcharger = findViewById(R.id.chargeron);
        startcharger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myref = database.getReference().child("CHARGER_STATUS");
                    myref.setValue(true);
                    Intent intent = new Intent(startcharging.this,stopcharging.class);
                    startActivity(intent);
                    finish();
            }
        });
    }
}
