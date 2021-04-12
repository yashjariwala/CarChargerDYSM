package com.dysm.carchargerdysm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class invoiceshow extends MainActivity{
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    String invoicenumber, userid,rechargeAmount,energymeterbill,energymeterwatthour;
    private FirebaseAuth firebaseauth;


    @Override
    protected void onStart() {
        super.onStart();
        Animation animation;
        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.invoicecarmoving);
        ImageView carimageinvoice = findViewById(R.id.invoicecar);
        carimageinvoice.startAnimation(animation);
    }


    @Override
    public void onBackPressed()
    {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            startActivity(new Intent(invoiceshow.this,LoginActivity.class));
            finish();
            return;
        }
        else { Toast.makeText(getBaseContext(), "Tap back button in order to exit", Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();
    }

    private void Notification(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel("charger","charger",NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }

        Intent intent = new Intent(this, invoiceshow.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"charger")
                .setContentText("Yash Jariwala ")
                .setSmallIcon(R.drawable.logo)
                .setAutoCancel(true)
                .setContentText("Charging Complete")
                .setContentIntent(pendingIntent);

        NotificationManagerCompat managercopat = NotificationManagerCompat.from(this);
        managercopat.notify(007,builder.build());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice);
        Notification();
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
                Float floatunits = Float.parseFloat(units);
                Float floatunitsonscreen = floatunits/1000;
                TextView unitsconsumed = findViewById(R.id.invoiceunitconsumed);
                unitsconsumed.setText("Units Consumed: "+ floatunitsonscreen);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
            }
        });
        DatabaseReference databasereinvoicenumber = firebaseDatabase.getReference().child("Invoice").child("RechargeNumber");
        databasereinvoicenumber.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String invoicenumber = snapshot.getValue().toString();
                TextView invoicenumbertext = findViewById(R.id.invoicenumber);
                invoicenumbertext.setText("Invoice Number : #"+invoicenumber);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
            }
        });
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                rechargeAmount= snapshot.child("RechargeAmount").getValue().toString();
                energymeterbill = snapshot.child("EnergyMeter").child("Bill").getValue().toString();
                energymeterwatthour = snapshot.child("EnergyMeter").child("WattHour").getValue().toString();
                userid = user.getUid();
                invoicenumber= snapshot.child("Invoice").child("RechargeNumber").getValue().toString();

                invoivetodb(userid,energymeterwatthour,energymeterbill,rechargeAmount,invoicenumber);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void invoivetodb(String userid,String energymeterwatthour, String energymeterbill, String rechargeAmount,String invoicenumbercuurent) {

        DatabaseReference databaserefrnce = FirebaseDatabase.getInstance().getReference("Invoice").child(invoicenumbercuurent);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("userid", userid);
        hashMap.put("energymeterwatthour",energymeterwatthour);
        hashMap.put("energymeterbill",energymeterbill);
        hashMap.put("rechargeAmount", rechargeAmount);
        databaserefrnce.setValue(hashMap);
        }
    }


