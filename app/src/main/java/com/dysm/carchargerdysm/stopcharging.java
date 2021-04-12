package com.dysm.carchargerdysm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import java.text.DecimalFormat;

public class stopcharging extends MainActivity {
    private static DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onStart() {
        super.onStart();
        Animation animation;
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        ImageView chargeron = findViewById(R.id.chargeron);
        chargeron.startAnimation(animation);
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        //Toast.makeText(getApplicationContext(),"Not allowed to go back",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stopcharging);
        FirebaseDatabase firebasedatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReferenceAmount = firebasedatabase.getReference().child("EnergyMeter").child("Bill");
        DatabaseReference databaseReferencerecahrgeamount = firebasedatabase.getReference().child("RechargeAmount");

        final String[] usedamount = {"0"};
        final String[] rechargeamount = {"0"};


        databaseReferenceAmount.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usedamount[0] = snapshot.getValue().toString();
                double usedamountdouble = Double.parseDouble(usedamount[0]);
                final TickerView tickerView = findViewById(R.id.tickerViewamps);
                tickerView.setCharacterLists(TickerUtils.provideNumberList());
                tickerView.setAnimationDuration(1500);
                tickerView.setAnimationInterpolator(new OvershootInterpolator());
                tickerView.setText(df.format(usedamountdouble));
                findchargerstatus();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
        databaseReferencerecahrgeamount.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                rechargeamount[0] = snapshot.getValue().toString();
                final TickerView tickerView = findViewById(R.id.tickerViewwatts);
                tickerView.setCharacterLists(TickerUtils.provideNumberList());
                tickerView.setAnimationInterpolator(new OvershootInterpolator());
                tickerView.setAnimationDuration(1500);
                tickerView.setText(rechargeamount[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

        Button chargeroff = findViewById(R.id.chargingoff);
        chargeroff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myref = database.getReference().child("CHARGER_STATUS");
                myref.setValue(false);
            }
        });
    }

    private void findchargerstatus() {
        FirebaseDatabase firebasedatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReferencestatus = firebasedatabase.getReference().child("CHARGER_STATUS");
        databaseReferencestatus.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String status = snapshot.getValue().toString();
                if ("false" == status) {
                    startActivity(new Intent(getApplicationContext(), invoiceshow.class));
                    Rechargenumberupdate();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void Rechargenumberupdate() {
        FirebaseDatabase firebasedatabase = FirebaseDatabase.getInstance();
        DatabaseReference dbref = firebasedatabase.getReference().child("Invoice").child("RechargeNumber");
        dbref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    String oldRechargenumber = String.valueOf(task.getResult().getValue());
                    int oldvalue = Integer.parseInt(oldRechargenumber);
                    int newvalue = oldvalue+1;
                    String newvaluestring = String.valueOf(newvalue);
                    dbref.setValue(newvaluestring);
                }
            }
        });
    }
}


