package com.dysm.carchargerdysm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

public class stopcharging extends MainActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stopcharging);

        FirebaseDatabase firebasedatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReferenceAmount =firebasedatabase.getReference().child("EnergyMeter").child("Bill");
        DatabaseReference databaseReferencewatts =firebasedatabase.getReference().child("EnergyMeter").child("WattHour");
        databaseReferenceAmount.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String ampere = snapshot.getValue().toString();
                final TickerView tickerView = findViewById(R.id.tickerViewamps);
                tickerView.setCharacterLists(TickerUtils.provideNumberList());
                tickerView.setAnimationDuration(1500);
                tickerView.setAnimationInterpolator(new OvershootInterpolator());

                tickerView.setText(ampere);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
            }
        });
        databaseReferencewatts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String watts = snapshot.getValue().toString();
                final TickerView tickerView = findViewById(R.id.tickerViewwatts);
                tickerView.setCharacterLists(TickerUtils.provideNumberList());
                tickerView.setAnimationInterpolator(new OvershootInterpolator());
                tickerView.setAnimationDuration(1500);
                tickerView.setText(watts);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
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
}
