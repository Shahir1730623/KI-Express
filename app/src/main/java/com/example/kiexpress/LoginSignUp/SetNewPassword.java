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

public class SetNewPassword extends AppCompatActivity {

    TextInputLayout newPasswordTextField,confirmPasswordTextField;
    String phoneNumber;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);

        //Hooks
        newPasswordTextField = findViewById(R.id.newPasswordTextField);
        confirmPasswordTextField = findViewById(R.id.confirmPasswordTextField);
        reference = FirebaseDatabase.getInstance().getReference().child("Users");

        //Get all the data from Intent
        phoneNumber = getIntent().getStringExtra("phoneNumber");
    }

    private Boolean validateNewPassword() {
        String val = newPasswordTextField.getEditText().getText().toString();
        String passwordVal = "^" +    // start-of-string
                "(?=.*[0-9])" +       // a digit must occur at least once
                "(?=.*[a-z])" +      // a lower case letter must occur at least once
                "(?=.*[A-Z])" +      // an upper case letter must occur at least once
                "(?=\\S+$)"   +      // no whitespace allowed in the entire string
                ".{4,}"       +     // anything, at least six places though
                "$";                 // end-of-string

        if (val.isEmpty()) {
            newPasswordTextField.setError("The field is empty");
            return false;
        }

        else if(!val.matches(passwordVal)){
            newPasswordTextField.setError("Password too weak");
            return false;
        }

        else {
            newPasswordTextField.setError(null);
            newPasswordTextField.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateConfirmPassword() {
        String val = confirmPasswordTextField.getEditText().getText().toString();
        String passwordVal = "^" +    // start-of-string
                "(?=.*[0-9])" +       // a digit must occur at least once
                "(?=.*[a-z])" +      // a lower case letter must occur at least once
                "(?=.*[A-Z])" +      // an upper case letter must occur at least once
                "(?=\\S+$)"   +      // no whitespace allowed in the entire string
                ".{4,}"       +     // anything, at least six places though
                "$";                 // end-of-string

        if (val.isEmpty()) {
            confirmPasswordTextField.setError("The field is empty");
            return false;
        }

        else if(!val.matches(passwordVal)){
            confirmPasswordTextField.setError("Password too weak");
            return false;
        }

        else {
            confirmPasswordTextField.setError(null);
            confirmPasswordTextField.setErrorEnabled(false);
            return true;
        }
    }

    private boolean checkPasswordMatch() {
        String newPassword = newPasswordTextField.getEditText().getText().toString();
        String confirmPassword = confirmPasswordTextField.getEditText().getText().toString();
        if(!(newPassword.equals(confirmPassword))){
            confirmPasswordTextField.setError("Password does not match");
            return false;
        }
        else{
            confirmPasswordTextField.setError(null);
            confirmPasswordTextField.setErrorEnabled(false);
            return true;
        }

    }


    public void ResetPassword(View view) {
        String newPassword = newPasswordTextField.getEditText().getText().toString();
        if(!validateNewPassword() || !validateConfirmPassword()){
            return;
        }

        if(!checkPasswordMatch()){
            return;
        }

        Query query = reference.orderByChild("phone").equalTo(phoneNumber);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        String key = snapshot1.getKey();
                        reference.child(key).child("password").setValue(newPassword);
                        Toast.makeText(SetNewPassword.this, "Password Updated Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SetNewPassword.this,Login.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }




}