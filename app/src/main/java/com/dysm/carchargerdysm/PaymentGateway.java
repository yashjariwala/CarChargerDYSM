package com.dysm.carchargerdysm;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.dynamicanimation.animation.DynamicAnimation;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PaymentGateway extends MainActivity{
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private TextView retriveTV;

    @Override
    protected void onStart() {
        super.onStart();
        Animation animation;
        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_up);
        ImageView carimage = findViewById(R.id.carimage);
        carimage.startAnimation(animation);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paymentgateway);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User").child(userid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String upiid= snapshot.child("userupiid").getValue().toString();
                TextView upiidtext = findViewById(R.id.upiidretrive);
                upiidtext.setText("UPI: "+ upiid);
                Button upibuttonpopup = findViewById(R.id.payupibutton);
                upibuttonpopup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    BottomSheetDialog bottomsheet = new BottomSheetDialog();
                    bottomsheet.show(getSupportFragmentManager(),"Modal Bottom Sheet ");
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(getApplicationContext(),"Error! Contact Us.",Toast.LENGTH_LONG).show();
            }
        });
    }

}
