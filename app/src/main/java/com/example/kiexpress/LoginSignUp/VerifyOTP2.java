package com.example.kiexpress.LoginSignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.example.kiexpress.ModelClasses.User;
import com.example.kiexpress.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class VerifyOTP2 extends AppCompatActivity {

    PinView otpFromUser;
    FirebaseAuth mAuth;
    PhoneAuthProvider.ForceResendingToken mResendToken;
    TextView otp_PhoneNumber;
    String codeBySystem,phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp2);

        //Hooks
        otpFromUser = findViewById(R.id.otpFromUser);
        mAuth = FirebaseAuth.getInstance();
        otp_PhoneNumber = findViewById(R.id.otp_PhoneNumber);

        //Get all the data from Intent
        phoneNumber = getIntent().getStringExtra("phone");

        //Extra
        otp_PhoneNumber.setText(phoneNumber);
        sentVerificationCodeToUser();
    }

    private void sentVerificationCodeToUser() {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)                // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                      // Activity (for callback binding)
                        .setCallbacks(mCallbacks)               // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                public void onCodeSent(@NonNull String verificationId,
                                       @NonNull PhoneAuthProvider.ForceResendingToken token) {
                    // The SMS verification code has been sent to the provided phone number, we
                    // now need to ask the user to enter the code and then construct a credential
                    // by combining the code with a verification ID.

                    // Save verification ID and resending token so we can use them later
                    codeBySystem = verificationId;
                    mResendToken = token;
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    signInWithPhoneAuthCredential(phoneAuthCredential);
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(VerifyOTP2.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            };


    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem, code);;
        signInWithPhoneAuthCredential(credential);
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(VerifyOTP2.this, "Verification Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(VerifyOTP2.this,SetNewPassword.class);
                            intent.putExtra("phoneNumber",phoneNumber);
                            startActivity(intent);

                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(VerifyOTP2.this, "Verification Not Completed! Try again.", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                });
    }


    public void verifyBtnOnClick(View view) {
        String code= otpFromUser.getText().toString();
        if (!code.isEmpty()){
            verifyCode(code);
        }
    }

    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }


    public void backBtn(View view) {
        finish();
    }
}