package com.dysm.carchargerdysm;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
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

public class PaymentGateway extends MainActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private TextView retriveTV;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    protected void onStart() {
        super.onStart();
        Animation animation;
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideinsmoothpaymentgateway);
        ImageView carimage = findViewById(R.id.carimage);
        carimage.startAnimation(animation);
    }

    @Override
    public void onBackPressed() {

        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            //startActivity(new Intent(invoiceshow.this,LoginActivity.class));
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return;
        } else {
            Toast.makeText(getBaseContext(), "Tap back button in order to exit", Toast.LENGTH_SHORT).show();
        }

        mBackPressed = System.currentTimeMillis();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paymentgateway);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaserefrence = firebaseDatabase.getReference().child("Emergency");
        databaserefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String emergency = snapshot.getValue().toString();
                if (emergency == "true") {
                    Toast toast = Toast.makeText(getApplicationContext(), "Charger Under Maintenance." +
                            "Try Again Later!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {

                    DatabaseReference myref = firebaseDatabase.getReference().child("CHARGER_STATUS");
                    myref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String chargerstatuscheck = snapshot.getValue().toString();
                            if (chargerstatuscheck == "true") {
                                Toast.makeText(getApplicationContext(), "Charger In Use", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                String userid = user.getUid();
                                databaseReference = FirebaseDatabase.getInstance().getReference().child("User").child(userid);
                                databaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String upiid = snapshot.child("userupiid").getValue().toString();
                                        TextView upiidtext = findViewById(R.id.upiidretrive);
                                        upiidtext.setText("UPI: " + upiid);
                                        Button upibuttonpopup = findViewById(R.id.payupibutton);
                                        upibuttonpopup.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                BottomSheetDialog bottomsheet = new BottomSheetDialog();
                                                bottomsheet.show(getSupportFragmentManager(), "Modal Bottom Sheet ");

                                            }
                                        });
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(getApplicationContext(), "Error! Contact Us.", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getApplicationContext(), "Error! Contact Us.", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error! Contact Us.", Toast.LENGTH_LONG).show();
            }
        });
    }
}


