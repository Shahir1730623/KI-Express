package com.example.kiexpress.LoginSignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.kiexpress.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ForgetPassword extends AppCompatActivity {

    TextInputLayout phoneNumberTextField;
    DatabaseReference reference;
    boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        //Hooks
        phoneNumberTextField = findViewById(R.id.phoneNumberTextField);
        reference = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    private Boolean validatePhone() {
        String val = phoneNumberTextField.getEditText().getText().toString();
        if (val.isEmpty()) {
            phoneNumberTextField.setError("The field is empty");
            return false;
        }

        else if(val.length() != 11){
            phoneNumberTextField.setError("Wrong phone number");
            return false;
        }

        else {
            phoneNumberTextField.setError(null);
            phoneNumberTextField.setErrorEnabled(false);
            return true;
        }

    }

    public void goToVerifyOTPScreen(View view) {
        if(!validatePhone()){
            return;
        }

        String phoneNumber = "+88" + phoneNumberTextField.getEditText().getText().toString();
        Query checkUser = reference.orderByChild("phone").equalTo(phoneNumber);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    phoneNumberTextField.setError(null);
                    phoneNumberTextField.setErrorEnabled(false);
                    String phoneFromDB="";
                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                        phoneFromDB = snapshot1.child("phone").getValue().toString();
                    }

                    Intent intent = new Intent(ForgetPassword.this,VerifyOTP2.class);
                    intent.putExtra("phone",phoneFromDB);
                    startActivity(intent);

                }

                else {
                    phoneNumberTextField.setError("Phone Number does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ForgetPassword.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onBackPressed() {
        finish();
    }
}