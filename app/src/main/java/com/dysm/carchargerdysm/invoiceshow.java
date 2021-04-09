package com.dysm.carchargerdysm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class invoiceshow extends MainActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice);
        Button exitapp = findViewById(R.id.rechargeamount);
        exitapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),PaymentGateway.class));
            }
        });

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReferenceamount = firebaseDatabase.getReference().child("RechargeAmount");
        databaseReferenceamount.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String amount = snapshot.getValue().toString();
                TextView invoiceamount = findViewById(R.id.invoiveamounttextview);
                invoiceamount.setText("₹"+amount);
                TextView invoicetotalamount = findViewById(R.id.invoicetotalamount);
                invoicetotalamount.setText("₹"+amount);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();

            }
        });
        DatabaseReference databaserefrenceunits =firebaseDatabase.getReference().child("EnergyMeter").child("WattHour");
        databaserefrenceunits.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String units = snapshot.getValue().toString();
                TextView unitsconsumed = findViewById(R.id.invoiceunitconsumed);
                unitsconsumed.setText("Units Consumed: "+units);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        Animation animation;
        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.invoicecarmoving);
        ImageView carimageinvoice = findViewById(R.id.invoicecar);
        carimageinvoice.startAnimation(animation);
    }
}
