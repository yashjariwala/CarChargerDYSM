package com.dysm.carchargerdysm;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import static com.dysm.carchargerdysm.R.id.forgetpasswordemailtext;


public class ForgotPassword extends MainActivity {
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth=FirebaseAuth.getInstance();
        setContentView(R.layout.forgotpassword);
        TextView emailidforgot = findViewById(forgetpasswordemailtext);
        Button resetpasswordbutton = findViewById(R.id.amount);
        resetpasswordbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailidstring = emailidforgot.getText().toString().trim();
                firebaseAuth.sendPasswordResetEmail(emailidstring).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Email Sent, " +
                                    "Please Check Your Mail For Further Details",Toast.LENGTH_LONG).show();
                            setContentView(R.layout.activity_main);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Email Id Not Found",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}
