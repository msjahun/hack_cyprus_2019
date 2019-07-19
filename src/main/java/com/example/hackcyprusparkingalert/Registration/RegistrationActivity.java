package com.example.hackcyprusparkingalert.Registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.hackcyprusparkingalert.R;
import com.example.hackcyprusparkingalert.SignIn.SignInActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RegistrationActivity extends AppCompatActivity {

    private static final String TAG = "RegistrationActivity";
    private ImageView imageViewCloseBtn;
    private Button btnCreateAccount;
    private Button btnSignInWithEmail;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();
        imageViewCloseBtn = (ImageView) findViewById(R.id.imageViewCloseIcon);
        btnCreateAccount = (Button) findViewById(R.id.btnCreateAccount);
        btnSignInWithEmail =(Button) findViewById(R.id.btnSignInWithEmail);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        progressDialog = new ProgressDialog(this);



        progressDialog.setMessage("Registering new user...");

        btnSignInWithEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( RegistrationActivity.this, SignInActivity.class));
                finish();
            }
        });

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                RegisterNewUser();

            }
        });

        imageViewCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }


    public void RegisterNewUser(){

        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();


        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){

        }else{
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();

                                int secondsDelayed = 1;
                                new Handler().postDelayed(new Runnable() {
                                    public void run() {
                                        progressDialog.dismiss();
                                        // startActivity(new Intent(RegistrationActivity.this, QrCodeActivity.class));

                                        finish();
                                    }
                                }, secondsDelayed * 2000);



                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegistrationActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }

                            // ...
                        }
                    });

        }


    }

}
