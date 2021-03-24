package com.dysm.carchargerdysm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class RegisterActivity extends MainActivity {
    private TextView registername, registeremailid, registerpassword, registermobileno, registerupiid;
    private Button registerscreenregisterbutton;
    private FirebaseAuth firebaseauth;
    private DatabaseReference databaserefrnce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        firebaseauth = FirebaseAuth.getInstance();
        registername = findViewById(R.id.registername);
        registeremailid = findViewById(R.id.registeremailid);
        registerpassword = findViewById(R.id.registerpassword);
        registermobileno = findViewById(R.id.registermobileno);
        registerupiid = findViewById(R.id.registerupiid);
        registerscreenregisterbutton = findViewById(R.id.registerscreenregisterbutton);

        registerscreenregisterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = registername.getText().toString();
                final String useremail = registeremailid.getText().toString();
                final String userpassword = registerpassword.getText().toString();
                final String usermobilenumber = registermobileno.getText().toString();
                final String userupiid = registerupiid.getText().toString();
                register(username, useremail, userpassword, usermobilenumber, userupiid);
            }
        });
    }

    private void register(final String username, final String useremail, final String userpassword, final String usermobilenumber, final String userupiid) {
        firebaseauth.createUserWithEmailAndPassword(useremail, userpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser rUser = firebaseauth.getCurrentUser();
                    assert rUser != null;
                    String userId = rUser.getUid();
                    databaserefrnce = FirebaseDatabase.getInstance().getReference("User").child(userId);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("userid", userId);
                    hashMap.put("username", username);
                    hashMap.put("useremail", useremail);
                    hashMap.put("userpassword", userpassword);
                    hashMap.put("usermobilenumber", usermobilenumber);
                    hashMap.put("userupiid", userupiid);
                    databaserefrnce.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                rUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(getApplicationContext(),"Email Sent",Toast.LENGTH_LONG).show();
                                    }
                                });
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                Toast.makeText(RegisterActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(RegisterActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
