package com.dysm.carchargerdysm;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetDialog extends BottomSheetDialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottomlayout,container,false);

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        Button button = v.findViewById(R.id.enterupibutton);
        TextView upipin = v.findViewById(R.id.enterupitext) ;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (upipin.getText().toString().equals("0000")){
                    Toast.makeText(getContext(), "Transaction Sucessful", Toast.LENGTH_SHORT).show();
                    getDialog().dismiss();
                    Intent intent = new Intent(getContext() , startcharging.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getContext(),"UPI Pin Incorrect. Please Enter Your Valid Pin ", Toast.LENGTH_LONG).show();
                }
            }
        });
        return v;
    }

}
